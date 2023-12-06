



private object Versions {
     const val core = "1.9.0"
     const val lifeCycle = "2.6.2"
     const val activity = "1.8.1"
     const val junit = "4.13.2"
     const val extJunit = "1.1.5"
     const val expressoCore = "3.5.1"
     const val viewModel = "2.6.2"
     const val navigation = "2.7.5"
     const val flowLayout = "0.17.0"
     const val coroutines = "1.7.3"
     const val dagger = "2.44"
     const val hiltNaviation = "1.1.0"
     const val retrofit = "2.9.0"
     const val okHttp3 = "5.0.0-alpha.2"
     const val coil = "2.5.0"
     const val paging = "3.2.1"
     const val landScapistCoil = "1.3.6"
     const val ratingBar = "1.2.3"
     const val json = "2.9.0"

}


object Deps{
     const val core = "androidx.core:core-ktx:${Versions.core}"
     const val lifeCycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifeCycle}"
     const val activityCompose = "androidx.activity:activity-compose:${Versions.activity}"

}

object ComposeUi{
     const val ui = "androidx.compose.ui:ui"
     const val graphics = "androidx.compose.ui:ui-graphics"
     const val toolingPreview = "androidx.compose.ui:ui-tooling-preview"
     const val material3 = "androidx.compose.material3:material3"
}

object TestImpl{
     const val junit = "junit:junit:${Versions.junit}"
     const val extJunit = "androidx.test.ext:junit:${Versions.extJunit}"
     const val espressCore = "androidx.test.espresso:espresso-core:${Versions.expressoCore}"
     const val uiTestUnit4 = "androidx.compose.ui:ui-test-junit4"
     const val uiTooling = "androidx.compose.ui:ui-tooling"
     const val uiManifist = "androidx.compose.ui:ui-test-manifest"
     const val composeBom = "androidx.compose:compose-bom:2023.03.00"
}


object Compose{
     const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.viewModel}"
     const val navigation = "androidx.navigation:navigation-compose:${Versions.navigation}"
     const val flowlayout = "com.google.accompanist:accompanist-flowlayout:${Versions.flowLayout}"
}

object Corotiens {
     const val core =  "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
     const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
}

object DaggerHilt{
     const val dagger = "com.google.dagger:hilt-android:${Versions.dagger}"
     const val kapCompiler = "com.google.dagger:hilt-android-compiler:${Versions.dagger}"
     const val navigation = "androidx.hilt:hilt-navigation-compose:${Versions.hiltNaviation}"
}

object Retrofit{
     const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
     const val convertorGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
     const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp3}"
     const val interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp3}"
}


object Coil{
     const val coil = "io.coil-kt:coil-compose:${Versions.coil}"
}


object Paging{
     const val paging = "androidx.paging:paging-runtime:${Versions.paging}"
     const val compose = "androidx.paging:paging-compose:${Versions.paging}"
}

object LandScapistCoil {
     const val coil =  "com.github.skydoves:landscapist-coil:${Versions.landScapistCoil}"
}

object RatingBar {
     const val lib = "io.github.a914-gowtham:compose-ratingbar:${Versions.ratingBar}"
}

object Json {
     const val lib = "com.google.code.gson:gson:${Versions.json}"
}




