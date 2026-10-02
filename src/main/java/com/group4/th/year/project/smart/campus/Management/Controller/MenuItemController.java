package com.group4.th.year.project.smart.campus.Management.Controller;

import com.group4.th.year.project.smart.campus.Management.MenuItem;
import com.group4.th.year.project.smart.campus.Management.Repo.MenuItemRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu-items")
public class MenuItemController {
    private final MenuItemRepo menuItemRepo;

    public MenuItemController(MenuItemRepo menuItemRepo) {
        this.menuItemRepo = menuItemRepo;
    }

    @GetMapping
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
        return menuItemRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MenuItem> createMenuItem(@RequestBody MenuItem menuItem) {
        return ResponseEntity.status(HttpStatus.CREATED).body(menuItemRepo.save(menuItem));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable Long id, @RequestBody MenuItem updatedMenuItem) {
        return menuItemRepo.findById(id)
                .map(existing -> {
                    updatedMenuItem.setId(id);
                    return ResponseEntity.ok(menuItemRepo.save(updatedMenuItem));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        if (!menuItemRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        menuItemRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
