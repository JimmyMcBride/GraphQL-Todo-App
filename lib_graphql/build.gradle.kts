plugins {
  id("com.android.library")
  id("org.jetbrains.kotlin.android")
  id("com.apollographql.apollo3").version(Versions.apollo)
  id("dagger.hilt.android.plugin")
  id("kotlin-kapt")
}

android {
  compileSdk = 32

  defaultConfig {
    minSdk = 23
    targetSdk = 32

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  kotlinOptions {
    jvmTarget = Versions.jvm
  }
}

apollo {
  packageName.set("com.fireninja")
  generateKotlinModels.set(true)
  generateOptionalOperationVariables.set(false)
}

dependencies {

  implementation("androidx.core:core-ktx:1.7.0")
  implementation("androidx.appcompat:appcompat:1.4.1")
  implementation("com.google.android.material:material:1.5.0")
  testImplementation("junit:junit:4.13.2")
  androidTestImplementation("androidx.test.ext:junit:1.1.3")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

  implementation("androidx.security:security-crypto:1.1.0-alpha03")

  implementation(Deps.apolloRuntime)
  implementation(Deps.hiltAndroid)
  kapt(Deps.hiltCompiler)
  implementation(Deps.coroutinesCore)
  implementation(Deps.datastorePreferences)
}