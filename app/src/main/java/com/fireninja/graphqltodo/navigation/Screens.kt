package com.fireninja.graphqltodo.navigation

import android.util.Log
import androidx.navigation.NavController
import com.fireninja.graphqltodo.util.Action
import com.fireninja.graphqltodo.util.Constants.LIST_SCREEN
import com.fireninja.graphqltodo.util.Constants.SPLASH_SCREEN

class Screens(navController: NavController) {
  val splash: () -> Unit = {
    navController.navigate(route = "list/${Action.NO_ACTION}") {
      popUpTo(SPLASH_SCREEN) { inclusive = true }
    }
  }
  val list: (Int) -> Unit = { taskId ->
    navController.navigate("task/$taskId")
  }
  val task: (Action) -> Unit = { action ->
    Log.d("Screens", "Screens: $action")
    navController.navigate("list/${action.name}") {
      popUpTo(LIST_SCREEN) { inclusive = true }
    }
  }
}