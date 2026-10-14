package com.group4.th.year.project.smart.campus.Management.Controller;

<<<<<<< HEAD

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
=======

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
>>>>>>> origin/master
import com.group4.th.year.project.smart.campus.Management.Resource;
import com.group4.th.year.project.smart.campus.Management.Repo.ResourceRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/resources")
public class ResourceController {
    private final ResourceRepo resourceRepo;

    public ResourceController(ResourceRepo resourceRepo) {
        this.resourceRepo = resourceRepo;
    }

<<<<<<< HEAD
    @Cacheable(value = "resources", key = "'all'")
    @GetMapping
=======
    @Cacheable(value = "resources", key = "'all'")
    @GetMapping
>>>>>>> origin/master
    public List<Resource> getResources() {
        return resourceRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getResourceById(@PathVariable Long id) {
        return resourceRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

<<<<<<< HEAD
    @CacheEvict(value = "resources", allEntries = true)
=======
    @CacheEvict(value = "resources", allEntries = true)
>>>>>>> origin/master
    @PostMapping
    public Resource addResource(@RequestBody Resource resource) {
        resource.setId(null);
        return resourceRepo.save(resource);
    }

<<<<<<< HEAD
    @CacheEvict(value = "resources", allEntries = true)
=======
    @CacheEvict(value = "resources", allEntries = true)
>>>>>>> origin/master
    @PutMapping("/{id}")
    public ResponseEntity<Resource> updateResource(@PathVariable Long id, @RequestBody Resource updatedResource) {
        return resourceRepo.findById(id)
                .map(resource -> {
                    resource.setName(updatedResource.getName());
                    resource.setType(updatedResource.getType());
                    resource.setCapacity(updatedResource.getCapacity());
                    return ResponseEntity.ok(resourceRepo.save(resource));
                })
                .orElse(ResponseEntity.notFound().build());
    }

<<<<<<< HEAD
    @CacheEvict(value = "resources", allEntries = true)
=======
    @CacheEvict(value = "resources", allEntries = true)
>>>>>>> origin/master
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable Long id) {
        if (!resourceRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        resourceRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
<<<<<<< HEAD


=======


>>>>>>> origin/master
