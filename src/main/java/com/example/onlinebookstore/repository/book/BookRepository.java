package com.example.onlinebookstore.repository.book;

import com.example.onlinebookstore.model.Book;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    Optional<Book> findById(Long id);

    Optional<Book> findByIsbn(String isbn);
}
