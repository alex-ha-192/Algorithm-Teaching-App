package com.puterscience.algorithmteachingapp.functions.algorithms

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import com.puterscience.algorithmteachingapp.settings.settings_classes.ColourMode

// TODO: nothing here is fun

fun mergeSort(array: MutableList<Int>, explanationText: MutableState<String>, colors: MutableList<Color>, colourMode: ColourMode,
              currentStage: MutableIntState) {
    // currentStage just says which step to execute, gets reset with reset and colours are handled by resetHandler()
    if (currentStage.intValue == 0) {
        explanationText.value =
            "Split the values in half repeatedly until sets of one element remain."
        colors.removeAll(colors.toSet())
        colors.addAll(
            mutableListOf(
                colourMode.defaultColour,
                colourMode.defaultColour,
                colourMode.defaultColour,
                colourMode.defaultColour,
                colourMode.selectedColour1,
                colourMode.selectedColour1,
                colourMode.selectedColour1,
                colourMode.selectedColour1
            )
        )
    }
    if (currentStage.intValue == 1) {
        colors.removeAll(colors.toSet())
        colors.addAll(
            mutableListOf(
                colourMode.defaultColour,
                colourMode.defaultColour,
                colourMode.selectedColour2,
                colourMode.selectedColour2,
                colourMode.selectedColour1,
                colourMode.selectedColour1,
                colourMode.lockedColour,
                colourMode.lockedColour
            )
        )
    }
    if (currentStage.intValue == 2) {
        colors.removeAll(colors.toSet())
        colors.addAll(
            mutableListOf(
                colourMode.defaultColour,
                colourMode.deselectedColour,
                colourMode.selectedColour2,
                colourMode.lockedColour,
                colourMode.selectedColour1,
                colourMode.defaultColour,
                colourMode.lockedColour,
                colourMode.defaultColour
            )
        )
    }
    if (currentStage.intValue == 3) {
        explanationText.value =
            "Then, merge the elements back in reverse order, adding the smallest elements first to created merged and sorted sublists."
        array.removeAll(array.toSet())
        array.addAll(mutableListOf(7, 8, 5, 6, 3, 4, 1, 2))
        colors.removeAll(colors.toSet())
        colors.addAll(mutableListOf(
            colourMode.defaultColour,
            colourMode.defaultColour,
            colourMode.selectedColour2,
            colourMode.selectedColour2,
            colourMode.selectedColour1,
            colourMode.selectedColour1,
            colourMode.lockedColour,
            colourMode.lockedColour))
    }
    if (currentStage.intValue == 4) {
        explanationText.value =
            "Continue merging sorted sublists until a full sorted list is acquired."
        array.removeAll(array.toSet())
        array.addAll(mutableListOf(5, 6, 7, 8, 1, 2,3, 4))
        colors.removeAll(colors.toSet())
        colors.addAll(
            mutableListOf(
            colourMode.defaultColour,
            colourMode.defaultColour,
            colourMode.defaultColour,
            colourMode.defaultColour,
            colourMode.selectedColour1,
            colourMode.selectedColour1,
            colourMode.selectedColour1,
            colourMode.selectedColour1
        ))
    }
    if (currentStage.intValue == 5) {
        explanationText.value = "Eventually, the list will be sorted."
        array.removeAll(array.toSet())
        array.addAll(mutableListOf(1, 2, 3, 4, 5, 6, 7, 8))
        colors.removeAll(colors.toSet())
        colors.addAll(mutableListOf(
            colourMode.selectedColour1,
            colourMode.selectedColour1,
            colourMode.selectedColour1,
            colourMode.selectedColour1,
            colourMode.selectedColour1,
            colourMode.selectedColour1,
            colourMode.selectedColour1,
            colourMode.selectedColour1
        ))
    }
}