package view.login_register.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.*
import showToast
import theme.Spacing
import utils.regexSpecialChars

@Composable
fun PayInfo(
    customSpacing: Spacing,
    isEditionMode: Boolean,
    name: MutableState<String>,
    nameChecked: MutableState<Boolean>,
    lastName: MutableState<String>,
    lastNameChecked: MutableState<Boolean>,
    country: MutableState<String>,
    countryChecked: MutableState<Boolean>,
    address: MutableState<String>,
    addressChecked: MutableState<Boolean>,
    postalCode: MutableState<String>,
    postalCodeChecked: MutableState<Boolean>,
    phone: MutableState<String>,
    phoneChecked: MutableState<Boolean>,
) {

    val spaceBetweenFields = customSpacing.small
    val emptyFieldError = "Field cannot be empty"
    val fieldOK = "Field Correct"
    val specialCharError = "Field can't contain special characters"


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

        /************ NAME ************/
        OutlinedTextField(
            value = name.value,
            onValueChange = {
                it.also {
                    name.value = it
                    nameChecked.value = name.value.isNotEmpty()
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
                    text = "Name",
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    )
                )
            },
            trailingIcon = {
                val image = Icons.Filled.Person
                IconButton(
                    onClick = {
                        if (nameChecked.value) showToast(fieldOK)
                        else showToast(emptyFieldError)
                    }
                ) {
                    Icon(
                        imageVector = image,
                        contentDescription = null
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (nameChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedBorderColor = if (nameChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                focusedLabelColor = if (nameChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedLabelColor = if (nameChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                cursorColor = if (nameChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary
            ),
            modifier = fieldModifier,
        )

        Spacer(modifier = Modifier.height(spaceBetweenFields))

        /************ LAST NAME ************/
        OutlinedTextField(
            value = lastName.value,
            onValueChange = {
                it.also {
                    lastName.value = it
                    lastNameChecked.value = lastName.value.isNotEmpty()
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
                    text = "Last Name",
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    )
                )
            },
            trailingIcon = {
                val image = Icons.Filled.Person
                IconButton(
                    onClick = {
                        if (lastNameChecked.value) showToast(fieldOK)
                        else showToast(emptyFieldError)
                    }
                ) {
                    Icon(
                        imageVector = image,
                        contentDescription = null
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (lastNameChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedBorderColor = if (lastNameChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                focusedLabelColor = if (lastNameChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedLabelColor = if (lastNameChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                cursorColor = if (lastNameChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary
            ),
            modifier = fieldModifier,
        )

        Spacer(modifier = Modifier.height(spaceBetweenFields))

        /************ COUNTRY ************/
        OutlinedTextField(
            value = country.value,
            onValueChange = {
                it.also {
                    country.value = it
                    countryChecked.value = country.value.isNotEmpty()
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            label = {
                Text(
                    text = "Country",
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    )
                )
            },
            trailingIcon = {
                val image = Icons.Filled.Person
                IconButton(
                    onClick = {
                        if (countryChecked.value) showToast(fieldOK)
                        else showToast(emptyFieldError)
                    }
                ) {
                    Icon(
                        imageVector = image,
                        contentDescription = null
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (countryChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedBorderColor = if (countryChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                focusedLabelColor = if (countryChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedLabelColor = if (countryChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                cursorColor = if (countryChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary
            ),
            modifier = fieldModifier,
        )

        Spacer(modifier = Modifier.height(spaceBetweenFields))

        /************ ADDRESS ************/
        OutlinedTextField(
            value = address.value,
            onValueChange = {
                it.also {
                    address.value = it
                    addressChecked.value = address.value.isNotEmpty()
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
                    text = "Address",
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    )
                )
            },
            trailingIcon = {
                val image = Icons.Filled.Person
                IconButton(
                    onClick = {
                        if (addressChecked.value) showToast(fieldOK)
                        else showToast(emptyFieldError)
                    }
                ) {
                    Icon(
                        imageVector = image,
                        contentDescription = null
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (addressChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedBorderColor = if (addressChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                focusedLabelColor = if (addressChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedLabelColor = if (addressChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                cursorColor = if (addressChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary
            ),
            modifier = fieldModifier,
        )

        Spacer(modifier = Modifier.height(spaceBetweenFields))

        /************ POSTAL CODE ************/
        OutlinedTextField(
            value = postalCode.value,
            onValueChange = {
                it.also {
                    postalCode.value = it
                    postalCodeChecked.value = !(it.contains(regexSpecialChars) || it.isEmpty())
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
            label = {
                Text(
                    text = "PostalCode",
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
                            postalCode.value.isEmpty() -> showToast(emptyFieldError)
                            postalCode.value.contains(regexSpecialChars) -> showToast(specialCharError)
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
                focusedBorderColor = if (postalCodeChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedBorderColor = if (postalCodeChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                focusedLabelColor = if (postalCodeChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedLabelColor = if (postalCodeChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                cursorColor = if (postalCodeChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary
            ),
            modifier = fieldModifier,
        )

        Spacer(modifier = Modifier.height(spaceBetweenFields))


        /************ PHONE ************/
        OutlinedTextField(
            value = phone.value,
            onValueChange = {
                it.also {
                    phone.value = it
                    phoneChecked.value = !( it.contains(regexSpecialChars) || it.isEmpty())
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
            label = {
                Text(
                    text = "Phone",
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    )
                )
            },
            trailingIcon = {
                val image = Icons.Filled.Phone
                IconButton(
                    onClick = {
                        when {
                            phone.value.isEmpty() -> showToast(emptyFieldError)
                            phone.value.contains(regexSpecialChars) -> showToast(specialCharError)
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
                focusedBorderColor = if (phoneChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedBorderColor = if (phoneChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                focusedLabelColor = if (phoneChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedLabelColor = if (phoneChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                cursorColor = if (phoneChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary
            ),
            modifier = fieldModifier,
        )

        Spacer(modifier = Modifier.height(spaceBetweenFields))

    }

}


