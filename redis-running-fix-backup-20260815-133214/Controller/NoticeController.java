package com.group4.th.year.project.smart.campus.Management.Controller;

import com.group4.th.year.project.smart.campus.Management.Notice;
import com.group4.th.year.project.smart.campus.Management.Repo.NoticeRepo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notices")
public class NoticeController {
    private final NoticeRepo noticeRepo;

    public NoticeController(NoticeRepo noticeRepo) {
        this.noticeRepo = noticeRepo;
    }

    @Cacheable(value = "notices", key = "'all'")


    @GetMapping public List<Notice> getNotices() {
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

