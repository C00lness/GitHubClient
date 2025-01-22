package com.example.gitclient.screen

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gitclient.ui.theme.Gainsboro

@Composable
fun DinamicScreen(vectorId:Int, msg: String, header: String, needBtn: Boolean, onClick: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    Column(
        Modifier.fillMaxSize().padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top)
    {
        val image = painterResource(id = vectorId)
        Text(text = header, Modifier.padding(top = 30.dp), fontSize = 22.sp, color = Color.DarkGray, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold )
        Text(text = msg, Modifier.padding(top = 10.dp, bottom = 30.dp), fontSize = 18.sp, color = Color.Gray, textAlign = TextAlign.Center)
        Image(painter = image, modifier = Modifier.rotate(angle), contentDescription = null)

        if (needBtn)
        {
            Button(onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.DarkGray,
                    containerColor = Gainsboro
                ), modifier = Modifier.padding(top = 20.dp)
            ){ Text("Обновить", fontSize = 18.sp) }
        }
    }
}