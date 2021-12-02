package tgo1014.listofbeers.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import tgo1014.listofbeers.models.Beer
import tgo1014.listofbeers.ui.theme.ListOfBeersTheme

@Composable
fun BeerComposableNew(beer: Beer, modifier: Modifier = Modifier) = ConstraintLayout(modifier) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text(
                text = beer.name.orEmpty(),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(16.dp),
            )
        }
        item {
            // AsyncImage(
            //     model = ImageRequest.Builder(LocalContext.current)
            //         .data(beer.imageUrl)
            //         .placeholder(R.mipmap.ic_launcher)
            //         .crossfade(true)
            //         .build(),
            //     loading = { CircularProgressIndicator() },
            //     contentDescription = null,
            //     modifier = Modifier
            //         .fillMaxWidth()
            //         .height(500.dp)
            // )
        }
        repeat(100) {
            item {
                Text(
                    text = beer.tagline.orEmpty(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(16.dp),
                )
            }
        }

    }
}

@Preview
@Composable
private fun BeerComposablePreview() = ListOfBeersTheme {
    BeerComposableNew(
        Beer(
            name = "Punk IPA 2007 - This is a long beer title",
            imageUrl = "https://images.punkapi.com/v2/192.png",
            tagline = "Post Modern Classic. Spiky. Tropical. Hoppy.",
            description = "Our flagship beer that kick started the craft beer revolution. This is James and Martin's original take on an American IPA, subverted with punchy New Zealand hops. Layered with new world hops to create an all-out riot of grapefruit, pineapple and lychee before a spiky, mouth-puckering bitter finish."
        )
    )
}