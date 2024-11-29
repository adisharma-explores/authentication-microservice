package com.ecommerce.authentication_service.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "customer_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @Column(name = "id")
    private int customerId;

    @Column(name = "phone_number")
    private Long customerPhoneNumber;

    private String customerUserName;

    @ManyToMany
    private List<Role> role = new ArrayList<>();

    private String customerPassword;

}
