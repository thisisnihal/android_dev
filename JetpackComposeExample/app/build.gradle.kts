plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.example.jetpackcomposeexample"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.jetpackcomposeexample"
        minSdk = 24
        targetSdk = 36
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)


    // navigation lib
    val nav_version = "2.9.6"
    implementation("androidx.navigation:navigation-compose:$nav_version")

    // material icons
    implementation("androidx.compose.material:material-icons-extended")

    //
    implementation ("androidx.compose.material:material")

    // for Image --------------

    // https://github.com/bumptech/glide    (image downloading caching)
    implementation("com.github.bumptech.glide:glide:5.0.5")

    // https://github.com/square/picasso
    implementation("com.squareup.picasso:picasso:2.8")

    // https://coil-kt.github.io/coil/compose/  (refer this good doc)

    // implementation("io.coil-kt.coil3:coil-network-okhttp:3.3.0")
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("io.coil-kt:coil-svg:2.6.0")
    implementation("io.coil-kt:coil-gif:2.6.0")


    // room db
    val room_version = "2.8.3"
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // Datastore
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // SAF
    implementation("androidx.documentfile:documentfile:1.1.0")


}