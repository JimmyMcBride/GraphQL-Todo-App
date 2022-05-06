package com.fireninja.graphqltodo.ui.screens.task

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.fireninja.AllTodosQuery
import com.fireninja.graphqltodo.util.Action
import com.fireninja.graphqltodo.viewModels.SharedViewModel
import com.fireninja.lib_graphql.domain.models.Task

@Composable
fun TaskScreen(
  navigateToListScreen: (Action) -> Unit,
  selectedTask: Task?,
  sharedViewModel: SharedViewModel
) {
  val title: String by sharedViewModel.title
  val description: String by sharedViewModel.description
  val completed: Boolean by sharedViewModel.completed

  val context = LocalContext.current

  BackHandler {
    navigateToListScreen(Action.NO_ACTION)
  }

  Scaffold(
    topBar = {
      TaskAppBar(
        navigateToListScreen = { action ->
          Log.d("TaskScreen", "TaskScreen: $action")
          if (action == Action.NO_ACTION) {

            navigateToListScreen(action)
          } else {
            if (sharedViewModel.validateFields()) {
              Log.d("TaskScreenNavigate", "TaskScreen: $action")
              navigateToListScreen(action)
            } else {
              displayToast(context = context)
            }
          }
        },
        selectedTask
      )
    },
    content = {
      TaskContent(
        title = title,
        onTitleChange = {
          sharedViewModel.updateTitle(it)
        },
        description = description,
        onDescriptionChange = {
          sharedViewModel.description.value = it
        },
        completed = completed,
      )
    }
  )
}

fun displayToast(context: Context) {
  Toast.makeText(
    context,
    "Fields Empty.",
    Toast.LENGTH_SHORT
  ).show()
}