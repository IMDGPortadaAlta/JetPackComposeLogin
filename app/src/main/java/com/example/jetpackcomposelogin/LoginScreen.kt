package com.example.jetpackcomposelogin

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.TextButton
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposelogin.Views.LoadingUI
import com.example.jetpackcomposelogin.Views.OfflineScreen
import com.example.jetpackcomposelogin.login.AccountLoginState

@Composable
fun LoginForm(navController: NavController, modifier: Modifier = Modifier, state: AccountLoginState){
    when{
        state.isOffline -> OfflineScreen(modifier)
        state.isLoading -> LoadingUI(modifier)
        else -> LoginScreen(navController, Modifier)
    }
}

@Composable
fun LoginScreen(navController: NavController, modifier: Modifier = Modifier) {
    val showLoginForm = rememberSaveable { mutableStateOf(true) }

    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (showLoginForm.value) {
                Text(text = "Iniciar sesión")
                UserForm(isCreateAccount = false) { email, password ->
                    Log.d("Logeo con éxito", "Logeado con email: $email y contraseña: $password")
                    // manejar inicio de sesión
                }
            } else {
                Text(text = "Crea una cuenta")
                UserForm(isCreateAccount = true) { email, password ->
                    Log.d("Creacion de cuenta con éxito", "Creando cuenta con email: $email y contraseña: $password")
                    // manejar creación de cuenta
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val text1 = if (showLoginForm.value) "¿No tienes cuenta?" else "¿Ya estás registrado?"
                val text2 = if (showLoginForm.value) "Regístrate" else "Inicia sesión"

                Text(text = text1)
                Text(
                    text = text2,
                    modifier = Modifier.clickable { showLoginForm.value = !showLoginForm.value }.padding(start = 5.dp),
                    color = MaterialTheme.colorScheme.primary
                )
                TextButton(onClick = {showLoginForm.value = !showLoginForm.value}) { Text(text2) }
            }
        }
    }
}

@Composable
fun UserForm(isCreateAccount: Boolean = false, onDone: (String, String) -> Unit = { _, _ -> }) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisual = rememberSaveable { mutableStateOf(false) }
    val isEmailError = rememberSaveable { mutableStateOf(false)}
    val success = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        EmailInput(emailState = email)
        PasswordInput(passwordState = password, labelId = "Password", passwordVisual = passwordVisual)
        SubmitButton(
            textId = if (isCreateAccount) "Crea cuenta" else "Login",
            inputSuccess = success,
        ) {
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }
    }
}

@Composable
fun SubmitButton(textId: String, inputSuccess: Boolean, onClick:() -> Unit) {
    Button(
        onClick = {},
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape,
        enabled = inputSuccess
    ) {
        Text(
            text = textId,
            modifier = Modifier.padding(5.dp)
        )
    }
}

@Composable
fun PasswordVisualIcon(passwordVisual: MutableState<Boolean>) {
    val image: ImageVector = if (passwordVisual.value) {
        Icons.Filled.VisibilityOff
    } else {
        Icons.Filled.Visibility
    }
    IconButton(onClick = { passwordVisual.value = !passwordVisual.value }) {
        Icon(imageVector = image, contentDescription = if (passwordVisual.value) "Hide password" else "Show password")
    }
}

@Composable
fun PasswordInput(passwordState: MutableState<String>, labelId: String, passwordVisual: MutableState<Boolean>) {
    val visualTransformation = if (passwordVisual.value) VisualTransformation.None else PasswordVisualTransformation()
    OutlinedTextField(
        value = passwordState.value,
        onValueChange = { passwordState.value = it },
        label = { Text(text = labelId) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        visualTransformation = visualTransformation,
        trailingIcon = {
            PasswordVisualIcon(passwordVisual)
        },
        supportingText = { },
    )
}

@Composable
fun EmailInput(emailState: MutableState<String>, labelId: String = "Email") {
    InputField(valueState = emailState, labelId = labelId, keyboardType = KeyboardType.Email)
}

@Composable
fun InputField(valueState: MutableState<String>, labelId: String, isSingleLine: Boolean = true, keyboardType: KeyboardType) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text(text = labelId) },
        singleLine = isSingleLine,
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

@Preview
@Composable
fun LoginScreenPreview() {
    val navControl = rememberNavController()
    LoginScreen(navControl)
}
