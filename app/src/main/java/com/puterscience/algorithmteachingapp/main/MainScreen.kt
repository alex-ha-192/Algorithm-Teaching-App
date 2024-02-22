package com.puterscience.algorithmteachingapp.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.puterscience.algorithmteachingapp.settings.settings_classes.Defaults
import com.puterscience.algorithmteachingapp.settings.settings_classes.Settings

@Composable
fun MainScreen(settings: Settings, defaults: Defaults) {
    Column{
        Text(text = "Welcome to the Application Teaching App!", fontSize = if (settings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp)
        Divider(Modifier.padding(vertical = 16.dp), color = Color(0))
        Text(text = "This app will allow you to observe how common sorting and searching algorithms work through intuitive explanations.",
            fontSize = if (settings.largeText.value) defaults.defaultLargeText.sp else defaults.defaultSmallText.sp)
    }
}