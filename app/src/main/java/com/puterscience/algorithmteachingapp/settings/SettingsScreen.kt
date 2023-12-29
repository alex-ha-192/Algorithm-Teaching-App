package com.puterscience.algorithmteachingapp.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.sp
import com.puterscience.algorithmteachingapp.database.settings_db.SettingsDataObject
import com.puterscience.algorithmteachingapp.database.settings_db.settingsDao
import com.puterscience.algorithmteachingapp.functions.constructSettingsString
import com.puterscience.algorithmteachingapp.settings.settings_classes.ColourMode
import com.puterscience.algorithmteachingapp.settings.settings_classes.Defaults
import com.puterscience.algorithmteachingapp.settings.settings_classes.Settings

@Composable
fun SettingsScreen(globalSettings: Settings, defaults: Defaults, allColourModes: List<ColourMode>, firstTimeFlag: MutableState<Boolean>, settingsDao: settingsDao) {
    val currentColourBlindMode: MutableState<String> = remember {mutableStateOf("")}
    if (globalSettings.colourMode.value == allColourModes[0]){
        currentColourBlindMode.value = "Standard"
    }
    if(globalSettings.colourMode.value == allColourModes[1]){
        currentColourBlindMode.value = "P/D"
    }
    if(globalSettings.colourMode.value == allColourModes[2]){
        currentColourBlindMode.value = "Tri"
    }
    if(globalSettings.colourMode.value == allColourModes[3]){
        currentColourBlindMode.value = "Mono"
    }

    Box {
        Column {
            Text(text = "Text size:", fontSize = if (globalSettings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp)
            Button(onClick = {(globalSettings.largeText.value) = !(globalSettings.largeText.value)}) {
                Text(
                    text = (if (globalSettings.largeText.value) "Disable large text" else "Enable large text"),
                    fontSize = (if (globalSettings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp)
                )
            }
            Divider()
            Text(text = "Select colour mode:", fontSize = if (globalSettings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp)
            Column {
                // Default colour mode
                Button(onClick = { globalSettings.colourMode.value = allColourModes[0]}) {
                    Text(
                        text = "Standard vision",
                        fontSize = if (globalSettings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp
                    )
                }
                // Protanopia
                Button(onClick = { globalSettings.colourMode.value = allColourModes[1]}) {
                    Text(
                        text = "Protanopia/Deuteranopia",
                        fontSize = if (globalSettings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp
                    )
                }

                // Tritanopia
                Button(onClick = { globalSettings.colourMode.value = allColourModes[2] }) {
                    Text(
                        text = "Tritanopia",
                        fontSize = if (globalSettings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp
                    )
                }

                // Monochromacy
                Button(onClick = { globalSettings.colourMode.value = allColourModes[3] }) {
                    Text(text = "Monochromacy",
                        fontSize = if (globalSettings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp)
                }
                Text(text = "Current colour mode: " +currentColourBlindMode.value, fontSize = if (globalSettings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp)
            }
            Divider()
            Column {
                Button(onClick = {
                    firstTimeFlag.value = false
                    if (settingsDao.getSettings().size != 0) {
                        settingsDao.updateSettings(SettingsDataObject(1, constructSettingsString(globalSettings, allColourModes)))
                    }
                    else {
                        settingsDao.addSettings(SettingsDataObject(1, constructSettingsString(globalSettings, allColourModes)))
                    }
                }) {
                    Text(text = "Save settings", fontSize = if (globalSettings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp)
                }
                Text(text = if (firstTimeFlag.value) "No settings saved!" else "", fontSize = if (globalSettings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp )
            }
        }
    }
}