package com.example.todocompose.ui.Screens.list

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.todocompose.R
import com.example.todocompose.ui.viewModels.SharedViewModel
import com.example.todocompose.util.SearchAppBarState

@Composable
fun ListScreen(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    sharedViewModel.getAllTasks()
    val allTasks = sharedViewModel.allTasks.collectAsState()
    val searchAppBarState: SearchAppBarState
            by sharedViewModel.searchAppBarState
    val searchTextState:String by sharedViewModel.searchTextState
    Scaffold(
        topBar = {
            ListAppBar(sharedViewModel = sharedViewModel,
                searchAppBarState = searchAppBarState,
            searchTextState = searchTextState
            )
        },
        content = {
            ListContent()
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTaskScreen)
        },

        )
}

@Composable
fun ListFab(
    onFabClicked: (taskId: Int) -> Unit
) {
    FloatingActionButton(onClick = {
        onFabClicked(-1)
    }) {

        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(R.string.add_button),
            tint = Color.White
        )

    }
}

