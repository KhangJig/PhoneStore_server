package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String name;

    private String address;

    private String phone_number;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthday;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date date_begin;

    private String email;

    private String sex;

    @OneToOne
    @JoinColumn(name = "type_staff_id")
    private TypeStaff typeStaff;
}