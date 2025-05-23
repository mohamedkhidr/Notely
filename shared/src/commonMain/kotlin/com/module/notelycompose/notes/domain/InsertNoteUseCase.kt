package com.module.notelycompose.notes.domain

import com.module.notelycompose.notes.domain.mapper.NoteDomainMapper
import com.module.notelycompose.notes.domain.mapper.TextFormatMapper
import com.module.notelycompose.notes.domain.model.TextAlignDomainModel
import com.module.notelycompose.notes.domain.model.TextFormatDomainModel

class InsertNoteUseCase(
    private val noteDataSource: NoteDataSource,
    private val textFormatMapper: TextFormatMapper,
    private val noteDomainMapper: NoteDomainMapper
) {
    suspend fun execute(
        title: String,
        content: String,
        starred: Boolean,
        formatting: List<TextFormatDomainModel>,
        textAlign: TextAlignDomainModel,
        recordingPath: String
    ) = noteDataSource.insertNote(
            title = title,
            content = content,
            starred = starred,
            formatting = formatting.map { textFormatMapper.mapToDataModel(it) },
            textAlign = noteDomainMapper.mapTextAlignToDataModel(textAlign),
            recordingPath = recordingPath
    )
}
