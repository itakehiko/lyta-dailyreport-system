package com.techacademy.repository;

import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import  org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

    List<Report> findByReportDateAndEmployee(LocalDate reportDate, Employee employee);

    List<Report> findByEmployee(Employee employee);

}
