plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.testinicial"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.testinicial"
        minSdk = 24
        targetSdk = 35
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    dependencies {
        implementation(libs.androidx.core.ktx.v1120)
        implementation(libs.androidx.lifecycle.runtime.ktx.v262)
        implementation(libs.androidx.activity.compose.v182)

        implementation(libs.ui)
        implementation(libs.ui.graphics)
        implementation(libs.ui.tooling.preview)
        implementation(libs.androidx.material3.v120)
        implementation(libs.material.icons.extended)
        implementation(libs.androidx.navigation.compose.v277)
        implementation(libs.retrofit)
        implementation(libs.converter.gson)
        implementation(libs.okhttp)
        implementation(libs.okhttp.urlconnection)

        // Test
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit.v115)
        androidTestImplementation(libs.androidx.espresso.core.v351)
        androidTestImplementation(platform(libs.androidx.compose.bom.v20250500))
        androidTestImplementation(libs.ui.test.junit4)

        debugImplementation(libs.ui.tooling)
        debugImplementation(libs.ui.test.manifest)
    }


}