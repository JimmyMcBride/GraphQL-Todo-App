package com.fireninja.graphqltodo.navigation

import android.util.Log
import androidx.navigation.NavController
import com.fireninja.graphqltodo.util.Action
import com.fireninja.graphqltodo.util.Constants.LIST_SCREEN
import com.fireninja.graphqltodo.util.Constants.SPLASH_SCREEN

//sealed class Screen(val route: String) {
//  object Splash : Screen("splash_screen")
//  object List : Screen("list_screen/{action}") {
//    fun passTodoAction(todoAction: Action = Action.NO_ACTION): String {
//      return "todo_list_screen/${todoAction}"
//    }
//  }
//  object Todo : Screen("todo_screen/{todoId}") {
//    fun passTodoId(todoId: Int): String {
//      return "todo_screen/$todoId"
//    }
//  }
//  object Details : Screen("details_screen/{heroId}") {
//    fun passHeroId(heroId: Int): String {
//      return "details_screen/$heroId"
//    }
//  }
//}
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