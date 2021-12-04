package tgo1014.listofbeers.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.loadingFlow
import com.dt.composedatepicker.ComposeCalendar
import com.dt.composedatepicker.SelectDateListener
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.pager.ExperimentalPagerApi
import tgo1014.listofbeers.R
import tgo1014.listofbeers.ui.composables.BeerComposable
import tgo1014.listofbeers.ui.composables.FilterChip
import tgo1014.listofbeers.ui.composables.InsetLargeTopAppBar
import tgo1014.listofbeers.ui.theme.Amber700
import java.util.Date

@OptIn(
    ExperimentalPagerApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()
    val beers by viewModel.beersFlow.collectAsState()
    val isLoading by viewModel.loadingFlow.collectAsState()
    val lazyState = rememberLazyListState()
    val scrollBehavior = remember { TopAppBarDefaults.enterAlwaysScrollBehavior() }
    val afterFilter by viewModel.startFilter.collectAsState(null)
    val beforeFilter by viewModel.endFilter.collectAsState(null)
    var afterCalendarOpen by remember { mutableStateOf(false) }
    var beforeCalendarOpen by remember { mutableStateOf(false) }
    Scaffold(
        topBar = { Toolbar(scrollBehavior) },
        bottomBar = { BottomSpacing() },
        backgroundColor = MaterialTheme.colorScheme.background
    ) { contentPadding ->
        BoxWithConstraints {
            val cellSize = 170
            val gridSize = (maxWidth.value / cellSize).toInt()
            LazyColumn(
                state = lazyState,
                contentPadding = contentPadding,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {
                item {
                    Filter(
                        startFilter = afterFilter,
                        endFilter = beforeFilter,
                        onStartClicked = {
                            // If the filter is set clear it TODO move to viewModel the logic
                            if (afterFilter != null) {
                                viewModel.onAfterClicked(null)
                            } else {
                                afterCalendarOpen = true
                            }
                        },
                        onEndClicked = {
                            // If the filter is set clear it TODO move to viewModel the logic
                            if (beforeFilter != null) {
                                viewModel.onBeforeFilterSelected(null)
                            } else {
                                beforeCalendarOpen = true
                            }
                        }
                    )
                }
                items(beers.chunked(gridSize)) { chunk ->
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        val modifier = Modifier.weight(1f)
                        chunk.forEach { beer ->
                            BeerComposable(beer, modifier)
                            if (beer == beers.lastOrNull()) {
                                SideEffect { viewModel.onBottomReached() }
                            }
                        }
                        // This avoids the last row to take all the space istead of just 1 "grid block"
                        val cellsNotFilled = gridSize - chunk.size
                        repeat(cellsNotFilled) { Spacer(modifier) }
                    }
                }
                item {
                    if (isLoading) {
                        Progress(Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }
    MonthYearCalendar(
        show = afterCalendarOpen,
        onDateSelected = {
            viewModel.onAfterClicked(it)
            afterCalendarOpen = false
        },
        onCancel = { afterCalendarOpen = false },
    )
    MonthYearCalendar(
        show = beforeCalendarOpen,
        onDateSelected = {
            viewModel.onBeforeFilterSelected(it)
            beforeCalendarOpen = false
        },
        onCancel = { beforeCalendarOpen = false },
    )
}

@Composable
private fun Filter(
    startFilter: String? = null,
    endFilter: String? = null,
    onStartClicked: () -> Unit,
    onEndClicked: () -> Unit,
) = Surface {
    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "_Filter:_")
        Spacer(modifier = Modifier.width(8.dp))
        FilterChip(
            isFilled = startFilter != null,
            noFilterText = "_Brewed After_",
            date = "After: $startFilter"
        ) { onStartClicked() }
        Spacer(modifier = Modifier.width(8.dp))
        FilterChip(
            isFilled = endFilter != null,
            noFilterText = "_Brewed Before_",
            date = "Before $endFilter"
        ) { onEndClicked() }
    }
}

@Composable
private fun Progress(modifier: Modifier) {
    Box(modifier = modifier) {
        CircularProgressIndicator(
            color = Amber700,
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun Toolbar(scrollBehavior: TopAppBarScrollBehavior) {
    InsetLargeTopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        backgroundColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
        modifier = Modifier.fillMaxWidth(),
        scrollBehavior = scrollBehavior,
        contentPadding = rememberInsetsPaddingValues(
            LocalWindowInsets.current.statusBars,
            applyBottom = false,
        ),
    )
}

@Composable
private fun BottomSpacing() {
    Spacer(
        modifier = Modifier
            .navigationBarsHeight()
            .fillMaxWidth()
    )
}

@Composable
private fun MonthYearCalendar(
    show: Boolean,
    onDateSelected: (Date) -> Unit,
    onCancel: () -> Unit,
) {
    if (!show) return
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x83000000)),
        contentAlignment = Alignment.Center
    ) {
        ComposeCalendar(
            themeColor = Amber700,
            listener = object : SelectDateListener {
                override fun onCanceled() {
                    onCancel()
                }

                override fun onDateSelected(date: Date) {
                    onDateSelected(date)
                }
            }
        )
    }
}