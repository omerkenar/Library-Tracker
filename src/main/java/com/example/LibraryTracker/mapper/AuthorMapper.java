package com.example.LibraryTracker.mapper;

import com.example.LibraryTracker.dto.author.*;
import com.example.LibraryTracker.entity.Author;
import com.example.LibraryTracker.entity.Book;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    //Yeni yazar oluştururken kullanılır
    Author toEntity(CreateAuthorDto dto);

    AuthorResponseDto toDto(Author author);

    AuthorWithBooksDto toDtoWithBooks(Author author);

    // Book entity listesini BookInfo listesine çevir
    List<AuthorWithBooksDto.BookInfo> toBookInfoList(List<Book> books);

    AuthorWithBooksDto.BookInfo toBookInfo(Book book);
}
