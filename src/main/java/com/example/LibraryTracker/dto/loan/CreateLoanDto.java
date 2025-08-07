package com.example.LibraryTracker.dto.loan;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateLoanDto {

    @NotNull
    private Long bookId;

    @NotNull
    private Long userId;
}
