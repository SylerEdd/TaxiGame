package ie.griffith.tilttaxi.android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ie.griffith.tilttaxi.R

private const val HOME = "home"
private const val DIFFICULTY = "difficulty"
private const val SETTINGS = "settings"

class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MaterialTheme{
                TaxiNavigation(
                    onPlay = {
                        startActivity(Intent(this, AndroidLauncher::class.java))
                    }
                )
            }
        }
    }
}

@Composable
private fun TaxiNavigation(onPlay: () -> Unit){
    val navController = rememberNavController()
    val context = LocalContext.current
    val settings = remember(context){
        SettingsStore(context)
    }
    val preferences = remember(context){
        context.getSharedPreferences("difficulty", Context.MODE_PRIVATE)
    }

    val maps = stringArrayResource(R.array.map_options)
    val trafficOptions = stringArrayResource(R.array.traffic_options)

    var mapIndex by remember {
        mutableIntStateOf(preferences.getInt("map", 0).coerceIn(maps.indices))
    }
    var trafficIndex by remember{
        mutableIntStateOf(
            preferences.getInt("traffic", 0).coerceIn(trafficOptions.indices)
        )
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF4F8FC)){
        NavHost(
            navController = navController,
            startDestination = HOME,
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
        ){
            composable(HOME) {
                HomeScreen(
                    selection = stringResource(
                        R.string.home_selection_format,
                        maps[mapIndex],
                        stringResource(R.string.difficulty_standard_taxi),
                        trafficOptions[trafficIndex]
                    ),
                    onPlay = onPlay,
                    onDifficulty = {navController.navigate(DIFFICULTY)},
                    onSettings = {navController.navigate(SETTINGS)}
                )
            }
            composable(DIFFICULTY){
                DifficultyScreen(
                    maps = maps,
                    trafficOptions = trafficOptions,
                    initialMap = mapIndex,
                    initialTraffic = trafficIndex,
                    onSave = {map, traffic ->
                        preferences.edit()
                            .putInt("map", map)
                            .putInt("traffic", traffic)
                            .apply()
                        mapIndex = map
                        trafficIndex = traffic
                        navController.popBackStack()
                    }
                )
            }

            composable(SETTINGS){
                SettingsScreen(
                    initialUseTilt = settings.useTilt,
                    initialSensitivity = settings.sensitivityLevel,
                    initialFlipPedals = settings.flipPedals,
                    onSave = {useTilt, sensitivity, flipPedals ->
                        settings.save(
                            useTilt = useTilt,
                            sensitivityLevel = sensitivity,
                            flipPedals = flipPedals
                        )
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
