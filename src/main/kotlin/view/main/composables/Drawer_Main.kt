package view.main.composables


import ScreenNav
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import theme.Spacing
import utils.isEditionMode
import utils.userDefaultNoAuth
import utils.userSelected
import utils.wishlistProducts
import view.main.MainViewModel

@Composable
fun MainDrawer(
    verticalGradientDisabled: Brush,
    screen : MutableState<String>
) {
    if(userSelected == null) userSelected = userDefaultNoAuth
    val customSpacing = Spacing.customSpacing
    val mainViewModel = MainViewModel()

    /*** USER INFO ***/
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .background(verticalGradientDisabled)
            .padding(vertical = customSpacing.mediumLarge, horizontal = customSpacing.mediumMedium)
    ){

        Card(
            border = BorderStroke(2.dp, Color.DarkGray),
            modifier = Modifier.background(Color.Transparent),
            shape = RoundedCornerShape(15.dp),
            elevation = 20.dp
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface)
            ){
                Text(

                    text = "Logged as:",
                    modifier = Modifier
                        .background(Color.Transparent)
                        .padding(
                            start = customSpacing.mediumMedium,
                            top = customSpacing.mediumMedium,
                            end = customSpacing.small,
                            bottom = customSpacing.default
                        )
                        .align(Alignment.Start),
                    fontSize = 10.sp,
                    textAlign = TextAlign.Left,


                    )
                Text(
                    text = if (userSelected == userDefaultNoAuth) "Not Logged" else userSelected!!.username,
                    modifier = Modifier
                        .background(Color.Transparent)
                        .fillMaxWidth()
                        .padding(
                            start = customSpacing.small,
                            top = customSpacing.small,
                            end = customSpacing.mediumMedium,
                            bottom = customSpacing.mediumLarge
                        )
                        .align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.End

                )
            }
        }
    }
    Spacer(modifier = Modifier.height(customSpacing.small))
    Divider(color = Color.DarkGray, thickness = 5.dp)
    Spacer(modifier = Modifier.height(customSpacing.small))
    Divider(color = Color.DarkGray, thickness = 3.dp)
    Spacer(modifier = Modifier.height(customSpacing.small))
    Divider(color = Color.DarkGray, thickness = 2.dp)
    Spacer(modifier = Modifier.height(customSpacing.small))
    Divider(color = Color.DarkGray, thickness = 1.dp)

    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.65f)
            .padding(10.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceAround
    ) {

        Divider(color = Color.DarkGray, thickness = 1.dp)

        /*** EDIT PERSONAL INFO ***/
        Text(
            text =
            if (userSelected == userDefaultNoAuth) "Register"
            else "Edit Personal Info",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1.copy(color = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(brush = verticalGradientDisabled)
                .padding(12.dp)
                .clickable(onClick = {
                    if (userSelected == userDefaultNoAuth){
                        isEditionMode = false
                        screen.value = ScreenNav.RegisterScreen.route
                    }
                    else{
                        isEditionMode = true
                        screen.value = ScreenNav.RegisterScreen.route
                    }
                })
        )


        if (userSelected!! != userDefaultNoAuth){
            Divider(color = Color.DarkGray, thickness = 1.dp)

            /*** WISHLIST ***/
            Text(
                text = "Wishlist",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush = verticalGradientDisabled)
                    .padding(12.dp)
                    .clickable(onClick = {
                        mainViewModel.getAllProductStock(userSelected!!.wishlist){
                            wishlistProducts = mainViewModel.productListResponse
                            screen.value = ScreenNav.WishScreen.route
                        }
                    })
            )

            Divider(color = Color.DarkGray, thickness = 1.dp)

            /*** DELIVERS ***/
            Text(
                text = "Orders",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush = verticalGradientDisabled)
                    .padding(12.dp)
                    .clickable(onClick = {
                       /* *//*if (userSelected!!.rol.uppercase() == "ADMIN") {
                            adminViewModel.getOrders() {
                                allOrders = adminViewModel.allOrdersResponse as MutableList<order_response>
                                navController.navigate(ScreenNav.OrderScreen.withArgs(true))
                            }
                        } else {
                            adminViewModel.getUserOrders(userId = userSelected!!._id) {
                                allOrders = adminViewModel.allOrdersResponse as MutableList<order_response>
                                navController.navigate(ScreenNav.OrderScreen.withArgs(false))
                            }
                        }*/
                    })
            )
        }


        /*** ADMIN SETTINGS IF USER ROL == ADMIN ***/
        if (userSelected!!.rol.uppercase() == "ADMIN") {
            Divider(color = Color.DarkGray, thickness = 1.dp)
            Text(
                text = "Menu Admin",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush = verticalGradientDisabled)
                    .padding(12.dp)
                    .clickable(onClick = { screen.value = ScreenNav.LoginScreen.route /*TODO MENU ADMIN*/ })
            )
        }

        Divider(color = Color.DarkGray, thickness = 1.dp)

        /*** CLOSE SESSION - INIT SESSION IF NO AUTH USER ***/
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(brush = verticalGradientDisabled)
                .padding(12.dp)
                .clickable(onClick = {
                    userSelected = null
                    screen.value = ScreenNav.LoginScreen.route
                }),
            text =
            if (userSelected == userDefaultNoAuth) "Login"
            else "Logout",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1.copy(color = Color.White),
        )
        Divider(color = Color.DarkGray, thickness = 1.dp)

    }

}