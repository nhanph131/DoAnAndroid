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
    sourceSets {
        getByName("main") {
            assets {
                srcDirs("src\\main\\assets", "src\\main\\assets")
            }
        }
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




    implementation("com.cloudinary:cloudinary-android:2.3.1")

    implementation ("androidx.fragment:fragment-ktx:1.6.2")




    //implementation("gun0912.ted:tedbottompicker:1.2.6")

    implementation("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")
    //implementation ("com.github.User:Repo:Tag")



}