package view.main.composables

import androidx.compose.foundation.background
import utils.AsyncImage
import utils.loadImageBitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.unit.dp
import data.model.product_stock.product_stock_response
import theme.GrayDisabledLight
import utils.URL_HEAD_IMAGES
import utils.productSelected

@Composable
fun ImageContent(
    product : product_stock_response,
    familyProducts: HashMap<String, List<product_stock_response>>,
    family: String,
    index: Int,
    popUpDetailsOpen: MutableState<Boolean>
) {
    Brush.verticalGradient(
        colors = listOf(MaterialTheme.colors.onError, Color.DarkGray),
        startY = 0f,
        endY = 100f
    )
    Card(
        modifier = Modifier
            .padding(5.dp)
            .clickable(onClick = {
                productSelected = product
                popUpDetailsOpen.value = true
            })

    ) {
            AsyncImage(
                load = { loadImageBitmap("$URL_HEAD_IMAGES${product.image}") },
                painterFor = { remember { BitmapPainter(it) } },
                contentDescription = "Sample",
                modifier = Modifier.width(200.dp).height(300.dp).background(Color.Black).padding(5.dp)

            )
    }
}


