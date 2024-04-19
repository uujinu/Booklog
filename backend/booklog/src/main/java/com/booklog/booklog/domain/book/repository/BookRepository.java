package com.booklog.booklog.domain.book.repository;

import com.booklog.booklog.domain.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
