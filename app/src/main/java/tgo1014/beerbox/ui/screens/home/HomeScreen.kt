package tgo1014.beerbox.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.loadingFlow
import com.dt.composedatepicker.ComposeCalendar
import com.dt.composedatepicker.SelectDateListener
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.Scaffold
import tgo1014.beerbox.R
import tgo1014.beerbox.models.Beer
import tgo1014.beerbox.ui.composables.BeerComposable
import tgo1014.beerbox.ui.composables.FilterChip
import tgo1014.beerbox.ui.composables.InsetLargeTopAppBar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: BeerViewModel,
    onBeerClicked: (Beer) -> Unit,
) {

    val monthYearFormat = SimpleDateFormat("MMM yyyy", Locale.getDefault())

    val isLoading by viewModel.loadingFlow.collectAsState()
    val lazyState = rememberLazyListState()
    val scrollBehavior = remember { TopAppBarDefaults.enterAlwaysScrollBehavior() }

    val state = viewModel.state.collectAsState().value
    val beerList = state.beerList
    val afterFilter = state.afterFilter?.let { monthYearFormat.format(it) }
    val beforeFilter = state.beforeFilter?.let { monthYearFormat.format(it) }

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
                    .padding(horizontal = 16.dp)
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Filter(
                        startFilter = afterFilter,
                        endFilter = beforeFilter,
                        onAfterClicked = { viewModel.onAfterFilterClicked() },
                        onBeforeClicked = { viewModel.onBeforeFilterClicked() }
                    )
                }
                if (beerList.isEmpty()) {
                    item { EmptyState() }
                }
                items(beerList.chunked(gridSize)) { chunk ->
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        val modifier = Modifier.weight(1f)
                        chunk.forEach { beer ->
                            BeerComposable(beer, modifier.clickable { onBeerClicked(beer) })
                            if (beer == beerList.lastOrNull()) {
                                SideEffect { viewModel.onBottomReached() }
                            }
                        }
                        // This avoids the last row to take all the space instead of just 1 "grid block"
                        val cellsNotFilled = gridSize - chunk.size
                        repeat(cellsNotFilled) { Spacer(modifier) }
                    }
                }
                if (isLoading) {
                    item { Progress(Modifier.fillMaxWidth()) }
                }
            }
        }
    }
    MonthYearCalendar(
        show = state.isCalendarAfterOpen,
        onDateSelected = { viewModel.onAfterFilterClicked(it) },
        onCancel = { viewModel.onCalendarCancel() },
    )
    MonthYearCalendar(
        show = state.isCalendarBeforeOpen,
        onDateSelected = { viewModel.onBeforeFilterSelected(it) },
        onCancel = { viewModel.onCalendarCancel() },
    )
}

@Composable
private fun Filter(
    startFilter: String? = null,
    endFilter: String? = null,
    onAfterClicked: () -> Unit,
    onBeforeClicked: () -> Unit,
) = Surface {
    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = stringResource(R.string.filter))
        Spacer(modifier = Modifier.width(8.dp))
        FilterChip(
            isFilled = startFilter != null,
            noFilterText = stringResource(R.string.brewed_after),
            date = "${stringResource(R.string.after)}: $startFilter"
        ) { onAfterClicked() }
        Spacer(modifier = Modifier.width(8.dp))
        FilterChip(
            isFilled = endFilter != null,
            noFilterText = stringResource(R.string.brewed_before),
            date = stringResource(R.string.before) + ": " + endFilter
        ) { onBeforeClicked() }
    }
}

@Composable
private fun Progress(modifier: Modifier) {
    Box(modifier = modifier) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun Toolbar(scrollBehavior: TopAppBarScrollBehavior) {
    InsetLargeTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Serif,
            )
        },
        modifier = Modifier.fillMaxWidth(),
        contentPadding = rememberInsetsPaddingValues(
            LocalWindowInsets.current.statusBars,
            applyBottom = false,
        ),
        backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.95f),
        scrollBehavior = scrollBehavior,
        titleContentColor = Color.Black
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
private fun EmptyState() {
    Surface(Modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.no_beers),
            textAlign = TextAlign.Center,
        )
    }
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
            themeColor = MaterialTheme.colorScheme.secondary,
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