package com.puterscience.algorithmteachingapp.functions.algorithms

import android.util.MutableInt
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.graphics.Color
import com.puterscience.algorithmteachingapp.settings.settings_classes.ColourMode
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.min

// TODO: the below code doesn't work
@OptIn(DelicateCoroutinesApi::class)
fun mergeSort(array: MutableList<Int>, explanationText: MutableState<String>, colors: MutableList<Color>, colourMode: ColourMode, paused: MutableState<Boolean>)  {
    val low = mutableIntStateOf(0)
    val high = mutableIntStateOf(array.size - 1)
    val temp = array
    var oldM = 1
    for (m in 1..(high.intValue-low.intValue)) {
        if (m != 2*oldM) {

        }
        else {
            oldM = m

            for (i in low.intValue..high.intValue - 1 step 2 * m) {
                val from = mutableIntStateOf(i)
                val mid = mutableIntStateOf(i + m - 1)
                val to = mutableIntStateOf(min(1 + 2 * m - 1, high.intValue))

                val result = merge(array, temp, from, mid, to)
                for (i in 0..array.size-1) {
                    array[i] = result[i]
                }
            }
        }
    }
}

fun merge(array: MutableList<Int>, temp: MutableList<Int>, from: MutableIntState, mid: MutableIntState, to: MutableIntState): MutableList<Int> {
    var k = from.intValue
    var i = from.intValue
    var j = mid.intValue + 1

    while (i <= mid.intValue && j <= to.intValue) {
        if (array[i] < array[j]) {
            temp.add(k, array[i])
            i++
        }
        else {
            temp.add(k, array[j])
            j++
        }
        k++
    }
    return temp
}