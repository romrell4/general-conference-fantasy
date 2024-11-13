package com.romrell4.general_conference_fantasy.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import generalconferencefantasy.composeapp.generated.resources.Res
import generalconferencefantasy.composeapp.generated.resources.next_button
import generalconferencefantasy.composeapp.generated.resources.pick_words_instructions
import generalconferencefantasy.composeapp.generated.resources.submit_button_0_words
import generalconferencefantasy.composeapp.generated.resources.submit_button_1_word
import generalconferencefantasy.composeapp.generated.resources.submit_button_n_words
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun WordEntryScreen(onStartTapped: (List<String>) -> Unit) {
    val savedWords = remember { mutableStateListOf<String>() }
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxHeight()
    ) {
        Text(
            text = stringResource(Res.string.pick_words_instructions),
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 32.dp, top = 32.dp).fillMaxWidth(),
            textAlign = TextAlign.Center,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val currentWord = remember { mutableStateOf("") }

            fun onNext() {
                savedWords.add(0, currentWord.value.trim())
                currentWord.value = ""
            }

            OutlinedTextField(
                value = currentWord.value,
                onValueChange = {
                    currentWord.value = it
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { onNext() },
                    onDone = { onNext() },
                )
            )

            Button(enabled = currentWord.value.isNotBlank(), onClick = { onNext() }) {
                Text(stringResource(Res.string.next_button))
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            savedWords.forEachIndexed { index, word ->
                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(word)
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = {
                            savedWords.removeAt(index)
                        }
                    ) {
                        Icon(Icons.Filled.Close, contentDescription = "Delete")
                    }
                }
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth().imePadding(),
            enabled = savedWords.isNotEmpty(),
            onClick = { onStartTapped(savedWords) }
        ) {
            val text = when (savedWords.size) {
                0 -> stringResource(Res.string.submit_button_0_words)
                1 -> stringResource(Res.string.submit_button_1_word)
                else -> stringResource(Res.string.submit_button_n_words, savedWords.size)
            }
            Text(text)
        }
    }
}
