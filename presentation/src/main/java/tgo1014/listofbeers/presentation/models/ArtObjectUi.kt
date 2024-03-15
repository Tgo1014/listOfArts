package tgo1014.listofbeers.presentation.models

data class ArtObjectUi(
    val id: String = "",
    val title: String = "",
    val creator: String = "",
    val description: String = "",
    val longTitle: String = "",
    val imageUrl: String = "",
    val imageHeight: Int = 0,
    val imageWidth: Int = 0,
    val principalMaker: String = "",
    val materialsList: List<String> = emptyList(),
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
