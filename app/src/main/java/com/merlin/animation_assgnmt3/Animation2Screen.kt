package com.merlin.animation_assgnmt3

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateRect
import androidx.compose.animation.core.createChildTransition
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


enum class BoxState {
    Collapsed,
    Expanded
}


@Composable
fun Animation2Screen(navController: NavController) {

    var enabled by remember { mutableStateOf(true) }

    val alpha: Float by animateFloatAsState(if (enabled) 1f else 0.5f, label = "alpha")

    //Animate multiple properties simultaneously with a transition
//    var currentState by remember { mutableStateOf(BoxState.Collapsed) }

    // Start in collapsed state and immediately animate to expanded
    val currentState = remember { MutableTransitionState(BoxState.Collapsed).apply { targetState = BoxState.Expanded } }

    val transition = updateTransition(currentState, label = "box state")

    val rect by transition.animateRect(label = "rectangle") { state ->
        when (state) {
            BoxState.Collapsed -> Rect(0f, 0f, 100f, 100f)
            BoxState.Expanded -> Rect(100f, 100f, 100f, 300f)
        }
    }

    val boxSize by transition.animateDp(label = "box size") { state ->
        when (state) {
            BoxState.Collapsed -> 100.dp
            BoxState.Expanded -> 300.dp
        }
    }

    val borderWidth by transition.animateDp(label = "border width") { state ->
        when (state) {
            BoxState.Collapsed -> 3.dp
            BoxState.Expanded -> 0.dp
        }
    }

    //to specify a different AnimationSpec for each of the combinations of transition state changes
    val color by transition.animateColor(
        transitionSpec = {
            when {
                BoxState.Expanded isTransitioningTo BoxState.Collapsed ->
                    spring(stiffness = 50f)

                else ->
                    tween(durationMillis = 500)
            }
        }, label = "color"
    ) { state ->
        when (state) {
            BoxState.Collapsed -> MaterialTheme.colorScheme.primary
            BoxState.Expanded -> MaterialTheme.colorScheme.onSurface
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .graphicsLayer(alpha = alpha)
            .background(Color.Red)
    ) {
        // Clickable box to trigger animation
        Box(
            Modifier
                .offset(x = rect.left.dp, y = rect.top.dp) // Animated position
             //   .size((rect.width - rect.left).dp, (rect.height - rect.top).dp)          // Animated size
                .size(boxSize)
                .background(color)
                .border(borderWidth, Color.White)           // Animated border
                .clickable {
                    enabled = !enabled
                  //  currentState = if (currentState == BoxState.Collapsed)
                    currentState.targetState = if (currentState.currentState == BoxState.Collapsed)
                        BoxState.Expanded
                    else
                        BoxState.Collapsed
                },
            contentAlignment = Alignment.Center
        ) {
            Text(text = if(enabled) "Fade out" else "Fade In", color = Color.White)
        }
    }

    var selected by remember { mutableStateOf(false) }
// Animates changes when `selected` is changed.
    val selectedStatetransition = updateTransition(selected, label = "selected state")
    val borderColor by selectedStatetransition.animateColor(label = "border color") { isSelected ->
        if (isSelected) Color.Magenta else Color.White
    }
    val elevation by selectedStatetransition.animateDp(label = "elevation") { isSelected ->
        if (isSelected) 10.dp else 2.dp
    }
    Surface (
        onClick = { selected = !selected },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, borderColor),
        shadowElevation = elevation
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Hello, world!")
            // AnimatedVisibility as a part of the transition.
           AnimatedVisibility(
                visible = selected,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Text(text = "It is fine today.")
            }
            // AnimatedContent as a part of the transition.
            selectedStatetransition.AnimatedContent { targetState ->
                if (targetState) {
                    Text(text = "Selected")
                } else {
                    Icon(imageVector = Icons.Default.Phone, contentDescription = "Phone")
                }
            }
        }
    }


}
