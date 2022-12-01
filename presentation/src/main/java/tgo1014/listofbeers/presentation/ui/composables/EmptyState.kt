package tgo1014.listofbeers.presentation.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tgo1014.listofbeers.presentation.R
import tgo1014.listofbeers.ui.theme.ListOfBeersTheme

@Composable
fun EmptyState(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.no_beers),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .then(modifier)
    )
}

@Preview(name = "Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun EmptyStatePreview() {
    ListOfBeersTheme {
        Surface {
            EmptyState()
        }
    }
}