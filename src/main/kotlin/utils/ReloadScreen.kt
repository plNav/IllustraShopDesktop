package utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun Reload(screen : MutableState<String>){
    screen.value = rememberScreen
}