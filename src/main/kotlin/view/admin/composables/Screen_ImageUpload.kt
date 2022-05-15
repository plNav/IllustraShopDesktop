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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import data.model.product_stock.product_stock_response
import showToast
import theme.Spacing
import view.admin.AdminViewModel
import utils.regexSpecialChars


@OptIn(ExperimentalComposeUiApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun Image_Upload(screen : MutableState<String>) {
    val customSpacing = Spacing.customSpacing
    val adminViewModel = AdminViewModel()

    val byteArray = remember { mutableStateOf<ByteArray?>(null) }
    val uriLoad = remember { mutableStateOf(false) }
    val bitmapLoad = remember { mutableStateOf(false) }
    val buttonOK = remember { mutableStateOf(false) }
    val chooseFamiliesOpen = remember { mutableStateOf(false) }
    val loadProductsFamily = remember { mutableStateOf(false) }
    val startLoading = remember { mutableStateOf(false) }
    val customName = remember { mutableStateOf("") }
    val popUpNewFamily = remember { mutableStateOf(false) }
    var products: List<product_stock_response> = remember { mutableListOf() }
    val nameVerified = remember { mutableStateOf(false) }
    val customPrice = remember { mutableStateOf(0.0f) }
    val customAmount = remember { mutableStateOf(0.0f) }
    val customPriceVerified = remember { mutableStateOf(false) }
    val customAmountVerified = remember { mutableStateOf(false) }
    val familyNames = remember { mutableStateOf<List<String>>(listOf()) }
    val familiesToAssign = remember { mutableStateOf<List<String>>(listOf()) }
    val verificationOpen = remember {mutableStateOf(false)}

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

    if(verificationOpen.value){
        PopUp_Verification(
            screen = screen,
            verificationOpen = verificationOpen,
            verticalGradient = verticalGradient,
            verticalGradientIncomplete = verticalGradientIncomplete,
            customSpacing = customSpacing,
            isEditionMode = false,
            isDelete = false
        )
    }



    if (chooseFamiliesOpen.value) {
        PopUp_Choose_Family(
            screen = screen,
            adminViewModel = adminViewModel,
            scope = scope,
            chooseFamiliesOpen = chooseFamiliesOpen,
            customSpacing = customSpacing,
            verticalGradient = verticalGradient,
            verticalGradientDisabled = verticalGradientDisabled,
            familyNames = familyNames,
            familiesToAssign = familiesToAssign
        )
    }


   /* // Declare launcher for pick image
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> imageUri = uri }*/

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                modifier = Modifier.background(verticalGradient),
                backgroundColor = Color.Transparent,
                title = {
                    Text(
                        text = "NEW PRODUCT",
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
                    placeholder = {
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
                Spacer(
                    modifier = Modifier.height(
                        if (bitmapLoad.value) customSpacing.small
                        else customSpacing.mediumMedium
                    )
                )

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
                    placeholder = {
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
                Spacer(
                    modifier = Modifier.height(
                        if (bitmapLoad.value) customSpacing.small
                        else customSpacing.mediumMedium
                    )
                )

                /************ STOCK ************/
                OutlinedTextField(
                    value = customAmount.value?.toInt().toString(),
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
                    placeholder = {
                        Text(
                            text = "Amount",
                            style = TextStyle(
                                color = MaterialTheme.colors.secondary
                            )
                        )
                    },
                    trailingIcon = {
                        val image = Icons.Filled.Info
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

                Spacer(
                    modifier = Modifier.height(
                        if (bitmapLoad.value) customSpacing.small
                        else customSpacing.mediumMedium
                    )
                )

                /************ FAMILIES ************/
                Row(Modifier.fillMaxWidth()) {

                    /************ CHOOSE ************/
                    Text(
                        text = "Choose Families",
                        textAlign = TextAlign.Center,
                        style = typography.body1.copy(color = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .background(brush = if(familiesToAssign.value.isNullOrEmpty()) verticalGradientIncomplete else verticalGradient)
                            .padding(12.dp)
                            .clickable(onClick = {
                                adminViewModel.getFamilyNames {
                                    familyNames.value = adminViewModel.familyNameListResponse as MutableList<String>
                                    chooseFamiliesOpen.value = true
                                }
                            })
                    )
                }

                Spacer(
                    modifier = Modifier.height(
                        if (bitmapLoad.value) customSpacing.small
                        else customSpacing.mediumSmall
                    )
                )

                /************ PICK ************/
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        enabled = !bitmapLoad.value,
                        elevation = ButtonDefaults.elevation(0.dp),
                        onClick = { showToast("Image compression not available in Desktop") },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Transparent,
                            contentColor = Color(0xFFFFF5EE),
                            disabledBackgroundColor = Color.Transparent,
                            disabledContentColor = Color.Transparent
                        ),
                        modifier = Modifier
                            //.clip(RoundedCornerShape(4.dp))
                            .background(Color.Transparent)
                            //.fillMaxWidth(0.8f)
                            .fillMaxHeight()
                            .padding(customSpacing.default)

                    ) {
                        Text(
                            text = "Pick Image",
                            textAlign = TextAlign.Center,
                            style = typography.body1.copy(color = Color.White),
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(verticalGradientDisabled)
                                .fillMaxHeight()
                                .fillMaxWidth(0.6f)
                                .padding(12.dp)

                        )
                    }
                    Spacer(modifier = Modifier.width(customSpacing.mediumSmall))

                    IconButton(
                        enabled = bitmapLoad.value,
                        modifier = Modifier
                            //  .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .background(if (bitmapLoad.value) verticalGradient else verticalGradientDisabled),
                        onClick = {
                            byteArray.value = null
                            uriLoad.value = false
                            bitmapLoad.value = false
                            buttonOK.value = false
                        },
                    ) {
                        Icon(
                            Icons.Filled.Refresh,
                            tint = Color.White,
                            contentDescription = null
                        )
                    }

                }



                Spacer(modifier = Modifier.height(customSpacing.small))

                /************ IMAGE ************/
         /*       imageUri?.let {
                    if (!uriLoad.value) {
                        if (Build.VERSION.SDK_INT < 28) {
                            bitmap.value = MediaStore.Images
                                .Media.getBitmap(context.contentResolver, it)
                        } else {
                            val source = ImageDecoder
                                .createSource(context.contentResolver, it)
                            bitmap.value = ImageDecoder.decodeBitmap(source)
                        }
                        uriLoad.value = true
                    }

                    bitmap.value?.let { btm ->

                        Logger.i(btm.asImageBitmap().toString())
                        Logger.i(imageUri.toString())
                        bitmapLoad.value = true
                        Image(
                            bitmap = btm.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize(0.6f)
                                .padding(5.dp)
                        )
                    }
                }*/
                Spacer(
                    modifier = Modifier.height(
                        if (bitmapLoad.value) customSpacing.small
                        else (customSpacing.extraLarge * 2)
                    )
                )


                /************ UPLOAD ************/
                Text(
                    text = "UPLOAD",
                    textAlign = TextAlign.Center,
                    style = typography.body1.copy(color = Color.White),
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            if (bitmapLoad.value
                                && nameVerified.value
                                && customAmountVerified.value
                                && customPriceVerified.value
                                && !familiesToAssign.value.isNullOrEmpty())
                                    verticalGradient
                            else verticalGradientDisabled
                        )
                        .padding(12.dp)
                        .clickable(onClick = {
                            showToast("Create Product not available in Desktop")
                         /*   if (bitmapLoad.value
                                && nameVerified.value
                                && customAmountVerified.value
                                && customPriceVerified.value
                                && !familiesToAssign.value.isNullOrEmpty()
                            ) {

                                adminViewModel.multipartImageUpload(
                                    byteArray = byteArray,
                                    context = context,
                                    bitmap = bitmap,
                                    customName = customName
                                ) {
                                    Logger.i("*** *** *** *** MULTIPART SUCCESSS!!!!!!!")

                                    val newProduct = product_stock_request(
                                        name = customName.value,
                                        image = customName.value + ".jpg",
                                        price = customPrice.value,
                                        stock = customAmount.value.toInt(),
                                        families = familiesToAssign.value
                                    )

                                    adminViewModel.createProductStock(newProduct){
                                        Logger.i("Success create Product")
                                        verificationOpen.value = true
                                        buttonOK.value = true;
                                    }
                                }


                            } else Toast.makeText(context, "Incorrect Fields", Toast.LENGTH_SHORT).show()*/

                        })
                )
            }
        }
    }
}










