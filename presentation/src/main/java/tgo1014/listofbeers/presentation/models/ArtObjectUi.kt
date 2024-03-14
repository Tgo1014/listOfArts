package tgo1014.listofbeers.presentation.models

data class ArtObjectUi(
    val id: String = "",
    val title: String = "",
    val imageUrl: String = "",
    val imageHeight: Int = 0,
    val imageWidth: Int = 0,
) {
    val safeAspectRatio = calculateSafeAspectRatio()

    private fun calculateSafeAspectRatio(): Float {
        var aspectRatio = 1f
        if (imageHeight > 0) {
            aspectRatio = imageWidth.toFloat() / imageHeight
        }
        return aspectRatio
    }
}
