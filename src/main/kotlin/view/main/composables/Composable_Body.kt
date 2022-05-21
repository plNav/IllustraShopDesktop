package view.main.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import data.model.product_stock.product_stock_response
import kotlinx.coroutines.launch
import theme.GrayDisabled
import theme.GrayDisabledLight
import theme.Spacing

import utils.excludedFamilies

@Composable
fun Body(
    familyProducts: HashMap<String, List<product_stock_response>>,
    popUpDetailsOpen: MutableState<Boolean>
) {

    val verticalGradientDisabled = Brush.verticalGradient(
        colors = listOf(MaterialTheme.colors.onError, Color.DarkGray),
        startY = 0f,
        endY = 100f
    )

    LazyColumn(
        modifier = Modifier.background(verticalGradientDisabled),
    ){
        itemsIndexed(familyProducts.keys.toMutableList()) { index, family ->
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .border(0.5.dp, MaterialTheme.colors.primaryVariant)
            ) {
                if (!excludedFamilies.contains(family)) {
                    Text(
                        text = family,
                        fontFamily = FontFamily.Cursive,
                        textAlign = TextAlign.Start,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, top = 5.dp)
                    )
                    var count = 0
                    val row = mutableListOf<product_stock_response>()
                    for(product in familyProducts.get(family)!!){

                        count++
                        row.add(product)
                       if(count == 6){
                            Row(
                                modifier = Modifier.background(verticalGradientDisabled)
                            ) {
                                for(prod in row){
                                    Box{
                                        ImageContent(
                                            product = prod,
                                            familyProducts = familyProducts,
                                            family = family,
                                            index = index,
                                            popUpDetailsOpen = popUpDetailsOpen
                                        )
                                    }
                                }
                                row.clear()
                            }
                        }
                    }
                    if(row.isNotEmpty()) {
                        Row(
                            modifier = Modifier.background(verticalGradientDisabled)
                        ) {
                            for (p in row) {
                                Box{
                                    ImageContent(
                                        product = p,
                                        familyProducts = familyProducts,
                                        family = family,
                                        index = index,
                                        popUpDetailsOpen = popUpDetailsOpen
                                    )
                                }
                            }
                            row.clear()
                        }
                    }
                }
            }
        }
        //item{ InlineBanner() } //TODO BOTTOM BANNER
    }
}