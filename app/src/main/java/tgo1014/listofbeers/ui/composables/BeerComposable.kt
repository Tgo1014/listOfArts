package tgo1014.listofbeers.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import tgo1014.listofbeers.models.Beer
import tgo1014.listofbeers.ui.theme.Amber700
import tgo1014.listofbeers.ui.theme.ListOfBeersTheme

@Composable
fun BeerComposable(beer: Beer, modifier: Modifier = Modifier) {
    val beerWidth = 60.dp
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
            .then(modifier),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
        ) {
            val padding = 16.dp
            Column(
                modifier = Modifier.padding(
                    start = beerWidth + padding,
                    end = padding,
                    top = padding,
                    bottom = padding
                )
            ) {
                Text(
                    text = beer.name.orEmpty(),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    ),
                    modifier = Modifier
                )
                Text(
                    text = beer.tagline.orEmpty(),
                    style = TextStyle(
                        fontStyle = FontStyle.Italic,
                        fontSize = 12.sp,
                        color = Color.Black
                    ),
                    modifier = Modifier
                )
            }
        }
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(beer.imageUrl)
                .crossfade(true)
                .build(),
            loading = { CircularProgressIndicator(color = Amber700) },
            contentDescription = null,
            modifier = Modifier
                .padding(start = 8.dp)
                .width(beerWidth)
                .align(Alignment.TopStart)
                .height(180.dp)
                .shadow(3.dp)
                .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp))
                .padding(8.dp)

        )
    }
}

@Preview
@Composable
private fun BeerComposablePreview() = ListOfBeersTheme {
    BeerComposable(
        Beer(
            name = "Punk IPA 2007 - 2010",
            imageUrl = "https://images.punkapi.com/v2/192.png",
            tagline = "Post Modern Classic. Spiky. Tropical. Hoppy.",
            description = "Our flagship beer that kick started the craft beer revolution. This is James and Martin's original take on an American IPA, subverted with punchy New Zealand hops. Layered with new world hops to create an all-out riot of grapefruit, pineapple and lychee before a spiky, mouth-puckering bitter finish."
        )
    )
}