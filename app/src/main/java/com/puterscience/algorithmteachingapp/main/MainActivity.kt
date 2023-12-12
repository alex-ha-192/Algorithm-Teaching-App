package com.puterscience.algorithmteachingapp.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.puterscience.algorithmteachingapp.database.db_classes.Database
import com.puterscience.algorithmteachingapp.settings.settings_classes.ColourMode
import com.puterscience.algorithmteachingapp.settings.settings_classes.Defaults
import com.puterscience.algorithmteachingapp.settings.settings_classes.Settings
import com.puterscience.algorithmteachingapp.navigation.NavigationDrawer
import com.puterscience.algorithmteachingapp.ui.theme.ApplicationTeachingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApplicationTeachingAppTheme {
                val persistentDb = Room.databaseBuilder(
                    applicationContext,
                    Database::class.java, "datasets"
                ).allowMainThreadQueries().build()


                val universalNavController = rememberNavController()
                val colourModeStd: ColourMode = ColourMode(Color.LightGray, Color.Gray, Color(34, 139, 34), Color.Red, Color.Black)
                val colourModeProDeu: ColourMode = ColourMode(Color.LightGray, Color.Gray, Color.Blue, Color(228, 208, 10), Color.Black)
                val colourModeTri: ColourMode = ColourMode(Color.LightGray, Color.Gray, Color.Blue, Color(/*PINK*/255, 192, 203), Color.Black)
                val colourModeMono: ColourMode = ColourMode(Color.LightGray, Color.Gray, Color.LightGray, Color.DarkGray, Color.Black)


                val globalSettings: Settings = Settings(
                    largeText = remember {mutableStateOf(false)},
                    colourMode = remember {mutableStateOf<ColourMode>(colourModeStd)},
                    maxSize = remember { mutableIntStateOf(12)}
                )
                val globalDefaults: Defaults = Defaults(
                    defaultLargeText = 26,
                    defaultSmallText = 16
                )
                val colourModes = listOf<ColourMode>(colourModeStd, colourModeProDeu, colourModeTri, colourModeMono)
                NavigationDrawer(
                    navController = universalNavController,
                    navHostController = universalNavController,
                    globalSettings = globalSettings,
                    globalDefaults = globalDefaults,
                    colourModes = colourModes,
                    persistentDb = persistentDb
                )
            }
        }
    }
}