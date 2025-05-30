package com.alievisa.bergersteak.ui.screens.auth

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import berger_steak_client.composeapp.generated.resources.Res
import berger_steak_client.composeapp.generated.resources.arrow_back
import berger_steak_client.composeapp.generated.resources.authorization
import berger_steak_client.composeapp.generated.resources.by_email
import berger_steak_client.composeapp.generated.resources.by_number
import berger_steak_client.composeapp.generated.resources.confirm
import berger_steak_client.composeapp.generated.resources.enter_code
import berger_steak_client.composeapp.generated.resources.enter_email
import berger_steak_client.composeapp.generated.resources.enter_number
import berger_steak_client.composeapp.generated.resources.get_the_code
import berger_steak_client.composeapp.generated.resources.id_code_is_not_received
import berger_steak_client.composeapp.generated.resources.invalid_code
import berger_steak_client.composeapp.generated.resources.invalid_email
import berger_steak_client.composeapp.generated.resources.invalid_number
import berger_steak_client.composeapp.generated.resources.login_or_signup_with_mail
import berger_steak_client.composeapp.generated.resources.login_or_signup_with_number
import berger_steak_client.composeapp.generated.resources.retry
import com.alievisa.bergersteak.ui.common.MainButton
import com.alievisa.bergersteak.ui.common.TwoOptionSegmentedControl
import com.alievisa.bergersteak.utils.PhoneVisualTransformation
import com.alievisa.bergersteak.utils.ScaleIndication
import com.alievisa.bergersteak.utils.extensions.checkEmailMatches
import com.alievisa.bergersteak.utils.extensions.checkPhoneNumberMatches
import com.alievisa.bergersteak.utils.extensions.filterNumberInput
import com.alievisa.bergersteak.utils.extensions.sec
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun AuthScreen(
    navController: NavController,
    showInBottomSheet: Boolean = false,
    onSuccess: () -> Unit = {},
) {
    val mainModifier = if (showInBottomSheet) {
        Modifier.wrapContentHeight().fillMaxWidth()
    } else {
        Modifier.fillMaxSize()
    }
    var screenState by remember { mutableStateOf(AuthState.DATA_INPUT) }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = mainModifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            }
    ) {
        if (screenState == AuthState.CODE_CHECK) {
            Box(
                modifier = Modifier
                    .clickable(
                        indication = ScaleIndication,
                        interactionSource = null,
                    ) {
                        screenState = AuthState.DATA_INPUT
                    }
                    .padding(horizontal = 20.dp)
                    .padding(top = 24.dp)
            ) {
                Icon(
                    imageVector = vectorResource(Res.drawable.arrow_back),
                    contentDescription = "Arrow Back",
                    tint = Color.Black,
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = stringResource(Res.string.authorization),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )

            when (screenState) {
                AuthState.DATA_INPUT -> {
                    DataInput(
                        onButtonClick = {
                            focusManager.clearFocus()
                            screenState = AuthState.CODE_CHECK
                        }
                    )
                }
                AuthState.CODE_CHECK -> {
                    CodeCheck(
                        onButtonClick = {
                            focusManager.clearFocus()
                            onSuccess()
                        }
                    )
                }
            }
        }
    }
}

private enum class AuthType {
    PHONE_NUMBER, EMAIL
}

private enum class AuthState {
    DATA_INPUT, CODE_CHECK
}

@Composable
private fun DataInput(
    onButtonClick: () -> Unit,
) {
    var selectedAuthType by remember { mutableStateOf(AuthType.PHONE_NUMBER) }
    var input by remember { mutableStateOf("") }

    val label = when (selectedAuthType) {
        AuthType.PHONE_NUMBER -> stringResource(Res.string.enter_number)
        AuthType.EMAIL -> stringResource(Res.string.enter_email)
    }

    val errorText = when (selectedAuthType) {
        AuthType.PHONE_NUMBER -> stringResource(Res.string.invalid_number)
        AuthType.EMAIL -> stringResource(Res.string.invalid_email)
    }
    var state by remember { mutableStateOf(DataInputState.GENERAL) }

    TwoOptionSegmentedControl(
        options = listOf(
            stringResource(Res.string.by_number),
            stringResource(Res.string.by_email)
        ),
        selectedIndex = selectedAuthType.ordinal,
        onOptionSelected = {
            if (state != DataInputState.LOADING) {
                selectedAuthType = AuthType.entries[it]
                state = DataInputState.GENERAL
                input = ""
            }
        },
        modifier = Modifier.padding(top = 20.dp),
    )

    OutlinedTextField(
        value = input,
        onValueChange = {
            input = when (selectedAuthType) {
                AuthType.PHONE_NUMBER -> it.filterNumberInput()
                AuthType.EMAIL -> it
            }
            if (state == DataInputState.ERROR) state = DataInputState.GENERAL
        },
        label = {
            Text(
                text = if (state == DataInputState.ERROR) errorText else label,
                color = if (state == DataInputState.ERROR) Color.Red else Color.Gray
            )
        },
        enabled = state != DataInputState.LOADING,
        isError = state == DataInputState.ERROR,
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.LightGray,
            errorBorderColor = Color.Red,
            cursorColor = Color.Gray,
            focusedLabelColor = Color.Gray,
            unfocusedLabelColor = Color.Gray,
            errorLabelColor = Color.Red
        ),
        keyboardOptions =  when (selectedAuthType) {
            AuthType.PHONE_NUMBER -> KeyboardOptions(keyboardType = KeyboardType.Number)
            AuthType.EMAIL -> KeyboardOptions.Default
        },
        visualTransformation = when (selectedAuthType) {
            AuthType.PHONE_NUMBER -> PhoneVisualTransformation()
            AuthType.EMAIL -> VisualTransformation.None
        },
    )

    Text(
        text = when (selectedAuthType) {
            AuthType.PHONE_NUMBER -> stringResource(Res.string.login_or_signup_with_number)
            AuthType.EMAIL -> stringResource(Res.string.login_or_signup_with_mail)
        },
        fontSize = 14.sp,
        lineHeight = 14.sp,
        color = Color.Gray,
    )

    LaunchedEffect(state) {
        if (state == DataInputState.LOADING) {
            delay(5000)
            state = DataInputState.SUCCESS
        }
        if (state == DataInputState.SUCCESS) {
            onButtonClick()
        }
    }

    MainButton(
        modifier = Modifier
            .padding(top = 20.dp, bottom = 12.dp),
        centerText = stringResource(Res.string.get_the_code),
        isLoading = state == DataInputState.LOADING,
        onClick = {
            val isValid = when (selectedAuthType) {
                AuthType.PHONE_NUMBER -> checkPhoneNumberMatches(input)
                AuthType.EMAIL -> checkEmailMatches(input)
            }

            if (isValid) {
                state = DataInputState.LOADING
            } else {
                state = DataInputState.ERROR
            }
        }
    )
}

