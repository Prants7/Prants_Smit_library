package com.prants.service

import com.prants.api.display.ReaderDisplayElement
import com.prants.api.forms.NewReaderForm
import com.prants.entity.Reader
import com.prants.repository.ReaderRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class ReaderService {
    @Inject
    private ReaderRepository readerRepository
    @Inject
    private DisplayPrepService displayPrepService

    String saveNewReader(NewReaderForm newReaderForm) {
        isFormValid(newReaderForm)
        Reader newReader = Reader.newReaderFromForm(newReaderForm)
        Reader savedReader = readerRepository.saveNewReader(newReader)
        return savedReader.getReaderCode()
    }

    private void isFormValid(NewReaderForm newReaderForm) {
        if (newReaderForm.getName() == null) {
            throw new RuntimeException("Form is missing reader name")
        }
        if (newReaderForm.getReaderCode() == null) {
            throw new RuntimeException("Form is missing reader code")
        }
        if (readerRepository.getReaderWithReaderCode(newReaderForm.getReaderCode()).isPresent()) {
            throw new RuntimeException("Reader code is in use")
        }
    }

    List<ReaderDisplayElement> getAllReadersBrowseList() {
        List<Reader> allReaders = this.readerRepository.getAllReaders()
        List<ReaderDisplayElement> displayElements = allReaders.stream()
                .map(oneReader -> this.displayPrepService.prepareReaderDisplayElement(oneReader))
                .toList()
        return displayElements
    }
}
