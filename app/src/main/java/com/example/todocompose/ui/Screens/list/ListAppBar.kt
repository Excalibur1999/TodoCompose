package com.example.todocompose.ui.Screens.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.todocompose.R
import com.example.todocompose.components.PriorityItem
import com.example.todocompose.data.models.Priority
import com.example.todocompose.ui.theme.*
import com.example.todocompose.ui.viewModels.SharedViewModel
import com.example.todocompose.util.SearchAppBarState
import com.example.todocompose.util.TrailingIconState

@Composable
fun ListAppBar(
    sharedViewModel: SharedViewModel,
    searchAppBarState: SearchAppBarState,
    searchTextState: String
) {
    when (searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultListAppBar(
                onSearchClicked = {
                    sharedViewModel.searchAppBarState.value = SearchAppBarState.OPENED
                },
                onSortClicked = {},
                onDeleteClicked = {}
            )
        }
        else -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = {
                    sharedViewModel.searchTextState.value = it
                },
                onCloseClicked = {
                    sharedViewModel.searchAppBarState.value =
                        SearchAppBarState.CLOSED
                    sharedViewModel.searchTextState.value = ""
//
                },
                onSearchClicked = {}
            )
        }
    }


}

@Composable
fun DefaultListAppBar(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = "Tasks", color = MaterialTheme.colors.topAppBarContentColor)
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            ListAppBarActions(
                onSearchClicked = onSearchClicked,
                onSortClicked = onSortClicked,
                onDeleteClicked = onDeleteClicked
            )
        }
    )
}

@Composable
fun ListAppBarActions(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteClicked: () -> Unit
) {
    SearchAction(onSearchClicked = onSearchClicked)
    SortAction(onSortClicked = onSortClicked)
    DeleteAllAction(onDeleteClicked = onDeleteClicked)
}

@Composable
fun SearchAction(
    onSearchClicked: () -> Unit,
) {
    IconButton(
        onClick = { onSearchClicked() }
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = "Search Icon",
            tint = MaterialTheme.colors.topAppBarContentColor
        )

    }
}

@Composable
fun SortAction(
    onSortClicked: (Priority) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    IconButton(
        onClick = { expanded = true }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_filter_list),
            contentDescription = "Sort Task",
            tint = MaterialTheme.colors.topAppBarContentColor
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onSortClicked(Priority.LOW)
                }) {
                PriorityItem(priority = Priority.LOW)
            }

            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onSortClicked(Priority.HIGH)
                }) {
                PriorityItem(priority = Priority.HIGH)
            }
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onSortClicked(Priority.NONE)
                }) {
                PriorityItem(priority = Priority.NONE)
            }
        }

    }
}

@Composable
fun DeleteAllAction(
    onDeleteClicked: () -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    IconButton(
        onClick = { expanded = true }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_vertical_menu),
            contentDescription = "Delete Task",
            tint = MaterialTheme.colors.topAppBarContentColor
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onDeleteClicked()
                }) {
                Text(
                    modifier = Modifier.padding(start = LARGE_PADDING),
                    text = "Delete All",
                    style = Typography.subtitle2
                )
            }
        }
    }
}

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {
    var trailingIconState by remember {
        mutableStateOf(TrailingIconState.READY_TO_DELETE)
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_APP_BAR_HEIGHT),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.topAppBarBackgroundColor
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    text = "Search....",
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                color = MaterialTheme.colors.topAppBarContentColor,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(modifier = Modifier.alpha(ContentAlpha.disabled),
                    onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = MaterialTheme.colors.topAppBarContentColor
                    )

                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    when (trailingIconState) {
                        TrailingIconState.READY_TO_DELETE -> {
                            onTextChange("")
                            trailingIconState = TrailingIconState.READY_TO_CLOSE

                        }
                        TrailingIconState.READY_TO_CLOSE -> {
                            if (text.isNotEmpty()) {
                                onTextChange("")
                            } else {
                                onCloseClicked()
                                trailingIconState = TrailingIconState.READY_TO_DELETE
                            }
                        }
                    }

                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close Icon",
                        tint = MaterialTheme.colors.topAppBarContentColor
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)

                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = MaterialTheme.colors.topAppBarContentColor,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent
            )
        )
    }
}