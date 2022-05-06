package com.fireninja.graphqltodo.viewModels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.Optional
import com.fireninja.AllTodosQuery
import com.fireninja.graphqltodo.util.Action
import com.fireninja.graphqltodo.util.Constants.MAX_TITLE_LENGTH
import com.fireninja.graphqltodo.util.RequestState
import com.fireninja.graphqltodo.util.SearchAppBarState
import com.fireninja.lib_graphql.domain.models.Task
import com.fireninja.lib_graphql.domain.use_cases.UseCases
import com.fireninja.type.EditTodoParams
import com.fireninja.type.NewTodoParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
  private val useCases: UseCases,
) : ViewModel() {
  val id: MutableState<Int> = mutableStateOf(0)
  val title: MutableState<String> = mutableStateOf("")
  val description: MutableState<String> = mutableStateOf("")
  val completed: MutableState<Boolean> = mutableStateOf(false)

  val action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)

  val searchAppBarState: MutableState<SearchAppBarState> =
    mutableStateOf(SearchAppBarState.CLOSED)
  val searchTextState: MutableState<String> = mutableStateOf("")

  private val _allTasks =
    MutableStateFlow<RequestState<MutableList<Task>>>(
      RequestState.Idle)
  val allTasks: StateFlow<RequestState<List<Task>>> = _allTasks

  private val _searchedTasks =
    MutableStateFlow<RequestState<List<Task>>>(RequestState.Idle)
  val searchedTasks: StateFlow<RequestState<List<Task>>> =
    _searchedTasks

  init {
    getAllTasks()
  }

  private fun getAllTasks() {
    _allTasks.value = RequestState.Loading
    try {
      viewModelScope.launch {
        useCases.getAllTasksUseCase().run {
          _allTasks.value = RequestState.Success(this.toMutableList())
        }
      }
    } catch (err: Exception) {
      _allTasks.value = RequestState.Error(err)
    }

  }

  fun searchDatabase(searchQuery: String) {
    _searchedTasks.value = RequestState.Loading
    try {
      viewModelScope.launch {
        // Todo
      }
    } catch (err: Exception) {
      _searchedTasks.value = RequestState.Error(err)
    }
    searchAppBarState.value = SearchAppBarState.TRIGGERED
  }

  private val _selectedTask: MutableStateFlow<Task?> =
    MutableStateFlow(null)
  val selectedTask: StateFlow<Task?> = _selectedTask

  fun getSelectedTask(taskId: Int) {
    viewModelScope.launch {
      if (taskId != -1) {
        _selectedTask.value = useCases.getTaskByIdUseCase(taskId)
      }
    }
  }

  private fun addTask() {
    viewModelScope.launch(Dispatchers.IO) {
      val todoTask = NewTodoParams(
        title = title.value,
        description = description.value
      )
      val todo = useCases.addNewTaskUseCase(todoTask)
      when (_allTasks.value) {
        is RequestState.Success -> {
          (_allTasks.value as RequestState.Success<MutableList<Task>>)
            .data.add(todo)
        }
        else -> {}
      }
    }
  }

  private fun updateTask() {
    viewModelScope.launch(Dispatchers.IO) {
      val todoTask = EditTodoParams(
        id = id.value,
        title = Optional.presentIfNotNull(title.value),
        description = Optional.presentIfNotNull(description.value),
        completed = Optional.presentIfNotNull(completed.value)
      )
      Log.d("RemoteDataSource", "updateTask: $todoTask")
      val task = useCases.editTaskUseCase(todoTask)
      _allTasks.value.apply {
        if (this is RequestState.Success) {
          this.data.map {
            if (it.id == todoTask.id) {
              it.title = task.title
              it.description = task.description
            }
          }
        }
      }
    }
  }

  private fun deleteTask() {
    viewModelScope.launch(Dispatchers.IO) {
      useCases.deleteTaskUseCase(id.value)
      _allTasks.value.apply {
        if (this is RequestState.Success) {
          this.data.filter { it.id == id.value }
        }
      }
    }
  }

  private fun deleteAllTasks() {
    viewModelScope.launch(Dispatchers.IO) {
      // Todo
    }
  }

  fun handleDatabaseActions(action: Action) {
    when (action) {
      Action.ADD -> {
        addTask()
      }
      Action.UPDATE -> {
        updateTask()
      }
      Action.DELETE -> {
        deleteTask()
      }
      Action.DELETE_ALL -> {
        deleteAllTasks()
      }
      Action.UNDO -> {
        addTask()
      }
      else -> {
      }
    }
  }

  fun updateTaskFields(selectedTask: Task?) {
    if (selectedTask != null) {
      id.value = selectedTask.id
      title.value = selectedTask.title
      description.value = selectedTask.description.toString()
    } else {
      id.value = 0
      title.value = ""
      description.value = ""
    }
  }

  fun updateTitle(newTitle: String) {
    if (newTitle.length < MAX_TITLE_LENGTH) {
      title.value = newTitle
    }
  }

  fun validateFields(): Boolean {
    return title.value.isNotEmpty() && description.value.isNotEmpty()
  }
}