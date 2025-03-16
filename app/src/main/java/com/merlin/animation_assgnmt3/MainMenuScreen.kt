package com.merlin.animation_assgnmt3

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun MainMenuScreen(navController: NavController) {
    Column (
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Main Menu",
            modifier = Modifier.padding(bottom= 16.dp),
            fontWeight = FontWeight.Bold
            )
        Box(
            modifier = Modifier.weight(1f)
                .fillMaxWidth()
                .border(1.dp, Color.Black)
                .padding(16.dp),
            contentAlignment = Alignment.TopCenter

        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Animation buttons
                for(i in 1..4) {
                    Button(
                        onClick = { navController.navigate("animation$i")},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.LightGray,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(4.dp),
                      //  modifier = Modifier.fillMaxWidth().height(48.dp)
                    ) {
                      Text(text="Animation $i Demo")
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier.fillMaxWidth()
                        .border(1.dp,Color.Black)
                        .padding(8.dp),
                 //   contentAlignment = Alignment.Center
                ) {
                    Text(text= "Merlin George\n301475560",
                     //   textAlign = TextAlign.Center,
                        color = Color.Blue
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainMenuScreenPreview() {
    MainMenuScreen(navController = rememberNavController())
}