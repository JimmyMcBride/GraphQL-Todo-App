package com.fireninja.graphqltodo.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.fireninja.graphqltodo.ui.screens.task.TaskScreen
import com.fireninja.graphqltodo.util.Action
import com.fireninja.graphqltodo.util.Constants.TASK_ARGUMENT_KEY
import com.fireninja.graphqltodo.util.Constants.TASK_SCREEN
import com.fireninja.graphqltodo.viewModels.SharedViewModel
import com.google.accompanist.navigation.animation.composable

@ExperimentalAnimationApi
fun NavGraphBuilder.taskComposable(
  navigateToListScreen: (Action) -> Unit,
  sharedViewModel: SharedViewModel
) {
  composable(
    TASK_SCREEN,
    listOf(navArgument(TASK_ARGUMENT_KEY) {
      type = NavType.IntType
    }),
    enterTransition = {
      slideInHorizontally(
        animationSpec = tween(
          durationMillis = 300
        ),
        initialOffsetX = { fullWidth -> -fullWidth }
      )
    }
  ) { navBackStackEntry ->
    val taskId = navBackStackEntry.arguments!!.getInt(TASK_ARGUMENT_KEY)

    val selectedTask by sharedViewModel.selectedTask.collectAsState()

    LaunchedEffect(key1 = taskId) {
      sharedViewModel.getSelectedTask(taskId)
    }

    LaunchedEffect(key1 = selectedTask) {
      if (selectedTask != null || taskId == -1) {
//        sharedViewModel.updateTaskFields(selectedTask)
      }
    }

    TaskScreen(
      navigateToListScreen,
      selectedTask,
      sharedViewModel
    )
  }
}