package com.prants.service

import java.time.LocalDate
import java.time.LocalDateTime

class TimeService {

    static getCurrentDate() {
        return LocalDate.now()
    }

    static getCurrentDateTime() {
        return LocalDateTime.now()
    }
}