private enum class DataInputState {
    GENERAL, LOADING, SUCCESS, ERROR
}

@Composable
private fun CodeCheck(
    onButtonClick: () -> Unit,
) {
    var startResendBlockTimer by remember { mutableStateOf(true) }
    var code by remember { mutableStateOf("") }
    var resendEnabled by remember { mutableStateOf(false) }
    var timer by remember { mutableStateOf(60) }
    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    var state by remember { mutableStateOf(CheckCodeState.GENERAL) }

    LaunchedEffect(startResendBlockTimer) {
        if (startResendBlockTimer) {
            resendEnabled = false
            timer = 60
            while (timer > 0) {
                delay(1000)
                timer--
            }
            resendEnabled = true
            startResendBlockTimer = false
        }
    }

    when (state) {
        CheckCodeState.ERROR -> {
            Text(
                text = stringResource(Res.string.invalid_code),
                fontSize = 14.sp,
                color = Color.Red,
                modifier = Modifier.padding(vertical = 20.dp)
            )
        }
        CheckCodeState.GENERAL,
        CheckCodeState.LOADING,
        CheckCodeState.SUCCESS -> {
            Text(
                text = stringResource(Res.string.enter_code),
                fontSize = 14.sp,
                modifier = Modifier.padding(vertical = 20.dp)
            )
        }
    }

    BasicTextField(
        value = code,
        onValueChange = {
            state = CheckCodeState.GENERAL
            if (it.length <= 4) {
                code = it
            }
        },
        enabled = state != CheckCodeState.LOADING,
        modifier = Modifier.focusRequester(focusRequester),
        interactionSource = interactionSource,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.Center,
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                repeat(4) { index ->
                    val char = when {
                        index >= code.length -> ""
                        else -> code[index].toString()
                    }
                    val isBoxFocused = isFocused && code.length == index
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(75.dp)
                            .border(
                                width = if (isBoxFocused) 2.dp else 1.dp,
                                color = when (state) {
                                    CheckCodeState.GENERAL,
                                    CheckCodeState.LOADING -> if (isBoxFocused) Color.Black else Color.Gray
                                    CheckCodeState.SUCCESS -> Color.Green
                                    CheckCodeState.ERROR -> Color.Red
                                },
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = char,
                            fontSize = 24.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.SansSerif,
                            color = Color.Black,
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )


    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!resendEnabled) {
            Text(
                text = "${stringResource(Res.string.id_code_is_not_received)} ${timer.sec()}",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
        }

        else {
            Text(
                text = stringResource(Res.string.retry),
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )

            Box(
                modifier = Modifier.clickable(
                    indication = ScaleIndication,
                    interactionSource = null,
                ) {
                    startResendBlockTimer = true
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        }
    }

    LaunchedEffect(state) {
        if (state == CheckCodeState.LOADING) {
            delay(5000)
            state = CheckCodeState.SUCCESS
        }
        if (state == CheckCodeState.SUCCESS) {
            delay(1000)
            onButtonClick()
        }
    }

    MainButton(
        modifier = Modifier
            .padding(top = 24.dp, bottom = 12.dp),
        centerText = stringResource(Res.string.confirm),
        isLoading = state == CheckCodeState.LOADING || state == CheckCodeState.SUCCESS,
        onClick = {
            if (code.length == 4) {
                val isValid = true
                if (isValid) {
                    state = CheckCodeState.LOADING
                } else {
                    state = CheckCodeState.ERROR
                }
            } else {
                state = CheckCodeState.ERROR
            }
        }
    )
}

private enum class CheckCodeState {
    GENERAL, LOADING, SUCCESS, ERROR
}