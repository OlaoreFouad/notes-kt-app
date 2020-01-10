package dev.foodie.notes.utils

import dev.foodie.notes.R
import dev.foodie.notes.models.Menu

object Constants {
    const val UNCATEGORIZED: String = "Uncategorised"
    const val PERSONAL: String = "Personal"
    const val WORK: String = "Work"
    const val STUDY: String = "Study"
    const val FAMIY: String = "Family"

    const val BOOKMARK = 1
    const val SHARE = 2
    const val LOCK = 3
    const val ARCHIVE = 4
    const val DELETE = 5

    const val BY_DATE_ADDED = 0
    const val BY_DATE_MODIFIED = 1
    const val BY_TITLE = 2
    const val BY_UNCATEGORIZED = 0
    const val BY_PERSONAL = 1
    const val BY_WORK = 2
    const val BY_STUDY = 3
    const val BY_FAMILY = 4
    const val BY_ALL_NOTES = 5

    val TAG_MAP = mapOf(1 to UNCATEGORIZED, 2 to PERSONAL, 3 to WORK, 4 to STUDY, 5 to FAMIY)

    fun getMenus(isBookmarked: Boolean): List<Menu> {
        return listOf(
            Menu(if (isBookmarked) "Remove Bookmark" else "Bookmark", R.drawable.bookmark_border, BOOKMARK),
            Menu("Share", R.drawable.share, SHARE),
            Menu("Lock", R.drawable.lock, LOCK),
            Menu("Archive", R.drawable.archive, ARCHIVE),
            Menu("Delete", R.drawable.delete, DELETE)
        )
    }
}