package view.admin.composables

import ScreenNav
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import view.admin.AdminViewModel
import showToast
import theme.Spacing
import utils.familyNameList
import utils.familySelected
import utils.regexSpecialChars

@Composable
fun PopUp_Edit_Family(
    screen : MutableState<String>,
) {

    val adminViewModel = AdminViewModel()
    val customSpacing = Spacing.customSpacing
    val scaffoldState = rememberScaffoldState()

    val customName = remember { mutableStateOf(familySelected?.name ?: "") }
    val nameVerified = remember { mutableStateOf(false) }

    val verticalGradient = Brush.verticalGradient(
        colors = listOf(MaterialTheme.colors.primary, MaterialTheme.colors.primaryVariant),
        startY = 0f,
        endY = 100f
    )
    val verticalGradientDisabled = Brush.verticalGradient(
        colors = listOf(MaterialTheme.colors.onError, Color.DarkGray),
        startY = 0f,
        endY = 100f
    )

    val verticalGradientIncomplete = Brush.verticalGradient(
        colors = listOf(MaterialTheme.colors.onSecondary, Color.DarkGray),
        startY = 0f,
        endY = 100f
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            EditFamilyTopAppBar(
                verticalGradient = verticalGradient,
                screen = screen
            )
        }
    ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp))
            ) {




                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        //.fillMaxSize()
                        .padding(horizontal = customSpacing.extraLarge)

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
                            customSpacing.extraLarge
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
                            .background(brush = if (nameVerified.value) verticalGradient else verticalGradientDisabled)
                            .padding(12.dp)
                            .clickable(onClick = {
                                if (nameVerified.value) {
                                    adminViewModel.updateFamily(newName = customName.value, oldName = familySelected) {
                                        screen.value = ScreenNav.AdminScreen.route
                                    }
                                }
                            })
                    )

                    Spacer(
                        modifier = Modifier.height(
                            customSpacing.extraLarge
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
                                    screen.value = ScreenNav.AdminScreen.route
                                }
                            })
                    )
                    Text("") // ÑAPAS
                }
            }
        }
    }

@Composable
private fun EditFamilyTopAppBar(verticalGradient: Brush, screen: MutableState<String>) {
    TopAppBar(
        elevation = 0.dp,
        modifier = Modifier.background(verticalGradient),
        backgroundColor = Color.Transparent,
        title = {
            Text(
                text = "EDIT FAMILY : ${familySelected!!.name}",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(10.dp, 0.dp, 0.dp, 0.dp),
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    screen.value = ScreenNav.AdminScreen.route
                }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null, tint = Color.White)
            }
        }
    )
}


