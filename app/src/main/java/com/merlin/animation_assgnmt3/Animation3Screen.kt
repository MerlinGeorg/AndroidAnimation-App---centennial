package com.merlin.animation_assgnmt3

import androidx.compose.animation.Animatable
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.TargetBasedAnimation
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun Animation3Screen(navController: NavController) {
    //Create an infinitely repeating animation with rememberInfiniteTransition
    //infinite color animation transitioning between red and green in a continuous loop
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    val color by infiniteTransition.animateColor(
        initialValue = Color.Red,
        targetValue = Color.Green,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "color"
    )

    //Animatable: Coroutine-based single value animation

    val interiorColor = remember { Animatable(Color.Gray) }
//    val ok = remember { true }
    LaunchedEffect(Unit) {
        while(true) {
            interiorColor.animateTo(Color.White, animationSpec = tween(1000))
            interiorColor.animateTo(Color.Blue, animationSpec = tween(1000))
        }

    }


    //TargetBasedAnimation
    val anim = remember {
        TargetBasedAnimation(
            animationSpec = tween(2000),
            typeConverter = Float.VectorConverter,
            initialValue = 200f,
            targetValue = 300f
        )
    }
    var playTime by remember { mutableLongStateOf(0L) }
    var animatedValue by remember { mutableStateOf(200f) }

    LaunchedEffect(anim) {
        val startTime = withFrameNanos { it }
      //  var isAnimating = true

        do {
            playTime = withFrameNanos { it } - startTime
            animatedValue = anim.getValueFromNanos(playTime)

            // Stop animation after 2000 ms
//            if(playTime > 2000000000L){
//                isAnimating = false
//            }
            //print("animatedValue: $animatedValue")
        } while (playTime < anim.durationNanos)

//        while (playTime >  5000000L) {
//            playTime = withFrameNanos { it } - startTime
//            animatedValue = anim.getValueFromNanos(playTime)
//
//            // Add a delay to control the animation frame rate
//            delay(16) // Roughly 60 FPS (16ms per frame)
//        }
    }



    Box(
        Modifier
            .fillMaxSize()
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .size(animatedValue.dp)
                .background(Color.Cyan)
        ) {
            // Start out gray and animate to blue/white based on `ok`
            Box(
                Modifier
                    .size(100.dp)
                    .background(interiorColor.value)
            )
        }

    }
}