package com.example.demo.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "phone")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(min = 6, max = 100)
    private String phone_name;

    @NotBlank
    @Size(min = 6, max = 100)
    private String price;

    @NotBlank
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date date_release;

    private String phone_type;

    private String size;

    private String screen_size;

    private String color;

    private String battery;

    private String camera_front;

    private String camera_back;

    private String weight;

    private String chip_set;

    private String gpu;

    private String gps;

    private String sound;

    private String connect;

    private String memory;

    private String screen_resolution;

    private String ram;

    private String rom;

    private String wifi;

    private String fourth_generation;

    private String bluetooth;

    private String jack_phone;

    private String type_design;

    private String material;

    private String cpu_speed;

    @OneToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @OneToOne
    @JoinColumn(name = "operating_system_id")
    private Operating_system operating_system;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "phone_supplier",
            joinColumns = @JoinColumn(name = "phone_id"),
            inverseJoinColumns = @JoinColumn(name = "supplier_id"))
    private Set<Supplier> supplier = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @OneToMany
    @JoinColumn(name = "comment_id")
    private Collection<Comment> comment;

}
