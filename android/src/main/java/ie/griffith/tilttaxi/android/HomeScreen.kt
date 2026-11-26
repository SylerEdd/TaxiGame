package ie.griffith.tilttaxi.android

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ie.griffith.tilttaxi.R

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
