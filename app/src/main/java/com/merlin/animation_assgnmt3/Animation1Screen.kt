import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize

@Composable
//Animate based on target state with AnimatedContent
fun Animation1Screen(navController: NavController) {
    MaterialTheme {
        Box(Modifier.padding(top = 32.dp)) {

            Column(
                modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Animation 1
                var count by remember { mutableIntStateOf(0) }

                Button(
                    onClick = { count++ },
//                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text("Add")
                }

                Spacer(modifier = Modifier.width(16.dp))

                AnimatedContent(
                    targetState = count,
                    label = "animated content"
                ) { targetCount ->
                    // Make sure to use `targetCount`, not `count`.
                    Text(text = "Count: $targetCount")
                }

                //Animation 2
                AnimatedContent(
                    targetState = count,
                    transitionSpec = {
                        // Compare the incoming number with the previous number.
                        if (targetState > initialState) {
                            // If the target number is larger, it slides up and fades in
                            // while the initial (smaller) number slides up and fades out.
                            slideInVertically { height -> height } + fadeIn() togetherWith
                                    slideOutHorizontally { height -> -height } + fadeOut()
                        } else {
                            // If the target number is smaller, it slides down and fades in
                            // while the initial number slides down and fades out.
                            slideInVertically { height -> -height } + fadeIn() togetherWith
                                    slideOutVertically { height -> height } + fadeOut()
                        }.using(
                            // Disable clipping since the faded slide-in/out should
                            // be displayed out of bounds.
                            SizeTransform(clip = false)
                        )
                    }, label = "animated content"
                ) { targetCount ->
                    Box(
                        modifier = Modifier
                            .size(128.dp)
                            .background(Color.Yellow)
                            .padding(16.dp)
                    ) {
                        Text(text = "$targetCount")
                    }

                }

                //Animation 3
                var expanded by remember { mutableStateOf(false) }
                Surface(
                    color = MaterialTheme.colorScheme.primary,
                    onClick = { expanded = !expanded }
                ) {
                    AnimatedContent(
                        targetState = expanded,
                        transitionSpec = {
                            fadeIn(animationSpec = tween(150, 150)) togetherWith
                                    fadeOut(animationSpec = tween(150)) using
                                    SizeTransform { initialSize, targetSize ->
                                        if (targetState) {
                                            keyframes {
                                                // Expand horizontally first.
                                                IntSize(targetSize.width, initialSize.height) at 150
                                                durationMillis = 300
                                            }
                                        } else {
                                            keyframes {
                                                // Shrink vertically first.
                                                IntSize(initialSize.width, targetSize.height) at 150
                                                durationMillis = 300
                                            }
                                        }
                                    }
                        }, label = "size transform"
                    ) { targetExpanded ->
                        if (targetExpanded) {
                            Expanded()
                        } else {
                            ContentIcon()
                        }
                    }
                }

              //Animation 4:  Animate between two layouts with Crossfade
                var currentPage by remember { mutableStateOf("A") }

                // Button to switch between screens "A" and "B"
                Button(
                    onClick = {
                        currentPage = if (currentPage == "A") "B" else "A"
                    },
                ) {
                    Text("Switch Page")
                }

                Crossfade(targetState = currentPage, label = "cross fade") { screen ->
                    when (screen) {
                        "A" -> Text("Page A")
                        "B" -> Text("Page B")
                    }
                }

                //Animation 5: Animate composable size changes with animateContentSize
                var expandedState by remember { mutableStateOf(false) }
                Box(
                    modifier = Modifier
                        .background(Color.Cyan)
                        .animateContentSize()
                        .height(if (expandedState) 400.dp else 200.dp)
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            expandedState = !expandedState
                        }

                ) {
                }
            }
        }
    }
}

@Composable
fun Expanded() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.Green)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Expanded Content", color = Color.White)
    }
}

@Composable
fun ContentIcon() {
    Box(
        modifier = Modifier
            .size(64.dp)
            .background(Color.Blue)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Click", color = Color.White)
    }
}
