package com.puterscience.algorithmteachingapp.settings.settings_classes

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState

class Settings (
    var largeText: MutableState<Boolean>, // settings need to change dynamically, so the use of memory's worth it
    var colourMode: MutableState<ColourMode>,
    var maxSize: MutableIntState // probably could get away without a mutable given it's a var? still, this was a decision made earlier in development and it's too much of a pain to refactor now; it works well enough
)