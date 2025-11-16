package com.example.filemanager

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(fileManager: FileManager) {

    var uiState by remember { mutableStateOf(emptyList<Summary>()) }

    // Load summaries initially + whenever recomposed
    LaunchedEffect(Unit) {
        uiState = fileManager.getSummries()
    }

    // Modal bottom sheet
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showSheet by remember { mutableStateOf(false) }

    // Editing state
    var summaryEdit by remember { mutableStateOf(Summary("", "", Type.INTERNAL)) }
    var isEdit by remember { mutableStateOf(false) }

    var type by remember { mutableStateOf(Type.INTERNAL) }
    val scope = rememberCoroutineScope()

    // When sheet closes → reset state
    LaunchedEffect(sheetState.currentValue) {
        if (!sheetState.isVisible) {
            summaryEdit = Summary("", "", Type.INTERNAL)
            isEdit = false
            type = Type.INTERNAL
        }
    }

    // ---------- SHEET UI ----------
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState
        ) {
            Form(
                summary = summaryEdit,
                type = type,
                onTypeChanged = { type = it }
            ) { title, desc ->

                val finalSummary = Summary(
                    fileName = if (title.endsWith(".txt")) title else "$title.txt",
                    summary = desc,
                    type = type
                )

                scope.launch {
                    if (isEdit) {
                        fileManager.update(finalSummary)
                    } else {
                        fileManager.save(finalSummary)
                    }

                    uiState = fileManager.getSummries()
                    sheetState.hide()
                    showSheet = false
                }
            }
        }
    }

    // ---------- MAIN SCREEN ----------
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Summary App") },
                actions = {
                    IconButton(onClick = {
                        isEdit = false
                        summaryEdit = Summary("", "", Type.INTERNAL)
                        type = Type.INTERNAL
                        showSheet = true
                        scope.launch { sheetState.show() }
                    }) {
                        Icon(Icons.Default.Add, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->

        if (uiState.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Nothing found")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                items(uiState) { item ->
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                isEdit = true
                                summaryEdit = item
                                type = item.type
                                showSheet = true
                                scope.launch { sheetState.show() }
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.fileName,
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = item.summary,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    text = item.type.toString(),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                            IconButton(onClick = {
                                scope.launch {
                                    fileManager.delete(item)
                                    uiState = fileManager.getSummries()
                                }
                            }) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = null,
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun Form(
    summary: Summary,
    type: Type,
    onTypeChanged: (Type) -> Unit,
    onClick: (String, String) -> Unit
) {
    val title = remember { mutableStateOf("") }
    val desc = remember { mutableStateOf("") }

    // When editing → populate fields
    LaunchedEffect(summary) {
        title.value = summary.fileName.removeSuffix(".txt")
        desc.value = summary.summary
    }

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title.value,
            onValueChange = { title.value = it },
            singleLine = true,
            label = { Text("Book Name") }
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = desc.value,
            onValueChange = { desc.value = it },
            singleLine = false,
            label = { Text("Summary") }
        )

        Spacer(Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = type == Type.INTERNAL,
                onCheckedChange = { onTypeChanged(Type.INTERNAL) }
            )
            Spacer(Modifier.width(8.dp))
            Text("Internal")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = type == Type.PRIVATE_EXTERNAL,
                onCheckedChange = { onTypeChanged(Type.PRIVATE_EXTERNAL) }
            )
            Spacer(Modifier.width(8.dp))
            Text("Private External")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = type == Type.SHARED,
                onCheckedChange = { onTypeChanged(Type.SHARED) }
            )
            Spacer(Modifier.width(8.dp))
            Text("Shared")
        }

        Spacer(Modifier.height(20.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onClick(title.value, desc.value) }
        ) {
            Text("Save")
        }
    }
}
