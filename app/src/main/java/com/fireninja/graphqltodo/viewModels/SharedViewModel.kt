package com.fireninja.graphqltodo.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fireninja.AllTodosQuery
import com.fireninja.graphqltodo.util.Action
import com.fireninja.graphqltodo.util.Constants.MAX_TITLE_LENGTH
import com.fireninja.graphqltodo.util.RequestState
import com.fireninja.graphqltodo.util.SearchAppBarState
import com.fireninja.lib_graphql.domain.use_cases.UseCases
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

  private val _allTasks = MutableStateFlow<RequestState<List<AllTodosQuery.Todo>>>(RequestState.Idle)
  val allTasks: StateFlow<RequestState<List<AllTodosQuery.Todo>>> = _allTasks

  private val _searchedTasks = MutableStateFlow<RequestState<List<AllTodosQuery.Todo>>>(RequestState.Idle)
  val searchedTasks: StateFlow<RequestState<List<AllTodosQuery.Todo>>> = _searchedTasks

//  private val _sortState = MutableStateFlow<RequestState<Priority>>(RequestState.Idle)
//  val sortState: StateFlow<RequestState<Priority>> = _sortState

  init {
//    persistSortingState(Priority.NONE)
    useCases.setAuthTokenUseCase("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJyYXZlYml6ei1hc3Nlc3NtZW50cy1hcHAiLCJpc3MiOiJyYXZlYml6ei1hc3Nlc3NtZW50cy1hcHAiLCJpZCI6Mn0.f0ZJ6U6rBM_dtxENCOaUcQ4PsQ86xJp5n2DfGy9ZB5k")
    getAllTasks()
//    readSortState()
  }

  private fun getAllTasks() {
    _allTasks.value = RequestState.Loading
    try {
      viewModelScope.launch {
        useCases.getAllTasksUseCase().run {
          _allTasks.value = RequestState.Success(this)
        }
      }
    } catch (err: Exception) {
      _allTasks.value = RequestState.Error(err)
    }

  }

//  fun searchDatabase(searchQuery: String) {
//    _searchedTasks.value = RequestState.Loading
//    try {
//      viewModelScope.launch {
//        repository.searchDatabase("%$searchQuery%").collect {
//          _searchedTasks.value = RequestState.Success(it)
//        }
//      }
//    } catch (err: Exception) {
//      _searchedTasks.value = RequestState.Error(err)
//    }
//    searchAppBarState.value = SearchAppBarState.TRIGGERED
//  }

//  val lowPriorityTasks: StateFlow<List<AllTodosQuery.Todo>> =
//    repository.sortByLowPriority.stateIn(
//      scope = viewModelScope,
//      started = SharingStarted.WhileSubscribed(),
//      emptyList()
//    )

//  val highPriorityTasks: StateFlow<List<AllTodosQuery.Todo>> =
//    repository.sortByHighPriority.stateIn(
//      scope = viewModelScope,
//      started = SharingStarted.WhileSubscribed(),
//      emptyList()
//    )

//  private fun readSortState() {
//    _sortState.value = RequestState.Loading
//    try {
//      viewModelScope.launch {
//        dataStoreRepository.readSortState
//          .map { Priority.valueOf(it) }
//          .collect {
//            _sortState.value = RequestState.Success(it)
//          }
//      }
//    } catch (e: Exception) {
//      _sortState.value = RequestState.Error(e)
//    }
//  }

//  fun persistSortingState(priority: Priority) {
//    viewModelScope.launch(Dispatchers.IO) {
//      dataStoreRepository.persistSortState(priority)
//    }
//  }

  private val _selectedTask: MutableStateFlow<AllTodosQuery.Todo?> = MutableStateFlow(null)
  val selectedTask: StateFlow<AllTodosQuery.Todo?> = _selectedTask

  fun getSelectedTask(taskId: Int) {
    viewModelScope.launch {
//      repository.getSelectedTask(taskId).collect { task ->
//        _selectedTask.value = task
//      }
    }
  }

  private fun addTask() {
    viewModelScope.launch(Dispatchers.IO) {
      val todoTask = NewTodoParams(
        title = title.value,
        description = description.value
      )
//      repository.addTask(todoTask)
    }
  }

  private fun updateTask() {
    viewModelScope.launch(Dispatchers.IO) {
//      val todoTask = AllTodosQuery.Todo(
//        id = id.value,
//        title = title.value,
//        description = description.value,
//        completed = completed.value
//      )
//      repository.updateTask(todoTask)
    }
  }

  private fun deleteTask() {
    viewModelScope.launch(Dispatchers.IO) {
      val todoTask = AllTodosQuery.Todo(
        id = id.value,
        title = title.value,
        description = description.value,
        completed = completed.value
      )
//      repository.deleteTask(todoTask)
    }
  }

  private fun deleteAllTasks() {
    viewModelScope.launch(Dispatchers.IO) {
//      repository.deleteAllTasks()
//      dataStoreRepository.persistSortState(Priority.NONE)
//      readSortState()
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

//  fun updateTaskFields(selectedTask: AllTodosQuery.Todo?) {
//    if (selectedTask != null) {
//      id.value = selectedTask.id
//      title.value = selectedTask.title
//      description.value = selectedTask.description
//      priority.value = selectedTask.priority
//    } else {
//      id.value = 0
//      title.value = ""
//      description.value = ""
//      priority.value = Priority.LOW
//    }
//  }

  fun updateTitle(newTitle: String) {
    if (newTitle.length < MAX_TITLE_LENGTH) {
      title.value = newTitle
    }
  }

  fun validateFields(): Boolean {
    return title.value.isNotEmpty() && description.value.isNotEmpty()
  }
}