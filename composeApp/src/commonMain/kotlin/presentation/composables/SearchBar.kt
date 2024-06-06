package presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import presentation.composables.previews.ThemeProvider
import presentation.theme.ListOfArtsTheme


@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = { onQueryChanged(it) },
        singleLine = true,
        placeholder = { Text(text = "Search"/*stringResource(id = R.string.search)*/) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        trailingIcon = {
            if (query.isBlank()) return@OutlinedTextField
            IconButton(onClick = { onQueryChanged("") }) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null
                )
            }
        },
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = TextFieldDefaults.colors().focusedIndicatorColor.copy(0.5f),
            unfocusedIndicatorColor = TextFieldDefaults.colors().unfocusedIndicatorColor.copy(0.5f),
            cursorColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = Color.Transparent,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
    )
}

@Preview
@Composable
private fun SearchBarPreview(
    @PreviewParameter(ThemeProvider::class) materialYouColors: Boolean
) = ListOfArtsTheme(darkTheme = materialYouColors) {
    Surface(color = MaterialTheme.colorScheme.secondaryContainer) {
        var text by remember { mutableStateOf("123") }
        Box(Modifier.padding(16.dp)) {
            SearchBar(text, { text = it })
        }
    }
}