package tgo1014.listofbeers.ui.composables

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import tgo1014.listofbeers.models.Beer
import tgo1014.listofbeers.ui.theme.Amber700

@Composable
fun BeerImage(beer: Beer, modifier: Modifier) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(beer.imageUrl)
            .crossfade(true)
            .build(),
        loading = { CircularProgressIndicator(color = Amber700) },
        error = {
            Text(
                text = "No Image",
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                lineHeight = 12.sp
            )
        },
        contentDescription = null,
        modifier = modifier
    )
}