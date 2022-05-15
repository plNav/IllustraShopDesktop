package view.login_register.composables

import ScreenNav
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import pab.lop.illustrashopandroid.data.model.shoppin.shopping_cart_request
import pab.lop.illustrashopandroid.data.model.user.user_request
import showToast
import theme.Spacing
import utils.getSHA256
import utils.shoppingCartSelected
import utils.userSelected
import view.login_register.LoginRegisterViewModel

@Composable
fun RegisterButton(
    verticalGradientDisabled: Brush,
    verticalGradient: Brush,
    customSpacing: Spacing,
    openBuyInfo: MutableState<Boolean>,
    basicInfoChecked: MutableState<Boolean>,
    payInfoChecked: MutableState<Boolean>,
    loginRegisterViewModel: LoginRegisterViewModel,
    email: MutableState<String>,
    username: MutableState<String>,
    password: MutableState<String>,
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
    popUpPasswordOpen: MutableState<Boolean>,
    passwordValidated: MutableState<Boolean>,
    screen: MutableState<String>,
) {

    Card(
        modifier = Modifier
            .padding(customSpacing.mediumMedium)
            .clip(RoundedCornerShape(4.dp))
            .clickable {
                if (isValidated(
                        openBuyInfo = openBuyInfo,
                        basicInfoChecked = basicInfoChecked,
                        payInfoChecked = payInfoChecked,
                        option1 = true,
                        option2 = false,
                        withToast = false,
                    ) as Boolean
                ) validateClick(
                    allFields = openBuyInfo.value,
                    loginRegisterViewModel = loginRegisterViewModel,
                    screen = screen,
                    email = email,
                    username = username,
                    password = password,
                    isEditionMode = isEditionMode,
                    popUpPasswordOpen = popUpPasswordOpen,
                    passwordValidated = passwordValidated,
                    phone = phone,
                    postalCode = postalCode,
                    address = address,
                    country = country,
                    lastName = lastName,
                    name = name
                )
                else isValidated(
                    openBuyInfo = openBuyInfo,
                    basicInfoChecked = basicInfoChecked,
                    payInfoChecked = payInfoChecked,
                    option1 = true,
                    option2 = false,
                    withToast = true,
                )
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(
                    isValidated(
                        openBuyInfo = openBuyInfo,
                        basicInfoChecked = basicInfoChecked,
                        payInfoChecked = payInfoChecked,
                        option1 = verticalGradient,
                        option2 = verticalGradientDisabled,
                        withToast = false,
                    ) as Brush
                )
        ) {
            Text(
                text = if (isEditionMode) "SAVE" else "REGISTER",
                modifier = Modifier
                    .padding(customSpacing.mediumLarge, customSpacing.default, customSpacing.mediumLarge, customSpacing.default),
                color = Color.White
            )

            IconButton(
                modifier = Modifier.padding(
                    customSpacing.default,
                    customSpacing.default,
                    customSpacing.small,
                    customSpacing.default
                ),
                onClick = {
                    if (isValidated(
                            openBuyInfo = openBuyInfo,
                            basicInfoChecked = basicInfoChecked,
                            payInfoChecked = payInfoChecked,
                            option1 = true,
                            option2 = false,
                            withToast = false,
                        ) as Boolean
                    ) validateClick(
                        allFields = openBuyInfo.value,
                        loginRegisterViewModel = loginRegisterViewModel,
                        email = email,
                        username = username,
                        password = password,
                        isEditionMode = isEditionMode,
                        popUpPasswordOpen = popUpPasswordOpen,
                        passwordValidated = passwordValidated,
                        name = name,
                        lastName = lastName,
                        country = country,
                        address = address,
                        postalCode = postalCode,
                        phone = phone,
                        screen = screen
                    )
                    else isValidated(
                        openBuyInfo = openBuyInfo,
                        basicInfoChecked = basicInfoChecked,
                        payInfoChecked = payInfoChecked,
                        option1 = true,
                        option2 = false,
                        withToast = true,
                    )
                }
            ) {
                Icon(
                    imageVector = isValidated(
                        openBuyInfo = openBuyInfo,
                        basicInfoChecked = basicInfoChecked,
                        payInfoChecked = payInfoChecked,
                        option1 = Icons.Filled.Check,
                        option2 = Icons.Filled.Close,
                        withToast = false,
                    ) as ImageVector,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

fun validateClick(
    allFields: Boolean,
    loginRegisterViewModel: LoginRegisterViewModel,
    email: MutableState<String>,
    username: MutableState<String>,
    password: MutableState<String>,
    isEditionMode: Boolean,
    popUpPasswordOpen: MutableState<Boolean>,
    passwordValidated: MutableState<Boolean>,
    phone: MutableState<String>,
    postalCode: MutableState<String>,
    address: MutableState<String>,
    country: MutableState<String>,
    lastName: MutableState<String>,
    name: MutableState<String>,
    screen: MutableState<String>
) {
    if (isEditionMode) {
        popUpPasswordOpen.value = true


    } else {
        val newUser = user_request(
            name = if (!allFields) "" else name.value,
            last_name = if (!allFields) "" else lastName.value,
            username = username.value,
            email = email.value,
            password = getSHA256(password.value),
            rol = "STANDART",
            address = if (!allFields) "" else address.value,
            country = if (!allFields) "" else country.value,
            postal_code = if (!allFields) "" else postalCode.value,
            phone = if (!allFields) "" else phone.value,
            pay_method = if (!allFields) "" else "",
            pay_number = if (!allFields) "" else ""
        )

        loginRegisterViewModel.createUser(newUser){
            userSelected = loginRegisterViewModel.currentUserResponse.value
            showToast( "Register Complete \n ${userSelected!!.username}")
            if(userSelected!!._id.isNotEmpty()){
                loginRegisterViewModel.createShoppingCart(shopping_cart_request(userSelected!!._id)){
                    shoppingCartSelected = loginRegisterViewModel.currentShoppingCartResponse.value
                    screen.value = ScreenNav.MainScreen.route
                }
            }
        }
    }
}

private fun isValidated(
    openBuyInfo: MutableState<Boolean>,
    basicInfoChecked: MutableState<Boolean>,
    payInfoChecked: MutableState<Boolean>,
    option1: Any,
    option2: Any,
    withToast: Boolean,
): Any {

    val errorInfo = "\nPress icon of wrong field to see errors"
    val errorBasic = "Check basic information fields $errorInfo"
    val errorPay = "Check pay info fields\nYou can do it later pressing green button $errorInfo"
    val errorBoth = "Check Basic Info and Pay Info fields\nYou can complete Pay Info later pressing green button$errorInfo"
    

    if (openBuyInfo.value) {
        return if (basicInfoChecked.value && payInfoChecked.value) option1
        else if (!basicInfoChecked.value && payInfoChecked.value) {
            if (withToast) showToast(errorBasic)
            option2
        } else if (basicInfoChecked.value && !payInfoChecked.value) {
            if (withToast) showToast(errorPay)
            option2
        } else {
            if (withToast) showToast(errorBoth)
            option2
        }
    } else {
        return if (basicInfoChecked.value) option1
        else {
            if (withToast) showToast(errorBasic)
            option2
        }
    }
}