package com.puterscience.algorithmteachingapp.functions.algorithms

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import com.puterscience.algorithmteachingapp.settings.settings_classes.ColourMode
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
fun mergeSort(array: MutableList<Int>, explanationText: MutableState<String>, colors: MutableList<Color>, colourMode: ColourMode, paused: MutableState<Boolean>)  {
    GlobalScope.launch {
        if (array.size > 1) {
            val mid: Int = array.size.div(2)
            while (paused.value){

            }
            val leftHalf = array.slice(0 until mid).toMutableList()
            val rightHalf = array.slice(mid until array.size).toMutableList()
            explanationText.value = "We now split the list between indices ${0}, ${mid}."
            while (paused.value){

            }
            mergeSort(leftHalf, explanationText, colors, colourMode, paused)
            explanationText.value = "We now split the list between indices ${mid}, ${array.size}."
            while (paused.value){

            }
            mergeSort(rightHalf, explanationText, colors, colourMode, paused)
            while(paused.value){

            }
            mergeLists(array, explanationText, paused, leftHalf, rightHalf)
        }
    }
}

fun mergeLists(array: MutableList<Int>, explanationText: MutableState<String>, paused: MutableState<Boolean>, leftHalf: MutableList<Int>, rightHalf: MutableList<Int>){
    var i=0
    var j=0
    var k=0
    while (i < leftHalf.size && j < rightHalf.size){
        if (leftHalf[i] < rightHalf[j]) {
            array[k] = leftHalf[i]
            explanationText.value = "We add ${leftHalf[i]} to the list."
            i++
            paused.value = true
            k++
            while (paused.value) {

            }
        }
        else {
            array[k] = rightHalf[j]
            explanationText.value = "We add ${rightHalf[j]} to the list."
            j++
            paused.value = true
            k++
            while (paused.value) {

            }
        }
    }
    while (i < leftHalf.size) {
        array[k] = leftHalf[i]
        explanationText.value = "We add ${leftHalf[i]} to the list."
        i++
        paused.value = true
        k++
        while (paused.value) {

        }
    }
    while (j < rightHalf.size) {
        array[k] = rightHalf[j]
        explanationText.value = "We add ${rightHalf[j]} to the list."
        j++
        k++
        paused.value = true
        while (paused.value) {

        }
    }
}