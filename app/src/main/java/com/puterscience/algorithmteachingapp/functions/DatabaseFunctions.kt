package com.puterscience.algorithmteachingapp.functions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Dialog
import com.puterscience.algorithmteachingapp.database.db_classes.Dataset
import com.puterscience.algorithmteachingapp.database.db_classes.DatasetDao

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