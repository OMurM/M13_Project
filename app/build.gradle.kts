plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.m13_project"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.m13_project"
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
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.coil)
    implementation(libs.retrofit2.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.paging.runtime)
    implementation(libs.volley)
    implementation(libs.squareup.picasso)
    implementation(libs.lifecycle.viewmodel.android)
    implementation(libs.glide)
    implementation (libs.play.services.base)
    annotationProcessor(libs.compiler.v4120)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}