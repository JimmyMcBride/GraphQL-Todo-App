import Versions.accompanist
import Versions.apollo
import Versions.coroutines
import Versions.datastore
import Versions.hilt

object Versions {
  const val gradle = "7.1.3"
  const val kotlin = "1.5.31"
  const val jvm = "11"
  const val compose = "1.0.5"
  const val apollo = "3.2.1"
  const val hilt = "2.41"
  const val coroutines = "1.6.1"
  const val accompanist = "0.21.4-beta"
  const val datastore = "1.0.0"
}
object Deps {
  const val apolloRuntime = "com.apollographql.apollo3:apollo-runtime:$apollo"
  // Hilt
  const val hiltAndroid = "com.google.dagger:hilt-android:$hilt"
  const val hiltCompiler = "com.google.dagger:hilt-compiler:$hilt"
  // Coroutines
  const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines"
  // Accompanist Navigation Animation
  const val accompanistNavigationAnimation = "com.google.accompanist:accompanist-navigation-animation:$accompanist"
  // Datastore dependencies
  const val datastorePreferences = "androidx.datastore:datastore-preferences:$datastore"
}