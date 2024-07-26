package com.estiak.todoapp.sunflower.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.estiak.todoapp.R
import com.estiak.todoapp.sunflower.ui.SunflowerPage

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CenteredAppBar(
    title: String,
    pagerState: PagerState,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        title = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
        modifier = modifier,
        actions = {
            if (pagerState.currentPage == SunflowerPage.PLANT_LIST.ordinal) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filter_list_24dp),
                        contentDescription = stringResource(
                            id = R.string.menu_filter_by_grow_zone
                        )
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}

//@Preview
//@Composable
//fun PreviewCenteredTextAppBar() {
//    CenteredAppBar(title = "Sunflower")
//}