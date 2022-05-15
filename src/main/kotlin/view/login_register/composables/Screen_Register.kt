package pab.lop.illustrashopandroid.ui.view.login_register.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import theme.Spacing
import utils.isEditionMode
import utils.userSelected
import view.login_register.LoginRegisterViewModel
import view.login_register.composables.BasicInfo
import view.login_register.composables.PayInfo
import view.login_register.composables.PopUpPassword
import view.login_register.composables.RegisterButton


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Register(
    screen : MutableState<String>
) {
    val loginRegisterViewModel = LoginRegisterViewModel()
    val customSpacing = Spacing.customSpacing

    val keyboardController = LocalSoftwareKeyboardController.current

    //Gradients
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


    //Basic Fields
    val email = remember { mutableStateOf(if (isEditionMode) userSelected!!.email else "") }
    val emailChecked = remember { mutableStateOf(isEditionMode) }

    val username = remember { mutableStateOf(if (isEditionMode) userSelected!!.username else "") }
    val usernameChecked = remember { mutableStateOf(isEditionMode) }

    val password1 = remember { mutableStateOf("") }
    val password2 = remember { mutableStateOf("") }
    val passwordChecked = remember { mutableStateOf(isEditionMode) }
    val passwordVisibility = remember { mutableStateOf(false) }
    val passwordUpdate = remember { mutableStateOf(false) }


    //Pay Fields //TODO PAY_METHOD & PAY_NUMBER
    val name = remember { mutableStateOf(if (isEditionMode) userSelected!!.name else "") }
    val nameChecked = remember {
        mutableStateOf(
            if (isEditionMode) userSelected!!.name.isNotEmpty()
            else false
        )
    }

    val lastName = remember { mutableStateOf(if (isEditionMode) userSelected!!.last_name else "") }
    val lastNameChecked = remember {
        mutableStateOf(
            if (isEditionMode) userSelected!!.last_name.isNotEmpty()
            else false
        )
    }

    val country = remember { mutableStateOf(if (isEditionMode) userSelected!!.country else "") }
    val countryChecked = remember {
        mutableStateOf(
            if (isEditionMode) userSelected!!.country.isNotEmpty()
            else false
        )
    }

    val address = remember { mutableStateOf(if (isEditionMode) userSelected!!.address else "") }
    val addressChecked = remember {
        mutableStateOf(
            if (isEditionMode) userSelected!!.address.isNotEmpty()
            else false
        )
    }

    val postalCode = remember { mutableStateOf(if (isEditionMode) userSelected!!.postal_code else "") }
    val postalCodeChecked = remember {
        mutableStateOf(
            if (isEditionMode) userSelected!!.postal_code.isNotEmpty()
            else false
        )
    }

    val phone = remember { mutableStateOf(if (isEditionMode) userSelected!!.phone else "") }
    val phoneChecked = remember {
        mutableStateOf(
            if (isEditionMode) userSelected!!.phone.isNotEmpty()
            else false
        )
    }

    //Check by Group
    val basicInfoChecked = remember { mutableStateOf(isEditionMode) }
    val payInfoChecked = remember { mutableStateOf(false) }

    basicInfoChecked.value = (emailChecked.value && usernameChecked.value && passwordChecked.value)
    payInfoChecked.value = (nameChecked.value
            && lastNameChecked.value
            && countryChecked.value
            && addressChecked.value
            && postalCodeChecked.value
            && phoneChecked.value)


    //Booleans categories
    val openBuyInfo = remember { mutableStateOf(false) }
    val showBuyButton = remember { mutableStateOf(false) }

    //Lists to check
    val usernameList = remember { mutableStateOf<List<String>>(listOf()) }
    val emailList = remember { mutableStateOf<List<String>>(listOf()) }

    //Get all emails and usernames one time
    val firstLoad = remember { mutableStateOf(true) }
    if (firstLoad.value) {
        loginRegisterViewModel.getAllEmails { emailList.value = loginRegisterViewModel.emailListResponse }
        loginRegisterViewModel.getAllUsernames { usernameList.value = loginRegisterViewModel.usernameListResponse }
        firstLoad.value = false
    }


    val popUpPasswordOpen = remember { mutableStateOf(false) }
    val passwordValidated = remember { mutableStateOf(false) }
    if (popUpPasswordOpen.value) {
        PopUpPassword(
            popUpPasswordOpen = popUpPasswordOpen,
            passwordValidated = passwordValidated,
            customSpacing = customSpacing,
            verticalGradient = verticalGradient,
            allFields = openBuyInfo.value,
            name = name,
            lastName = lastName,
            username = username,
            email = email,
            phone = phone,
            postalCode = postalCode,
            address = address,
            country = country,
            screen = screen,
            loginRegisterViewModel = loginRegisterViewModel
        )
    }

    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = customSpacing.large)

    ) {
        item {
            TitleAndBack(
                isEditionMode = isEditionMode,
                customSpacing = customSpacing,
                screen = screen
            )
        }
        item {
            BasicInfo(
                isEditionMode = isEditionMode,
                customSpacing = customSpacing,
                email = email,
                emailChecked = emailChecked,
                username = username,
                usernameChecked = usernameChecked,
                password1 = password1,
                password2 = password2,
                passwordChecked = passwordChecked,
                passwordUpdate = passwordUpdate,
                passwordVisibility = passwordVisibility,
                keyboardController = keyboardController,
                emailList = emailList,
                usernameList = usernameList,
            )
        }
        item {
            DropdownButtonPayInfo(
                isEditionMode = isEditionMode,
                verticalGradient = verticalGradient,
                verticalGradientDisabled = verticalGradientDisabled,
                customSpacing = customSpacing,
                openBuyInfo = openBuyInfo
            )
        }
        if (openBuyInfo.value) {
            item {
                PayInfo(
                    isEditionMode = isEditionMode,
                    customSpacing = customSpacing,
                    name = name,
                    nameChecked = nameChecked,
                    lastName = lastName,
                    lastNameChecked = lastNameChecked,
                    country = country,
                    countryChecked = countryChecked,
                    address = address,
                    addressChecked = addressChecked,
                    postalCode = postalCode,
                    postalCodeChecked = postalCodeChecked,
                    phone = phone,
                    phoneChecked = phoneChecked,
                )
            }
        }
        item {
            RegisterButton(
                isEditionMode = isEditionMode,
                verticalGradientDisabled = verticalGradientDisabled,
                verticalGradient = verticalGradient,
                customSpacing = customSpacing,
                openBuyInfo = openBuyInfo,
                basicInfoChecked = basicInfoChecked,
                payInfoChecked = payInfoChecked,
                loginRegisterViewModel = loginRegisterViewModel,
                screen = screen,
                email = email,
                username = username,
                password = password2,
                name = name,
                nameChecked = nameChecked,
                lastName = lastName,
                lastNameChecked = lastNameChecked,
                country = country,
                countryChecked = countryChecked,
                address = address,
                addressChecked = addressChecked,
                postalCode = postalCode,
                postalCodeChecked = postalCodeChecked,
                phone = phone,
                phoneChecked = phoneChecked,
                popUpPasswordOpen = popUpPasswordOpen,
                passwordValidated = passwordValidated
            )
        }
    }
}



