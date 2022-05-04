package com.fireninja.graphqltodo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apollographql.apollo3.ApolloClient
import com.fireninja.HelloQuery
import com.fireninja.graphqltodo.ui.theme.GraphQLTodoTheme
import com.fireninja.lib_graphql.utils.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  private val viewModel by viewModels<TestViewModel>()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    lifecycleScope.launch {
      viewModel.getAuthStatus()
    }

    setContent {
      GraphQLTodoTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colors.background) {
          Greeting(viewModel)
        }
      }
    }
  }
  companion object {
    const val TAG = "gql_log"
  }
}

@Composable
fun Greeting(viewModel: TestViewModel) {
  val message = viewModel.message.collectAsState()
  val scope = rememberCoroutineScope()
  Column {
    Text(text = message.value)
    Button(onClick = {
      scope.launch {
        viewModel.setToken("")
        viewModel.getAuthStatus()
      }
    }) {
      Text(text = "Clear token")
    }
    Button(onClick = {
      scope.launch {
        viewModel.setToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJyYXZlYml6ei1hc3Nlc3NtZW50cy1hcHAiLCJpc3MiOiJyYXZlYml6ei1hc3Nlc3NtZW50cy1hcHAiLCJpZCI6MX0.1NjUGYCtR6QCbpiSof31zOheYhiKHD_deJ1QtkHM6d0")
        viewModel.getAuthStatus()
      }
    }) {
      Text(text = "Set token")
    }
  }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//  GraphQLTodoTheme {
//    Greeting("Android")
//  }
//}