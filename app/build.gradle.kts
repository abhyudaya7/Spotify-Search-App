plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    buildFeatures {
        buildConfig = true
    }
    namespace = "com.example.spotifysearchapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.spotifysearchapp"
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
            buildConfigField("String", "CLIENT_ID", "\"d31423194e13489caca69889e28291d5\"")
            buildConfigField("String", "CLIENT_SECRET", "\"314098cc8bd64a8d86c0d976b1ef98db\"")
        }

        debug {
            buildConfigField("String", "CLIENT_ID", "\"d31423194e13489caca69889e28291d5\"")
            buildConfigField("String", "CLIENT_SECRET", "\"314098cc8bd64a8d86c0d976b1ef98db\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    viewBinding.enable = true
}

val projectRetrofitVersion: String by rootProject.extra
val projectRoomVersion: String by rootProject.extra
val projectNavigationVersion: String by rootProject.extra
val projectHiltVersion: String by rootProject.extra

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.navigation:navigation-fragment-ktx:$projectNavigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$projectNavigationVersion")
    implementation("com.squareup.retrofit2:retrofit:$projectRetrofitVersion")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.retrofit2:converter-gson:$projectRetrofitVersion")
    implementation("androidx.room:room-runtime:$projectRoomVersion")
    annotationProcessor("androidx.room:room-compiler:$projectRoomVersion")
    implementation("androidx.room:room-ktx:$projectRoomVersion")
    kapt("androidx.room:room-compiler:$projectRoomVersion")
    implementation("com.google.dagger:hilt-android:$projectHiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$projectHiltVersion")
    implementation("androidx.hilt:hilt-navigation-fragment:1.1.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("com.github.bumptech.glide:glide:4.16.0")
}