plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id ("kotlin-parcelize")
}

android {
    namespace = "com.mamon.movieapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mamon.movieapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {


    implementation(project(":domain"))


    implementation(Deps.core)
    implementation(Deps.lifeCycle)
    implementation(Deps.activityCompose)
    implementation(platform(TestImpl.composeBom))
    implementation(ComposeUi.ui)
    implementation(ComposeUi.graphics)
    implementation(ComposeUi.toolingPreview)
    implementation(ComposeUi.material3)
    implementation(project(mapOf("path" to ":data")))


    testImplementation(TestImpl.junit)
    androidTestImplementation(TestImpl.extJunit)
    androidTestImplementation(TestImpl.espressCore)
    androidTestImplementation(platform(TestImpl.composeBom))
    androidTestImplementation(TestImpl.uiTestUnit4)
    debugImplementation(TestImpl.uiTooling)
    debugImplementation(TestImpl.uiManifist)


    // dagger hilt
    implementation(DaggerHilt.dagger)
    implementation(DaggerHilt.navigation)
    kapt(DaggerHilt.kapCompiler)


    implementation(Paging.paging)
    implementation(Paging.compose)

    implementation(Coil.coil)

    implementation(LandScapistCoil.coil)

    // Rating Bar
    implementation(RatingBar.lib)

    implementation(Json.lib)

}
