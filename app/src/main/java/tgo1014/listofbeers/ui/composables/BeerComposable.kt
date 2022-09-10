package tgo1014.listofbeers.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tgo1014.listofbeers.models.Beer
import tgo1014.listofbeers.ui.theme.ListOfBeersTheme

@Composable
fun BeerComposable(
    beer: Beer,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        BeerImage(
            beer = beer,
            modifier = Modifier
                .height(100.dp)
                .width(40.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = beer.name.orEmpty().uppercase(),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = beer.tagline.orEmpty(),
                style = MaterialTheme.typography.labelSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = beer.description.orEmpty(),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Justify,
                lineHeight = 16.sp,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

private val PreviewBeer = Beer(
    name = "Punk IPA 2007 - 2010",
    imageUrl = "https://images.punkapi.com/v2/192.png",
    tagline = "Post Modern Classic. Spiky. Tropical. Hoppy.",
    description = "Our flagship beer that kick started the craft beer revolution. This is James and Martin's original take on an American IPA, subverted with punchy New Zealand hops. Layered with new world hops to create an all-out riot of grapefruit, pineapple and lychee before a spiky, mouth-puckering bitter finish."
)

@Preview(name = "Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BeerComposablePreview(
    @PreviewParameter(ThemeProvider::class) materialYouColors: Boolean
) {
    ListOfBeersTheme(materialYouColors = materialYouColors) {
        Surface(color = MaterialTheme.colorScheme.secondaryContainer) {
            BeerComposable(PreviewBeer)
        }
    }
}