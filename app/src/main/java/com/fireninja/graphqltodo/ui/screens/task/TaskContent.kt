package com.fireninja.graphqltodo.ui.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fireninja.graphqltodo.ui.theme.LARGE_PADDING
import com.fireninja.graphqltodo.R
import com.fireninja.graphqltodo.ui.theme.MEDIUM_PADDING

@Composable
fun TaskContent(
  title: String,
  onTitleChange: (String) -> Unit,
  description: String,
  onDescriptionChange: (String) -> Unit,
  completed: Boolean,
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colors.background)
      .padding(all = LARGE_PADDING)
  ) {
    OutlinedTextField(
      modifier = Modifier.fillMaxWidth(),
      value = title,
      onValueChange = { onTitleChange(it) },
      label = { Text(text = stringResource(id = R.string.title)) },
      textStyle = MaterialTheme.typography.body1,
      singleLine = true
    )
    Divider(
      modifier = Modifier.height(MEDIUM_PADDING),
      color = MaterialTheme.colors.background
    )
    OutlinedTextField(
      modifier = Modifier.fillMaxSize(),
      value = description,
      onValueChange = {
        onDescriptionChange(it)
      },
      label = { Text(text = stringResource(id = R.string.description)) },
      textStyle = MaterialTheme.typography.body1
    )
  }
}

@Composable
@Preview
private fun TaskContentPreview() {
  TaskContent(
    title = "",
    onTitleChange = {},
    description = "",
    onDescriptionChange = {},
    completed = false,
  )
}