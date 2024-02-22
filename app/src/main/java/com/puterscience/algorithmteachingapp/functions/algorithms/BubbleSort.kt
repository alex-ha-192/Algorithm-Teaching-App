package com.puterscience.algorithmteachingapp.functions.algorithms

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import com.puterscience.algorithmteachingapp.functions.swapElements
import com.puterscience.algorithmteachingapp.settings.settings_classes.ColourMode

// not exactly a special bubble sort implementation
fun bubbleSort(array: MutableList<Int>, explanationText: MutableState<String>, colors: MutableList<Color>, lockedColors: MutableList<Color>, colourMode: ColourMode) {
    // init colours to apply from colour set
    colors.removeAll(colors.toSet())
    colors.addAll(lockedColors)
    for (i in 0..array.size){
        for (j in 0..array.size-i-2) {
            if (array[j] > array[j+1]) {
                explanationText.value = "As ${array[j]} > ${array[j+1]}, we swap them."
                // highlight swapped elements
                colors[j] = colourMode.selectedColour1
                colors[j+1] = colourMode.selectedColour2
                swapElements(array, j, j+1) // animation is handled by the screen function
                return
            }
        }
    }
    explanationText.value = ("The list is already sorted!")
}