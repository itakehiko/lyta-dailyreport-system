package com.techacademy.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "reports")
@SQLRestriction("delete_flg = false")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private LocalDate reportDate;

    @Column(length = 100, nullable = false)
    @NotEmpty
    private String title;

    @Column(columnDefinition = "LONGTEXT")
    @NotEmpty
    private String content;

    @ManyToOne
    @JoinColumn(name = "employee_code")
    @NotNull
    private Employee employee;

    @Column(columnDefinition = "TINYINT", nullable = false)
    private boolean deleteFlg;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

}
