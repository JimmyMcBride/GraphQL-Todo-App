package com.fireninja.graphqltodo.navigation.destinations

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.fireninja.graphqltodo.ui.screens.list.ListScreen
import com.fireninja.graphqltodo.util.Action
import com.fireninja.graphqltodo.util.Constants.LIST_ARGUMENT_KEY
import com.fireninja.graphqltodo.util.Constants.LIST_SCREEN
import com.fireninja.graphqltodo.util.toAction
import com.fireninja.graphqltodo.viewModels.SharedViewModel
import com.google.accompanist.navigation.animation.composable

@ExperimentalAnimationApi
@ExperimentalMaterialApi
fun NavGraphBuilder.listComposable(
  navigateToTaskScreen: (taskId: Int) -> Unit,
  sharedViewModel: SharedViewModel
) {
  composable(
    route = LIST_SCREEN,
    arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
      type = NavType.StringType
    })
  ) { navBackStackEntry ->
    val action = navBackStackEntry.arguments?.getString(LIST_ARGUMENT_KEY).toAction()

    var myAction by rememberSaveable { mutableStateOf(Action.NO_ACTION) }

    LaunchedEffect(key1 = myAction) {
      Log.d("listComposable", "MyAction: $myAction, Action: $action")
      if(action != myAction){
        myAction = action
        sharedViewModel.action.value = action
      }
    }

    val databaseAction by sharedViewModel.action

    ListScreen(
      action = databaseAction,
      navigateToTaskScreen = navigateToTaskScreen,
      sharedViewModel = sharedViewModel
    )
  }
}