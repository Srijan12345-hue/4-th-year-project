package com.group4.th.year.project.smart.campus.Management.Controller;

import com.group4.th.year.project.smart.campus.Management.Service.SmartCampusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmartCampusController {
    private final SmartCampusService smartCampusService;

    public SmartCampusController(SmartCampusService smartCampusService) {
        this.smartCampusService = smartCampusService;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return smartCampusService.dashboardSummary();
    }

    @GetMapping("/student-panel/access")
    public String studentPanelAccess() {
        return "Student portal access granted";
    }

    @GetMapping("/librarian-panel/access")
    public String librarianPanelAccess() {
        return "Librarian panel access granted";
    }

    @GetMapping("/placement-panel/access")
    public String placementPanelAccess() {
        return "Training and placement panel access granted";
    }
}

