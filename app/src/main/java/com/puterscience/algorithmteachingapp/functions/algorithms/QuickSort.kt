package com.puterscience.algorithmteachingapp.functions.algorithms

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.graphics.Color
import com.puterscience.algorithmteachingapp.functions.swapElements
import com.puterscience.algorithmteachingapp.settings.settings_classes.ColourMode
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
fun quickSort(array: MutableList<Int>, explanationText: MutableState<String>, colors: MutableList<Color>, colourMode: ColourMode, low: MutableIntState, high: MutableIntState, paused: MutableState<Boolean>) {
    // TODO: Colours
    GlobalScope.launch{
        val colorsNew: MutableList<Color> = mutableListOf()
            for (i in 0 until colors.size){
                colorsNew.add(colourMode.lockedColour)
            }
        if (Thread.currentThread().isInterrupted){
            return@launch
        }
        if (low.value < high.value) {
            val partitionIndex =
                partition(array, explanationText, colors, colourMode, low.value, high.value, paused)
            while (paused.value) {

            }
            if (Thread.currentThread().isInterrupted) {
                return@launch
            }
            //colors.removeAll(colors)
            //colors.addAll(colorsNew)
            quickSort(
                array,
                explanationText,
                colors,
                colourMode,
                low,
                mutableIntStateOf(partitionIndex - 1),
                paused
            )
            while (paused.value) {

            }
            if (Thread.currentThread().isInterrupted) {
                return@launch
            }
            //colors.removeAll(colors)
            //colors.addAll(colorsNew)
            quickSort(
                array,
                explanationText,
                colors,
                colourMode,
                mutableIntStateOf(partitionIndex + 1),
                high,
                paused
            )
            if (Thread.currentThread().isInterrupted) {
                return@launch
            }
        }
    }
}

fun partition(array: MutableList<Int>, explanationText: MutableState<String>, colors: MutableList<Color>, colourMode: ColourMode, low: Int, high: Int, paused: MutableState<Boolean>): Int{
    if (Thread.currentThread().isInterrupted){
        return 0
    }
    if (high >= array.size) {
        return 0
    }
    val pivot = array[high]
    val newColors = mutableListOf<Color>()
    for (i in 0 until colors.size-1){
        newColors.add(colors[i])
    }
    //newColors[high] = colourMode.selectedColour1
    //colors.removeAll(colors)
    //colors.addAll(newColors)
    explanationText.value = "We will select ${pivot} as the pivot."
    paused.value = true
    while (paused.value) {

    }
    if (Thread.currentThread().isInterrupted){
        return 0
    }
    var i = low - 1
    explanationText.value = "We will now scan along the array until an item larger than the pivot is found."
    paused.value = true
    while (paused.value){

    }
    if (Thread.currentThread().isInterrupted){
        return 0
    }
    for (j in low until high){
        if (j >= array.size){ // This should never call unless something bad happened but it's good to have it just in case
            return 0
        }
        if (array[j] <= pivot) {
            i++
            if (i != j) {
                explanationText.value =
                    "As ${array[j]} is smaller than the pivot, we swap ${array[j]}, ${array[i]}."
                swapElements(array, i, j)
                paused.value = true
                while (paused.value){

                }
                if (Thread.currentThread().isInterrupted){
                    return 0
                }
            }
        }
    }
    //colors[i+1] = colourMode.deselectedColour
    //colors[high] = colourMode.selectedColour2
    if (i + 1 >= array.size || high >= array.size){
        return 0 // just in case
    }
    explanationText.value = "${array[i+1]} is larger than the pivot, so we swap ${array[i+1]} and ${array[high]}. When these are the same, the next-final element becomes the pivot."
    swapElements(array, i+1, high)
    paused.value = true
    return (i + 1)
}