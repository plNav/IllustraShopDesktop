package view.main.composables

import utils.AsyncImage
import utils.loadImageBitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.unit.dp
import pab.lop.illustrashopandroid.data.model.product_stock.product_stock_response
import pab.lop.illustrashopandroid.utils.URL_HEAD_IMAGES
import pab.lop.illustrashopandroid.utils.productSelected

@Composable
fun ImageContent(
    product : product_stock_response,
    familyProducts: HashMap<String, List<product_stock_response>>,
    family: String,
    index: Int,
    popUpDetailsOpen: MutableState<Boolean>
) {
    Card(
        modifier = Modifier
            //  .padding(5.dp)
            .clickable(onClick = {
                productSelected = product
                popUpDetailsOpen.value = true
            })

    ) {

            AsyncImage(
                load = { loadImageBitmap("$URL_HEAD_IMAGES${product.image}") },
                painterFor = { remember { BitmapPainter(it) } },
                contentDescription = "Sample",
                modifier = Modifier.width(200.dp)
            )





      /*  Image(
            model = ImageRequest.Builder(LocalContext.current)
                .data("$URL_HEAD_IMAGES${familyProducts.get(family)?.get(page)?.image}")
                .crossfade(true)
                .crossfade(1000)
                .build(),
            loading = { CircularProgressIndicator() },
            contentDescription = familyProducts.get(family)?.get(page)?.image,
            contentScale = ContentScale.Crop,
            error = {
                Image(
                    painter = painterResource(id = R.drawable.loading_image),
                    contentDescription = stringResource(R.string.error),
                )
            },
        )*/
        /*      AsyncImage(
                  model = ImageRequest.Builder(LocalContext.current)
                      .data("$URL_HEAD_IMAGES${familyProducts.get(family)?.get(page)?.image}")
                      .crossfade(true)
                      .crossfade(1000)
                      .build(),
                  contentDescription = null,

                  //placeholder = painterResource(id = R.drawable.loading_image),
                  //   modifier = Modifier.fillMaxSize(0.8f)
              )*/



    }
}

/*
fun loadNetworkImage(link: String): ImageBitmap {
    val url = URL(link)
    val connection = url.openConnection() as HttpURLConnection
    connection.connect()

    val inputStream = connection.inputStream
    val bufferedImage = ImageIO.read(inputStream)

    val stream = ByteArrayOutputStream()
    ImageIO.write(bufferedImage, "jpg", stream)
    val byteArray = stream.toByteArray()
    val image = org.jetbrains.skia.Image.makeFromEncoded(byteArray).asImageBitmap()
    return image
}
*/

