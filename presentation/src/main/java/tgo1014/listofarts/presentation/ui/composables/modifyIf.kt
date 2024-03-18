package tgo1014.listofarts.presentation.ui.composables

import androidx.compose.ui.Modifier

inline fun Modifier.modifyIf(
    condition: Boolean,
    modifier: Modifier.() -> Modifier,
): Modifier = if (condition) {
    this.then(modifier())
} else {
    this
}