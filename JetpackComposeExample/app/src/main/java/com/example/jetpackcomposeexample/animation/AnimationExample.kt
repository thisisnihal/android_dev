package com.example.jetpackcomposeexample.animation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*
import com.example.jetpackcomposeexample.ui.theme.JetpackComposeExampleTheme
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class AnimationExample : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                JetpackComposeExampleTheme {
                    AnimationDemoScreen()
                }
            }
        }
    }
}

@Composable
fun AnimationDemoScreen() {
    val scrollState = rememberScrollState()

    Scaffold { padding ->
        Column(
            Modifier
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            Title("1. animate*AsState")
            AnimateAsStateDemo()

            Title("2. Animatable (proper side effects)")
            AnimatableDemo()

            Title("3. AnimatedVisibility")
            AnimatedVisibilityDemo()

            Title("4. AnimatedContent")
            AnimatedContentDemo()

            Title("5. updateTransition (coordinated animations)")
            UpdateTransitionDemo()

            Title("6. InfiniteTransition (looping)")
            InfiniteAnimationDemo()

            Title("7. Modern 'Swipeable' with draggable + Animatable")
            DraggableSwipeDemo()

            Title("8. Canvas Animation")
            CanvasAnimationDemo()

            Title("9. animateContentSize")
            ExpandableCard()

            Title("End")
        }
    }
}

/* ----------------------------------------------------------
                        Title Text
---------------------------------------------------------- */
@Composable
fun Title(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(vertical = 12.dp)
    )
}

/* ----------------------------------------------------------
                   1 animate*AsState Demo
---------------------------------------------------------- */
@Composable
fun AnimateAsStateDemo() {
    var toggled by remember { mutableStateOf(false) }

    val size by animateDpAsState(if (toggled) 120.dp else 60.dp)
    val color by animateColorAsState(if (toggled) Color.Red else Color.Blue)

    Box(
        Modifier
            .size(size)
            .background(color, CircleShape)
            .clickable { toggled = !toggled }
    )
}

/* ----------------------------------------------------------
                   2 Modern Animatable Usage
---------------------------------------------------------- */
@Composable
fun AnimatableDemo() {
    val offsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope() //  the scope variable must be in composable scope

    Column {
        Button(onClick = {
            scope.launch {
                // animateTo() is a suspend function, so it must run inside coroutine
                offsetX.animateTo(200f, tween(300))
                offsetX.animateTo(0f, spring())
            }
        }) {
            Text("Bounce")
        }

        Spacer(Modifier.height(8.dp))

        Box(
            Modifier
                .offset(x = offsetX.value.dp)
                .size(60.dp)
                .background(Color.Green, CircleShape)
        )
    }
}

/* ----------------------------------------------------------
                   3 AnimatedVisibility
---------------------------------------------------------- */
@Composable
fun AnimatedVisibilityDemo() {
    var visible by remember { mutableStateOf(true) }

    Button(onClick = { visible = !visible }) {
        Text("Toggle")
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        Box(
            Modifier
                .size(120.dp)
                .background(Color(0xFF009688))
        )
    }
}

/* ----------------------------------------------------------
                   4 AnimatedContent
---------------------------------------------------------- */
@Composable
fun AnimatedContentDemo() {
    var count by remember { mutableStateOf(0) }

    Button(onClick = { count++ }) {
        Text("Increment")
    }

    AnimatedContent(targetState = count) { number ->
        Text("Count: $number", style = MaterialTheme.typography.headlineMedium)
    }
}

/* ----------------------------------------------------------
                   5 updateTransition
---------------------------------------------------------- */
@Composable
fun UpdateTransitionDemo() {
    var active by remember { mutableStateOf(false) }
    val transition = updateTransition(active, label = "transition")

    val size by transition.animateDp(label = "size") { if (it) 120.dp else 60.dp }
    val color by transition.animateColor(label = "color") { if (it) Color.Magenta else Color.Gray }

    Box(
        Modifier
            .size(size)
            .background(color, CircleShape)
            .clickable { active = !active }
    )
}

/* ----------------------------------------------------------
                   6 InfiniteTransition
---------------------------------------------------------- */
@Composable
fun InfiniteAnimationDemo() {
    val infinite = rememberInfiniteTransition()
    val alpha by infinite.animateFloat(
        0.4f,
        1f,
        infiniteRepeatable(
            tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        Modifier
            .size(80.dp)
            .alpha(alpha)
            .background(Color.Yellow, CircleShape)
    )
}

/* ----------------------------------------------------------
            7️ SWIPE using draggable + Animatable
---------------------------------------------------------- */
@Composable
fun DraggableSwipeDemo() {
    val maxDistance = 200.dp
    val density = LocalDensity.current
    val scope = rememberCoroutineScope()

    // store the X offset
    val offsetX = remember { Animatable(0f) }

    val maxPx = with(density) { maxDistance.toPx() }

    Box(
        Modifier
            .width(maxDistance + 60.dp)
            .height(60.dp)
            .background(Color(0xFFE0E0E0))
            .padding(8.dp)
    ) {
        Box(
            Modifier
                .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                .size(50.dp)
                .background(Color.Red, CircleShape)
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        scope.launch {
                            val new = (offsetX.value + delta).coerceIn(0f, maxPx)
                            offsetX.snapTo(new)
                        }
                    },
                    onDragStopped = {
                        scope.launch {
                            val end = if (offsetX.value > maxPx / 2) maxPx else 0f
                            offsetX.animateTo(end, spring())
                        }
                    }
                )
        )
    }
}

/* ----------------------------------------------------------
                   8  Canvas Animation
---------------------------------------------------------- */
@Composable
fun CanvasAnimationDemo() {
    var grow by remember { mutableStateOf(false) }
    val radius by animateDpAsState(if (grow) 80.dp else 20.dp)

    Box(
        Modifier
            .size(200.dp)
            .border(1.dp, Color.DarkGray)
            .clickable { grow = !grow }
    ) {
        Canvas(Modifier.fillMaxSize()) {
            drawCircle(Color.Cyan, radius.toPx())
        }
    }
}


@Composable
fun ExpandableCard(
    title: String = "FAQ Question",
    body: String = "This is the answer text that will expand and collapse smoothly."
) {
    var expanded by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(if (expanded) 180f else 0f)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
            .clickable { expanded = !expanded },
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )

                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier.rotate(rotation)
                )
            }

            if (expanded) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = body,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