@Composable
private fun TitleAndBack(
    customSpacing: Spacing,
    isEditionMode: Boolean,
    screen: MutableState<String>
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Spacer(modifier = Modifier.width(customSpacing.mediumSmall))

        IconButton(
            onClick = {
                if (isEditionMode) screen.value = ScreenNav.MainScreen.route
                else screen.value = ScreenNav.LoginScreen.route
            },
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .align(Alignment.Start)
                .padding(customSpacing.default, customSpacing.mediumMedium, customSpacing.default, customSpacing.default)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                tint = MaterialTheme.colors.primary,
                contentDescription = null
            )
        }

        Text(
            text = if (isEditionMode) "Edit Personal Info" else "Register",
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}


@Composable
fun DropdownButtonPayInfo(
    verticalGradientDisabled: Brush,
    verticalGradient: Brush,
    customSpacing: Spacing,
    openBuyInfo: MutableState<Boolean>,
    isEditionMode: Boolean
) {

    Card(
        modifier = Modifier
            .padding(customSpacing.mediumMedium)
            .clip(RoundedCornerShape(4.dp))
            .clickable { openBuyInfo.value = !openBuyInfo.value }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(if (openBuyInfo.value) verticalGradient else verticalGradientDisabled)
        ) {
            Text(
                text =
                if (isEditionMode) {
                    if (openBuyInfo.value) "Edit Pay Info Now"
                    else "Edit Pay Info Later"
                } else {
                    if (openBuyInfo.value) "Complete Pay Info Now"
                    else "Complete Pay Info Later"
                },
                modifier = Modifier
                    .padding(customSpacing.mediumLarge, customSpacing.default, customSpacing.mediumLarge, customSpacing.default)
                    .fillMaxWidth(0.7f),
                color = Color.White
            )

            IconButton(
                modifier = Modifier
                    .padding(
                        customSpacing.default,
                        customSpacing.default,
                        customSpacing.small,
                        customSpacing.default
                    ),
                onClick = { openBuyInfo.value = !openBuyInfo.value }
            ) {
                Icon(
                    imageVector = if (openBuyInfo.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}


