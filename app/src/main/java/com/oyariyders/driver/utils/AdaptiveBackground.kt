package com.oyariyders.driver.utils

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

sealed interface AdaptiveBackground {
    data class ImageResource(val resourceId: Int) : AdaptiveBackground
    data class SolidColor(val color: Color) : AdaptiveBackground
    data class SolidBrush(val brush: Brush) : AdaptiveBackground
}