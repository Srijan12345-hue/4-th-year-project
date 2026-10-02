package com.group4.th.year.project.smart.campus.Management.Controller;

import com.group4.th.year.project.smart.campus.Management.*;
import com.group4.th.year.project.smart.campus.Management.Repo.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmartCampusController {
    private final UserRepo userRepo;
    private final CoursesRepo courseRepo;
    private final NoticeRepo noticeRepo;
    private final ResourceRepo resourceRepo;
    private final FacultyRepo facultyRepo;
    private final BookRepo bookRepo;
    private final StudentRepo studentRepo;
    private final EventRepo eventRepo;
    private final BookingRepo bookingRepo;
    private final IssueRepo issueRepo;
    private final LogEntryRepo logEntryRepo;

    public SmartCampusController(
            UserRepo userRepo,
            CoursesRepo courseRepo,
            NoticeRepo noticeRepo,
            ResourceRepo resourceRepo,
            FacultyRepo facultyRepo,
            BookRepo bookRepo,
            StudentRepo studentRepo,
            EventRepo eventRepo,
            BookingRepo bookingRepo,
            IssueRepo issueRepo,
            LogEntryRepo logEntryRepo
    ) {
        this.userRepo = userRepo;
        this.courseRepo = courseRepo;
        this.noticeRepo = noticeRepo;
        this.resourceRepo = resourceRepo;
        this.facultyRepo = facultyRepo;
        this.bookRepo = bookRepo;
        this.studentRepo = studentRepo;
        this.eventRepo = eventRepo;
        this.bookingRepo = bookingRepo;
        this.issueRepo = issueRepo;
        this.logEntryRepo = logEntryRepo;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "Smart Campus Dashboard: users=" + userRepo.count()
                + ", courses=" + courseRepo.count()
                + ", notices=" + noticeRepo.count()
                + ", resources=" + resourceRepo.count()
                + ", faculties=" + facultyRepo.count()
                + ", students=" + studentRepo.count()
                + ", events=" + eventRepo.count()
                + ", bookings=" + bookingRepo.count()
                + ", books=" + bookRepo.count()
                + ", issues=" + issueRepo.count()
                + ", logs=" + logEntryRepo.count();
    }
}
