package com.fireninja.graphqltodo.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fireninja.graphqltodo.navigation.destinations.listComposable
import com.fireninja.graphqltodo.navigation.destinations.splashComposable
import com.fireninja.graphqltodo.navigation.destinations.taskComposable
import com.fireninja.graphqltodo.util.Constants.SPLASH_SCREEN
import com.fireninja.graphqltodo.viewModels.SharedViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
  navController: NavHostController,
  sharedViewModel: SharedViewModel
) {
  val screen = remember(navController) {
    Screens(navController)
  }

  AnimatedNavHost(navController, SPLASH_SCREEN) {
    splashComposable(screen.splash)
    listComposable(screen.list, sharedViewModel)
    taskComposable(screen.task, sharedViewModel)
  }
}
