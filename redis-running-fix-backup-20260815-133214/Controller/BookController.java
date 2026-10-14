package com.group4.th.year.project.smart.campus.Management.Controller;

import com.group4.th.year.project.smart.campus.Management.Book;
import com.group4.th.year.project.smart.campus.Management.Repo.BookRepo;
<<<<<<< HEAD
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
=======
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
>>>>>>> origin/master
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookRepo bookRepo;

    public BookController(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

<<<<<<< HEAD
    @Cacheable(value = "books", key = "'all'")
=======
    @Cacheable(value = "books", key = "'all'")
>>>>>>> origin/master


    @GetMapping public List<Book> getBooks() {
        return bookRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookRepo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

<<<<<<< HEAD
    @CacheEvict(value = "books", allEntries = true)
=======
    @CacheEvict(value = "books", allEntries = true)
>>>>>>> origin/master
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        book.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookRepo.save(book));
    }

<<<<<<< HEAD
    @CacheEvict(value = "books", allEntries = true)
=======
    @CacheEvict(value = "books", allEntries = true)
>>>>>>> origin/master
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        return bookRepo.findById(id)
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setIsbn(updatedBook.getIsbn());
                    book.setAuthor(updatedBook.getAuthor());
                    book.setAvailableCopies(updatedBook.getAvailableCopies());
                    return ResponseEntity.ok(bookRepo.save(book));
                })
                .orElse(ResponseEntity.notFound().build());
    }

<<<<<<< HEAD
    @CacheEvict(value = "books", allEntries = true)
=======
    @CacheEvict(value = "books", allEntries = true)
>>>>>>> origin/master
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (!bookRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bookRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
<<<<<<< HEAD

=======

>>>>>>> origin/master
