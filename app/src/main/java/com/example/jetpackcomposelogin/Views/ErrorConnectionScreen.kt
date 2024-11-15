package com.example.jetpackcomposelogin.Views
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposelogin.R


@Composable
fun OfflineScreen(modifier: Modifier = Modifier) {
    Box {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (false) {
                Image(
                    painter = painterResource(id = R.drawable.error_logo),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.requiredHeight(30.dp))
                Text("Se encuentra desconectado de la red en este momento")
            }
        }
    }
}

@Preview
@Composable
fun OfflineScreenPreview(){
    OfflineScreen()
}