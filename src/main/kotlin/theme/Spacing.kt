package theme


import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


sealed class Spacing(
    val default: Dp,
    val extraSmall: Dp,
    val small: Dp,
    val mediumSmall: Dp,
    val mediumMedium: Dp,
    val mediumLarge: Dp,
    val large: Dp,
    val superLarge: Dp,
    val extraLarge: Dp
) {

    object customSpacing : Spacing(
        default = 0.dp,
        extraSmall = 10.dp,
        small = 15.dp,
        mediumSmall = 25.dp,
        mediumMedium = 35.dp,
        mediumLarge = 45.dp,
        large = 55.dp,
        superLarge = 70.dp,
        extraLarge = 80.dp
    )
}


