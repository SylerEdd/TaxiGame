package ie.griffith.tilttaxi.android

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import ie.griffith.tilttaxi.R


@Composable
internal fun DifficultyScreen(
    maps: Array<String>,
    trafficOptions: Array<String>,
    initialMap: Int,
    initialTraffic: Int,
    onSave: (Int, Int) -> Unit
){
    var selectedMap by remember { mutableIntStateOf(initialMap) }
    var selectedTraffic by remember { mutableIntStateOf(initialTraffic) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF4F8FC),
        contentColor = Color(0xFF172C45)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Text(
                text = stringResource(R.string.difficulty_title),
                style = MaterialTheme.typography.headlineMedium
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ){
                DifficultyOptions(
                    title = stringResource(R.string.difficulty_map),
                    options = maps,
                    selectedIndex = selectedMap,
                    onSelect = { selectedMap = it }
                )

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)){
                    Text(
                        text = stringResource(R.string.difficulty_car),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(stringResource(R.string.difficulty_standard_taxi))
                    Text(stringResource(R.string.difficulty_fast_taxi_locked))
                }

                DifficultyOptions(
                    title = stringResource(R.string.difficulty_traffic),
                    options = trafficOptions,
                    selectedIndex = selectedTraffic,
                    onSelect = { selectedTraffic = it}
                )
            }

            Button(onClick = { onSave(selectedMap, selectedTraffic)}){
                Text(stringResource(R.string.difficulty_save))
            }
        }
    }
}

@Composable
private fun DifficultyOptions(
    title: String,
    options: Array<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit
){
    Column {
        Text(text = title, style = MaterialTheme.typography.titleMedium)

        Column(modifier = Modifier.selectableGroup()){
            options.forEachIndexed { index, label ->
                val isSelected = index == selectedIndex

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 48.dp)
                        .selectable(
                            selected = isSelected,
                            onClick = {onSelect(index)},
                            role = Role.RadioButton
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    RadioButton(selected = isSelected, onClick = null)
                    Text(text = label, modifier = Modifier.padding(start = 12.dp))
                }
            }
        }
    }
}
