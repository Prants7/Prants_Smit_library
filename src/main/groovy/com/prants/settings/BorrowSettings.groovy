package com.prants.settings

import com.prants.service.TimeService
import jakarta.inject.Singleton

import java.time.LocalDate

@Singleton
class BorrowSettings {
    private Integer borrowDays = 7

    Integer getNormalBorrowDaysAmount() {
        return borrowDays
    }
}
