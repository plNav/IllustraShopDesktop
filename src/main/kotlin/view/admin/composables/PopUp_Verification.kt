package view.admin.composables

import ScreenNav
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import theme.Spacing

@Composable
fun PopUp_Verification(
    screen : MutableState<String>,
    verificationOpen: MutableState<Boolean>,
    verticalGradient: Brush,
    verticalGradientIncomplete: Brush,
    customSpacing: Spacing,
    isEditionMode: Boolean,
    isDelete : Boolean

) {

    Window(
        onCloseRequest = {
            verificationOpen.value = false
            screen.value = ScreenNav.AdminScreen.route
                         },
        state = rememberWindowState(
            width = 500.dp,
            height = 1000.dp,
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
            Column(
                modifier = Modifier
                    .background(brush = verticalGradient)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(brush = verticalGradient)
                ) {


                    /************ TITLE ************/
                    Text(
                        text = if(isEditionMode){
                            if(isDelete) "Delete Completed"
                            else "Update Completed"
                        } else "Upload Compolete",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1.copy(color = Color.White),
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Transparent)
                            .padding(12.dp)
                            .clickable(onClick = { })
                    )

                    /************ CLOSE ************/
                    IconButton(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Transparent),
                        onClick = { verificationOpen.value = false },
                    ) {
                        Icon(
                            Icons.Filled.Close,
                            tint = MaterialTheme.colors.onSecondary,
                            contentDescription = null
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(
                        customSpacing.mediumSmall
                    )
                )

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(customSpacing.mediumMedium)
                ) {

                    /************ RETURN ************/
                    Text(
                        text = "BACK",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1.copy(color = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .background(brush = verticalGradientIncomplete)
                            .padding(12.dp)
                            .clickable(onClick = { screen.value = ScreenNav.AdminScreen.route })
                    )
                    Text("")
                }
            }
        }
    }
}