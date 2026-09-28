package com.group4.th.year.project.smart.campus.Management.Controller;

import com.group4.th.year.project.smart.campus.Management.*;
import com.group4.th.year.project.smart.campus.Management.Repo.CoursesRepo;
import com.group4.th.year.project.smart.campus.Management.Repo.NoticeRepo;
import com.group4.th.year.project.smart.campus.Management.Repo.ResourceRepo;
import com.group4.th.year.project.smart.campus.Management.Repo.UserRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmartCampusController {
    private final UserRepo userRepo;
    private final CoursesRepo courseRepo;
    private final NoticeRepo noticeRepo;
    private final ResourceRepo resourceRepo;

    public SmartCampusController(UserRepo userRepo, CoursesRepo courseRepo, NoticeRepo noticeRepo, ResourceRepo resourceRepo) {
        this.userRepo = userRepo;
        this.courseRepo = courseRepo;
        this.noticeRepo = noticeRepo;
        this.resourceRepo = resourceRepo;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "Smart Campus Dashboard: users=" + userRepo.count()
                + ", courses=" + courseRepo.count()
                + ", notices=" + noticeRepo.count()
                + ", resources=" + resourceRepo.count();
    }
}

