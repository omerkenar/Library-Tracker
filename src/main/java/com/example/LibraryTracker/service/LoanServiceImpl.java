package com.example.LibraryTracker.service;

import com.example.LibraryTracker.dto.loan.CreateLoanDto;
import com.example.LibraryTracker.dto.loan.LoanResponseDto;
import com.example.LibraryTracker.entity.Book;
import com.example.LibraryTracker.entity.Loan;
import com.example.LibraryTracker.entity.User;
import com.example.LibraryTracker.exception.BusinessException;
import com.example.LibraryTracker.exception.NotFoundException;
import com.example.LibraryTracker.mapper.LoanMapper;
import com.example.LibraryTracker.repository.BookRepository;
import com.example.LibraryTracker.repository.LoanRepository;
import com.example.LibraryTracker.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final LoanMapper loanMapper;

    private static final int MAX_ACTIVE_LOAN_LIMIT = 3;

    @Override
    @Transactional
    public LoanResponseDto borrowBook(CreateLoanDto dto) {
        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new NotFoundException("Book not found: " + dto.getBookId(), HttpStatus.NOT_FOUND));
        if (!book.getAvailable() || loanRepository.existsByBookIdAndReturnedFalse(book.getId())) {
            throw new BusinessException("Book is not available for borrowing!", HttpStatus.CONFLICT);
        }
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found: " + dto.getUserId(), HttpStatus.NOT_FOUND));

        int activeLoans = loanRepository.findByUserIdAndReturnedFalse(dto.getUserId()).size();
        if (activeLoans >= MAX_ACTIVE_LOAN_LIMIT) {
            throw new BusinessException("The user cannot borrow more than " + MAX_ACTIVE_LOAN_LIMIT + " books at the same time!", HttpStatus.CONFLICT);
        }

        Loan loan = loanMapper.toEntity(dto);
        loan.setBook(book);
        loan.setUser(user);
        loan.setBorrowDate(LocalDate.now());
        loan.setReturned(false);
        loan.setReturnDate(LocalDate.now().plusDays(15));
        loan = loanRepository.save(loan);
        book.setAvailable(false);
        bookRepository.save(book);
        return loanMapper.toDto(loan);
    }

    @Override
    @Transactional
    public LoanResponseDto returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new NotFoundException("Loan not found: " + loanId, HttpStatus.NOT_FOUND));
        if (loan.isReturned()) {
            throw new BusinessException("Book already returned!", HttpStatus.CONFLICT);
        }
        loan.setReturned(true);
        loan.setReturnDate(LocalDate.now());
        loanRepository.save(loan);

        Book book = loan.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        return loanMapper.toDto(loan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanResponseDto> getLoansByUser(Long userId) {
        return loanRepository.findByUserId(userId).stream()
                .map(loanMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanResponseDto> getLoansByBook(Long bookId) {
        return loanRepository.findByBookId(bookId).stream()
                .map(loanMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanResponseDto> getActiveLoans() {
        return loanRepository.findByReturnedFalse().stream()
                .map(loanMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanResponseDto> getOverdueLoans() {
        return loanRepository.findOverdueLoans().stream()
                .map(loanMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanResponseDto> getActiveLoansByUser(Long userId) {
        return loanRepository.findByUserIdAndReturnedFalse(userId).stream()
                .map(loanMapper::toDto)
                .toList();
    }
}
