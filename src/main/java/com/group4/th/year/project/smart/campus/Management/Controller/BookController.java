package com.group4.th.year.project.smart.campus.Management.Controller;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import com.group4.th.year.project.smart.campus.Management.Book;
import com.group4.th.year.project.smart.campus.Management.Repo.BookRepo;
import com.group4.th.year.project.smart.campus.Management.Service.FileStorageService;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookRepo bookRepo;
    private final FileStorageService fileStorageService;

    public BookController(BookRepo bookRepo, FileStorageService fileStorageService) {
        this.bookRepo = bookRepo;
        this.fileStorageService = fileStorageService;
    }

    @Cacheable(value = "books", key = "'all'")
    @GetMapping
    public List<Book> getBooks() {
        return bookRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookRepo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @CacheEvict(value = "books", allEntries = true)
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        book.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookRepo.save(book));
    }

    @CacheEvict(value = "books", allEntries = true)
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        return bookRepo.findById(id)
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setIsbn(updatedBook.getIsbn());
                    book.setAuthor(updatedBook.getAuthor());
                    book.setAvailableCopies(updatedBook.getAvailableCopies());
                    book.setDriveLink(updatedBook.getDriveLink());
                    return ResponseEntity.ok(bookRepo.save(book));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/{id}/attachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadAttachment(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return bookRepo.findById(id).map(book -> {
            try {
                book.setAttachmentPath(fileStorageService.storePdf(file));
                book.setAttachmentName(file.getOriginalFilename());
                return ResponseEntity.ok(bookRepo.save(book));
            } catch (Exception exception) {
                return ResponseEntity.badRequest().body(exception.getMessage());
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/attachment")
    public ResponseEntity<?> downloadAttachment(@PathVariable Long id) {
        return bookRepo.findById(id).filter(book -> book.getAttachmentPath() != null).map(book -> {
            try {
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + book.getAttachmentName() + "\"")
                        .body(new UrlResource(fileStorageService.getFile(book.getAttachmentPath()).toUri()));
            } catch (Exception exception) {
                return ResponseEntity.notFound().build();
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/{id}/cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadCover(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return bookRepo.findById(id).map(book -> {
            try {
                book.setCoverImagePath(fileStorageService.storeImage(file));
                return ResponseEntity.ok(bookRepo.save(book));
            } catch (Exception exception) {
                return ResponseEntity.badRequest().body(exception.getMessage());
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/cover")
    public ResponseEntity<?> downloadCover(@PathVariable Long id) {
        return bookRepo.findById(id).filter(book -> book.getCoverImagePath() != null).map(book -> {
            try {
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                        .body(new UrlResource(fileStorageService.getFile(book.getCoverImagePath()).toUri()));
            } catch (Exception exception) {
                return ResponseEntity.notFound().build();
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @CacheEvict(value = "books", allEntries = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (!bookRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bookRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
