package tgo1014.beerbox.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tgo1014.beerbox.R
import tgo1014.beerbox.models.Beer
import tgo1014.beerbox.ui.theme.BeerBoxTheme
import tgo1014.beerbox.ui.theme.Yellow

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
            beer,
            Modifier
                .height(100.dp)
                .width(40.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = beer.name.orEmpty(),
                fontSize = 16.sp,
                color = Color.White
            )
            Text(
                text = beer.tagline.orEmpty(),
                fontSize = 14.sp
            )
            Spacer(Modifier.padding(4.dp))
            Text(
                text = beer.description.orEmpty(),
                maxLines = 2,
                lineHeight = 16.sp,
                overflow = TextOverflow.Ellipsis,
                fontSize = 14.sp
            )
            Text(
                text = stringResource(R.string.more_info),
                color = Yellow,
                fontSize = 14.sp,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BeerComposablePreview() = BeerBoxTheme {
    BeerComposable(
        Beer(
            name = "Punk IPA 2007 - 2010",
            imageUrl = "https://images.punkapi.com/v2/192.png",
            tagline = "Post Modern Classic. Spiky. Tropical. Hoppy.",
            description = "Our flagship beer that kick started the craft beer revolution. This is James and Martin's original take on an American IPA, subverted with punchy New Zealand hops. Layered with new world hops to create an all-out riot of grapefruit, pineapple and lychee before a spiky, mouth-puckering bitter finish."
        )
    )
}