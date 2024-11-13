package com.romrell4.general_conference_fantasy.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PlayGameScreen(words: List<String>) {
    val sortedWords = words.sorted()
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        items(sortedWords.size) { index ->
            val count = remember(index) { mutableIntStateOf(0) }
            Button(
                modifier = Modifier
                    .padding(8.dp)
                    .height(128.dp)
                    .fillMaxWidth(),
                onClick = {
                    count.intValue++
                },
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        sortedWords[index],
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text("Count: ${count.intValue}", style = MaterialTheme.typography.body2)
                }
            }
        }
    }
}