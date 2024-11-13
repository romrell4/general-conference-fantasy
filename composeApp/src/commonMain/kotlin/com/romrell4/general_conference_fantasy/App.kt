package com.romrell4.general_conference_fantasy

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.romrell4.general_conference_fantasy.view.PlayGameScreen
import com.romrell4.general_conference_fantasy.view.WordEntryScreen
import generalconferencefantasy.composeapp.generated.resources.Res
import generalconferencefantasy.composeapp.generated.resources.app_bar_title
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource

@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination

        Scaffold(
            topBar = {
                TopBar(
                    currentDestination = currentDestination,
                    onResetGameMenuOptionTapped = {
                        navController.navigate(Screen.PickWords)
                    }
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.PickWords,
                modifier = Modifier.fillMaxSize().padding(innerPadding)
            ) {
                composable<Screen.PickWords> {
                    WordEntryScreen(
                        onStartTapped = {
                            navController.navigate(Screen.PlayGame(it))
                        }
                    )
                }
                composable<Screen.PlayGame> {
                    val route = it.toRoute<Screen.PlayGame>()
                    PlayGameScreen(words = route.words)
                }
            }
        }
    }
}

sealed interface Screen {
    @Serializable
    data object PickWords : Screen

    @Serializable
    data class PlayGame(val words: List<String>) : Screen
}

@Composable
fun TopBar(
    currentDestination: NavDestination?,
    onResetGameMenuOptionTapped: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    TopAppBar(
        title = { Text(stringResource(Res.string.app_bar_title)) },
        actions = {
            if (currentDestination?.hasRoute<Screen.PlayGame>() == true) {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More",
                    )
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        DropdownMenuItem(onClick = {
                            expanded = false
                            onResetGameMenuOptionTapped()
                        }) {
                            Text("Restart")
                        }
                    }
                }
            }
        },
    )
}
