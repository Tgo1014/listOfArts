package tgo1014.listofbeers.presentation.models

data class FilterState(
    val filter: Filter,
    val isSelected: Boolean
) {
    companion object {
        val Default = Filter.entries.map {
            FilterState(it, it == Filter.PAINTING)
        }
    }
}