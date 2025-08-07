package com.example.LibraryTracker.repository;

import com.example.LibraryTracker.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b JOIN FETCH b.author WHERE b.id = :id")
    Optional<Book> findByIdWithAuthor(@Param("id") Long id);

    List<Book> findByCategory(String category);

    List<Book> findByTitleContainingIgnoreCase(String keyword);

    List<Book> findByAuthorName(String authorName);

    List<Book> findByAvailableTrue();

    List<Book> findByCategoryAndAvailableTrue(String category);
}
