package utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class ToastDuration(val value: Int) {
    Short(1000), Long(3000)
}

private var isShown: Boolean = false

@Composable
fun Toast(
    text: String,
    visibility: MutableState<Boolean> = mutableStateOf(false),
    duration: ToastDuration = ToastDuration.Long
) {
    if (isShown) {
        return
    }

    val verticalGradientDisabled = Brush.verticalGradient(
        colors = listOf(MaterialTheme.colors.onError, Color.DarkGray),
        startY = 0f,
        endY = 100f
    )


    if (visibility.value) {
        isShown = true
        Window(
            onCloseRequest = { isShown = false },
            state = rememberWindowState(
                width = 220.dp,
                height = 120.dp,
                placement = WindowPlacement.Floating,
                position = WindowPosition(alignment = Alignment.Center)
            ),
            title = "Adaptive",
            resizable = false,
            undecorated = true,
            transparent = true,
            alwaysOnTop = true,
            focusable = true
        ) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color.Transparent,
            ) {
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier.background(brush = verticalGradientDisabled).clip(RoundedCornerShape(10.dp)),

                    ) {
                    Surface(
                        modifier = Modifier.size(220.dp, 120.dp),
                        color = Color.DarkGray,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = text,
                                color = Color.White,
                                modifier = Modifier.padding(20.dp)
                            )
                        }
                        DisposableEffect(Unit) {
                            GlobalScope.launch {
                                delay(duration.value.toLong())
                                isShown = false
                                visibility.value = false
                            }
                            onDispose { }
                        }
                    }
                }
            }
        }
    }
}