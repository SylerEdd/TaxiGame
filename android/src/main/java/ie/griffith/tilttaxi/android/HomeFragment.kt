package ie.griffith.tilttaxi.android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import ie.griffith.tilttaxi.R

class HomeFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val preferences = requireContext()
            .getSharedPreferences("difficulty", Context.MODE_PRIVATE)

        val maps = resources.getStringArray(R.array.map_options)
        val trafficOptions = resources.getStringArray(R.array.traffic_options)

        val map = maps[preferences.getInt("map", 0).coerceIn(maps.indices)]
        val traffic = trafficOptions[preferences.getInt("traffic", 0).coerceIn(trafficOptions.indices)]

        val selection = getString(R.string.home_selection_format, map, getString(R.string.difficulty_standard_taxi), traffic)

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                MaterialTheme{
                    HomeScreen(
                        selection = selection,
                        onPlay = {
                            startActivity(Intent(requireContext(), AndroidLauncher::class.java))
                        },
                        onDifficulty = { openScreen(DifficultyFragment())},
                        onSettings = {openScreen(SettingsFragment())}
                    )
                }
            }
        }
    }

    private fun openScreen(screen: Fragment){
        parentFragmentManager.beginTransaction()
            .replace(R.id.screenContainer, screen)
            .addToBackStack(null)
            .commit()
    }
}

@Composable
internal fun HomeScreen(
    selection: String,
    onPlay: () -> Unit,
    onDifficulty: () -> Unit,
    onSettings: () -> Unit
){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF4F8FC),
        contentColor = Color(0xFF172C45)
    ){
        Box(contentAlignment = Alignment.Center){
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Text(
                    text = stringResource(R.string.home_title),
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = selection,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = onPlay, modifier = Modifier.width(220.dp)){
                    Text(stringResource(R.string.home_play))
                }
                OutlinedButton(onClick = onDifficulty, modifier = Modifier.width(220.dp)) {
                    Text(stringResource(R.string.difficulty_title))
                }
                OutlinedButton(onClick = onSettings, modifier = Modifier.width(220.dp)) {
                    Text(stringResource(R.string.settings_title))
                }
            }
        }
    }
}
