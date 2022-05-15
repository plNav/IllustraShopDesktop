package view.admin.composables

import ScreenNav
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import data.model.family.family_response
import data.model.product_stock.product_stock_response
import view.admin.AdminViewModel
import theme.Spacing
import utils.familyNameList
import utils.familySelected
import utils.productSelected

@Composable
fun PopUp_Selection(
    screen : MutableState<String>,
    adminViewModel: AdminViewModel,
    selectionOpen: MutableState<Boolean>,
    customSpacing: Spacing,
    verticalGradient: Brush,
    verticalGradientDisabled: Brush,
    isProduct: Boolean,
    verticalGradientIncomplete: Brush
) {
    val families = remember { mutableStateOf<List<family_response>>(listOf()) }
    val products = remember { mutableStateOf<List<product_stock_response>>(listOf()) }
    val editFamilyOpen = remember { mutableStateOf(false) }

    if (editFamilyOpen.value) {
        PopUp_Edit_Family(
            screen = screen,
            adminViewModel = adminViewModel,
            createFamilyOpen = editFamilyOpen,
            customSpacing = customSpacing,
            verticalGradient = verticalGradient,
            verticalGradiendDisabled = verticalGradientDisabled,
            verticalGradientIncomplete = verticalGradientIncomplete,
        )
    }



    if (isProduct) {
        adminViewModel.getProducts {
            products.value = adminViewModel.productListResponse
        }
    } else {
        adminViewModel.getFamilies {
            families.value = adminViewModel.familyListResponse
        }
    }

    Window(
        onCloseRequest = {
            selectionOpen.value = false
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

                    /************ TITLE ************/
                Text(
                        text = if(isProduct) "EDIT PRODUCT" else "EDIT FAMILY",
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
                        onClick = {
                            selectionOpen.value = false
                            screen.value = ScreenNav.AdminScreen.route
                        },
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

                LazyColumn(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = customSpacing.mediumSmall)
                        .fillMaxHeight(0.8f)

                ) {


                    if (isProduct) {
                        for (product in products.value) {
                            item {
                                Column() {
                                    Text(
                                        text = product.name,
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.body1.copy(color = Color.White),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(brush = verticalGradientDisabled)
                                            .padding(12.dp)
                                            .clickable(onClick = {
                                                productSelected = product
                                                screen.value = ScreenNav.ProductEditScreen.route

                                            })
                                    )
                                    Spacer(
                                        modifier = Modifier.height(
                                            customSpacing.mediumSmall
                                        )
                                    )
                                }
                            }
                        }

                    } else {
                        for(family in families.value) {
                            item {
                                Column() {
                                    Text(
                                        text = family.name,
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.body1.copy(color = Color.White),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(brush = verticalGradientDisabled)
                                            .padding(12.dp)
                                            .clickable(onClick = {
                                                adminViewModel.getFamilyNames {
                                                    familySelected = family
                                                    familyNameList =
                                                        adminViewModel.familyNameListResponse as MutableList<String>
                                                    editFamilyOpen.value = true
                                                }
                                            })
                                    )
                                    Spacer(
                                        modifier = Modifier.height(
                                            customSpacing.mediumSmall
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
                Text("") // ÑAPAS
            }
        }
    }

