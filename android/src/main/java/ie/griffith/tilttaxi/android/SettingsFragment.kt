package ie.griffith.tilttaxi.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import ie.griffith.tilttaxi.R
import kotlin.math.roundToInt

class SettingsFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val settings = SettingsStore(requireContext())
        val initialUseTilt = settings.useTilt
        val initialSensitivity = settings.sensitivityLevel
        val initialFlipPedals = settings.flipPedals

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent{
                MaterialTheme{
                    SettingsScreen(
                        initialUseTilt = initialUseTilt,
                        initialSensitivity = initialSensitivity,
                        initialFlipPedals = initialFlipPedals,
                        onSave = {useTilt, sensitivity, flipPedals ->
                            settings.save(
                                useTilt = useTilt,
                                sensitivityLevel = sensitivity,
                                flipPedals = flipPedals
                            )
                            parentFragmentManager.popBackStack()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsScreen(
    initialUseTilt: Boolean,
    initialSensitivity: Int,
    initialFlipPedals: Boolean,
    onSave: (Boolean, Int, Boolean) -> Unit
){
    var useTilt by remember {mutableStateOf(initialUseTilt)}
    var sensitivity by remember {mutableIntStateOf(initialSensitivity)}
    var flipPedals by remember {mutableStateOf(initialFlipPedals)}
    val sensitivityLabels = stringArrayResource(R.array.settings_sensitivity_options)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF4F8FC),
        contentColor = Color(0xFF172C45)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Text(
                text = stringResource(R.string.settings_title),
                style = MaterialTheme.typography.headlineMedium
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column{
                    Text(
                        text = stringResource(R.string.settings_steering),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Row(
                        modifier = Modifier.selectableGroup(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ){
                        FilterChip(
                            selected = useTilt,
                            onClick = { useTilt = true },
                            label = {Text(stringResource(R.string.settings_tilt))}
                        )
                        FilterChip(
                            selected = !useTilt,
                            onClick = {useTilt = false},
                            label = { Text(stringResource(R.string.settings_touch))}
                        )
                    }
                }

                Column{
                    Text(
                        text = stringResource(R.string.settings_sensitivity),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(text = sensitivityLabels[sensitivity])

                    Slider(
                        value = sensitivity.toFloat(),
                        onValueChange = { sensitivity = it.roundToInt()},
                        valueRange = 0f..2f,
                        steps = 1
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 48.dp)
                        .toggleable(
                            value = flipPedals,
                            role = Role.Switch,
                            onValueChange = {flipPedals = it}
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = stringResource(R.string.settings_flip),
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 12.dp)
                    )
                    Switch(checked = flipPedals, onCheckedChange = null)
                }
            }
            Button(onClick = {onSave(useTilt, sensitivity, flipPedals)}){
                Text(stringResource(R.string.settings_save))
            }
        }
    }
}
