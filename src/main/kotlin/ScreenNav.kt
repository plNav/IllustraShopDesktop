sealed class ScreenNav(val route: String) {

    object LoginScreen : ScreenNav("login_screen")
    object RegisterScreen : ScreenNav("register_screen")

    object MainScreen : ScreenNav("main_screen")
    object ShoppingCartScreen : ScreenNav("shopping_cart_screen")
    object WishScreen : ScreenNav("wish_screen")

    object ImageUploadScreen : ScreenNav("image_upload_screen")
    object AdminScreen : ScreenNav("admin_screen")
    object ProductEditScreen : ScreenNav("product_edit_screen")
    object OrderScreen : ScreenNav("order_screen")

    object PayScreen : ScreenNav("pay_screen")

    object PersonalInfoScreen : ScreenNav("personal_info_screen")

    object ReloadScreen : ScreenNav("reload_screen")

}