
- Create two compose screens.
- set up the dependencies (app level gradle)
```kotlin
val nav_version = "2.9.6"
implementation("androidx.navigation:navigation-compose:$nav_version")
```


- NavHost will host all the screens in our Application
- NavHostController will use it to naviagte from one screen to another



https://developer.android.com/jetpack/androidx/releases/compose-runtime
``implementation("androidx.compose.runtime:runtime-livedata:1.9.4")``