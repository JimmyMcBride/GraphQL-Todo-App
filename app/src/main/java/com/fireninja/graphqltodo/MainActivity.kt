package com.fireninja.graphqltodo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.ApolloClient
import com.fireninja.HelloQuery
import com.fireninja.graphqltodo.ui.theme.GraphQLTodoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  @Inject lateinit var apolloClient: ApolloClient
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val state: MutableState<String> = mutableStateOf("")

    lifecycleScope.launch {
      val response = apolloClient.query(HelloQuery()).execute()
      Log.d(TAG, "onCreate: ${response.data?.hello}")
//      response.data?.hello?.let {
//        state.value = it
//      }
    }

    setContent {
      GraphQLTodoTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colors.background) {
          Greeting("Android")
        }
      }
    }
  }
  companion object {
    const val TAG = "gql_log"
  }
}

@Composable
fun Greeting(name: String) {
  Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  GraphQLTodoTheme {
    Greeting("Android")
  }
}