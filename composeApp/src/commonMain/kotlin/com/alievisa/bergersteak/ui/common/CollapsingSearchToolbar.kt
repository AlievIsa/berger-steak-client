package com.alievisa.bergersteak.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import berger_steak_client.composeapp.generated.resources.Res
import berger_steak_client.composeapp.generated.resources.dish_name
import berger_steak_client.composeapp.generated.resources.search
import berger_steak_client.composeapp.generated.resources.sora_regular
import berger_steak_client.composeapp.generated.resources.sora_semibold
import com.alievisa.bergersteak.getInsetTop
import com.alievisa.bergersteak.ui.theme.AppDefaults
import com.alievisa.bergersteak.utils.ScaleIndication
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun CollapsingSearchToolbar(
    title: String,
    listState: LazyListState,
    leftButton: (@Composable () -> Unit)? = null,
    rightButton: (@Composable () -> Unit)? = null,
    modifier: Modifier = Modifier,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onCollapsedSearchIconClick: () -> Unit,
    focusManager: FocusManager,
    focusRequester: FocusRequester,
    keyboardController: SoftwareKeyboardController?,
) {

    val isAtTop by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(brush = AppDefaults.Brushes.verticalDark)
            .clickable(indication = null,interactionSource = null) {
                focusManager.clearFocus()
            }
    ) {
        Spacer(modifier = Modifier.fillMaxWidth().height(getInsetTop()))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                leftButton?.invoke()
            }
            Box(modifier = Modifier.weight(2f), contentAlignment = Alignment.Center) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AnimatedVisibility(
                        visible = !isAtTop,
                        enter = fadeIn() + expandVertically(expandFrom = Alignment.Top),
                        exit = fadeOut() + shrinkVertically(shrinkTowards = Alignment.Top)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clickable(
                                    indication = ScaleIndication,
                                    interactionSource = null,
                                ) {
                                    onCollapsedSearchIconClick()
                                    keyboardController?.show()
                                }
                        ) {
                            Icon(
                                imageVector = vectorResource(Res.drawable.search),
                                contentDescription = "Search Icon",
                                tint = Color.White,
                            )
                        }
                    }
                    rightButton?.invoke()
                }
            }
        }

        AnimatedVisibility(
            visible = isAtTop,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically(),
        ) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                        .focusRequester(focusRequester),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFF5F5F5F),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.White,
                        textColor = Color.White,
                    ),
                    value = searchQuery,
                    onValueChange = { newQuery ->
                        onSearchQueryChanged(newQuery)
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        }
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = vectorResource(Res.drawable.search),
                            contentDescription = "Search Icon",
                            tint = Color.Unspecified,
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            Box(
                                modifier.clickable(
                                    indication = ScaleIndication,
                                    interactionSource = null,
                                ) {
                                    onSearchQueryChanged("")
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    tint = Color.White,
                                    contentDescription = "Clear Icon",
                                )
                            }
                        }
                    },
                    placeholder = {
                        Text(
                            text = stringResource(Res.string.dish_name),
                            fontSize = 14.sp,
                            color = Color(0xFFEAEAEA)
                        )
                    },
                    maxLines = 1,
                )
            }
        }
    }
}
