// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import view.main.composables.Main
import view.login_register.composables.Login
import theme.IllustraShopAndroidTheme
import utils.Toast

val message: MutableState<String> = mutableStateOf("")
val state: MutableState<Boolean> = mutableStateOf(false)

fun showToast(msg : String){
     message.value = msg
    state.value = true
}

@Composable
fun App() {


    val screen = remember { mutableStateOf(ScreenNav.LoginScreen.route)}
    Toast(message.value, state)

    IllustraShopAndroidTheme {
        when(screen.value){
            ScreenNav.LoginScreen.route -> Login(screen)
            ScreenNav.RegisterScreen.route -> Register(screen)
            ScreenNav.MainScreen.route -> Main(screen)
        }
    }
}
fun main() = singleWindowApplication(
    title = "IlustraShop Desktop",
    state = WindowState(
        size = DpSize(1280.dp, 920.dp),
        position = WindowPosition(alignment = Alignment.Center)
    )
){
        App()
}

/*
@Composable
fun Login(screen: MutableState<String>) {
    Column {
        Text("Login")
        Button(onClick = {
            screen.value = ScreenNav.RegisterScreen.route

        } ){
            Text("to register")
        }
    }
}
*/

@Composable
fun Register(screen: MutableState<String>) {
    Column {
        Text("Register")
        Button(onClick = {
            screen.value = ScreenNav.LoginScreen.route
        } ){
            Text("to Login")
        }
    }
}

