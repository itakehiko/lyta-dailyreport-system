package com.techacademy.service;

import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.repository.ReportRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    // 日報新規登録
    @Transactional
    public Report create(Report report) {
        return reportRepository.save(report);
    }

    // 日報更新
    @Transactional
    public Report update(Report report) {
        return reportRepository.save(report);
    }

    // 日報削除
    @Transactional
    public void delete(Integer id) {
        Report report = findById(id);
        report.setDeleteFlg(true);
        reportRepository.save(report);
    }

    // 日報一覧
	public List<Report> findAll() {
        return reportRepository.findAll();
    }

    public List<Report> findMyReports(String code) {
        List<Report> list = reportRepository.findAll();

        for (int i =0; i < list.size(); i++) {
            if (list.get(i).getEmployee().getCode() != code) {
                list.remove(i);
            }
        }

        return list;
    }

    public List<Report> findByEmployee(Employee employee) {
        List<Report> list = reportRepository.findByEmployee(employee);
        return list;
    }

    // 1件を検索
    public Report findById(Integer id) {
        Optional<Report> option = reportRepository.findById(id);
        Report report = option.orElse(null);
        return report;
    }

    public List<Report> findByReportDateAndEmployee(LocalDate reportDate, Employee employee) {
        List<Report> list = reportRepository.findByReportDateAndEmployee(reportDate, employee);
        return list;
    }

    public List<Report> findByReportDateAndEmployeeUpdate(LocalDate reportDate, Employee employee, Integer id) {
        List<Report> list = reportRepository.findByReportDateAndEmployee(reportDate, employee);

        for (int i =0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                list.remove(i);
            }
        }

        return list;
    }

}
