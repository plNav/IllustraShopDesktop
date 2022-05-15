package view.admin.composables

import ScreenNav
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import data.model.product_stock.product_stock_response
import view.admin.AdminViewModel
import showToast
import theme.Spacing
import utils.*


@OptIn(ExperimentalComposeUiApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun Edit_Product(screen : MutableState<String>) {
    val customSpacing = Spacing.customSpacing
    val adminViewModel = AdminViewModel()

    val buttonOK = remember { mutableStateOf(false) }
    val chooseFamiliesOpen = remember { mutableStateOf(false) }
    val loadProductsFamily = remember { mutableStateOf(false) }
    val startLoading = remember { mutableStateOf(false) }
    val popUpNewFamily = remember { mutableStateOf(false) }
    val isDeleted = remember { mutableStateOf(false) }
    var products: List<product_stock_response>

    val nameVerified = remember { mutableStateOf(false) }
    val customName = remember { mutableStateOf(productSelected!!.name) }

    val customPrice = remember { mutableStateOf(productSelected!!.price) }
    val customPriceVerified = remember { mutableStateOf(false) }

    val customAmount = remember { mutableStateOf(productSelected!!.stock) }
    val customAmountVerified = remember { mutableStateOf(false) }

    val familiesToAssign = remember { mutableStateOf<List<String>>(productSelected!!.families) }
    val familyNames = remember { mutableStateOf<List<String>>(listOf()) }
    val verificationOpen = remember { mutableStateOf(false) }

    val scaffoldState = rememberScaffoldState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()

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

    if (!startLoading.value) {
        startLoading.value = true
        adminViewModel.getProducts {
            products = adminViewModel.productListResponse
            loadProductsFamily.value = true
        }
    }

    if (verificationOpen.value) {
        PopUp_Verification(
            screen = screen,
            verificationOpen = verificationOpen,
            verticalGradient = verticalGradient,
            verticalGradientIncomplete = verticalGradientIncomplete,
            customSpacing = customSpacing,
            isEditionMode = true,
            isDelete = isDeleted.value
        )
    }



    if (chooseFamiliesOpen.value) {
        PopUp_Choose_Family(
            adminViewModel = adminViewModel,
            screen = screen,
            scope = scope,
            chooseFamiliesOpen = chooseFamiliesOpen,
            customSpacing = customSpacing,
            verticalGradient = verticalGradient,
            verticalGradientDisabled = verticalGradientDisabled,
            familyNames = familyNames,
            familiesToAssign = familiesToAssign
        )
    }


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                modifier = Modifier.background(verticalGradient),
                backgroundColor = Color.Transparent,
                title = {
                    Text(
                        text = "EDIT PRODUCT",
                        modifier = Modifier
                            .padding(30.dp, 0.dp, 0.dp, 0.dp),
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
    ) {

        //CONTENT
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(customSpacing.mediumSmall)
        ) {


            item() {

                /************ NAME ************/
                OutlinedTextField(
                    value = customName.value,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Words,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Text,
                        imeAction = if (nameVerified.value) ImeAction.Next else ImeAction.None
                    ),
                    onValueChange = {
                        it.also {
                            customName.value = it
                            when {
                                it.contains(regexSpecialChars) -> {
                                    nameVerified.value = false
                                    showToast("Special characters not allowed")
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
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(customSpacing.mediumMedium))

                /************ PRICE ************/
                OutlinedTextField(
                    value = customPrice.value.toString(),
                    onValueChange = {
                        it.also {
                            try {
                                customPrice.value = it.toString().toFloat()
                                customPriceVerified.value = true
                            } catch (e: Exception) {
                                customPriceVerified.value = false
                                showToast("Not a number")
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Words,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    label = {
                        Text(
                            text = "Price",
                            style = TextStyle(
                                color = MaterialTheme.colors.secondary
                            )
                        )
                    },
                    trailingIcon = {
                        val image = Icons.Filled.AccountBox
                        Icon(
                            imageVector = image,
                            contentDescription = null
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = if (customPriceVerified.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                        unfocusedBorderColor = if (customPriceVerified.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                        focusedLabelColor = if (customPriceVerified.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                        unfocusedLabelColor = if (customPriceVerified.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                        cursorColor = if (customPriceVerified.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer( modifier = Modifier.height(customSpacing.mediumMedium))

                /************ STOCK ************/
                OutlinedTextField(
                    value = customAmount.value.toInt().toString(),
                    onValueChange = {
                        it.also {
                            try {
                                customAmount.value = it.toInt().toFloat()
                                customAmountVerified.value = true
                            } catch (e: Exception) {
                                customAmountVerified.value = false
                                showToast("Not a number")
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Words,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            popUpNewFamily.value = true
                        }),
                    singleLine = true,
                    label = {
                        Text(
                            text = "Amount",
                            style = TextStyle(
                                color = MaterialTheme.colors.secondary
                            )
                        )
                    },
                    trailingIcon = {
                        val image = Icons.Filled.AccountBox
                        Icon(
                            imageVector = image,
                            contentDescription = null
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = if (customAmountVerified.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                        unfocusedBorderColor = if (customAmountVerified.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                        focusedLabelColor = if (customAmountVerified.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                        unfocusedLabelColor = if (customAmountVerified.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                        cursorColor = if (customAmountVerified.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(customSpacing.mediumMedium))

                /************ FAMILIES ************/
                Row(Modifier.fillMaxWidth()) {

                    /************ CHOOSE ************/
                    Text(
                        text = "ChooseFamilies",
                        textAlign = TextAlign.Center,
                        style = typography.body1.copy(color = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .background(brush = if (familiesToAssign.value.isNullOrEmpty()) verticalGradientIncomplete else verticalGradient)
                            .padding(12.dp)
                            .clickable(onClick = {
                                adminViewModel.getFamilyNames {
                                    familyNames.value = adminViewModel.familyNameListResponse as MutableList<String>
                                    chooseFamiliesOpen.value = true
                                }
                            })
                    )
                }

                Spacer(modifier = Modifier.height(customSpacing.mediumSmall))



                Spacer(modifier = Modifier.height(customSpacing.small))

                /************ IMAGE ************/
                Card(
                    backgroundColor = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .padding(5.dp)
                        .clickable(onClick = {
                            showToast("Image cannot be modified")
                        })
                ) {
                    AsyncImage(
                        load = { loadImageBitmap("$URL_HEAD_IMAGES${productSelected!!.image}") },
                        painterFor = { remember { BitmapPainter(it) } },
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                }


                /************ UPDATE ************/
                Text(
                    text = "UPDATE",
                    textAlign = TextAlign.Center,
                    style = typography.body1.copy(color = Color.White),
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            if (nameVerified.value
                                && customAmountVerified.value
                                && customPriceVerified.value
                                && !familiesToAssign.value.isNullOrEmpty()
                            )
                                verticalGradient
                            else verticalGradientDisabled
                        )
                        .padding(12.dp)
                        .clickable(onClick = {
                            if (nameVerified.value
                                && customAmountVerified.value
                                && customPriceVerified.value
                                && !familiesToAssign.value.isNullOrEmpty()
                            ) {
                                val newProduct = product_stock_response(
                                    _id = productSelected!!._id,
                                    name = customName.value,
                                    image = productSelected!!.image,
                                    price = customPrice.value,
                                    stock = customAmount.value,
                                    families = familiesToAssign.value as MutableList<String>,
                                    likes = productSelected!!.likes,
                                    wishlists = productSelected!!.wishlists,
                                    sales = productSelected!!.sales
                                )

                                adminViewModel.updateProductStock(newProduct = newProduct, oldProductId = productSelected!!._id) {
                                    verificationOpen.value = true
                                    buttonOK.value = true;
                                }

                            } else showToast("IncorrectFields")
                        })
                )


                Spacer(modifier = Modifier.height(customSpacing.small))


                /************ DELETE ************/
                Text(
                    text = "DELETE",
                    textAlign = TextAlign.Center,
                    style = typography.body1.copy(color = Color.White),
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                        .background(verticalGradientIncomplete)
                        .padding(12.dp)
                        .clickable(onClick = {
                            adminViewModel.deleteProductStock(oldProductId = productSelected!!._id) {
                                isDeleted.value = true
                                verificationOpen.value = true
                                buttonOK.value = true;
                            }
                        })
                )
            }
        }
    }
}










