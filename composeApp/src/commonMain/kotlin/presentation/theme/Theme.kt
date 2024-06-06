package presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable


/**
 * Light default theme color scheme
 */
val LightDefaultColorScheme = lightColorScheme()

/**
 * Dark default theme color scheme
 */
val DarkDefaultColorScheme = darkColorScheme()

/**
 * Light Android theme color scheme
 */
val LightAndroidColorScheme = lightColorScheme()

/**
 * Dark Android theme color scheme
 */
val DarkAndroidColorScheme = darkColorScheme()


///**
// * Light Android background theme
// */
//val LightAndroidBackgroundTheme = BackgroundTheme(color = DarkGreenGray95)
//
///**
// * Dark Android background theme
// */
//val DarkAndroidBackgroundTheme = BackgroundTheme(color = Color.Black)


@Composable
fun ListOfArtsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    androidTheme: Boolean = false,
    materialYouColors: Boolean = false,
    content: @Composable () -> Unit
) {
//     SystemBarsColorEffect(isSystemInDarkTheme = darkTheme)
//
//    val (colorScheme, backgroundTheme) = mobileThemes(androidTheme, darkTheme, disableDynamicTheming)
//
//    CompositionLocalProvider(
//        LocalBackgroundTheme provides backgroundTheme
//    ) {
        MaterialTheme(
            colorScheme = if (darkTheme) darkColorScheme() else lightColorScheme(),
            content = content
        )
}

//
//@Composable
//fun mobileThemes(
//    androidTheme: Boolean,
//    darkTheme: Boolean,
//    disableDynamicTheming: Boolean
//): Pair<ColorScheme, BackgroundTheme> {
//    val context = LocalContext.current
//    val colorScheme = colorScheme(androidTheme, darkTheme, disableDynamicTheming, context)
//
//    val defaultBackgroundTheme = BackgroundTheme(
//        color = colorScheme.surface,
//        tonalElevation = BackgroundTheme.DEFAULT_TONAL_ELEVATION,
//        )
//    val backgroundTheme = if (androidTheme) {
//        if (darkTheme) DarkAndroidBackgroundTheme else LightAndroidBackgroundTheme
//    } else {
//        defaultBackgroundTheme
//    }
//    return Pair(colorScheme, backgroundTheme)
//}
//
//fun colorScheme(
//    androidTheme: Boolean,
//    darkTheme: Boolean,
//    disableDynamicTheming: Boolean,
//    context: Context
//): ColorScheme {
//    val colorScheme = if (androidTheme) {
//        if (darkTheme) DarkAndroidColorScheme else LightAndroidColorScheme
//    } else if (!disableDynamicTheming && supportsDynamicTheming()) {
//        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//    } else {
//        if (darkTheme) DarkDefaultColorScheme else LightDefaultColorScheme
//    }
//    return colorScheme
//}
//
//@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
//fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
