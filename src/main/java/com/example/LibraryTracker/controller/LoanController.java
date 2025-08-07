package com.example.LibraryTracker.controller;

import com.example.LibraryTracker.dto.loan.CreateLoanDto;
import com.example.LibraryTracker.dto.loan.LoanResponseDto;
import com.example.LibraryTracker.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @Operation(summary = "Admin -> Kitap odunc alma")
    @PostMapping("/borrow")
    public ResponseEntity<LoanResponseDto> borrowBook(@RequestBody CreateLoanDto dto) {
        return ResponseEntity.ok(loanService.borrowBook(dto));
    }

    @Operation(summary = "Admin -> Kitap iade etme")
    @PutMapping("/{loanId}/return")
    public ResponseEntity<LoanResponseDto> returnBook(@PathVariable Long loanId) {
        return ResponseEntity.ok(loanService.returnBook(loanId));
    }

    @Operation(summary = "Admin -> Kullanicinin odunc gecmisi")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LoanResponseDto>> getLoansByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(loanService.getLoansByUser(userId));
    }

    @Operation(summary = "Admin -> Kullanicinin aktif oduncleri")
    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<LoanResponseDto>> getActiveLoansByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(loanService.getActiveLoansByUser(userId));
    }

    @Operation(summary = "Admin -> Bir kitabin odunc durumu")
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<LoanResponseDto>> getLoansByBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(loanService.getLoansByBook(bookId));
    }

    @Operation(summary = "Admin -> Tum aktif oduncler(iade edilmemis)")
    @GetMapping("/active")
    public ResponseEntity<List<LoanResponseDto>> getActiveLoans() {
        return ResponseEntity.ok(loanService.getActiveLoans());
    }

    @Operation(summary = "Admin -> Tum gecikmis oduncler")
    @GetMapping("/overdue")
    public ResponseEntity<List<LoanResponseDto>> getOverdueLoans() {
        return ResponseEntity.ok(loanService.getOverdueLoans());
    }
}
