package dev.foodie.notes.utils

object Constants {
    const val UNCATEGORIZED: String = "Uncategorised"
    const val PERSONAL: String = "Personal"
    const val WORK: String = "Work"
    const val STUDY: String = "Study"
    const val FAMIY: String = "Family"

    val TAG_MAP = mapOf(
        1 to UNCATEGORIZED, 2 to PERSONAL, 3 to WORK, 4 to STUDY, 5 to FAMIY
    )
}