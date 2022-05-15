package view.admin.composables

import ScreenNav
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import data.model.order.order_response
import view.admin.AdminViewModel
import theme.Spacing
import utils.userSelected

@Composable
fun OrderStart(screen : MutableState<String>) {
    val customSpacing = Spacing.customSpacing
    val adminViewModel = AdminViewModel()
    val isAdmin = userSelected!!.rol.uppercase() == "ADMIN"

    val isEditOpen = remember { mutableStateOf(false) }
    val orderSelected = remember { mutableStateOf<order_response?>(null) }
    val filter = remember { mutableStateOf("") }
    val scaffoldState = rememberScaffoldState()
    val verticalGradient = Brush.verticalGradient(
        colors = listOf(MaterialTheme.colors.primary, MaterialTheme.colors.primaryVariant),
        startY = 0f,
        endY = 100f
    )

    if (isEditOpen.value) {
        PopUp_EditOrder(
            isEditOpen = isEditOpen,
            orderSelected = orderSelected,
            adminViewModel = adminViewModel,
            verticalGradient = verticalGradient,
            screen = screen,
            customSpacing = customSpacing,
            isAdmin = isAdmin
        )
    }


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            OrderTopBar(
                verticalGradient = verticalGradient,
                screen = screen
            )
        }
    ) {

        Column {
            Filters(
                filter = filter,
                customSpacing = customSpacing
            )
            Orders(
                customSpacing = customSpacing,
                filter = filter,
                orderSelected = orderSelected,
                isEditOpen = isEditOpen,
                isAdmin = isAdmin
            )
        }
    }
}



@Composable
private fun OrderTopBar(verticalGradient: Brush, screen : MutableState<String>) {
    TopAppBar(
        elevation = 0.dp,
        modifier = Modifier.background(verticalGradient),
        backgroundColor = Color.Transparent,
        title = {
            Text(
                text = "ORDERS",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(10.dp, 0.dp, 0.dp, 0.dp),
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { screen.value = ScreenNav.MainScreen.route }
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null, tint = Color.White)
            }
        }
    )
}



