plugins {
    alias(libs.plugins.android.application)

    // KHÔNG dùng kotlin-kapt nếu chỉ dùng Java
}

android {
    namespace = "com.example.doanandroid"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.doanandroid"
        minSdk = 24
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
}

dependencies {
    val roomVersion = "2.6.1"
    // Thư viện Lifecycle (bắt buộc)
    val lifecycleVersion = "2.6.2"  // Kiểm tra phiên bản mới nhất tại: https://developer.android.com/jetpack/androidx/releases/lifecycle
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion") // Dùng annotationProcessor thay vì kapt

    // AndroidX Core
    implementation("androidx.core:core:1.12.0")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")





    implementation("androidx.lifecycle:lifecycle-viewmodel:$lifecycleVersion")  // ViewModel
    implementation("androidx.lifecycle:lifecycle-livedata:$lifecycleVersion")   // LiveData
    implementation("androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion") // Hỗ trợ Java 8

    // Optional: ProcessLifecycleOwner (theo dõi lifecycle toàn app)
    // implementation("androidx.lifecycle:lifecycle-process:$lifecycleVersion")

    // Optional: ReactiveStreams với LiveData
    // implementation("androidx.lifecycle:lifecycle-reactivestreams:$lifecycleVersion")
}