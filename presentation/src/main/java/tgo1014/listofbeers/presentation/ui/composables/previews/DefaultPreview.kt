package tgo1014.listofbeers.presentation.ui.composables.previews

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "Light", group = "Theme")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL, name = "Dark", group = "Theme")
annotation class DefaultPreview
