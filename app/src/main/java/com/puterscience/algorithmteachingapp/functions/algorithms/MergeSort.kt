package com.puterscience.algorithmteachingapp.functions.algorithms

import android.util.MutableInt
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.node.GlobalPositionAwareModifierNode
import com.puterscience.algorithmteachingapp.settings.settings_classes.ColourMode
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.min

// TODO: nothing here is fun
@OptIn(DelicateCoroutinesApi::class)
fun mergeSort(array: MutableList<Int>, explanationText: MutableState<String>, colors: MutableList<Color>, colourMode: ColourMode, paused: MutableState<Boolean>) {
    GlobalScope.launch {
        explanationText.value =
            "Split the values in half repeatedly until sets of one element remain."
        paused.value = true
        while (paused.value) {

        }
        explanationText.value =
            "Then, merge the elements back in reverse order, adding the smallest elements first to created merged and sorted sublists."
        paused.value = true
        while (paused.value) {

        }
        explanationText.value =
            "Continue merging sorted sublists until a full sorted list is acquired."
    }
}