package com.puterscience.algorithmteachingapp.algo_screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import com.puterscience.algorithmteachingapp.functions.algorithms.bubbleSort
import com.puterscience.algorithmteachingapp.functions.removeHandler
import com.puterscience.algorithmteachingapp.functions.resetHandler
import com.puterscience.algorithmteachingapp.functions.saveToDb

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun BubbleSortScreen(toSort: List<Int>, settings: Settings, defaults: Defaults, colourMode: ColourMode, db: DatasetDatabase) {
    // initialise variables
    val mutItems = remember { mutableStateListOf<Int>().apply { addAll(toSort) } }
    var initialState: List<Int> = toSort
    val lock = remember { mutableStateOf(false) }
    val textToggle = remember { mutableStateOf(true)}
    val explanationText = remember {mutableStateOf("")}
    val showLoadDialog = remember { mutableStateOf(false) }
    val toUseName = remember {mutableStateOf("")}


    // Colors
    val defaultColors = mutableListOf<Color>()
    val actualColors = remember {
        mutableStateListOf<Color>()
    }
    val lockedColors = mutableListOf<Color>()

    for (i in 0 until mutItems.size) {
        defaultColors.add(colourMode.defaultColour)
        actualColors.add(colourMode.defaultColour)
        lockedColors.add(colourMode.lockedColour)
    }
    val daoElements = db.datasetDao().getAll() // get elements from db
    if (showLoadDialog.value) {
        Dialog(onDismissRequest = { showLoadDialog.value = false }) { // use a dialog for load
            Column(
                Modifier
                    .background(Color.White)
                    .verticalScroll(rememberScrollState())) { // scrollable!
                daoElements.forEachIndexed { _, i ->
                    Row {
                        // parse string to display
                        Text(text = i.name + ": ")
                        Text(text = i.listInts.split("_").toList().toString())
                        IconButton(onClick = {
                            mutItems.removeAll(mutItems)
                            // parse string to new array
                            val intermediary: List<String> = i.listInts.split("_")
                            val intermediaryList: MutableList<Int> = mutableListOf()
                            for (item in intermediary) {
                                intermediaryList.add(item.toInt())
                            }
                            // set current array to new array and close dialog
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
        // main box for all ui elements
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
                    .verticalScroll(rememberScrollState())
                    ) {
                    mutItems.forEachIndexed { index, i -> // improves efficiency over using a for loop
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = {if (!lock.value) mutItems[index]--}, modifier = Modifier
                                .width(24.dp)
                                .weight(1f)
                                .alpha(if (!lock.value) 1f else 0f)) {
                                Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = "Reduce")
                            }
                            Box(modifier = Modifier.weight(4f).background(actualColors[index])) {
                                AnimatedContent( // sort algo, elements will change to add animation
                                    //contentAlignment = Alignment.Center,
                                    targetState = i,
                                    transitionSpec = {
                                        if (i < (if (index < mutItems.size - 1) mutItems[index + 1] else i + 1)) { // can change animation depending on how the item changes
                                            slideInVertically { fullHeight -> fullHeight } + fadeIn() with
                                                    slideOutVertically { fullHeight -> -fullHeight } + fadeOut() // pretty standard slides and fades
                                        } else {
                                            slideInVertically { fullHeight -> -fullHeight } + fadeIn() with
                                                    slideOutVertically { fullHeight -> fullHeight } + fadeOut()
                                        }
                                    },
                                    label = ""
                                ) { number ->
                                    Box(
                                        modifier = Modifier
                                            //.background(actualColors[index]) no need for this, handled by algorithm function
                                            .fillMaxWidth()
                                    ) {
                                        Text( // actually display number
                                            text = number.toString(),
                                            modifier = Modifier.align(Alignment.Center),
                                            fontSize = (if (settings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp),
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                            IconButton(onClick = {if (!lock.value) mutItems[index]++}, modifier = Modifier // increment
                                .width(24.dp)
                                .weight(1f)
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
                    .verticalScroll(rememberScrollState()) // make scrollable
                    ) {
                    Text(text = explanationText.value, fontSize = (if (settings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp))
                    Divider(modifier = Modifier.padding(16.dp))
                    Text( // hard-coded pseudocode
                            text = ("1. for i = 0 to A.length - 1:\r\n" +
                                    "2.    noSwap = true\r\n" +
                                    "3.    for j = 0 to A.length - (i+1):\r\n" +
                                    "4.        if A[j] > A[j+1]:\r\n" +
                                    "5.            swap(A[j],A[j+1]\r\n" +
                                    "6.            noSwap = false\r\n" +
                                    "7.    if noSwap:\r\n" +
                                    "8.        break\r\n" +
                                    "9. return A"),
                            fontSize = (if (settings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp),
                            modifier = Modifier.alpha(if (textToggle.value) 1f else 0f)
                    )
                }
            }
            Row {
                Button(onClick = { if (!lock.value) showLoadDialog.value = true }) {
                    Text(text = "Load") // load button
                }
                Button(onClick = { if (saveToDb(db.datasetDao(), mutItems.toList(), toUseName.value))
                explanationText.value = "Saved!" else explanationText.value = "Use a unique name."}) {
                    Text(text = "Save") // save button
                }
                OutlinedTextField( // dataset name
                    value = toUseName.value,
                    onValueChange = { toUseName.value = it },
                    label = { Text(text = "Dataset name")}
                )
            }
            LargeFloatingActionButton(onClick = { bubbleSort(mutItems, explanationText, actualColors, lockedColors, colourMode) // run algorithm and apply lock
                                                lock.value = true}, modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f)) {
                Text(text = "Bubble sort (will lock values)", fontSize = if (settings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp)
            }
            // buttons self-explanatory by contentDescriptions
            Divider(modifier = Modifier.padding(all = 8.dp))
            Row (modifier = Modifier.align(alignment = Alignment.CenterHorizontally)) {
                IconButton(onClick = {resetHandler(defaultColors, actualColors, mutItems, initialState, null, explanationText, null, lock)
                }) {
                    Icon(imageVector = Icons.Filled.Refresh, contentDescription = "Reset")
                }
                IconButton(onClick = { if (!lock.value) addHandler(mutItems, settings.maxSize.intValue)}, modifier = Modifier.background(color = if (mutItems.size > settings.maxSize.intValue) colourMode.lockedColour else colourMode.defaultColour)) {
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