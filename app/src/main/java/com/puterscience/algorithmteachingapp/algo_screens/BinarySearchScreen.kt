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
import com.puterscience.algorithmteachingapp.functions.algorithms.binarySearch
import com.puterscience.algorithmteachingapp.functions.removeHandler
import com.puterscience.algorithmteachingapp.functions.resetHandler
import com.puterscience.algorithmteachingapp.functions.saveToDb

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BinarySearchScreen(toSort: List<Int>, settings: Settings, defaults: Defaults, colourMode: ColourMode, db: DatasetDatabase) {
	// Initialise variables
	val mutItems = remember { mutableListOf<Int>().apply { addAll(toSort) } }
	var initialState: List<Int> = toSort
	val lock = remember { mutableStateOf(false) }
	val textToggle = remember { mutableStateOf(true) }
	val explanationText = remember { mutableStateOf("") }
	val currentLeftIndex = remember { mutableIntStateOf(0) }
	val currentRightIndex = remember { mutableIntStateOf(mutItems.size) }
	val loadedList = remember {
		mutableListOf<Int>()
	}

	// Button variables
	val toSearch = remember { mutableIntStateOf(0) }
	val iState = remember { mutableIntStateOf(0) }
	val searchFinished = remember { mutableStateOf(false) }
	val mutColors = remember { mutableStateListOf<Color>() }
	val blankColors = mutableListOf<Color>()
	val lockedColors = mutableListOf<Color>()
	val showLoadDialog = remember { mutableStateOf(false) }
	val toUseName = remember {mutableStateOf("")}

	// Use colour mode argument to set up the colour arrays
	for (i in 0 until mutItems.size) {
		mutColors.add(colourMode.defaultColour)
		blankColors.add(colourMode.defaultColour)
		lockedColors.add(colourMode.lockedColour)
	}

	// Get the saved elements from the database
	val daoElements = db.datasetDao().getAll()
	if (showLoadDialog.value) {
			Dialog(onDismissRequest = { showLoadDialog.value = false }) {
				Column(
					Modifier
						.background(Color.White)
						.verticalScroll(rememberScrollState())) { // Make it scrollable
					daoElements.forEachIndexed { _, i -> // Use a lambda for speed
						Row {
							Text(text = i.name + ": ")
							Text(text = i.listInts.split("_").toList().toString()) // Split up by _ due to how database items are stored
							IconButton(onClick = {
								mutItems.removeAll(mutItems.toSet()) // reset current array
								// create array of loaded items by parsing the string
								val intermediary: List<String> = i.listInts.split("_")
								val intermediaryList: MutableList<Int> = mutableListOf()
								for (item in intermediary) {
									intermediaryList.add(item.toInt())
								}
								// ENSURE current array is empty
								mutItems.removeAll(mutItems.toSet())
								// set current array to parsed list
								mutItems.addAll(intermediaryList)
								// set initial state to the loaded list
								initialState = intermediaryList.toList()
								explanationText.value = "Loaded!"
								// create loadedlist for backup reset
								loadedList.removeAll(loadedList.toSet())
								loadedList.addAll(mutItems)
								showLoadDialog.value = false
							}) {
								Icon(
									imageVector = Icons.Filled.PlayArrow, // "play" icon
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
		Column(
			modifier = Modifier
				.fillMaxSize()
			) {
				Row(modifier = Modifier.weight(1f)) {
					Divider(
						modifier = Modifier
							.padding(2.dp)
							.width(0.dp), color = Color.White
					)
					Column(
						modifier = Modifier
							.weight(1f)
							.verticalScroll(rememberScrollState())
					) {
						Row(verticalAlignment = Alignment.CenterVertically) { // toSearch button
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
										.align(Alignment.Center),
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
						mutItems.forEachIndexed { index, i -> // lambda greatly increases the application's speed to reset and modify the array
							Row(verticalAlignment = Alignment.CenterVertically) {
								IconButton( // decrement
									onClick = { if (!lock.value) mutItems[index]-- },
									modifier = Modifier
										.width(24.dp)
										.alpha(if (!lock.value) 1f else 0f)
								) {
									Icon(
										imageVector = Icons.Filled.KeyboardArrowDown,
										contentDescription = "Reduce"
									)
								}
								Box( // show value of element
									modifier = Modifier
										.weight(1f)
										.background(mutColors[index])
								)
								{
									Text(
										text = i.toString(),
										modifier = Modifier.align(Alignment.Center),
										fontSize = (if (settings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp),
										color = Color.White
									)
								}
								IconButton( // increment
									onClick = { if (!lock.value) mutItems[index]++ },
									modifier = Modifier
										.width(24.dp)
										.alpha(if (!lock.value) 1f else 0f)
								) {
									Icon(
										imageVector = Icons.Filled.KeyboardArrowUp,
										contentDescription = "Increase"
									)
								}
							}
						}
					}
					Divider(
						modifier = Modifier
							.alpha(0f)
							.width(12.dp)
					)
					Column(
						modifier = Modifier
							.weight(2f)
							.alpha(1f)
							.verticalScroll(rememberScrollState())
					) {
						Text( // show current explanationText
							text = explanationText.value,
							fontSize = (if (settings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp)
						)
						Divider(modifier = Modifier.padding(16.dp))
						Text(
							text = ( // hard-coded pseudocode
									"1. while left <= right:" +
											"\n2.     middle = left + right div 2" +
											"\n3.     if array[middle] = x, return middle" +
											"\n4.     if array[middle] < x, left = middle + 1" +
											"\n5.     if array[middle] > x, right = middle - 1" +
											"\n6. return -1"
									),
							fontSize = (if (settings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp),
							modifier = Modifier.alpha(if (textToggle.value) 1f else 0f)
						)
					}
				}
				Row {
					Button(onClick = { if (!lock.value) showLoadDialog.value = true }) { // Load button
						Text(text = "Load")
					}
					Button(onClick = { if (saveToDb(db.datasetDao(), mutItems.toList(), toUseName.value)) // Save button
					explanationText.value = "Saved!" else explanationText.value = "Use a unique name."}) {
						Text(text = "Save")
					}
					OutlinedTextField( // Enter name of dataset
						value = toUseName.value,
						onValueChange = { toUseName.value = it },
						label = { Text(text = "Dataset name")}
					)
				}
				LargeFloatingActionButton( // Run algorithm
					onClick = {
						if (!lock.value) { // What to do on first run
							lock.value = true
							initialState = mutItems.toList() // ensure that on reset, go back to current dataset
							currentLeftIndex.intValue = 0
							currentRightIndex.intValue = mutItems.size
						}
						binarySearch(
							mutItems,
							explanationText,
							toSearch,
							searchFinished,
							mutColors,
							currentLeftIndex,
							currentRightIndex,
							lockedColors,
							colourMode
						)
					}, modifier = Modifier
						.fillMaxWidth()
						.weight(0.1f)
				) {
					Text(
						text = "Binary search (will lock values)",
						fontSize = if (settings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp
					)
				}
				Divider(modifier = Modifier.padding(all = 8.dp))
				Row(modifier = Modifier.align(alignment = Alignment.CenterHorizontally)) {
					IconButton(onClick = { // reset button
						resetHandler(
							blankColors,
							mutColors,
							mutItems,
							if (loadedList.isEmpty()) initialState else loadedList.toList(),
							searchFinished,
							explanationText,
							iState,
							lock
						)
						currentLeftIndex.intValue = 0
						currentRightIndex.intValue = mutItems.size
					}) {
						Icon(imageVector = Icons.Filled.Refresh, contentDescription = "Reset")
					}
					// add an item to the list
					IconButton(
						onClick = {
							if (!lock.value) {
								addHandler(mutItems, settings.maxSize.intValue)
								currentRightIndex.intValue++
							}
						},
						modifier = Modifier.background(color = if (mutItems.size > settings.maxSize.intValue) colourMode.lockedColour else colourMode.defaultColour)
					) {
						Icon(imageVector = Icons.Filled.Add, contentDescription = "Add element")
					}
					// remove an item from the list
					IconButton(
						onClick = {
							if (!lock.value) {
								removeHandler(mutItems)
								currentRightIndex.intValue--
								if (currentRightIndex.intValue <= 0) {
									currentRightIndex.intValue = 0
								}
							}
						},
						modifier = Modifier.background(color = if (mutItems.size <= 1) colourMode.lockedColour else colourMode.defaultColour)
					)
					{
						Icon(
							imageVector = Icons.Filled.Clear,
							contentDescription = "Remove element"
						)
					}
					Button(onClick = { textToggle.value = !textToggle.value }) { // textToggle
						Text(
							text = (if (textToggle.value) "Text Off" else "Text On"),
							fontSize = if (settings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp
						)
					}
				}
			}
		}
}