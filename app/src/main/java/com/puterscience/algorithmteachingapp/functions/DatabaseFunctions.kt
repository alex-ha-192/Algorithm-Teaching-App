package com.puterscience.algorithmteachingapp.functions

import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Dialog
import com.puterscience.algorithmteachingapp.database.db_classes.Dataset
import com.puterscience.algorithmteachingapp.database.db_classes.DatasetDao
import com.puterscience.algorithmteachingapp.settings.settings_classes.BasicSettings
import com.puterscience.algorithmteachingapp.settings.settings_classes.ColourMode

fun saveToDb(db: DatasetDao, toSave: List<Int>, name: String): Boolean{
    val currentList = db.getAll()
    val lastOid = if (currentList.isNotEmpty()) currentList[currentList.size-1].oid else 0
    val toUseOid = lastOid+1
    val listToString: StringBuilder = java.lang.StringBuilder()
    for (element in toSave) {
        listToString.append("${element.toString()}_")
    }
    if (!(name in db.getNames()) && name != "") // Name must be unique and must not be null
        {
        listToString.removeSuffix("_")
        val toSaveDataset: Dataset =
            Dataset(toUseOid, name, listToString.deleteAt(listToString.length - 1).toString(), 0)
        db.addDataset(toSaveDataset)
        return true
    }
    else {
        return false
    }
}

@Composable
fun loadFromDb(db: DatasetDao, initialState: List<Int>): List<Int> {
    val showDialog = remember { mutableStateOf<Boolean>(true)}
    val daoElements = db.getAll()
    var toUseList = mutableListOf<Int>()
    if (showDialog.value){
        Dialog(onDismissRequest = {showDialog.value = false}) {
            Column {
                daoElements.forEachIndexed { _, i ->
                    Row {
                        Text(text = i.name + ": ")
                        Text(text = i.listInts)
                        IconButton(onClick = {
                            toUseList.removeAll(toUseList)
                            val intermediary: List<String> = i.listInts.split("_")
                            for (item in intermediary) {
                                toUseList.add(item.toInt())
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.PlayArrow,
                                contentDescription = "Remove"
                            )
                        }
                    }
                }
                Button(onClick = { showDialog.value = false }) {
                    Text(text = "Continue")
                }
            }
        }
    }
    if (!showDialog.value) {
        if (toUseList.isEmpty()) {
            return initialState
        } else {
            return toUseList
        }
    }
    return listOf(5, 4, 3, 2, 1)
}

fun loadSettings(str: String, colourModes: List<ColourMode>): BasicSettings?{
    val intermediary: List<String> = str.split("_")
    var largeTextFlag: Boolean = false
    var clrMode: ColourMode = colourModes[0]
    var maxSize: Int = 5
    if (intermediary.size != 3) {
        // something has gone wrong, return a default settings
        return null
    }
    else {
        // process array
        largeTextFlag = intermediary[0] != "0"

        if (intermediary[1].uppercase() == "S") {
             clrMode = colourModes[0]
        }
        else if (intermediary[1].uppercase() == "P") {
             clrMode = colourModes[1]
        }
        else if (intermediary[1].uppercase() == "T") {
             clrMode = colourModes[2]
        }
        else if (intermediary[1].uppercase() == "M") {
             clrMode = colourModes[3]
        }
        else {
             clrMode = colourModes[0]
        }

         maxSize = intermediary[2].toInt()

        return BasicSettings(largeTextFlag, clrMode, maxSize)
    }
}

fun constructSettingsString(settings: com.puterscience.algorithmteachingapp.settings.settings_classes.Settings, colourModes: List<ColourMode>): String {
    val string: StringBuilder = java.lang.StringBuilder()
    string.append(if (settings.largeText.value) "1_" else "0_")
    string.append(
        if (settings.colourMode.value == colourModes[0]) {
            "S_"
        }
        else if (settings.colourMode.value == colourModes[1]){
            "P_"
        }
        else if (settings.colourMode.value == colourModes[2]) {
            "T_"
        }
        else if (settings.colourMode.value == colourModes[3]) {
            "M_"
        }
        else{
            "S_"
        }
    )
    string.append(settings.maxSize.value.toString())
    return string.toString()
}