package com.fireninja.graphqltodo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.apollographql.apollo3.ApolloClient
import com.fireninja.HelloQuery
import com.fireninja.graphqltodo.navigation.SetupNavigation
import com.fireninja.graphqltodo.ui.theme.GraphQLTodoTheme
import com.fireninja.graphqltodo.viewModels.SharedViewModel
import com.fireninja.lib_graphql.utils.User
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  private lateinit var navController: NavHostController
  private val sharedViewModel: SharedViewModel by viewModels()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      GraphQLTodoTheme {
        navController = rememberAnimatedNavController()
        SetupNavigation(navController, sharedViewModel)
      }
    }
  }
}
