package tgo1014.listofbeers.presentation.ui.composables.previews

import androidx.compose.ui.tooling.preview.Preview

/**
 * Multipreview annotation that represents various device sizes. Add this annotation to a composable
 * to render various devices.
 *
 * From https://github.com/android/nowinandroid/blob/7dec90ba30a5a0512b4b72bbf5b34a7bbe3c15de/core/ui/src/main/java/com/google/samples/apps/nowinandroid/core/ui/DevicePreviews.kt
 */
@Preview(name = "phone", device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480", group = "Devices")
@Preview(name = "landscape", device = "spec:shape=Normal,width=640,height=360,unit=dp,dpi=480", group = "Devices")
@Preview(name = "foldable", device = "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480", group = "Devices")
@Preview(name = "tablet", device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480", group = "Devices")
annotation class DevicePreviews
