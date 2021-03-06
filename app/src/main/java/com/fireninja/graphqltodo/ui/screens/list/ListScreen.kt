package com.fireninja.graphqltodo.ui.screens.list

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.fireninja.graphqltodo.ui.theme.fabBackgroundColor
import com.fireninja.graphqltodo.util.Action
import com.fireninja.graphqltodo.util.SearchAppBarState
import com.fireninja.graphqltodo.viewModels.SharedViewModel
import kotlinx.coroutines.launch
import com.fireninja.graphqltodo.R

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ListScreen(
  navigateToTaskScreen: (taskId: Int) -> Unit,
  sharedViewModel: SharedViewModel,
  action: Action
) {
  LaunchedEffect(key1 = action) {
    Log.d("ListScreen", "ListScreen: $action")
    sharedViewModel.handleDatabaseActions(action)
  }

  val allTasks = sharedViewModel.allTasks.collectAsState()
  val searchedTasks = sharedViewModel.searchedTasks.collectAsState()

  val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
  val searchTextState: String by sharedViewModel.searchTextState

  val scaffoldState = rememberScaffoldState()

  DisplaySnackBar(
    scaffoldState,
    onComplete = { sharedViewModel.action.value = it },
    taskTitle = sharedViewModel.title.value,
    action = action,
    onUndoClicked = {
      sharedViewModel.action.value = it
    }
  )

  Scaffold(
    scaffoldState = scaffoldState,
    topBar = {
      ListAppBar(
        sharedViewModel,
        searchAppBarState,
        searchTextState
      )
    },
    content = {
      ListContent(
        allTasks = allTasks.value,
        searchedTasks = searchedTasks.value,
        navigateToTaskScreen,
        searchAppBarState,
        onSwipeToDelete = { action, todoTask ->
          sharedViewModel.action.value = action
          Log.d("RemoteDataSource", "ListScreen: $todoTask")
          sharedViewModel.updateTaskFields(todoTask)
          scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
        }
      )
    },
    floatingActionButton = {
      ListFab(navigateToTaskScreen = navigateToTaskScreen)
    }
  )
}

@Composable
fun ListFab(
  navigateToTaskScreen: (taskId: Int) -> Unit
) {
  FloatingActionButton(
    onClick = {
      navigateToTaskScreen(-1)
    },
    backgroundColor = MaterialTheme.colors.fabBackgroundColor
  ) {
    Icon(
      imageVector = Icons.Filled.Add,
      contentDescription = stringResource(id = R.string.add_button),
      tint = Color.White
    )
  }
}

@Composable
fun DisplaySnackBar(
  scaffoldState: ScaffoldState,
  onComplete: (Action) -> Unit,
  taskTitle: String,
  action: Action,
  onUndoClicked: (Action) -> Unit
) {
  val scope = rememberCoroutineScope()

  LaunchedEffect(key1 = action) {
    if (action != Action.NO_ACTION) {
      scope.launch {
        val snackBarResult =
          scaffoldState.snackbarHostState.showSnackbar(
            message = setMessage(action, taskTitle),
            actionLabel = setActionLabel(action)
          )
        undoDeletedTask(
          action,
          snackBarResult,
          onUndoClicked
        )
      }
      onComplete(Action.NO_ACTION)
    }
  }
}

private fun setMessage(
  action: Action,
  taskTitle: String
): String {
  return when (action) {
    Action.DELETE_ALL -> "All tasks have been removed."
    else ->
      "Successful ${action.name.lowercase()} for task: $taskTitle"
  }
}

private fun setActionLabel(action: Action): String {
  return if (action.name == "DELETE") {
    "UNDO"
  } else {
    "OK"
  }
}

private fun undoDeletedTask(
  action: Action,
  snackBarResult: SnackbarResult,
  onUndoClicked: (Action) -> Unit
) {
  if (snackBarResult == SnackbarResult.ActionPerformed && action == Action.DELETE) {
    onUndoClicked(Action.UNDO)
  }
}