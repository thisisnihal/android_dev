package com.example.jetpackcomposeexample.storage.datastore


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.jetpackcomposeexample.ui.theme.JetpackComposeExampleTheme
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


/*
* Dependencies Needed
*  implementation("androidx.datastore:datastore-preferences:1.1.1")
*/

private val ComponentActivity.dataStore by preferencesDataStore("user_prefs")

class DataStoreActivity : ComponentActivity() {

    // preference keys
    private val KEY_USERNAME = stringPreferencesKey("username")
    private val KEY_AGE = intPreferencesKey("age")
    private val KEY_DARK = booleanPreferencesKey("dark_mode")
    private val KEY_SCORE = floatPreferencesKey("score")
    private val KEY_PREMIUM = booleanPreferencesKey("is_premium")
    private val KEY_COUNTRY = stringPreferencesKey("country")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefsFlow = dataStore.data.map { prefs ->
            UserPrefs(
                username = prefs[KEY_USERNAME] ?: "",
                age = prefs[KEY_AGE] ?: 0,
                dark = prefs[KEY_DARK] ?: false,
                score = prefs[KEY_SCORE] ?: 0f,
                premium = prefs[KEY_PREMIUM] ?: false,
                country = prefs[KEY_COUNTRY] ?: ""
            )
        }

        setContent {
            JetpackComposeExampleTheme {
                val prefs by prefsFlow.collectAsState(initial = UserPrefs())

                // input fields
                var username by remember { mutableStateOf(prefs.username) }
                var age by remember { mutableStateOf(prefs.age.toString()) }
                var score by remember { mutableStateOf(prefs.score.toString()) }
                var country by remember { mutableStateOf(prefs.country) }

                Column(Modifier.padding(16.dp)) {

                    Text(
                        "DataStore: Key-Value Storage",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(Modifier.height(12.dp))

                    // ---------------- INPUT FIELDS ----------------
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Username (String)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = age,
                        onValueChange = { age = it },
                        label = { Text("Age (Int)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = score,
                        onValueChange = { score = it },
                        label = { Text("Score (Float)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = country,
                        onValueChange = { country = it },
                        label = { Text("Country (String)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(12.dp))

                    // ---------------- SAVE BUTTON ----------------
                    Button(
                        onClick = {
                            lifecycleScope.launch {
                                dataStore.edit {
                                    it[KEY_USERNAME] = username
                                    it[KEY_AGE] = age.toIntOrNull() ?: 0
                                    it[KEY_SCORE] = score.toFloatOrNull() ?: 0f
                                    it[KEY_COUNTRY] = country
                                }
                            }
                        }, modifier = Modifier.fillMaxWidth()
                    ) { Text("Save Values") }

                    Spacer(Modifier.height(10.dp))

                    // ---------------- DARK MODE TOGGLE ----------------
                    Button(
                        onClick = {
                            lifecycleScope.launch {
                                dataStore.edit {
                                    it[KEY_DARK] = !(prefs.dark)
                                }
                            }
                        }, modifier = Modifier.fillMaxWidth()
                    ) { Text("Toggle Dark Mode: ${prefs.dark}") }

                    Spacer(Modifier.height(10.dp))

                    // ---------------- PREMIUM TOGGLE ----------------
                    Button(
                        onClick = {
                            lifecycleScope.launch {
                                dataStore.edit {
                                    it[KEY_PREMIUM] = !(prefs.premium)
                                }
                            }
                        }, modifier = Modifier.fillMaxWidth()
                    ) { Text("Toggle Premium: ${prefs.premium}") }

                    Spacer(Modifier.height(16.dp))

                    Divider()
                    Spacer(Modifier.height(16.dp))

                    // ---------------- DISPLAY STORED VALUES ----------------
                    Text("Stored Values:", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(6.dp))

                    Text("Username: ${prefs.username}")
                    Text("Age: ${prefs.age}")
                    Text("Score: ${prefs.score}")
                    Text("Country: ${prefs.country}")
                    Text("Dark Mode: ${prefs.dark}")
                    Text("Premium: ${prefs.premium}")

                    Spacer(Modifier.height(16.dp))

                    // ---------------- CLEAR BUTTON ----------------
                    Button(
                        onClick = {
                            lifecycleScope.launch {
                                dataStore.edit { it.clear() }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("Clear All Data") }
                }
            }

        }
    }
}

data class UserPrefs(
    val username: String = "",
    val age: Int = 0,
    val dark: Boolean = false,
    val score: Float = 0f,
    val premium: Boolean = false,
    val country: String = ""
)
