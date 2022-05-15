package view.main.composables

import ScreenNav
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import utils.URL_HEAD_IMAGES
import utils.productSelected
import utils.wishlistProducts
import theme.Spacing
import utils.AsyncImage
import utils.loadImageBitmap

@Composable
fun WishList(
    screen : MutableState<String>
) {
    val scaffoldState = rememberScaffoldState()
    val openPopUpDetails = remember { mutableStateOf(false) }
    val addToShoppingCart = remember { mutableStateOf(false) }

    val customSpacing = Spacing.customSpacing

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

    if (openPopUpDetails.value) {
        PopUpDetails(
            popUpDetailsOpen = openPopUpDetails,
            verticalGradient = verticalGradient,
            addShoppingCart = addToShoppingCart,
            verticalGradientDisabled = verticalGradientDisabled,
            isWishList = true,
            screen = screen
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
                        text = "WISHLIST",
                        modifier = Modifier
                            .padding(30.dp, 0.dp, 0.dp, 0.dp),
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            screen.value = ScreenNav.MainScreen.route
                        }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                },
            )
        }
    ) {

        LazyColumn(
            modifier = Modifier
                .padding(customSpacing.mediumMedium)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            itemsIndexed(wishlistProducts) { _, item ->
                Card(
                    modifier = Modifier.padding(vertical = customSpacing.mediumSmall, horizontal = customSpacing.small),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .background(verticalGradient)
                            .padding(customSpacing.small)
                            .clickable(onClick = {
                                productSelected = item
                                openPopUpDetails.value = true
                            }),
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Card(
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(100.dp)
                                    .padding(customSpacing.small),
                                shape = RoundedCornerShape(15.dp),
                                border = BorderStroke(2.dp, Color.DarkGray),

                                ) {
                                AsyncImage(
                                    load = { loadImageBitmap("$URL_HEAD_IMAGES${productSelected!!.image}") },
                                    painterFor = { remember { BitmapPainter(it) } },
                                    contentDescription = "Sample",
                                    contentScale = ContentScale.Crop
                                )
                            }


                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {

                                Text(text = item.name)
                                Spacer(modifier = Modifier.height(customSpacing.small))
                                Text(text = "${item.price}€")
                            }
                        }
                    }
                }
            }
        }
    }
}
