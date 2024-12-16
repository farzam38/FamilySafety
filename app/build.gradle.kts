plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("org.jetbrains.kotlin.kapt") // Required for Kotlin annotation processing
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.familysafety"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.familysafety"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

}


dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.room.common)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Ola Maps SDK
    implementation(files("libs/olamaps.aar"))

    // Maplibre (choose one version, preferably the latest)
    implementation("org.maplibre.gl:android-sdk:10.2.0") // Using 10.2.0
    implementation("org.maplibre.gl:android-plugin-annotation-v9:1.0.0")
    implementation("org.maplibre.gl:android-plugin-markerview-v9:1.0.0")

    // Required for Ola-MapsSdk (remove redundant entries)
    implementation("com.moengage:moe-android-sdk:12.6.01")
    implementation("org.maplibre.gl:android-sdk-directions-models:5.9.0")
    implementation("org.maplibre.gl:android-sdk-services:5.9.0")
    implementation("org.maplibre.gl:android-sdk-turf:5.9.0")

    // Lifecycle components (replace deprecated lifecycle-extensions)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2") // Updated version
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2") // Updated version

    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")


// Room Components
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

// LiveData & Lifecycle
    val lifecycle_version = "2.6.1" // Updated to the latest compatible version
    val arch_version = "2.2.0"

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version")
    kapt("androidx.lifecycle:lifecycle-compiler:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-common-java8:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-service:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-process:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-reactivestreams-ktx:$lifecycle_version")
    testImplementation("androidx.arch.core:core-testing:$arch_version")
    testImplementation("androidx.lifecycle:lifecycle-runtime-testing:$lifecycle_version")

// Material Components
    implementation("com.google.android.material:material:1.9.0")

// Coroutines
    val coroutine_version = "1.7.3" // Upgraded to the latest version
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutine_version")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutine_version")

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))


    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")

    implementation ("com.google.firebase:firebase-auth")
    implementation ("com.google.android.gms:play-services-auth")


    // Add the dependencies for any other desired Firebase products

// Testing Libraries
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.6") // Upgraded to the latest version
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1") // Latest stable version

    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))

    // Declare the dependency for the Cloud Firestore library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-firestore")

    implementation ("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.1")

    implementation ("com.google.android.gms:play-services-auth:21.3.0")
    implementation ("com.google.firebase:firebase-auth:21.0.8" )
    implementation ("com.google.firebase:firebase-firestore:24.4.3")
    implementation ("com.google.firebase:firebase-database:20.2.5")
    implementation ("com.google.android.material:material:1.9.0")
    implementation (libs.play.services.location)


}