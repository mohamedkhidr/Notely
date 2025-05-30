package com.module.notelycompose

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.module.notelycompose.notes.presentation.list.NoteListIntent
import com.module.notelycompose.notes.ui.list.SharedNoteListScreen
import com.module.notelycompose.notes.ui.theme.MyApplicationTheme

fun NoteListController(
    selectedTabTitle :String,
    onFloatingActionButtonClicked: () -> Unit,
    onNoteClicked: (Long) -> Unit,
    onFilterTabClicked:(String) -> Unit
) = ComposeUIViewController {
    MyApplicationTheme {
        val appModule = remember { AppModule() }
        val viewmodel = remember {
            IOSNoteListViewModel(
                selectedTabTitle = selectedTabTitle,
                getAllNotesUseCase = appModule.getAllNotesUseCase,
                searchNotesUseCase = appModule.searchNotesUseCase,
                deleteNoteById = appModule.deleteNoteById,
                notePresentationMapper = appModule.notePresentationMapper,
                notesFilterMapper = appModule.notesFilterMapper
            )
        }
        val state = viewmodel.state.collectAsState()
        val notes = viewmodel.onGetUiState(state.value)
        SharedNoteListScreen(
            notes = notes,
            onFloatingActionButtonClicked = onFloatingActionButtonClicked,
            onNoteClicked = onNoteClicked,
            onNoteDeleteClicked = { id ->
                viewmodel.onProcessIntent(NoteListIntent.OnNoteDeleted(id))
            },
            onFilterTabItemClicked = { filter ->
                onFilterTabClicked(filter)
                viewmodel.onProcessIntent(NoteListIntent.OnFilterNote(filter))
            },
            onSearchByKeyword = { keyword ->
                viewmodel.onProcessIntent(NoteListIntent.OnSearchNote(keyword))
            },
            selectedTabTitle = state.value.selectedTabTitle
        )
    }
}
