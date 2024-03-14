package tgo1014.listofbeers.presentation.ui.composables

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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tgo1014.listofbeers.presentation.models.ArtObjectUi
import tgo1014.listofbeers.presentation.ui.composables.previews.DefaultPreview
import tgo1014.listofbeers.presentation.ui.composables.providers.ThemeProvider
import tgo1014.listofbeers.presentation.ui.theme.ListOfBeersTheme

@Composable
fun BeerComposable(
    beer: ArtObjectUi,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
//        BeerImage(
//            beer = beer,
//            modifier = Modifier
//                .height(100.dp)
//                .width(40.dp)
//        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = beer.title.uppercase(),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
//            Text(
//                text = beer.tagline,
//                style = MaterialTheme.typography.labelSmall
//            )
//            Spacer(modifier = Modifier.height(4.dp))
//            Text(
//                text = beer.description,
//                maxLines = 3,
//                overflow = TextOverflow.Ellipsis,
//                textAlign = TextAlign.Justify,
//                lineHeight = 16.sp,
//                style = MaterialTheme.typography.bodyMedium,
//            )
        }
    }
}

private val PreviewBeer = ArtObjectUi(
    title = "Punk IPA 2007 - 2010",
    imageUrl = "https://images.punkapi.com/v2/192.png",
)

@DefaultPreview
@Composable
private fun BeerComposablePreview(
    @PreviewParameter(ThemeProvider::class) materialYouColors: Boolean
) = ListOfBeersTheme(materialYouColors = materialYouColors) {
    Surface(color = MaterialTheme.colorScheme.secondaryContainer) {
        BeerComposable(PreviewBeer)
    }
}