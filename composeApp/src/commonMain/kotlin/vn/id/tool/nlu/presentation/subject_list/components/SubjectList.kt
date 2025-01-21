package vn.id.tool.nlu.presentation.subject_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import vn.id.tool.nlu.domain.Subject

@Composable
fun SubjectList(
    subjects: List<Subject>,
    onSubjectClick: (Subject) -> Unit,
    scrollState: LazyListState = rememberLazyListState(),
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = subjects,
            key = {
                it.id
            }
        ) { subject ->
            SubjectListItem(
                subject = subject,
                modifier = Modifier.widthIn(max = 700.dp).fillMaxWidth().padding(16.dp),
                onClick = { onSubjectClick(subject) }
            )
        }
    }
}