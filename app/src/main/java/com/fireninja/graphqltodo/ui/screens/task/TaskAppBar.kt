package com.fireninja.graphqltodo.ui.screens.task

import android.util.Log
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.fireninja.graphqltodo.util.Action
import com.fireninja.graphqltodo.R
import com.fireninja.graphqltodo.ui.components.DisplayAlertDialog
import com.fireninja.graphqltodo.ui.theme.topAppBarBackgroundColor
import com.fireninja.graphqltodo.ui.theme.topAppBarContentColor
import com.fireninja.lib_graphql.domain.models.Task

@Composable
fun TaskAppBar(
  navigateToListScreen: (Action) -> Unit,
  selectedTask: Task?
) {
  Log.d("asdf", "TaskAppBar: ${selectedTask.toString()}")
  if (selectedTask == null) {
    NewTaskAppBar(navigateToListScreen)
  } else {
    ExistingTaskAppBar(
      selectedTask,
      navigateToListScreen
    )
  }

}

@Composable
fun NewTaskAppBar(
  navigateToListScreen: (Action) -> Unit
) {
  TopAppBar(
    navigationIcon = {
      BackAction(onBackClicked = navigateToListScreen)
    },
    title = {
      Text(
        text = stringResource(id = R.string.add_task),
        color = MaterialTheme.colors.topAppBarContentColor
      )
    },
    backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
    actions = {
      AddAction(onAddClicked = navigateToListScreen)
    }
  )
}

@Composable
fun ExistingTaskAppBar(
  selectedTask: Task,
  navigateToListScreen: (Action) -> Unit
) {
  TopAppBar(
    navigationIcon = {
      CloseAction(onCloseClicked = navigateToListScreen)
    },
    title = {
      Text(
        text = selectedTask.title,
        color = MaterialTheme.colors.topAppBarContentColor,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )
    },
    backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
    actions = {
      ExistingTaskAppBarActions(selectedTask, navigateToListScreen)
    }
  )
}

@Composable
fun ExistingTaskAppBarActions(
  selectedTask: Task,
  navigateToListScreen: (Action) -> Unit
) {
  var openDialog by remember {
    mutableStateOf(false)
  }

  DisplayAlertDialog(
    title = stringResource(
      id = R.string.delete_task,
      selectedTask.title
    ),
    message = stringResource(
      id = R.string.delete_task_confirmation,
      selectedTask.title
    ),
    openDialog,
    closeDialog = { openDialog = false },
    onYesClicked = { navigateToListScreen(Action.DELETE) }
  )

  DeleteAction(onDeleteClicked = {
    openDialog = true
  })
  UpdateAction(onUpdateClicked = navigateToListScreen)
}

@Composable
fun CloseAction(
  onCloseClicked: (Action) -> Unit
) {
  IconButton(onClick = { onCloseClicked(Action.NO_ACTION) }) {
    Icon(
      imageVector = Icons.Filled.Close,
      contentDescription = stringResource(id = R.string.close_icon),
      tint = MaterialTheme.colors.topAppBarContentColor
    )
  }
}

@Composable
fun DeleteAction(
  onDeleteClicked: () -> Unit
) {
  IconButton(onClick = { onDeleteClicked() }) {
    Icon(
      imageVector = Icons.Filled.Delete,
      contentDescription = stringResource(id = R.string.delete_action),
      tint = MaterialTheme.colors.topAppBarContentColor
    )
  }
}

@Composable
fun UpdateAction(
  onUpdateClicked: (Action) -> Unit
) {
  IconButton(onClick = {
    onUpdateClicked(Action.UPDATE)
    Log.d("UpdateAction", "UpdateAction")
  }) {
    Icon(
      imageVector = Icons.Filled.Check,
      contentDescription = stringResource(id = R.string.update_action),
      tint = MaterialTheme.colors.topAppBarContentColor
    )
  }
}

@Composable
fun BackAction(
  onBackClicked: (Action) -> Unit
) {
  IconButton(onClick = { onBackClicked(Action.NO_ACTION) }) {
    Icon(
      imageVector = Icons.Filled.ArrowBack,
      contentDescription = stringResource(id = R.string.back_arrow),
      tint = MaterialTheme.colors.topAppBarContentColor
    )
  }
}

@Composable
fun AddAction(
  onAddClicked: (Action) -> Unit
) {
  IconButton(onClick = { onAddClicked(Action.ADD) }) {
    Icon(
      imageVector = Icons.Filled.Check,
      contentDescription = stringResource(id = R.string.add_task),
      tint = MaterialTheme.colors.topAppBarContentColor
    )
  }
}

@Composable
@Preview
fun NewTaskAppBarPreview() {
  TaskAppBar(navigateToListScreen = {}, null)
}

@Composable
@Preview
fun ExistingTaskAppBarPreview() {
  ExistingTaskAppBar(
    selectedTask = Task(
      0,
      "Heal",
      "Don't do crazy shit until you have healed!",
      false
    ),
    navigateToListScreen = {}
  )
}