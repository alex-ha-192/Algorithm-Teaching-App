package com.puterscience.algorithmteachingapp.functions.algorithms

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import com.puterscience.algorithmteachingapp.settings.settings_classes.ColourMode
import kotlin.math.roundToInt

fun binarySearch(array: MutableList<Int>, explanationText: MutableState<String>, toLookFor: MutableState<Int>, iState: MutableState<Int>, finished: MutableState<Boolean>, colors: MutableList<Color>,
                 currentLeft: MutableState<Int>, currentRight: MutableState<Int>, lColors: MutableList<Color>, colourMode: ColourMode
) {
    var middleIndex: Int = 0
    middleIndex = ((currentLeft.value + currentRight.value) / 2).toFloat().roundToInt()

    if (currentLeft.value == 0 && currentRight.value == array.size) {
        colors.removeAll(colors)
        colors.addAll(lColors)
    }
    var unsorted: Boolean = false
    var colorsToApply: MutableList<Color> = mutableListOf<Color>()
    for (i in 0 until colors.size) {
        colorsToApply.add(colors[i])
    }

    if (!finished.value) {
        // Check for sorted
        for (i in 0 until array.size-1) {
            if (array[i] > array[i+1]) {
                unsorted = true
            }
        }

        // If currentLeft and currentRight are the same: the element is not in the array
        /*        if ((currentLeft.value - currentRight.value).absoluteValue < 2) {
                    if (array[currentLeft.value] != toLookFor.value) {
                        explanationText.value = "The element is not in the array."
                        finished.value = true
                    }
                }*/

        if (unsorted) { // If array not sorted
            val sortedArray: MutableList<Int> = mutableListOf()
            for (i in 0 until array.size) {
                sortedArray.add(array[i])
            }
            sortedArray.sort()
            array.removeAll(array)
            array.addAll(sortedArray)
            explanationText.value = "The array is unsorted, so we sort it."
            return
        }
        else {
            if (currentRight.value - currentLeft.value <= 1) {
                if (array[currentLeft.value] == toLookFor.value) {
                    finished.value = true
                    colorsToApply[currentLeft.value] = colourMode.selectedColour1
                    colors.removeAll(colors)
                    colors.addAll(colorsToApply)
                    explanationText.value = "As ${toLookFor.value} is the only unique element left, it has been found."
                    return
                }
                else {
                    for (i in currentLeft.value until currentRight.value) {
                        colorsToApply[i] = colourMode.deselectedColour
                    }
                    colors.removeAll(colors)
                    colors.addAll(colorsToApply)
                    explanationText.value = "${toLookFor.value} is not in the array."
                    finished.value = true
                    return
                }
            }
            // Array is already sorted
            // Perform one step at a time
            if (array[middleIndex] == toLookFor.value) {
                // If item is in middle:
                colorsToApply[middleIndex] = colourMode.selectedColour1
                colors.removeAll(colors)
                colors.addAll(colorsToApply)
                finished.value = true
                explanationText.value = "As ${toLookFor.value} is in the middle of the available array, it has been found."
                return
            }
            // Item is not in middle
            if (toLookFor.value > array[middleIndex]) {
                // Item is on right side of array, change leftIndex to middleIndex
                explanationText.value = "As ${toLookFor.value} > ${array[middleIndex]}, we remove the lower half of the remaining list."
                for (i in currentLeft.value until middleIndex) {
                    colorsToApply[i] = colourMode.deselectedColour
                }
                colors.removeAll(colors)
                colors.addAll(colorsToApply)
                currentLeft.value = middleIndex
                return
            }
            else {
                // Item is on left side
                explanationText.value = "As ${toLookFor.value} < ${array[middleIndex]}, we remove the upper half of the remaining list."
                for (i in middleIndex until currentRight.value) {
                    colorsToApply[i] = colourMode.deselectedColour
                }
                colors.removeAll(colors)
                colors.addAll(colorsToApply)
                currentRight.value = middleIndex
                return
            }
        }
    }
    if (finished.value) {
        explanationText.value = "Already searched!"
    }
}