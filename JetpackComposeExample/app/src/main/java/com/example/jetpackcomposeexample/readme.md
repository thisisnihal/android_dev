
### Add new activity
step 1. create a subpackage say `text` inside the main package
step 2. create an activity say `SimpleTextActivity.kt` inside the subpackage `com.example.jetpackcomposeexample.text`
step 3. Add this newly created activity in `AndroidManifest.xml` 
```xml
<application
    android:theme="@style/Theme.Material3.DayNight.NoActionBar"
    android:label="Compose Demo">

    <activity android:name=".MainActivity">
        <intent-filter>
            <action android:name="android.intent.action.MAIN"/>
            <category android:name="android.intent.category.LAUNCHER"/>
        </intent-filter>
    </activity>

    <!-- Activities in different packages -->
    <activity android:name=".text.SimpleTextActivity" />
    <activity android:name=".material.ButtonExampleActivity" />
    <activity android:name=".animation.AnimationExampleActivity" />

</application>
```  

`MainActivity.kt`
```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MainScreen() }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current

    val examples = listOf(
        "Simple Text Example" to SimpleTextActivity::class.java,
        "Button Material Example" to ButtonExampleActivity::class.java,
        "Animation Example" to AnimationExampleActivity::class.java
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(examples.size) { i ->
            Button(
                onClick = {
                    context.startActivity(Intent(context, examples[i].second))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(examples[i].first)
            }
        }
    }
}
```

`SimpleTextActivity.kt`
```kotlin
package com.example.jetpackcomposeexample.text

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text

class SimpleTextActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Text("This is Simple Text Example!") }
    }
}
```