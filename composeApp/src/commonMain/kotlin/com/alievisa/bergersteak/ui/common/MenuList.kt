package com.alievisa.bergersteak.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alievisa.bergersteak.domain.models.DishModel
import com.alievisa.bergersteak.domain.models.MenuModel
import com.alievisa.bergersteak.ui.utils.ScaleIndication
import com.alievisa.bergersteak.ui.utils.extensions.roundUpDiv
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MenuList(
    menuModel: MenuModel,
    menuScrollState: LazyListState,
    isMainButtonVisible: Boolean,
    onDishClick: (DishModel) -> Unit,
    onAddDishClick: (DishModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val selectedTabState = remember { mutableStateOf(0) }
    val tabRowScrollState = rememberLazyListState()
    val isCustomScrollAnimating = remember { mutableStateOf(false) }

    var previousDishesAmount = 0
    val categoryOffsets = menuModel.categories.mapIndexed { index, categoryModel ->
        if (index != 0) {
            previousDishesAmount += menuModel.categories[index - 1].dishes.size.roundUpDiv(2) + 1
        }
        categoryModel.id to previousDishesAmount
    }.toMap()

    println(categoryOffsets)

    val categoryOffsetList = categoryOffsets.entries.sortedBy { it.value }

    LaunchedEffect(menuScrollState.firstVisibleItemIndex) {
        if (!isCustomScrollAnimating.value) {
            val layoutInfo = menuScrollState.layoutInfo
            val centerY = layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset / 2

            val centeredItem = layoutInfo.visibleItemsInfo.minByOrNull { item ->
                val itemCenter = item.offset + item.size / 2
                kotlin.math.abs(itemCenter - centerY)
            }

            val centeredIndex = centeredItem?.index ?: return@LaunchedEffect

            val currentCategory = categoryOffsetList
                .lastOrNull { it.value <= centeredIndex }
                ?.let { menuModel.categories.indexOfFirst { cat -> cat.id == it.key } }

            if (currentCategory != null && currentCategory != selectedTabState.value) {
                selectedTabState.value = currentCategory

                coroutineScope.launch {
                    tabRowScrollState.animateScrollToItem(currentCategory)
                }
            }
        }
    }

    Column(
        modifier = modifier.padding(top = 12.dp),
    ) {
        LazyRow(
            state = tabRowScrollState,
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) {
            itemsIndexed(menuModel.categories) { index, categoryModel ->
                val selected = selectedTabState.value == index

                Box(
                    modifier = Modifier
                        .clickable(
                            indication = ScaleIndication,
                            interactionSource = null,
                        ) {
                            isCustomScrollAnimating.value = true
                            selectedTabState.value = index
                            coroutineScope.launch {
                                menuScrollState.animateScrollToItem(categoryOffsets[categoryModel.id] ?: 0)
                                delay(100)
                                isCustomScrollAnimating.value = false
                            }
                        }
                        .clip(RoundedCornerShape(6.dp))
                        .height(36.dp)
                        .background(
                            color = if (selected) Color(0xFFC67C4E) else Color(0xFFEDEDED)
                        )
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = categoryModel.name,
                        color = if (selected) Color.White else Color.Black,
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 12.dp),
            state = menuScrollState
        ) {
            menuModel.categories.forEach { categoryModel ->

                item {
                    Text(
                        text = categoryModel.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .padding(top = 4.dp, bottom = 4.dp),
                    )
                }

                items(categoryModel.dishes.chunked(2)) { row ->
                    Row(
                        Modifier
                            .padding(horizontal = 12.dp)
                    ) {
                        row.forEach { dishModel ->
                            MenuItem(
                                dishModel = dishModel,
                                onDishClick = { onDishClick(dishModel) },
                                onAddDishClick = { onAddDishClick(dishModel) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                        if (row.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }

            }

            if (isMainButtonVisible) {
                item {
                    Spacer(modifier = Modifier.height(70.dp))
                }
            }
        }
    }
}
