plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.nhanph.doanandroid"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.nhanph.doanandroid"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        viewBinding = true
    }

}

dependencies {
    implementation(libs.lombok)
    annotationProcessor(libs.lombok)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.appcompat)
    implementation(libs.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)


    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.v115)

    implementation(libs.lifecycle.viewmodel)  // ViewModel
    implementation(libs.lifecycle.livedata)   // LiveData
    implementation(libs.lifecycle.common.java8) // Hỗ trợ Java 8

    implementation(libs.glide)
    annotationProcessor(libs.compiler)

    implementation (libs.swiperefreshlayout)

    //cloudinary android lib luu anh
    implementation(libs.cloudinary.android)
    implementation(libs.constraintlayout.v214)
    implementation(libs.navigation.fragment.v277)
    implementation(libs.navigation.ui.v277)

    implementation("com.github.amarjain07:StickyScrollView:1.0.3")

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
}