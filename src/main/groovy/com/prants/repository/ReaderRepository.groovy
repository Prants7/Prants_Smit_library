package com.prants.repository

import com.prants.entity.Reader

interface ReaderRepository {

    Reader saveNewReader(Reader newReader)

    Optional<Reader> getReaderWithReaderCode(String readerCode)

    List<Reader> getAllReaders()

}