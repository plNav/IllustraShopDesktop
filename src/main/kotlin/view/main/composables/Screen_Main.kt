package view.main.composables

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import kotlinx.coroutines.CoroutineScope
import pab.lop.illustrashopandroid.data.model.product_stock.product_stock_response
import pab.lop.illustrashopandroid.ui.view.main.composables.Body
import pab.lop.illustrashopandroid.ui.view.main.composables.MainDrawer
import pab.lop.illustrashopandroid.ui.view.main.composables.PopUpDetails
import view.main.MainViewModel
import pab.lop.illustrashopandroid.utils.*
import theme.Spacing


@Composable
fun Main(
    screen: MutableState<String>
) {
    val loadProductsFamily = remember { mutableStateOf(false) }
    val startLoading = remember { mutableStateOf(false) }
    val popUpDetailsOpen = remember { mutableStateOf(false) }
    val addShoppingCart = remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val mainViewModel = MainViewModel()
    val customSpacing = Spacing.customSpacing



    if (userSelected == null) {
        userSelected = userDefaultNoAuth
        shoppingCartSelected = shoppingCartDefaultNoAuth
    }

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


    if (!startLoading.value) {
        startLoading.value = true
        mainViewModel.getProductsFamily {
            familyProducts = mainViewModel.familyProductsResponse
            loadProductsFamily.value = true
        }
        if (userSelected != userDefaultNoAuth) {
            mainViewModel.getShoppingCart(userSelected!!._id) {
                shoppingCartSelected = mainViewModel.currentShoppingCartResponse
                mainViewModel.getAllProductShopping(shoppingCartSelected!!._id) {
                    currentShoppingProducts = mainViewModel.currentProductsShopping
                }
            }
        }
    }



    if (loadProductsFamily.value)
        MainStart(
            snackbarHostState = snackbarHostState,
            scope = scope,
            scaffoldState = rememberScaffoldState(),
            mainViewModel = mainViewModel,
            familyProducts = familyProducts,
            popUpDetailsOpen = popUpDetailsOpen,
            verticalGradient = verticalGradient,
            verticalGradientDisabled = verticalGradientDisabled,
            addShoppingCart = addShoppingCart,
            customSpacing = customSpacing,
            screen = screen
        )

    if (popUpDetailsOpen.value) {
        PopUpDetails(
            scope = scope,
            popUpDetailsOpen = popUpDetailsOpen,
            verticalGradient = verticalGradient,
            addShoppingCart = addShoppingCart,
            verticalGradientDisabled = verticalGradientDisabled,
            isWishList = false,
            screen = screen
        )
    }
}


@Composable
fun MainStart(
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    mainViewModel: MainViewModel,
    familyProducts: HashMap<String, List<product_stock_response>>,
    popUpDetailsOpen: MutableState<Boolean>,
    verticalGradient: Brush,
    addShoppingCart: MutableState<Boolean>,
    customSpacing: Spacing,
    verticalGradientDisabled: Brush,
    screen: MutableState<String>,
) {

    Scaffold(
        scaffoldState = scaffoldState,
        drawerBackgroundColor = MaterialTheme.colors.primaryVariant,
        drawerShape = customShape(),
        drawerContent = {
            MainDrawer(
                verticalGradientDisabled = verticalGradientDisabled,
                screen = screen
            )
        },
        topBar = {
            TopAppBar(
                verticalGradient = verticalGradient,
                scope = scope,
                scaffoldState = scaffoldState,
                addShoppingCart = addShoppingCart,
                screen = screen,
            )
        }
    ) {
        Body(familyProducts, popUpDetailsOpen)
    }
}




@Composable
fun customShape() = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Rectangle(
            Rect(
                left = 0f,
                top = 0f,
                right = size.width * 2 / 3,
                bottom = size.height
            )
        )
    }
}




