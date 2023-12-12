package com.puterscience.algorithmteachingapp.settings.settings_classes

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState

class Settings (
    var largeText: MutableState<Boolean>,
    var colourMode: MutableState<ColourMode>,
    var maxSize: MutableIntState
) {

}