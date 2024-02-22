package com.puterscience.algorithmteachingapp.database

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.puterscience.algorithmteachingapp.database.dataset_db.Dataset
import com.puterscience.algorithmteachingapp.database.dataset_db.DatasetDatabase
import com.puterscience.algorithmteachingapp.settings.settings_classes.Defaults
import com.puterscience.algorithmteachingapp.settings.settings_classes.Settings

@Composable
fun DatabaseScreen(globalSettings: Settings, defaults: Defaults, database: DatasetDatabase) {
    // get dataset dao and get the elements from it
    val dao = database.datasetDao()
    val daoSorted = dao.getAll()
    // get a list to display
    val allElements: MutableList<Dataset> = remember { mutableStateListOf<Dataset>().apply { addAll(daoSorted) }}
    // unused var, was going to be important when datasets could be added
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE") var currentOid = if (daoSorted.isNotEmpty()) daoSorted[daoSorted.size-1].oid else 0
    Box {
        // Create UI
        Column(Modifier.verticalScroll(rememberScrollState())) {
            allElements.forEachIndexed { _, i ->
                Row {
                    //Text(text = i.oid.toString())
                    Text(text = i.name + ": " + i.listInts.split("_").toList().toString(),
                        fontSize = if (globalSettings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp)
                    IconButton(onClick = { dao.removeDataset(i)
                    allElements.remove(i)}) {
                        Icon(imageVector = Icons.Filled.Clear, contentDescription = "Remove")
                    }
                }
            }
            Row {
                /* This was just for testing
                Button(onClick = { currentOid++
                    allElements.add(Dataset(currentOid, "Example", "1_2_3_4_5", 0))
                    dao.addDataset(Dataset(currentOid, "Example", "1_2_3_4_5", 0)) }) {
                    Text(text = "Add")
                }
                 */
                Button(onClick = { for (element in dao.getAll()) {
                    // remove from db
                    dao.removeDataset(element)
                    // remove from displayed list
                    allElements.removeAll(allElements)
                    currentOid = 0
                }
                }) {
                    Text(text = "Clear all", fontSize = if (globalSettings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp)
                }
                /* this was removed, no need to be picky with list
                Button(
                    onClick = {
                        if (sortOrder != SortOrder.DESC) {
                            allElements.sortByDescending { it.oid }
                            sortOrder = SortOrder.DESC
                        } else {
                            allElements.sortBy { it.oid }
                            sortOrder = SortOrder.ASC
                        }
                    }
                )
                {
                    Text(text = "Srt")
                }
                 */
            }
        }
    }
}