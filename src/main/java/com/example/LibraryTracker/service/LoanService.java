package com.example.LibraryTracker.service;

import com.example.LibraryTracker.dto.loan.CreateLoanDto;
import com.example.LibraryTracker.dto.loan.LoanResponseDto;

import java.util.List;

public interface LoanService {
    LoanResponseDto borrowBook(CreateLoanDto dto);
    LoanResponseDto returnBook(Long loanId);
    List<LoanResponseDto> getLoansByUser(Long userId);
    List<LoanResponseDto> getLoansByBook(Long bookId);
    List<LoanResponseDto> getActiveLoans();
    List<LoanResponseDto> getOverdueLoans();
    List<LoanResponseDto> getActiveLoansByUser(Long userId);

}
