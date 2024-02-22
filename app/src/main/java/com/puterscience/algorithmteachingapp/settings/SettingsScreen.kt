package com.puterscience.algorithmteachingapp.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
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
    // justSaved determines whether or not to show the user the "Saved!" message, start at false
    val justSaved: MutableState<Boolean> = remember { mutableStateOf(false)}
    // load current colour mode
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
            Button(onClick = { justSaved.value = false // text size toggle
                (globalSettings.largeText.value) = !(globalSettings.largeText.value)}) {
                Text(
                    text = (if (globalSettings.largeText.value) "Disable large text" else "Enable large text"),
                    fontSize = (if (globalSettings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp)
                )
            }
            Divider()
            Text(text = "Select colour mode:", fontSize = if (globalSettings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp)
            Column {
                // Default colour mode
                Button(onClick = { justSaved.value = false
                    globalSettings.colourMode.value = allColourModes[0] }) {
                    Text(
                        text = "Standard vision",
                        fontSize = if (globalSettings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp
                    )
                }
                // Protanopia
                Button(onClick = { justSaved.value = false
                    globalSettings.colourMode.value = allColourModes[1] }) {
                    Text(
                        text = "Protanopia/Deuteranopia",
                        fontSize = if (globalSettings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp
                    )
                }

                // Tritanopia
                Button(onClick = { justSaved.value = false
                    globalSettings.colourMode.value = allColourModes[2] }) {
                    Text(
                        text = "Tritanopia",
                        fontSize = if (globalSettings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp
                    )
                }

                // Monochromacy
                Button(onClick = { justSaved.value = false
                    globalSettings.colourMode.value = allColourModes[3] }) {
                    Text(
                        text = "Monochromacy",
                        fontSize = if (globalSettings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp
                    )
                }
                Text(
                    text = "Current colour mode: " + currentColourBlindMode.value,
                    fontSize = if (globalSettings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp
                )
                Divider()
                Column {
                    Text(
                        text = ("Maximum size of dataset: " + globalSettings.maxSize.intValue.toString()),
                        fontSize = if (globalSettings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp
                    )
                    Row {
                        Button(onClick = { justSaved.value = false
                            if (globalSettings.maxSize.intValue > 1) globalSettings.maxSize.intValue-- }) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowDown,
                                contentDescription = "Reduce maxSize"
                            )
                        }
                        Button(onClick =
                        { justSaved.value = false
                            globalSettings.maxSize.intValue++ }) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowUp,
                                contentDescription = "Increase maxSize"
                            )
                        }
                    }
                }
                Divider()
                Column {
                    Button(onClick = {
                        firstTimeFlag.value = false
                        justSaved.value = true
                        if (settingsDao.getSettings().isNotEmpty()) { // only attempt to update settings if an entry already exists
                            settingsDao.updateSettings(
                                SettingsDataObject(
                                    1,
                                    constructSettingsString(globalSettings, allColourModes)
                                )
                            )
                        } else {
                            settingsDao.addSettings( // add a new settings object if none exist
                                SettingsDataObject(
                                    1,
                                    constructSettingsString(globalSettings, allColourModes)
                                )
                            )
                        }
                    }) {
                        Text(
                            text = "Save settings",
                            fontSize = if (globalSettings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp
                        )
                    }
                    Text( // this should notify the user if they've just saved
                        text = if (firstTimeFlag.value) "No settings saved!" else if (!justSaved.value)"" else "Saved successfully!",
                        fontSize = if (globalSettings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp
                    )
                }
            }
        }
    }
}