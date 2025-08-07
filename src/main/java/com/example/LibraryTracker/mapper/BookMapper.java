package com.example.LibraryTracker.mapper;


import com.example.LibraryTracker.dto.book.BookResponseDto;
import com.example.LibraryTracker.dto.book.CreateBookDto;
import com.example.LibraryTracker.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    // CreateBookDto -> Book (author set edilmeyecek burada)
    Book toEntity(CreateBookDto dto);

    // Book -> BookResponseDto (author.name maplenecek)
    BookResponseDto toDto(Book book);
}
