package rutledge.james.currencyconverter.UI.util

import android.content.Context


object Flag {
    // Returns a drawable id based on a currency code
    internal fun getFlagFromID(ID: String, context: Context): Int? {
        return when (val id =
            context.resources.getIdentifier(ID.lowercase(), "drawable", context.packageName)) {
            // Some files like "try.png" may need to be renamed to "resource_try" since "try" is an invalid drawable name
            0 -> when (val resId = context.resources.getIdentifier(
                "resource_${ID.lowercase()}",
                "drawable",
                context.packageName
            )) {
                0 -> null
                else -> resId
            }
            else -> id
        }
    }
}
