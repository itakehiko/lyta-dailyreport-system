package com.techacademy.controller;

import com.techacademy.constants.ErrorKinds;
import com.techacademy.constants.ErrorMessage;
import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.service.EmployeeService;
import com.techacademy.service.ReportService;
import com.techacademy.service.UserDetail;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("reports")
public class ReportController {

    private final ReportService reportService;
    private final EmployeeService employeeService;

    @Autowired
    public ReportController(ReportService reportService, EmployeeService employeeService) {
        this.reportService = reportService;
        this.employeeService = employeeService;
    }

    // 日報一覧画面
    @GetMapping
    public String list(Model model) {

        model.addAttribute("listSize", reportService.findAll().size());
        model.addAttribute("reportList", reportService.findAll());

        return "reports/list";
    }

    // 日報詳細画面
    @GetMapping(value = "/{id}/")
    public String detail(@PathVariable Integer id, Model model) {

        model.addAttribute("report", reportService.findById(id));
        return "reports/detail";
    }

    // 日報新規登録画面
    @GetMapping(value = "/add")
    public String create(@ModelAttribute Report report, Model model) {

        String code = SecurityContextHolder.getContext().getAuthentication().getName(); // ログイン中ユーザーのcode
        String name = employeeService.findByCode(code).getName();

        model.addAttribute("employeeName", name);

        return "reports/new";
    }

    // 日報新規登録処理
    @PostMapping(value = "/add")
    public String add(@Validated Report report, BindingResult res, Model model) {

        var code = SecurityContextHolder.getContext().getAuthentication().getName(); // ログイン中ユーザーのcode
        Employee employee = employeeService.findByCode(code);
        report.setEmployee(employee);

        LocalDateTime now = LocalDateTime.now();
        report.setCreatedAt(now);
        report.setUpdatedAt(now);

        if (res.hasErrors()) {
            return create(report, model);
        }

        reportService.create(report);
        return "redirect:/reports";
    }

    // 日報削除処理
    @PostMapping(value = "/{id}/delete")
    public String delete(@PathVariable Integer id, @AuthenticationPrincipal UserDetail userDetail, Model model) {

        reportService.delete(id);

        return "redirect:/reports";
    }

    // 日報更新画面
    @GetMapping(value = "/{id}/update")
    public String edit(@PathVariable("id") Integer id, Model model) {

        String code = SecurityContextHolder.getContext().getAuthentication().getName(); // ログイン中ユーザーのcode
        String name = employeeService.findByCode(code).getName();

        model.addAttribute("employeeName", name);

        if (id != null) {
            model.addAttribute("report", reportService.findById(id));
            return "reports/edit";
        } else {
            return "reports/edit";
        }
    }

    // 日報更新処理
    @PostMapping(value = "/{id}/update")
    public String update(@Validated Report report, BindingResult res, Model model) {

        if (res.hasErrors()) {
            return edit(null, model);
        }

        report.setUpdatedAt(LocalDateTime.now());
        reportService.update(report);
        return "redirect:/reports";
    }

}
