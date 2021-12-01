package tgo1014.listofbeers.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tgo1014.listofbeers.models.Beer
import tgo1014.listofbeers.ui.theme.ListOfBeersTheme

@Composable
fun BeerComposable(beer: Beer) {
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = beer.name.orEmpty())
    }
}

@Preview
@Composable
fun BeerComposablePreview() = ListOfBeersTheme {
    BeerComposable(beer = Beer(name = "Beertastic"))
}