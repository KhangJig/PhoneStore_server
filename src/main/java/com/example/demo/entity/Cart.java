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
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String content;

    @NotBlank
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date created_date;

    @NotBlank
    private String transport_fee;

    @NotBlank
    private String total_price;

    @NotBlank
    private String address;

    private String note;

    @NotBlank
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date received_date;

    @NotBlank
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date ship_date;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @OneToOne
    @JoinColumn(name = "status_cart_id")
    private StatusCart statusCart;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;
}
