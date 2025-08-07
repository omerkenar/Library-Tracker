package com.example.LibraryTracker.repository;

import com.example.LibraryTracker.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByUserId(Long userId);

    List<Loan> findByBookId(Long bookId);

    List<Loan> findByUserIdAndReturnedFalse(Long userId);

    List<Loan> findByReturnedFalse();

    boolean existsByBookIdAndReturnedFalse(Long bookId);

    @Query("SELECT l FROM Loan l WHERE l.returnDate < CURRENT_DATE AND l.returned = false")
    List<Loan> findOverdueLoans();

}
