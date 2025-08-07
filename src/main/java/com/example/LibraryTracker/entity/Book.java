package com.example.LibraryTracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "books", schema = "library")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 45, message = "name size must be between 2 to 45")
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "publish_year")
    private Integer publishYear;

    @NotNull
    @Size(min = 2, max = 45, message = "category size must be between 2 to 45")
    @Column(name = "category")
    private String category;

    @NotNull
    @Column(name = "available")
    private Boolean available;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @OneToMany(mappedBy = "book")
    private List<Loan> loans;




}