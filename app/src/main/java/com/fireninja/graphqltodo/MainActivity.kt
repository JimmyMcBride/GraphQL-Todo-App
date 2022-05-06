package com.fireninja.graphqltodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.navigation.NavHostController
import com.fireninja.graphqltodo.navigation.SetupNavigation
import com.fireninja.graphqltodo.ui.theme.GraphQLTodoTheme
import com.fireninja.graphqltodo.viewModels.SharedViewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

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
