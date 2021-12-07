package tgo1014.listofbeers.ui.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tgo1014.listofbeers.models.Beer
import tgo1014.listofbeers.ui.composables.BeerImage
import tgo1014.listofbeers.ui.composables.simpleVerticalScrollbar
import tgo1014.listofbeers.ui.theme.ListOfBeersTheme

@Composable
fun BeerDetails(beer: Beer) = Surface(
    color = MaterialTheme.colorScheme.primary,
    contentColor = Color.Black
) {
    val scrollState = rememberLazyListState()
    Row(
        modifier = Modifier
            .padding(12.dp)
            .height(350.dp)
    ) {
        BeerImage(beer, Modifier.weight(0.7f))
        Spacer(Modifier.size(12.dp))
        LazyColumn(
            state = scrollState,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .weight(1f)
                .simpleVerticalScrollbar(scrollState)
        ) {
            item {
                Text(
                    text = beer.name.orEmpty(),
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Serif,
                    fontSize = 30.sp,
                )
                Text(
                    text = beer.tagline.orEmpty(),
                    fontFamily = FontFamily.Serif,
                    fontStyle = FontStyle.Italic,
                    fontSize = 16.sp,
                )
            }
            item {
                Text(
                    text = "First Brewed:",
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Serif,
                    fontSize = 20.sp,
                )
                Text(text = beer.firstBrewed.orEmpty())
            }
            item {
                Text(
                    text = "Description",
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Serif,
                    fontSize = 20.sp,
                )
                Text(text = beer.description.orEmpty())
            }
            item {
                Text(
                    text = "Food Pairing",
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Serif,
                    fontSize = 20.sp,
                )

                beer.foodPairing.orEmpty().forEach {
                    Text(text = " • $it")
                }
            }
            item {
                Text(
                    text = "Brewer Tips",
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Serif,
                    fontSize = 20.sp,
                )
                Text(text = beer.brewersTips.orEmpty())
            }
        }
    }
}

@Preview
@Composable
private fun BeerDetailsPreview() = ListOfBeersTheme {
    BeerDetails(beer = Beer(name = "Punk IPA 2007 - 2010"))
}