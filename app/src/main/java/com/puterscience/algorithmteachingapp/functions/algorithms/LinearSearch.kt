package com.puterscience.algorithmteachingapp.functions.algorithms

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import com.puterscience.algorithmteachingapp.settings.settings_classes.ColourMode

fun linearSearch(array: MutableList<Int>, explanationText: MutableState<String>, toLookFor: MutableState<Int>, iState: MutableState<Int>, finished: MutableState<Boolean>, colors: MutableList<Color>, colorsToApply: MutableList<Color>, colourMode: ColourMode) {
    // Skip busywork if the search is already finished
    if (!finished.value) {
        if (iState.value == 0){
            for (i in 0 until colorsToApply.size) {
                colorsToApply[i] = colourMode.lockedColour
            }
            colors.removeAll(colors)
            colors.addAll(colorsToApply)
        }
        // Don't increment iState automatically to allow element 0 to be searched
        if (array[iState.value] == toLookFor.value) { // Check if item is at location
            colors[iState.value] = colourMode.selectedColour1
            explanationText.value = "As ${array[iState.value]} = ${toLookFor.value}, we have found the item."
            finished.value = true // Don't try to keep advancing even if search is complete
        }
        else {
            explanationText.value = "As ${array[iState.value]} != ${toLookFor.value}, we advance." // Note why search must continue
            colors[iState.value] = colourMode.deselectedColour // Only one change, so recomp is OK
            if (iState.value + 1 < array.size) { // Don't overflow!
                iState.value++ // Increment iState
            } else {
                if (iState.value >= array.size-1) { // If iState has reached end of the list
                    explanationText.value =
                        "As no element = ${toLookFor.value}, we conclude that the value is not in the array." // If iState is at array.size then element cannot be present
                    finished.value = true // Set finished
                }
            }
        }
    }
    else {
        explanationText.value = "Already searched!"
    }
}