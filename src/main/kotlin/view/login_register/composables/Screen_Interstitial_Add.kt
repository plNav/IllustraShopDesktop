package view.login_register.composables

import ScreenNav
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import utils.userDefaultNoAuth
import utils.userSelected

@Composable
fun InterstitialAdd(screen : MutableState<String>){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ){
        IconButton(onClick = {
            userSelected = userDefaultNoAuth
            screen.value = ScreenNav.MainScreen.route
        }){
            Icon(Icons.Filled.Close, null, tint = Color.White)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(text = "Space For Adds", color = Color.White)
            Spacer(Modifier.height(10.dp))
            Text(text = "Please Google improve your integration soon!", color = Color.White)
        }
    }
}