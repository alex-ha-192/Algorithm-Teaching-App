package com.puterscience.algorithmteachingapp.algo_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.puterscience.algorithmteachingapp.database.dataset_db.DatasetDatabase
import com.puterscience.algorithmteachingapp.settings.settings_classes.ColourMode
import com.puterscience.algorithmteachingapp.settings.settings_classes.Defaults
import com.puterscience.algorithmteachingapp.settings.settings_classes.Settings
import com.puterscience.algorithmteachingapp.functions.addHandler
import com.puterscience.algorithmteachingapp.functions.algorithms.linearSearch
import com.puterscience.algorithmteachingapp.functions.removeHandler
import com.puterscience.algorithmteachingapp.functions.resetHandler
import com.puterscience.algorithmteachingapp.functions.saveToDb

@OptIn(ExperimentalMaterial3Api::class) // need to do this for material3
@Composable
fun LinearSearchScreen(toSort: List<Int>, settings: Settings, defaults: Defaults, colourMode: ColourMode, db: DatasetDatabase) {
    // init variables
    val mutItems = remember { mutableStateListOf<Int>().apply { addAll(toSort) } }
    var initialState: List<Int> = toSort // what to reset back to
    val lock = remember { mutableStateOf(false) }
    val textToggle = remember { mutableStateOf(true)}
    val explanationText = remember {mutableStateOf("")}
    val showLoadDialog = remember { mutableStateOf(false) }
    val toUseName = remember {mutableStateOf("")}

    // Button variables
    val toSearch = remember { mutableIntStateOf(0) }
    val iState = remember { mutableIntStateOf(0) }
    val searchFinished = remember {mutableStateOf(false)}
    val mutColors = remember { mutableStateListOf<Color>()}

    // init colours
    val colorsToApply: MutableList<Color> = mutableListOf()
    for (i in 0 until mutItems.size) {
        mutColors.add(colourMode.defaultColour)
        colorsToApply.add(colourMode.defaultColour)
    }
    // get db
    val daoElements = db.datasetDao().getAll()
    if (showLoadDialog.value) { // only show load dialog if showLoadDialog
        Dialog(onDismissRequest = { showLoadDialog.value = false }) { // dismissRequest is just clicking away or hw/sw back button
            Column(
                Modifier
                    .background(Color.White) // add white bg
                    .verticalScroll(rememberScrollState())) { // scrollable
                daoElements.forEachIndexed { _, i -> // lambdas are just faster for this use case
                    Row {
                        // parse into comprehensible output
                        Text(text = i.name + ": ")
                        Text(text = i.listInts.split("_").toList().toString())
                        IconButton(onClick = {
                            // parse db entry into new array and load it
                            mutItems.removeAll(mutItems)
                            val intermediary: List<String> = i.listInts.split("_")
                            val intermediaryList: MutableList<Int> = mutableListOf()
                            for (item in intermediary) {
                                intermediaryList.add(item.toInt())
                            }
                            mutItems.removeAll(mutItems)
                            mutItems.addAll(intermediaryList)
                            initialState = intermediaryList.toList()
                            explanationText.value = "Loaded!"
                            showLoadDialog.value = false
                        }) {
                            Icon(
                                imageVector = Icons.Filled.PlayArrow,
                                contentDescription = "Use"
                            )
                        }
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0))
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
        ) {
            Row(modifier = Modifier.weight(1f)) {
                Divider(modifier = Modifier
                    .padding(2.dp)
                    .width(0.dp), color = Color.White)
                Column (modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()) // scrollable dataset!
                ) {
                    Row (verticalAlignment = Alignment.CenterVertically){ // tosearch goes on top of main dataset
                        IconButton(
                            onClick = { if (!lock.value) toSearch.intValue-- },
                            modifier = Modifier
                                .width(24.dp)
                                .alpha(if (!lock.value) 1f else 0f)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowDown,
                                contentDescription = "Reduce",
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(2 / 3f)
                                .background(if (!lock.value) colourMode.defaultColour else colourMode.lockedColour)
                        )
                        {
                            Text(
                                text = toSearch.intValue.toString(),
                                color = Color.White,
                                modifier = Modifier
                                    .height(IntrinsicSize.Min)
                                    .align(Center),
                                fontSize = (if (settings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp)
                            )
                        }
                        IconButton(
                            onClick = { if (!lock.value) toSearch.intValue++ },
                            modifier = Modifier
                                .width(24.dp)
                                .alpha(if (!lock.value) 1f else 0f)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowUp,
                                contentDescription = "Increase",
                            )
                        }
                    }
                    Divider()
                    mutItems.forEachIndexed { index, i -> // this is so much faster than a for loop
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = {if (!lock.value) mutItems[index]--}, modifier = Modifier // decrement element
                                .width(24.dp)
                                .alpha(if (!lock.value) 1f else 0f)) {
                                Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = "Reduce")
                            }
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .background(mutColors[index])
                            )
                            {
                                Text( // text of element
                                    text = i.toString(),
                                    modifier = Modifier.align(Center),
                                    fontSize = (if (settings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp),
                                    color = Color.White
                                )
                            }
                            IconButton(onClick = {if (!lock.value) mutItems[index]++}, modifier = Modifier // increment element
                                .width(24.dp)
                                .alpha(if (!lock.value) 1f else 0f)) {
                                Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = "Increase")
                            }
                        }
                    }
                }
                Divider(modifier = Modifier
                    .alpha(0f)
                    .width(12.dp))
                Column (modifier = Modifier
                    .weight(2f)
                    .alpha(1f)
                    .verticalScroll(rememberScrollState())) {
                    Text(text = explanationText.value, fontSize = (if (settings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp))
                    Divider(modifier = Modifier.padding(16.dp))
                    Text(text = ( // just hard code the pseudocode, there's no point in making anything but opacity dynamic
                            "1. for i in len(array):" +
                                    "\n2.    if toFind = array[i] return i" +
                                    "\n3. return -1"
                            ), fontSize = (if (settings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp),modifier = Modifier.alpha(if (textToggle.value) 1f else 0f))
                }
            }
            Row {
                Button(onClick = { if (!lock.value) showLoadDialog.value = true }) {
                    Text(text = "Load")
                }
                Button(onClick = { if (saveToDb(db.datasetDao(), mutItems.toList(), toUseName.value))
                explanationText.value = "Saved!" else explanationText.value = "Use a unique name."}) {
                    Text(text = "Save")
                }
                OutlinedTextField( // enter dataset name, need to use Back button to unfocus
                    value = toUseName.value,
                    onValueChange = { toUseName.value = it },
                    label = { Text(text = "Dataset name")}
                )
            }
            LargeFloatingActionButton(onClick = { if(!lock.value) {
                lock.value = true // just lock
            }
                linearSearch(mutItems, explanationText, toSearch, iState, searchFinished, mutColors, colorsToApply, colourMode)
                }, modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f)) {
                Text(text = "Linear search (will lock values)", fontSize = if (settings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp)
            }
            Divider(modifier = Modifier.padding(all = 8.dp))
            Row (modifier = Modifier.align(alignment = Alignment.CenterHorizontally)) {
                IconButton(onClick = { // reset
                    resetHandler(colorsToApply, mutColors, mutItems, initialState, finishedSearch = searchFinished, explanationText, iState, lock)
                }) {
                    Icon(imageVector = Icons.Filled.Refresh, contentDescription = "Reset")
                }
                IconButton(onClick = { if (!lock.value) addHandler(mutItems, settings.maxSize.intValue) }, modifier = Modifier.background(color = if (mutItems.size > settings.maxSize.intValue) colourMode.lockedColour else colourMode.defaultColour)) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Add element")
                }
                IconButton(onClick = { if (!lock.value) removeHandler(mutItems) }, modifier = Modifier.background(color = if (mutItems.size <= 1) colourMode.lockedColour else colourMode.defaultColour)) {
                    Icon(imageVector = Icons.Filled.Clear, contentDescription = "Remove element")
                }
                Button(onClick = { textToggle.value = !textToggle.value }) {
                    Text(text = (if (textToggle.value) "Text Off" else "Text On"), fontSize = if (settings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp)
                }
            }
        }
    }
}