package tgo1014.beerbox.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tgo1014.beerbox.R
import tgo1014.beerbox.ui.theme.BeerBoxTheme

@Composable
fun OfferComposable(modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(12.dp),
        backgroundColor = MaterialTheme.colorScheme.secondary,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Row(Modifier.padding(12.dp)) {
            Column(Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.offer_title),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = stringResource(R.string.offer_description),
                    fontSize = 13.sp,
                    color = Color.Black
                )
            }
            Image(
                painter = painterResource(R.drawable.ic_gift),
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun OfferComposablePreview() = BeerBoxTheme {
    OfferComposable(Modifier.padding(12.dp))
}