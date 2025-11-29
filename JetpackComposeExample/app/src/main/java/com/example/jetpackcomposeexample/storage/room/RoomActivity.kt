package com.example.jetpackcomposeexample.storage.room


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.jetpackcomposeexample.ui.theme.JetpackComposeExampleTheme
import kotlinx.coroutines.launch

class RoomActivity : ComponentActivity() {

    private val dao by lazy { NoteDatabase.get(this).noteDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            JetpackComposeExampleTheme {
                val notes by dao.getAll().collectAsState(initial = emptyList())

                // Text fields for CRUD
                var title by remember { mutableStateOf("") }
                var content by remember { mutableStateOf("") }

                // If editing a note
                var editingNote by remember { mutableStateOf<Note?>(null) }

                Column(Modifier.padding(16.dp)) {

                    Text(
                        "Room Storage CRUD",
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(Modifier.height(12.dp))

                    // ---------- INPUT FIELDS ----------
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = content,
                        onValueChange = { content = it },
                        label = { Text("Content") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(12.dp))

                    // ---------- ADD OR UPDATE BUTTON ----------
                    Button(
                        onClick = {
                            lifecycleScope.launch {
                                if (editingNote == null) {
                                    // ADD
                                    dao.insert(Note(title = title, content = content))
                                } else {
                                    // UPDATE
                                    dao.update(
                                        editingNote!!.copy(
                                            title = title,
                                            content = content
                                        )
                                    )
                                    editingNote = null
                                }
                                title = ""
                                content = ""
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (editingNote == null) "Add Note" else "Update Note")
                    }

                    Spacer(Modifier.height(20.dp))

                    Divider()

                    Spacer(Modifier.height(10.dp))

                    // ---------- NOTES LIST ----------
                    LazyColumn {
                        items(notes) { note ->
                            NoteItem(
                                note = note,
                                onEdit = {
                                    editingNote = note
                                    title = note.title
                                    content = note.content
                                },
                                onDelete = {
                                    lifecycleScope.launch { dao.delete(note) }
                                }
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun NoteItem(
    note: Note,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(12.dp)) {

            Text(note.title, style = MaterialTheme.typography.titleMedium)
            Text(note.content, style = MaterialTheme.typography.bodyMedium)

            Spacer(Modifier.height(8.dp))

            Row {
                Button(
                    onClick = onEdit,
                    modifier = Modifier.weight(1f)
                ) { Text("Edit") }

                Spacer(Modifier.width(8.dp))

                Button(
                    onClick = onDelete,
                    modifier = Modifier.weight(1f)
                ) { Text("Delete") }
            }
        }
    }
}
