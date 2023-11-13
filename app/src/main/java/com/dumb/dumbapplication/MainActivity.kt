package com.dumb.dumbapplication

import android.icu.text.CaseMap.Title
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dumb.dumbapplication.ui.theme.DumbApplicationTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DumbApplicationTheme {
                DumbApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DumbApp() {
    val navHostController: NavHostController = rememberNavController()
    var buttonVisible = remember {
        mutableStateOf(true)
    }

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomBar(
            navHostController = navHostController
        ) }
    ) {
        Column (
            modifier = Modifier.padding(it)
        ) {
            NavigationGraph(navHostController = navHostController)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = { Text(text = "My Top Bar") },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        )
    )
}

@Preview(
    showBackground = true,
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
fun GreetingPreview() {
    DumbApplicationTheme {
        DumbApp()
    }
}

@Composable
fun BottomBar(navHostController: NavHostController) {
    val screens = listOf(
        BottomNavItem.Home,
        BottomNavItem.Bnbk,
        BottomNavItem.Nusantara,
        BottomNavItem.Internasional,
        BottomNavItem.Favorit
    )
    NavigationBar {
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
        val currentRoute= navBackStackEntry?.destination?.route

        screens.forEach {bottomNavItem ->
            NavigationBarItem(
                modifier = Modifier.padding(vertical = 10.dp),
                icon = {
                    Icon(painter = painterResource(id = bottomNavItem.icon), contentDescription = bottomNavItem.title)
                },
                label = { Text(text = bottomNavItem.title, fontSize = 9.sp) },
                alwaysShowLabel = true,

                selected = currentRoute == bottomNavItem.route,
                onClick = {
                          navHostController.navigate(bottomNavItem.route) {
                              popUpTo(navHostController.graph.findStartDestination().id) {
                                  saveState = true
                              }
                              launchSingleTop = true
                              restoreState = true
                          }
                },


            )
        }
    }
    
}

enum class Destinations(val route: String) {
    Home("home"),
    BNBK("bnbk"),
    Nusantara("nusantara"),
    Internasional("internasional"),
    Favorit("favorit")
}

sealed class BottomNavItem(var title: String, var icon: Int, var route: String) {
    object Home : BottomNavItem(Destinations.Home.name, icon = R.drawable.icon_home, Destinations.Home.route)
    object Bnbk : BottomNavItem(Destinations.BNBK.name, icon = R.drawable.icon_book, Destinations.BNBK.route)
    object Nusantara : BottomNavItem(Destinations.Nusantara.name, icon = R.drawable.icon_red_shield_indonesia, Destinations.Nusantara.route)
    object Internasional : BottomNavItem(Destinations.Internasional.name, icon = R.drawable.icon_world, Destinations.Internasional.route)
    object Favorit : BottomNavItem(Destinations.Favorit.name, icon = R.drawable.icon_archive_minus, Destinations.Favorit.route)
}

@Composable
fun NavigationGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Destinations.Home.route) {
        composable(route = Destinations.Home.route) {
            HomeScreen()
        }
        composable(route = Destinations.BNBK.route) {
            BnbkScreen()
        }
        composable(route = Destinations.Nusantara.route) {
            NusantaraScreen()
        }
        composable(route = Destinations.Internasional.route) {
            InternasionalScreen()
        }
        composable(route = Destinations.Favorit.route) {
            FavoritScreen()
        }
    }
}