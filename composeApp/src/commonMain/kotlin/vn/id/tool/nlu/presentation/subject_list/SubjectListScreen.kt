package vn.id.tool.nlu.presentation.subject_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import vn.id.tool.nlu.domain.Subject
import vn.id.tool.nlu.presentation.subject_list.components.SubjectList
import vn.id.tool.nlu.presentation.subject_list.components.SubjectSearchBar
import vn.id.tool.nlu.res.Res
import vn.id.tool.nlu.res.favorites
import vn.id.tool.nlu.res.no_favorites
import vn.id.tool.nlu.res.no_result
import vn.id.tool.nlu.res.search_result

@Composable
fun SubjectListScreenRoot(
    viewModel: SubjectListViewModel = koinViewModel(),
    onSubjectClick: (Subject) -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SubjectListScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is SubjectListAction.OnSubjectClick -> onSubjectClick(action.subject)
                else -> Unit
            }
            viewModel.onAction(action)
        },
        modifier = modifier
    )
}

@Composable
private fun SubjectListScreen(
    state: SubjectListState,
    onAction: (SubjectListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val pageState = rememberPagerState { 2 }
    val searchResultListState = rememberLazyListState()
    val favoriteBookListState = rememberLazyListState()
    LaunchedEffect(state.searchResult) {
        searchResultListState.animateScrollToItem(0)
    }
    LaunchedEffect(state.selectedTabIndex) {
        pageState.animateScrollToPage(state.selectedTabIndex)
    }
    LaunchedEffect(pageState.currentPage) {
        onAction(SubjectListAction.OnTabSelected(pageState.currentPage))
    }
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SubjectSearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChange = {
                onAction(SubjectListAction.OnSearchQueryChange(it))
            },
            onImeSearch = {
                keyboardController?.hide()
            },
            modifier.widthIn(max = 600.dp).padding(16.dp)
        )
        Surface(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            shape = RoundedCornerShape(
                topStart = 32.dp,
                topEnd = 32.dp
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TabRow(
                    selectedTabIndex = state.selectedTabIndex,
                    modifier = Modifier.padding(vertical = 12.dp).widthIn(max = 700.dp),
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[state.selectedTabIndex])
                        )
                    }
                ) {
                    Tab(
                        selected = state.selectedTabIndex == 0,
                        onClick = {
                            onAction(SubjectListAction.OnTabSelected(0))
                        },
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(
                            text = stringResource(Res.string.search_result),
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                    Tab(
                        selected = state.selectedTabIndex == 1,
                        onClick = {
                            onAction(SubjectListAction.OnTabSelected(1))
                        },
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(
                            text = stringResource(Res.string.favorites),
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                }
                Spacer(Modifier.height(4.dp))
                HorizontalPager(
                    state = pageState,
                    modifier = Modifier.fillMaxWidth().weight(1f)
                ) { pageIndex ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        when (pageIndex) {
                            0 -> {
                                if (state.isLoading) {
                                    CircularProgressIndicator()
                                } else {
                                    when {
                                        state.errorMessage != null -> {
                                            Text(
                                                text = state.errorMessage.asString(),
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.headlineSmall,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }

                                        state.searchResult.isEmpty() -> {
                                            Text(
                                                text = stringResource(Res.string.no_result),
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.headlineSmall
                                            )
                                        }

                                        else -> {
                                            SubjectList(
                                                subjects = state.searchResult,
                                                onSubjectClick = {
                                                    onAction(
                                                        SubjectListAction.OnSubjectClick(
                                                            it
                                                        )
                                                    )
                                                },
                                                modifier = Modifier.fillMaxSize(),
                                                scrollState = searchResultListState
                                            )
                                        }
                                    }
                                }
                            }

                            1 -> {
                                if (state.favoriteSubject.isEmpty()) {
                                    Text(
                                        text = stringResource(Res.string.no_favorites),
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.headlineSmall
                                    )
                                } else {
                                    SubjectList(
                                        subjects = state.favoriteSubject,
                                        onSubjectClick = {
                                            onAction(
                                                SubjectListAction.OnSubjectClick(
                                                    it
                                                )
                                            )
                                        },
                                        modifier = Modifier.fillMaxSize(),
                                        scrollState = favoriteBookListState
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}