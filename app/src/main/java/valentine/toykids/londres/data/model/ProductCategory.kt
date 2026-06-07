package valentine.toykids.londres.data.model

import androidx.annotation.StringRes
import valentine.toykids.londres.R

enum class ProductCategory(@field:StringRes val titleRes: Int) {
    TOYS(R.string.gurkm_category_toys),
    EDUCATIONAL(R.string.gurkm_category_educational),
    NEWBORN(R.string.gurkm_category_newborn),
    CLOTHING(R.string.gurkm_category_clothing),
    ACCESSORIES(R.string.gurkm_category_accessories),
}
