package com.alievisa.bergersteak.ui.sheets.aboutus

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import berger_steak_client.composeapp.generated.resources.Res
import berger_steak_client.composeapp.generated.resources.advantages
import berger_steak_client.composeapp.generated.resources.halal
import berger_steak_client.composeapp.generated.resources.halal_burger_cafe
import berger_steak_client.composeapp.generated.resources.halal_burger_cafe_description
import berger_steak_client.composeapp.generated.resources.our_advantages
import coil3.compose.AsyncImage
import com.alievisa.bergersteak.ui.theme.AppDefaults
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun AboutUsContent(
    showInBottomSheet: Boolean = false,
) {
    val restaurantImages = imagesMock
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { restaurantImages.size })
    val mainModifier = if (showInBottomSheet) {
        Modifier.wrapContentHeight().fillMaxWidth()
    } else {
        Modifier.fillMaxSize()
    }

    Column(
        modifier = mainModifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                val imageUrl = restaurantImages[page]
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            Text(
                text = "${pagerState.currentPage + 1}/${restaurantImages.size}",
                color = Color.White,
                fontSize = 14.sp,
                lineHeight = 14.sp,
                modifier = Modifier.align(Alignment.TopEnd).padding(20.dp)
            )
        }

        Box(
            modifier = Modifier
                .wrapContentHeight()
                .offset(y = (-50).dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(Color.White)
                    .padding(horizontal = 20.dp, vertical = 20.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.halal_burger_cafe),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                        color = AppDefaults.Colors.brown,
                    )

                    Icon(
                        imageVector = vectorResource(Res.drawable.halal),
                        contentDescription = "halal mark",
                        tint = Color.Unspecified,
                    )
                }

                Text(
                    text = stringResource(Res.string.halal_burger_cafe_description),
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(top = 8.dp),
                )

                Divider(modifier = Modifier.padding(vertical = 20.dp))

                Text(
                    text = stringResource(Res.string.our_advantages),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier,
                    color = AppDefaults.Colors.brown,
                )
                Text(
                    text = stringResource(Res.string.advantages),
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(top = 8.dp),
                )
            }
        }
    }
}


val imagesMock = listOf(
    "https://avatars.mds.yandex.net/get-altay/5448678/2a0000017fa17019848b205082bf166d5f66/XXXL",
    "https://images.fooby.ru/1/56/62/2780790"
)