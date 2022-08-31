package tgo1014.beerbox.ui.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import tgo1014.beerbox.R
import tgo1014.beerbox.models.Beer
import tgo1014.beerbox.ui.composables.BeerImage
import tgo1014.beerbox.ui.composables.simpleVerticalScrollbar
import tgo1014.beerbox.ui.theme.BeerBoxTheme

@Composable
fun BeerDetails(beer: Beer) = Surface {
    Box {
        val scrollState = rememberLazyListState()
        Icon(
            painter = painterResource(id = R.drawable.ic_bookmark),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .width(45.dp)
                .padding(end = 20.dp)
                .zIndex(1f)
        )
        Row(
            modifier = Modifier
                .padding(16.dp)
                .height(350.dp)
        ) {
            BeerImage(
                beer,
                Modifier
                    .height(200.dp)
                    .width(100.dp)
            )
            Spacer(Modifier.size(12.dp))
            LazyColumn(
                state = scrollState,

                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.simpleVerticalScrollbar(scrollState)
            ) {
                item {
                    Text(
                        text = beer.name.orEmpty(),
                        fontSize = 22.sp,
                        color = Color.White
                    )
                    Text(
                        text = beer.tagline.orEmpty(),
                        fontSize = 18.sp
                    )
                }
                item {
                    Text(
                        text = stringResource(R.string.first_brewed),
                        color = Color.White,
                    )
                    Text(text = beer.firstBrewed.orEmpty())
                }
                item {
                    Text(
                        text = stringResource(R.string.description),
                        color = Color.White,
                    )
                    Text(text = beer.description.orEmpty())
                }
                item {
                    Text(
                        text = stringResource(R.string.food_pairing),
                        color = Color.White,
                    )
                    beer.foodPairing.orEmpty().forEach {
                        Text(text = " • $it")
                    }
                }
                item {
                    Text(
                        text = stringResource(R.string.brewer_tips),
                        color = Color.White,
                    )
                    Text(text = beer.brewersTips.orEmpty())
                }
            }
        }
    }
}

@Preview
@Composable
private fun BeerDetailsPreview() = BeerBoxTheme {
    BeerDetails(beer = Beer(name = "Punk IPA 2007 - 2010", tagline = "This is a test"))
}