package com.example.LibraryTracker.mapper;

import com.example.LibraryTracker.dto.loan.CreateLoanDto;
import com.example.LibraryTracker.dto.loan.LoanResponseDto;
import com.example.LibraryTracker.entity.Loan;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    // Loan entity'ye dönüşüm (book ve user servis katmanında set edilecek)
    Loan toEntity(CreateLoanDto dto);

    // Entity'den DTO'ya dönüşüm (nested alanlar mapleniyor)
    LoanResponseDto toDto(Loan loan);
}
