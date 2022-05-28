package view.login_register.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import showToast
import theme.Spacing
import utils.regexEmail
import utils.regexSpecialChars

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BasicInfo(
    customSpacing: Spacing,
    email: MutableState<String>,
    keyboardController: SoftwareKeyboardController?,
    passwordVisibility: MutableState<Boolean>,
    password1: MutableState<String>,
    password2: MutableState<String>,
    passwordChecked: MutableState<Boolean>,
    usernameChecked: MutableState<Boolean>,
    username: MutableState<String>,
    emailChecked: MutableState<Boolean>,
    emailList: MutableState<List<String>>,
    usernameList: MutableState<List<String>>,
    isEditionMode: Boolean,
    passwordUpdate: MutableState<Boolean>
) {

    val alreadyUseError = "Already in use"
    val noEmailError = "Not an email"
    val specialCharError = "Field can't contain special characters"
    val passwordMatchError = "Passwords must be equal"
    val emptyFieldError = "Field cannot be empty"
    val fieldOK = "Field correct"
    val minPassword = "Password min 4 characters"


    val spaceBetweenFields = customSpacing.small

    val fieldModifier = Modifier
        .fillMaxWidth()
        .height(customSpacing.extraLarge + customSpacing.small)
        .padding(customSpacing.default)

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = customSpacing.mediumMedium)

    ) {

        Spacer(modifier = Modifier.height(customSpacing.mediumMedium))

        /************ EMAIL ************/
        OutlinedTextField(
            value = email.value,
            onValueChange = {
                it.also {
                    email.value = it
                    when {
                        emailList.value.contains(it) -> emailChecked.value = false
                        it.isEmpty() -> emailChecked.value = false
                        it.contains(regexEmail) -> emailChecked.value = true
                        else -> emailChecked.value = false
                    }
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            label = {
                Text(
                    text = "Email",
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    )
                )
            },
            trailingIcon = {
                val image = Icons.Filled.Email
                IconButton(
                    onClick = {
                        when {
                            emailList.value.contains(email.value) -> showToast(alreadyUseError)
                            email.value.isEmpty() -> showToast(emptyFieldError)
                            email.value.contains(regexEmail) -> showToast(fieldOK)
                            else -> showToast(noEmailError)
                        }
                    }
                ) {
                    Icon(
                        imageVector = image,
                        contentDescription = null
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (emailChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedBorderColor = if (emailChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                focusedLabelColor = if (emailChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedLabelColor = if (emailChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                cursorColor = if (emailChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary
            ),
            modifier = fieldModifier,
        )

        Spacer(modifier = Modifier.height(spaceBetweenFields))


        /************ USERNAME ************/
        OutlinedTextField(
            value = username.value,
            onValueChange = {
                it.also {
                    username.value = it
                    when {
                        usernameList.value.contains(it) -> usernameChecked.value = false
                        it.contains(regexSpecialChars) -> usernameChecked.value = false
                        it.isEmpty() -> usernameChecked.value = false
                        else -> usernameChecked.value = true
                    }
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            label = {
                Text(
                    text = "Username",
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    )
                )
            },
            trailingIcon = {
                val image = Icons.Filled.Person
                IconButton(
                    onClick = {
                        when {
                            usernameList.value.contains(username.value) -> showToast(alreadyUseError)
                            username.value.isEmpty() -> showToast(emptyFieldError)
                            username.value.contains(regexSpecialChars) -> showToast(specialCharError)
                            else -> showToast(fieldOK)
                        }
                    }
                ) {
                    Icon(
                        imageVector = image,
                        contentDescription = null
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (usernameChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedBorderColor = if (usernameChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                focusedLabelColor = if (usernameChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedLabelColor = if (usernameChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                cursorColor = if (usernameChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary
            ),
            modifier = fieldModifier,
        )

        Spacer(modifier = Modifier.height(spaceBetweenFields))

        if(isEditionMode){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.small)
                    .clickable(
                        indication = rememberRipple(color = MaterialTheme.colors.primary),
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {
                            passwordUpdate.value = !passwordUpdate.value
                            if(!passwordUpdate.value) passwordChecked.value = true
                            else {
                                password1.value = ""
                                password2.value = ""
                                passwordChecked.value = false
                            }
                        }
                    )
                    .requiredHeight(ButtonDefaults.MinHeight)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = passwordUpdate.value,
                    onCheckedChange = null
                )

                Spacer(Modifier.size(6.dp))

                Text(
                    text = "Update Password?"
                )
            }
        }

        /************ PASSWORD ************/
        OutlinedTextField(
            enabled = if(!isEditionMode) true else passwordUpdate.value,
            value = password1.value,
            onValueChange = { it.also { password1.value = it } },
            singleLine = true,
            label = {
                Text(
                    text = "Password",
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    )
                )
            },
            visualTransformation =
            if (passwordVisibility.value) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next,
            ),
            trailingIcon = {
                val image =
                    if (passwordVisibility.value) Icons.Filled.Lock
                    else Icons.Filled.Close
                IconButton(
                    onClick = {
                        passwordVisibility.value = !passwordVisibility.value
                        when {
                            password1.value.isEmpty() || password2.value.isEmpty() -> showToast(emptyFieldError)
                            password1.value.length < 4 -> showToast(minPassword)
                            password2.value == password1.value -> showToast(fieldOK)
                            else -> showToast(passwordMatchError)
                        }
                    }                ) {
                    Icon(
                        imageVector = image,
                        contentDescription = null
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = MaterialTheme.colors.secondary,
                focusedLabelColor = MaterialTheme.colors.primary,
                unfocusedLabelColor = MaterialTheme.colors.secondary,
                cursorColor = MaterialTheme.colors.primary
            ),
            modifier = fieldModifier,
        )

        Spacer(modifier = Modifier.height(spaceBetweenFields))


        /************ REPEAT PASSWORD ************/
        OutlinedTextField(
            enabled = if(!isEditionMode) true else passwordUpdate.value,
            value = password2.value,
            onValueChange = {
                it.also {
                    password2.value = it
                    passwordChecked.value = (password2.value == password1.value && password1.value.length > 3
                            && (password1.value.isNotEmpty() && password2.value.isNotEmpty()))
                }
            },
            singleLine = true,
            label = {
                Text(
                    text = "Repeat Password",
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    )
                )
            },
            visualTransformation =
            if (passwordVisibility.value) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            trailingIcon = {
                val image =
                    if (passwordVisibility.value) Icons.Filled.Lock
                    else Icons.Filled.Close
                IconButton(
                    onClick = {
                        passwordVisibility.value = !passwordVisibility.value
                        when {
                            password1.value.isEmpty() || password2.value.isEmpty() -> showToast(emptyFieldError)
                            password1.value.length < 4 -> showToast(minPassword)
                            password2.value == password1.value -> showToast(fieldOK)
                            else -> showToast(passwordMatchError)
                        }
                    }
                ) {
                    Icon(
                        imageVector = image,
                        contentDescription = null
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (passwordChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedBorderColor = if (passwordChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                focusedLabelColor = if (passwordChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedLabelColor = if (passwordChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                cursorColor = if (passwordChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary
            ),
            modifier = fieldModifier,
        )


        Spacer(modifier = Modifier.height(customSpacing.small))


    }
}
