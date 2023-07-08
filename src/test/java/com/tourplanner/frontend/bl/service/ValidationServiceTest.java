package com.tourplanner.frontend.bl.service;


import com.tourplanner.shared.enums.Difficulty;
import com.tourplanner.shared.enums.TransportType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ValidationServiceTest {


    @ParameterizedTest
    @ValueSource(strings = {"Test", "Test1"})
    void isValidNameTest(String name) {
        assertTrue(ValidationService.isValidName(name));
    }
    @ParameterizedTest
    @ValueSource(strings = {"Testö", "Test%", ""})
    void isInvalidNameTest(String name) {
        assertFalse(ValidationService.isValidName(name));
    }

    @ParameterizedTest
    @CsvSource({"2014-04-28", "2022-04-28"})
    void isValidDate(LocalDate date) {
        assertTrue(ValidationService.isValidDate(date));
    }
    @ParameterizedTest
    @CsvSource({"2032-04-28"})
    void isInvalidDate(LocalDate date) {
        assertFalse(ValidationService.isValidDate(date));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Drehertraße 99/8/4", "Müllerstrasse 9/1/2"})
    void isValidDestination(String dest) {
        assertTrue(ValidationService.isValidDestination(dest));
    }
    @ParameterizedTest
    @ValueSource(strings = {"Drehertraße 99\\8/4", "Müllerstras&&e 9/1/2", ""})
    void isInValidDestination(String dest) {
        assertFalse(ValidationService.isValidDestination(dest));
    }

    @ParameterizedTest
    @EnumSource(value = TransportType.class)
    void isValidTransType(TransportType transportType) {
        assertTrue(ValidationService.isValidTransType(transportType));
    }

    @ParameterizedTest
    @ValueSource(strings = {"11:44", "07:55"})
    void isValidTime(String time) {
        assertTrue(ValidationService.isValidTime(time));
    }
    @ParameterizedTest
    @ValueSource(strings = {"99::88", "07-:55", "99:88"})
    void isInvalidTime(String time) {
        assertFalse(ValidationService.isValidTime(time));
    }
    @ParameterizedTest
    @EnumSource(value = Difficulty.class)
    void isValidDifficulty(Difficulty difficulty) {
        assertTrue(ValidationService.isValidDifficulty(difficulty));
    }
}