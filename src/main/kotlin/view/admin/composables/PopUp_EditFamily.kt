package view.admin.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import view.admin.AdminViewModel
import showToast
import theme.Spacing
import utils.familyNameList
import utils.familySelected
import utils.regexSpecialChars

@Composable
fun PopUp_Edit_Family(
    screen : MutableState<String>,
    adminViewModel: AdminViewModel,
    createFamilyOpen: MutableState<Boolean>,
    customSpacing: Spacing,
    verticalGradient: Brush,
    verticalGradiendDisabled: Brush,
    verticalGradientIncomplete: Brush
) {
    val customName = remember { mutableStateOf(familySelected?.name ?: "") }
    val nameVerified = remember { mutableStateOf(false) }


    Window(
        onCloseRequest = { createFamilyOpen.value = false },
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
                        text = "EDIT FAMILY",
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
                        onClick = { createFamilyOpen.value = false },
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
                        //.fillMaxSize()
                        .padding(horizontal = customSpacing.mediumSmall)

                ) {


                    /************ NAME ************/
                    OutlinedTextField(
                        value = customName.value,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            capitalization = KeyboardCapitalization.Words,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        onValueChange = {
                            it.also {
                                customName.value = it
                                when {
                                    it.contains(regexSpecialChars) -> {
                                        nameVerified.value = false
                                        showToast("Special characters not allowed")
                                    }
                                    familyNameList.contains(it.trim()) -> {
                                        nameVerified.value = false
                                        showToast("Already in use")
                                    }
                                    else -> {
                                        nameVerified.value = true
                                    }
                                }
                            }
                        },
                        singleLine = true,
                        label = {
                            Text(
                                text = "Name",
                                style = TextStyle(
                                    color = MaterialTheme.colors.secondary
                                )
                            )
                        },
                        trailingIcon = {
                            val image = Icons.Filled.Edit
                            Icon(
                                imageVector = image,
                                contentDescription = null
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = if (nameVerified.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                            unfocusedBorderColor = if (nameVerified.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                            focusedLabelColor = if (nameVerified.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                            unfocusedLabelColor = if (nameVerified.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                            cursorColor = if (nameVerified.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(
                        modifier = Modifier.height(
                            customSpacing.mediumMedium
                        )
                    )

                    /************ OK ************/
                    Text(
                        text = "OK",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1.copy(color = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .background(brush = if (nameVerified.value) verticalGradient else verticalGradiendDisabled)
                            .padding(12.dp)
                            .clickable(onClick = {
                                if (nameVerified.value) {
                                    adminViewModel.updateFamily(newName = customName.value, oldName = familySelected) {
                                        createFamilyOpen.value = false
                                    }
                                }
                            })
                    )

                    Spacer(
                        modifier = Modifier.height(
                            customSpacing.mediumMedium
                        )
                    )

                    /************ DELETE ************/
                    Text(
                        text = "DELETE",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1.copy(color = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .background(brush = verticalGradientIncomplete)
                            .padding(12.dp)
                            .clickable(onClick = {
                                adminViewModel.deleteFamily(familySelected) {
                                    createFamilyOpen.value = false
                                }
                            })
                    )
                    Text("") // ÑAPAS
                }
            }
        }
    }
}
