package tgo1014.beerbox.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tgo1014.beerbox.R
import tgo1014.beerbox.ui.theme.BeerBoxTheme

@Composable
fun FilterChip(
    isFilled: Boolean,
    noFilterText: String,
    date: String? = null,
    onClick: () -> Unit,
) {
    val containerColor = if (!isFilled) Color.Transparent else MaterialTheme.colorScheme.primary
    OutlinedButton(
        contentPadding = PaddingValues(10.dp),
        onClick = { onClick() },
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor,
            contentColor = contentColorFor(containerColor)
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary
        ),
        content = {
            Text(
                text = if (isFilled) date.orEmpty() else noFilterText,
                fontSize = 10.sp
            )
        }
    )
}

@Preview
@Composable
fun FilterChipPreview() = BeerBoxTheme {
    FilterChip(false, stringResource(R.string.brewed_before)) {}
}

@Preview
@Composable
fun FilterChipPreviewWithFilter() = BeerBoxTheme {
    FilterChip(true, stringResource(R.string.brewed_before), "11-2021") {}
}