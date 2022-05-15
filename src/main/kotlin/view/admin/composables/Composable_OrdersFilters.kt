package view.admin.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import theme.Spacing
import theme.SurfaceAlmostBlack
import utils.ENDED
import utils.PENDING
import utils.SENT

@Composable
fun Filters(filter: MutableState<String>, customSpacing: Spacing) {
    val all: String = "ALL"
    val pending: String = PENDING
    val sent: String = SENT

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {

        Button(
            modifier = Modifier.height(customSpacing.superLarge),
            colors =
            if (filter.value == "") ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            else ButtonDefaults.buttonColors(backgroundColor = SurfaceAlmostBlack),


            onClick = {
                filter.value = ""
            },
        ) {
            Text(text = all, color = Color.White)
        }
        Button(
            modifier = Modifier.height(customSpacing.superLarge),
            colors =
            if (filter.value == PENDING) ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            else ButtonDefaults.buttonColors(backgroundColor = SurfaceAlmostBlack),


            onClick = {
                filter.value = PENDING
            },
        ) {
            Text(text = pending, color = Color.White)
        }
        Button(
            modifier = Modifier.height(customSpacing.superLarge),
            colors =
            if (filter.value == SENT) ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            else ButtonDefaults.buttonColors(backgroundColor = SurfaceAlmostBlack),


            onClick = {
                filter.value = SENT
            },
        ) {
            Text(text = sent, color = Color.White)
        }
        Button(
            modifier = Modifier.height(customSpacing.superLarge),
            colors =
            if (filter.value == ENDED) ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            else ButtonDefaults.buttonColors(backgroundColor = SurfaceAlmostBlack),


            onClick = {
                filter.value = ENDED
            },
        ) {
            Icon(Icons.Filled.Done, contentDescription = null, tint = Color.White)
        }

    }
}