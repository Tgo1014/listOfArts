package presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EmptyState(
    modifier: Modifier = Modifier,
    onRetryClicked: () -> Unit = {},
) = Column(
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Text(
        text = "No items", // stringResource(R.string.no_items),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .then(modifier)
    )
    Button(onClick = onRetryClicked, shape = MaterialTheme.shapes.small) {
        Text(text = "Retry") //stringResource(id = R.string.retry)
    }
}

@Preview
@Composable
private fun EmptyStatePreview() {
    Surface { EmptyState() }
}