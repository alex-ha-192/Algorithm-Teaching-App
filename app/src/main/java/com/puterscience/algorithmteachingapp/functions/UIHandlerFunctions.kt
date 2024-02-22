package com.puterscience.algorithmteachingapp.functions

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color

fun resetHandler(bColors: MutableList<Color>?, mColors: MutableList<Color>?, mutItems: MutableList<Int>, initialState: List<Int>, finishedSearch: MutableState<Boolean>?, upperText: MutableState<String>, iState: MutableState<Int>?, lock: MutableState<Boolean>){
    // remove all from current array then reconstruct from initState
    mutItems.removeAll(mutItems.toSet())
    mutItems.addAll(initialState)
    if (mColors != null && bColors != null){
        if (lock.value) {
            mColors.removeAll(mColors)
            mColors.addAll(bColors)
        }
    }
    // Add to length of mutItems until size of initialState
    if (finishedSearch != null) {
        finishedSearch.value = false
    }
    lock.value = false
    upperText.value = ""
    if (iState != null) {
        iState.value = 0
    }
}

fun addHandler(mutItems: MutableList<Int>, maximumArraySize: Int) {
    if (mutItems.size <= maximumArraySize) { // off-by-one error
        mutItems.add(0)
    }
}

fun removeHandler(mutItems: MutableList<Int>) {
    if (mutItems.size >= 2) {
        mutItems.remove(mutItems[mutItems.size - 1])
    }
}

fun swapElements(mutableList: MutableList<Int>, index1: Int, index2:Int) {
    val temp: Int = mutableList[index1]
    mutableList[index1] = mutableList[index2]
    mutableList[index2] = temp
} // nice shorthand for bubblesort, quicksort
