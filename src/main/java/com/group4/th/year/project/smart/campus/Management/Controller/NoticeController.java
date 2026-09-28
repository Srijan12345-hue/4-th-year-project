package com.group4.th.year.project.smart.campus.Management.Controller;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import com.group4.th.year.project.smart.campus.Management.Notice;
import com.group4.th.year.project.smart.campus.Management.Repo.NoticeRepo;
import com.group4.th.year.project.smart.campus.Management.Service.FileStorageService;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/notices")
public class NoticeController {
    private final NoticeRepo noticeRepo;
    private final FileStorageService fileStorageService;

    public NoticeController(NoticeRepo noticeRepo, FileStorageService fileStorageService) {
        this.noticeRepo = noticeRepo;
        this.fileStorageService = fileStorageService;
    }

    @Cacheable(value = "notices", key = "'all'")
    @GetMapping
    public List<Notice> getNotices() {
        return noticeRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notice> getNoticeById(@PathVariable Long id) {
        return noticeRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @CacheEvict(value = "notices", allEntries = true)
    @PostMapping
    public Notice addNotice(@RequestBody Notice notice) {
        notice.setId(null);
        return noticeRepo.save(notice);
    }

    @CacheEvict(value = "notices", allEntries = true)
    @PutMapping("/{id}")
    public ResponseEntity<Notice> updateNotice(@PathVariable Long id, @RequestBody Notice updatedNotice) {
        return noticeRepo.findById(id)
                .map(notice -> {
                    notice.setTitle(updatedNotice.getTitle());
                    notice.setBody(updatedNotice.getBody());
                    notice.setIssuedForRoles(updatedNotice.getIssuedForRoles());
                    notice.setValidFrom(updatedNotice.getValidFrom());
                    notice.setValidTo(updatedNotice.getValidTo());
                    return ResponseEntity.ok(noticeRepo.save(notice));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/{id}/attachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadAttachment(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return noticeRepo.findById(id).map(notice -> {
            try {
                notice.setAttachmentPath(fileStorageService.storePdf(file));
                notice.setAttachmentName(file.getOriginalFilename());
                return ResponseEntity.ok(noticeRepo.save(notice));
            } catch (Exception exception) {
                return ResponseEntity.badRequest().body(exception.getMessage());
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/attachment")
    public ResponseEntity<?> downloadAttachment(@PathVariable Long id) {
        return noticeRepo.findById(id).filter(notice -> notice.getAttachmentPath() != null).map(notice -> {
            try {
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + notice.getAttachmentName() + "\"")
                        .body(new UrlResource(fileStorageService.getFile(notice.getAttachmentPath()).toUri()));
            } catch (Exception exception) {
                return ResponseEntity.notFound().build();
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @CacheEvict(value = "notices", allEntries = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long id) {
        if (!noticeRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        noticeRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
