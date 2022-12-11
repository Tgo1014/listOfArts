package tgo1014.listofbeers.presentation.ui.composables.previews

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

object Device {
    const val PHONE_LANDSCAPE = "spec:shape=Normal,width=640,height=360,unit=dp,dpi=480"
}

/**
 * Multipreview annotation that represents various device sizes. Add this annotation to a composable
 * to render various devices.
 */
@Preview(name = "phone", device = Devices.PHONE, group = "Devices", showBackground = true)
@Preview(name = "landscape", device = Device.PHONE_LANDSCAPE, group = "Devices", showBackground = true)
@Preview(name = "foldable", device = Devices.FOLDABLE, group = "Devices", showBackground = true)
@Preview(name = "tablet", device = Devices.TABLET, group = "Devices", showBackground = true)
annotation class DevicePreviews
