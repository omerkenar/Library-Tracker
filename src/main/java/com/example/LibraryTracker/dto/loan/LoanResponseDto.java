package com.example.LibraryTracker.dto.loan;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanResponseDto {

    private Long id;

    private String bookTitle;

    private String username;

    private LocalDate borrowDate;

    private LocalDate returnDate;

    private boolean returned;
}
