package com.example.jetpackcomposeexample.image

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ----- Coil 2.x -----
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.decode.SvgDecoder

// ----- Glide -----
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

// ----- Picasso -----
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

import com.example.jetpackcomposeexample.R
import com.example.jetpackcomposeexample.ui.theme.JetpackComposeExampleTheme


class ImageExample : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeExampleTheme {
                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    displayImagesComponent()
                }
            }
        }
    }
}

fun LazyListScope.displayImagesComponent() {

    item {
        TitleComponent("Local PNG/JPG Resources")
        LocalResourceImageComponent(R.drawable.landscape)
    }

    item {
        TitleComponent("Vector XML Drawable")
        VectorXmlImage(R.drawable.ic_launcher_foreground)
    }

    item {
        TitleComponent("Load Network Image - Picasso")
        NetworkImageComponentPicasso(
            url = "https://github.com/vinaygaba/CreditCardView/raw/master/images/Feature%20Image.png"
        )
    }

    item {
        TitleComponent("Load Network Image - Glide")
        NetworkImageComponentGlide(
            url = "https://github.com/vinaygaba/CreditCardView/raw/master/images/Feature%20Image.png"
        )
    }

    item {
        TitleComponent("Load Network Image - Coil")
        NetworkImageCoil(
            url = "https://avatars.githubusercontent.com/u/92685826?v=4"
        )
    }

    item {
        TitleComponent("SVG Image using Coil")
        SvgImageCoil(
            url = "https://dev.w3.org/SVG/tools/svgweb/samples/svg-files/android.svg"
        )
    }

    item {
        TitleComponent("GIF / Animated WebP using Coil")
        AnimatedImageCoil(
            url = "https://media.giphy.com/media/ICOgUNjpvO0PC/giphy.gif"
        )
    }
}

/* ---------------------------------------------------------------------
 * Local PNG/JPG
------------------------------------------------------------------------ */
@Composable
fun LocalResourceImageComponent(@DrawableRes resId: Int) {
    Image(
        painter = painterResource(resId),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .sizeIn(maxHeight = 200.dp)
            .clip(RoundedCornerShape(8.dp))
    )
}

/* ---------------------------------------------------------------------
 * Vector XML
------------------------------------------------------------------------ */
@Composable
fun VectorXmlImage(@DrawableRes id: Int) {
    Image(
        painter = painterResource(id),
        contentDescription = null,
        modifier = Modifier.size(120.dp)
    )
}

/* ---------------------------------------------------------------------
 * Picasso loader
------------------------------------------------------------------------ */
@Composable
fun NetworkImageComponentPicasso(
    url: String,
    modifier: Modifier = Modifier
) {
    var image by remember { mutableStateOf<ImageBitmap?>(null) }
    var drawable by remember { mutableStateOf<Drawable?>(null) }

    DisposableEffect(url) {
        val picasso = Picasso.get()
        val target = object : Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                drawable = placeHolderDrawable
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                drawable = errorDrawable
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                image = bitmap?.asImageBitmap()
            }
        }

        picasso.load(url).into(target)

        onDispose {
            image = null
            drawable = null
            picasso.cancelRequest(target)
        }
    }

    val sizeModifier = modifier.fillMaxWidth().sizeIn(maxHeight = 200.dp)

    when {
        image != null -> Image(bitmap = image!!, contentDescription = null, modifier = sizeModifier)
        drawable != null -> Canvas(sizeModifier) {
            drawIntoCanvas { drawable!!.draw(it.nativeCanvas) }
        }
    }
}

/* ---------------------------------------------------------------------
 * Glide loader
------------------------------------------------------------------------ */
@Composable
fun NetworkImageComponentGlide(
    url: String,
    modifier: Modifier = Modifier
) {
    var image by remember { mutableStateOf<ImageBitmap?>(null) }
    var drawable by remember { mutableStateOf<Drawable?>(null) }

    val context = LocalContext.current

    DisposableEffect(url) {
        val glide = Glide.with(context)
        val target = object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) {
                drawable = placeholder
                image = null
            }

            override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                image = bitmap.asImageBitmap()
            }
        }

        glide.asBitmap().load(url).into(target)

        onDispose {
            glide.clear(target)
            image = null
            drawable = null
        }
    }

    val sizeModifier = modifier.fillMaxWidth().sizeIn(maxHeight = 200.dp)

    when {
        image != null -> Image(bitmap = image!!, contentDescription = null, modifier = sizeModifier)
        drawable != null -> Canvas(sizeModifier) {
            drawIntoCanvas { drawable!!.draw(it.nativeCanvas) }
        }
    }
}

/* ---------------------------------------------------------------------
 * Coil 2.x PNG/JPG/WebP
------------------------------------------------------------------------ */
@Composable
fun NetworkImageCoil(url: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .sizeIn(maxHeight = 200.dp)
            .clip(RoundedCornerShape(12.dp))
    )
}

/* ---------------------------------------------------------------------
 * Coil 2.x SVG
------------------------------------------------------------------------ */
@Composable
fun SvgImageCoil(url: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .decoderFactory(SvgDecoder.Factory())
            .build(),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .sizeIn(maxHeight = 200.dp)
    )
}

/* ---------------------------------------------------------------------
 * Coil 2.x GIF / Animated WebP
------------------------------------------------------------------------ */
@Composable
fun AnimatedImageCoil(url: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .sizeIn(maxHeight = 200.dp)
    )
}

@Composable
fun TitleComponent(title: String) {
    Text(
        title,
        style = TextStyle(
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.W900,
            fontSize = 14.sp,
            color = Color.Black
        ),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )
}

fun Modifier.RoundedCornerClipModifier(size: Dp): Modifier = composed {
    clip(RoundedCornerShape(size))
}
