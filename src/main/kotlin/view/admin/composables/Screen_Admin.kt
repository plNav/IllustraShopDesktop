package view.admin.composables

import ScreenNav
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import data.model.order.order_response
import view.admin.AdminViewModel
import theme.Spacing
import utils.allOrders
import utils.analytics
import utils.familyNameList

@Composable
fun Admin_Screen(
    screen: MutableState<String>
) {
    val customSpacing = Spacing.customSpacing
    val adminViewModel = AdminViewModel()

    val createFamilyOpen = remember { mutableStateOf(false) }
    val selectionProductOpen = remember { mutableStateOf(false) }
    val selectionFamilyOpen = remember { mutableStateOf(false) }


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

    if (createFamilyOpen.value) {
        PopUp_Create_Family(
            adminViewModel = adminViewModel,
            screen = screen,
            createFamilyOpen = createFamilyOpen,
            customSpacing = customSpacing,
            verticalGradient = verticalGradient,
            verticalGradiendDisabled = verticalGradientDisabled
        )
    }

    if (selectionProductOpen.value) {
        PopUp_Selection(
            screen = screen,
            adminViewModel = adminViewModel,
            selectionOpen = selectionProductOpen,
            customSpacing = customSpacing,
            verticalGradient = verticalGradient,
            verticalGradientDisabled = verticalGradientDisabled,
            isProduct = true,
            verticalGradientIncomplete = verticalGradientIncomplete

        )
    }

    if (selectionFamilyOpen.value) {
        PopUp_Selection(
            screen = screen,
            adminViewModel = adminViewModel,
            selectionOpen = selectionFamilyOpen,
            customSpacing = customSpacing,
            verticalGradient = verticalGradient,
            verticalGradientDisabled = verticalGradientDisabled,
            isProduct = false,
            verticalGradientIncomplete = verticalGradientIncomplete
        )
    }


    Column() {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(brush = verticalGradient)
        ) {

            /************ BACK ************/
            IconButton(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .fillMaxWidth(0.15f)
                    .background(Color.Transparent),
                onClick = { screen.value = ScreenNav.MainScreen.route },
            ) {
                Icon(
                    Icons.Filled.ArrowBack,
                    tint = Color.White,
                    contentDescription = null
                )
            }


            /************ TITLE ************/
            Text(
                text = "ADMIN MENU",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.Transparent)
                    .padding(0.dp, customSpacing.mediumSmall, customSpacing.superLarge, customSpacing.mediumSmall)
                    .clickable(onClick = { })
            )


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
                .fillMaxSize()
                .padding(customSpacing.mediumMedium)
        ) {


            /************ CREATE PRODUCT STOCK ************/
            Text(
                text = "NEW PRODUCT",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush = verticalGradientDisabled)
                    .padding(12.dp)
                    .clickable(onClick = { screen.value = ScreenNav.ImageUploadScreen.route })
            )

            Spacer(
                modifier = Modifier.height(
                    customSpacing.extraLarge
                )
            )

            /************ EDIT/DELETE PRODUCT STOCK ************/
            Text(
                text = "EDIT/DELETE PRODUCT",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush = verticalGradientDisabled)
                    .padding(12.dp)
                    .clickable(onClick = { selectionProductOpen.value = true })
            )

            Spacer(
                modifier = Modifier.height(
                    customSpacing.extraLarge
                )
            )

            /************ CREATE FAMILY ************/
            Text(
                text = "NEW FAMILY",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush = verticalGradientDisabled)
                    .padding(12.dp)
                    .clickable(onClick = {
                        adminViewModel.getFamilyNames {
                            familyNameList = adminViewModel.familyNameListResponse as MutableList<String>

                            // loadProductsFamily.value = true
                            createFamilyOpen.value = true

                        }
                    })
            )

            Spacer(
                modifier = Modifier.height(
                    customSpacing.extraLarge
                )
            )

            /************ EDIT/DELETE FAMILY ************/
            Text(
                text = "EDIT/DELETE FAMILY",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush = verticalGradientDisabled)
                    .padding(12.dp)
                    .clickable(onClick = { selectionFamilyOpen.value = true })
            )

            Spacer(
                modifier = Modifier.height(
                    customSpacing.extraLarge
                )
            )


            /************ REQUESTS ************/
            Text(
                text = "ORDERS",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush = verticalGradientDisabled)
                    .padding(12.dp)
                    .clickable(onClick = {
                        adminViewModel.getOrders() {
                            allOrders = adminViewModel.allOrdersResponse as MutableList<order_response>
                            screen.value = ScreenNav.OrderScreen.route
                        }
                    })
            )

            Spacer(
                modifier = Modifier.height(
                    customSpacing.extraLarge
                )
            )

            /************ ANALYTICS ************/
            Text(
                text = "Analytics",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush = verticalGradientDisabled)
                    .padding(12.dp)
                    .clickable(onClick = {
                        adminViewModel.getAnalytics() {
                            analytics = adminViewModel.analyticsResponse!!
                            screen.value = ScreenNav.AnalyticsScreen.route
                        }
                    })
            )

            Spacer(
                modifier = Modifier.height(
                    customSpacing.large
                )
            )

            Spacer(
                modifier = Modifier.height(
                    customSpacing.large
                )
            )


        }
    }
}
