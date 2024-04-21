package com.prants.service

import java.time.LocalDate
import java.time.LocalDateTime

class TimeService {

    static LocalDate getCurrentDate() {
        return LocalDate.now()
    }

    static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now()
    }
}
