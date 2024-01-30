package com.puterscience.algorithmteachingapp.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.puterscience.algorithmteachingapp.settings.settings_classes.ColourMode
import com.puterscience.algorithmteachingapp.settings.settings_classes.Defaults
import com.puterscience.algorithmteachingapp.settings.settings_classes.Settings
import com.puterscience.algorithmteachingapp.algo_screens.BinarySearchScreen
import com.puterscience.algorithmteachingapp.algo_screens.BubbleSortScreen
import com.puterscience.algorithmteachingapp.algo_screens.LinearSearchScreen
import com.puterscience.algorithmteachingapp.algo_screens.MergeSortScreen
import com.puterscience.algorithmteachingapp.algo_screens.QuickSortScreen
import com.puterscience.algorithmteachingapp.database.DatabaseScreen
import com.puterscience.algorithmteachingapp.database.dataset_db.DatasetDatabase
import com.puterscience.algorithmteachingapp.database.settings_db.settingsDao
import com.puterscience.algorithmteachingapp.main.MainScreen
import com.puterscience.algorithmteachingapp.settings.SettingsScreen
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(navController: NavController,
                     navHostController: NavHostController,
                     globalSettings: Settings,
                     globalDefaults: Defaults,
                     colourModes: List<ColourMode>,
                     persistentDb: DatasetDatabase,
                     firstTimeFlag: MutableState<Boolean>,
                     settingsDao: settingsDao
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val stdPass = listOf(5, 4, 3, 2, 1)
    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        ModalDrawerSheet (modifier = Modifier.fillMaxWidth(0.67f),){
            /*
            Text(text = "ATA",
                modifier = Modifier.padding(16.dp),
            )
            for (i in 0 until 3){
                Divider()
            }
            */
            /* Remove instructions?
            NavigationDrawerItem(label = { Text(text = "Instructions")}, selected = false, onClick = {
                coroutineScope.launch{
                    drawerState.close()
                }
                navController.navigate("InstructionsScreen")
            })
            */
            NavigationDrawerItem(label = { Text(text = "Main")}, selected = false, onClick = {
                coroutineScope.launch{
                    drawerState.close()
                }
                navController.navigate("MainScreen")
            })
            Divider()
            NavigationDrawerItem(label = { Text("Bubble Sort") }, selected = false, onClick = {
                coroutineScope.launch{
                    drawerState.close()
                }
                navController.navigate("BubbleSortScreen")
            })
            NavigationDrawerItem(label = {Text(text = "Linear Search")}, selected = false, onClick = {
                coroutineScope.launch{
                    drawerState.close()
                }
                navController.navigate("LinearSearchScreen")
            })
            NavigationDrawerItem(label = { Text(text = "Binary Search")}, selected = false, onClick = {
                coroutineScope.launch{
                    drawerState.close()
                }
                navController.navigate("BinarySearchScreen")
            })
            NavigationDrawerItem(label = { Text(text = "Quick Sort") }, selected = false, onClick = {
                coroutineScope.launch {
                    drawerState.close()
                }
                navController.navigate("QuickSortScreen")
            })
            NavigationDrawerItem(label = { Text(text = "Merge Sort (Demo)") }, selected = false, onClick = {
                coroutineScope.launch{
                    drawerState.close()
                }
                navController.navigate("MergeSortScreen")
            })

            Divider(modifier = Modifier
                .fillMaxWidth(0.5f)
                .align(Alignment.CenterHorizontally))

            NavigationDrawerItem(label = { Text(text = "Data")}, selected = false, onClick = {
                coroutineScope.launch {
                    drawerState.close()
                }
                navController.navigate("DatabaseScreen")
            })
            NavigationDrawerItem(label = { Text(text = "Settings")}, selected = false , onClick = {
                coroutineScope.launch{
                    drawerState.close()
                }
                navController.navigate("SettingsScreen")
            })
        }
    }) {
        Column(){
            Row {
                CenterAlignedTopAppBar(title = {Text(text = "Algorithm Teaching App")},
                    navigationIcon = {
                        IconButton(onClick = { coroutineScope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }

                        } }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                )
            }
            NavHost(navController = navHostController, startDestination = if (firstTimeFlag.value) "SettingsScreen" else "MainScreen", modifier = Modifier.padding(8.dp)) {
                composable(route = "BubbleSortScreen") {
                    BubbleSortScreen(
                        toSort = stdPass,
                        globalSettings,
                        globalDefaults,
                        globalSettings.colourMode.value,
                        persistentDb
                    )
                }
                composable(route = "MainScreen") {
                    MainScreen(
                        globalSettings,
                        globalDefaults
                    )
                }
                composable(route = "LinearSearchScreen") {
                    LinearSearchScreen(stdPass,
                        globalSettings,
                        globalDefaults,
                        globalSettings.colourMode.value,
                        persistentDb)
                }
                composable(route = "BinarySearchScreen") {
                    BinarySearchScreen(toSort = stdPass,
                        globalSettings,
                        globalDefaults,
                        globalSettings.colourMode.value,
                        persistentDb)
                }
                composable(route = "SettingsScreen") {
                    SettingsScreen(globalSettings = globalSettings,
                        globalDefaults,
                        colourModes,
                        firstTimeFlag,
                        settingsDao = settingsDao)
                }
                composable(route = "QuickSortScreen"){
                    QuickSortScreen(toSort = stdPass, settings = globalSettings, defaults = globalDefaults, colourMode = globalSettings.colourMode.value, db = persistentDb)
                }
                composable(route = "DatabaseScreen"){
                    DatabaseScreen(globalSettings = globalSettings, defaults = globalDefaults, persistentDb)
                }
                composable(route = "MergeSortScreen"){
                    MergeSortScreen(//toSort = stdPass,
                        settings = globalSettings, defaults = globalDefaults, colourMode = globalSettings.colourMode.value, db = persistentDb)
                }
            }
        }
    }
}