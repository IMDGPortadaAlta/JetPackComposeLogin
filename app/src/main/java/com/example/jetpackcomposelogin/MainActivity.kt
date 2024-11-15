package com.example.jetpackcomposelogin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposelogin.login.AccountLoginState
import com.example.jetpackcomposelogin.ui.theme.JetPackComposeLoginTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LogginRegistroApp()
        }
    }
}

@Composable
fun LogginRegistroApp() {
    JetPackComposeLoginTheme {
        val navController = rememberNavController()
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            content = { paddingValues ->
                LoginForm(
                    navController = navController,
                    modifier = Modifier.padding(paddingValues),
                    state = AccountLoginState()
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LogginRegistroAppPreview() {
    LogginRegistroApp()
}