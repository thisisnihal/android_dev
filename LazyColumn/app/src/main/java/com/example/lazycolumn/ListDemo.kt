package com.example.lazycolumn

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ListDemo() {
    // SimpleColumn()
    LazyColumnDemo()
}


@Composable
fun LazyColumnDemo() {
    //similar to recycler view, it will render item dynamically
    LazyColumn (content = {
        items(100, itemContent = {
            TextItem(text = "Index $it")
        })
    })

    var myList =  listOf<String>("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "k")
    LazyColumn(content = {
        itemsIndexed(myList, itemContent = { index, item ->
            TextItem(text = "Item $item")
        })
    })
}


@Composable
fun SimpleColumn() {
    var scrollState = rememberScrollState()

    Column(modifier = Modifier.verticalScroll(scrollState)) {
        for (i in 1..100) {
            TextItem("Item $i")
        }
    }
}


@Composable
fun TextItem(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        textAlign = TextAlign.Center,
        fontSize = 20.sp
    )
}