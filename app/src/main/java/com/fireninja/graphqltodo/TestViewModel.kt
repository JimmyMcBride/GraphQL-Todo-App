package com.fireninja.graphqltodo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.fireninja.lib_graphql.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(private val useCases: UseCases) : ViewModel() {
  val state: MutableState<String> = mutableStateOf("")
  private val _message = MutableStateFlow("")
  val message: StateFlow<String> = _message

  suspend fun getAuthStatus() {
    _message.value = useCases.getAuthStatusUseCase() ?: ""
  }

  fun setToken(token: String) {
    useCases.setAuthTokenUseCase(token)
  }
}