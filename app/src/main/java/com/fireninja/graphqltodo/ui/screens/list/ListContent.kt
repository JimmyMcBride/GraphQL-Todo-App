package com.fireninja.graphqltodo.ui.screens.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fireninja.AllTodosQuery
import com.fireninja.graphqltodo.util.Action
import com.fireninja.graphqltodo.util.RequestState
import com.fireninja.graphqltodo.util.SearchAppBarState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.fireninja.graphqltodo.R
import com.fireninja.graphqltodo.ui.theme.*
import com.fireninja.lib_graphql.domain.models.Task

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ListContent(
  allTasks: RequestState<List<Task>>,
  searchedTasks: RequestState<List<Task>>,
  navigateToTaskScreen: (taskId: Int) -> Unit,
  searchAppBarState: SearchAppBarState,
  onSwipeToDelete: (Action, Task) -> Unit,
) {
  if (allTasks is RequestState.Success) {
    HandleListContent(tasks = allTasks.data, navigateToTaskScreen,
      onSwipeToDelete)
  }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun HandleListContent(
  tasks: List<Task>,
  navigateToTaskScreen: (taskId: Int) -> Unit,
  onSwipeToDelete: (Action, Task) -> Unit,
) {
  if (tasks.isEmpty()) {
    EmptyContent()
  } else {
    DisplayTasks(tasks, navigateToTaskScreen, onSwipeToDelete)
  }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun DisplayTasks(
  tasks: List<Task>,
  navigateToTaskScreen: (taskId: Int) -> Unit,
  onSwipeToDelete: (Action, Task) -> Unit,
) {
  LazyColumn {
    items(
      items = tasks,
      key = { task ->
        task.id
      }
    ) { todoTask ->
      val dismissState = rememberDismissState()
      val dismissDirection = dismissState.dismissDirection
      val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)

      if (isDismissed && dismissDirection == DismissDirection.EndToStart) {
        val scope = rememberCoroutineScope()
        LaunchedEffect(key1 = onSwipeToDelete) {
          scope.launch {
            delay(300)
            onSwipeToDelete(Action.DELETE, todoTask)
          }
        }

      }

      val degrees by animateFloatAsState(
        if (dismissState.targetValue == DismissValue.Default)
          0f
        else
          -45f
      )

      var itemAppeared by remember { mutableStateOf(false) }
      LaunchedEffect(key1 = true) {
        itemAppeared = true
      }

      AnimatedVisibility(
        visible = itemAppeared && !isDismissed,
        enter = expandVertically(
          animationSpec = tween(
            durationMillis = 300
          )
        ),
        exit = shrinkVertically(
          animationSpec = tween(
            durationMillis = 300
          )
        )
      ) {
        SwipeToDismiss(
          state = dismissState,
          directions = setOf(DismissDirection.EndToStart),
          dismissThresholds = { FractionalThreshold(0.2f) },
          background = { RedBackground(degrees) },
          dismissContent = {
            TaskItem(
              todoTask = todoTask,
              navigateToTaskScreen
            )
          }
        )
      }
    }
  }
}

@Composable
fun RedBackground(degrees: Float) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(HighPriorityColor)
      .padding(horizontal = LARGEST_PADDING),
    contentAlignment = Alignment.CenterEnd
  ) {
    Icon(
      modifier = Modifier.rotate(degrees),
      imageVector = Icons.Filled.Delete,
      contentDescription = stringResource(id = R.string.delete_action),
      tint = Color.White
    )
  }
}

@ExperimentalMaterialApi
@Composable
fun TaskItem(
  todoTask: Task,
  navigateToTaskScreen: (taskId: Int) -> Unit,
) {
  Surface(
    modifier = Modifier.fillMaxWidth(),
    color = MaterialTheme.colors.taskItemBackgroundColor,
    shape = RectangleShape,
    elevation = TASK_ITEM_ELEVATION,
    onClick = {
      todoTask.id?.let { navigateToTaskScreen(it) }
    }
  ) {
    Column(
      modifier = Modifier
        .padding(all = LARGE_PADDING)
        .fillMaxWidth()
    ) {
      Row {
        todoTask.title?.let {
          Text(
            text = it,
            color = MaterialTheme.colors.taskItemTextColor,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            modifier = Modifier.weight(8f)
          )
        }
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
          contentAlignment = Alignment.TopEnd
        ) {
          Canvas(
            modifier = Modifier
              .width(PRIORITY_INDICATOR_SIZE)
              .height(
                PRIORITY_INDICATOR_SIZE
              )
          ) {
            drawCircle(
              color = HighPriorityColor
            )
          }
        }
      }
      todoTask.description?.let {
        Text(
          text = it,
          modifier = Modifier.fillMaxWidth(),
          color = MaterialTheme.colors.taskItemTextColor,
          style = MaterialTheme.typography.subtitle1,
          maxLines = 2,
          overflow = TextOverflow.Ellipsis
        )
      }
    }
  }
}

@ExperimentalMaterialApi
@Composable
@Preview
fun TaskItemPreview() {
  TaskItem(
    todoTask = Task(0, "Title", "Some random text...", false),
    navigateToTaskScreen = {}
  )
}

@Composable
@Preview
private fun RedBackgroundPreview() {
  Column(modifier = Modifier.height(75.dp)) {
    RedBackground(0f)
  }
}