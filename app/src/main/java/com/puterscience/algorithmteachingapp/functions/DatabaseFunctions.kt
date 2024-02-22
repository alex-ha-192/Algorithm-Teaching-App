package com.puterscience.algorithmteachingapp.functions

import com.puterscience.algorithmteachingapp.database.dataset_db.Dataset
import com.puterscience.algorithmteachingapp.database.dataset_db.DatasetDao
import com.puterscience.algorithmteachingapp.settings.settings_classes.BasicSettings
import com.puterscience.algorithmteachingapp.settings.settings_classes.ColourMode

fun saveToDb(db: DatasetDao, toSave: List<Int>, name: String): Boolean{
    // get current datasets
    val currentList = db.getAll()
    // get a valid PK
    val lastOid = if (currentList.isNotEmpty()) currentList[currentList.size-1].oid else 0
    val toUseOid = lastOid+1
    val listToString: StringBuilder = java.lang.StringBuilder() // this is a java function for some reason
    for (element in toSave) {
        listToString.append("${element}_") // build the string element by element
    }
    return if (name !in db.getNames() && name != "") // Name must be unique and must not be null
    {
        listToString.removeSuffix("_") // remove final underscore
        val toSaveDataset =
            Dataset(toUseOid, name, listToString.deleteAt(listToString.length - 1).toString(), 0)
        db.addDataset(toSaveDataset)
        true
    }
    else {
        false
    }
}

// settings are treated differently to datasets so they need their own function
fun loadSettings(str: String, colourModes: List<ColourMode>): BasicSettings?{ // allow nullable return in case of error
    val intermediary: List<String> = str.split("_")
    val largeTextFlag: Boolean
    val clrMode: ColourMode
    val maxSize: Int

    if (intermediary.size != 3) {
        // something has gone wrong, return nothing
        return null
    }
    else {
        // process array to generate settings mode
        largeTextFlag = intermediary[0] != "0"

        clrMode = if (intermediary[1].uppercase() == "S") {
            colourModes[0]
        } else if (intermediary[1].uppercase() == "P") {
            colourModes[1]
        } else if (intermediary[1].uppercase() == "T") {
            colourModes[2]
        } else if (intermediary[1].uppercase() == "M") {
            colourModes[3]
        } else {
            colourModes[0]
        }

         maxSize = intermediary[2].toInt()

        return BasicSettings(largeTextFlag, clrMode, maxSize)
    }
}

fun constructSettingsString(settings: com.puterscience.algorithmteachingapp.settings.settings_classes.Settings, colourModes: List<ColourMode>): String {
    val string: StringBuilder = java.lang.StringBuilder()
    string.append(if (settings.largeText.value) "1_" else "0_")
    string.append( // build up slowly
        when (settings.colourMode.value) {
            colourModes[0] -> {
                "S_"
            }
            colourModes[1] -> {
                "P_"
            }
            colourModes[2] -> {
                "T_"
            }
            colourModes[3] -> {
                "M_"
            }
            else -> {
                "S_"
            }
        }
    )
    string.append(settings.maxSize.intValue.toString())
    return string.toString()
}