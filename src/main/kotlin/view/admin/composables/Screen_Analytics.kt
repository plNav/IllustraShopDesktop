package view.admin.composables

import ScreenNav
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import theme.Spacing
import utils.analytics
import view.admin.AdminViewModel

@Composable
fun Analytics(
    screen: MutableState<String>
) {

    val buys = "Buys"
    val users = "Users"
    val total = "Total"

    val adminViewModel = AdminViewModel()
    val customSpacing = Spacing.customSpacing


    val firstOpen = remember { mutableStateOf(true) }
    val scaffoldState = rememberScaffoldState()

    val verticalGradient = Brush.verticalGradient(
        colors = listOf(MaterialTheme.colors.primary, MaterialTheme.colors.primaryVariant),
        startY = 0f,
        endY = 100f
    )




    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            EditFamilyTopAppBar(
                verticalGradient = verticalGradient,
                screen = screen
            )
        }
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(customSpacing.mediumSmall),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            item {
                CustomCard(
                    name = users,
                    value = "${analytics.users}"
                )
            }

            item {
                CustomCard(
                    name = buys,
                    value = "${analytics.buys}"
                )
            }

            item {
                CustomCard(
                    name = total,
                    value = "${analytics.total} €"
                )
            }
        }
    }
}


@Composable
fun CustomCard(name: String, value: String) {
    Card(
        backgroundColor = Color.DarkGray,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = name, modifier = Modifier.fillMaxWidth(0.5f), color = Color.White, fontWeight = FontWeight.Bold)
            Text(
                text = value,
                modifier = Modifier.fillMaxWidth(0.5f),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }

}


@Composable
private fun EditFamilyTopAppBar(verticalGradient: Brush, screen: MutableState<String>) {
    TopAppBar(
        elevation = 0.dp,
        modifier = Modifier.background(verticalGradient),
        backgroundColor = Color.Transparent,
        title = {
            Text(
                text = "ANALYTICS",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(10.dp, 0.dp, 0.dp, 0.dp),
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


