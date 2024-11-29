package com.ecommerce.authentication_service.service;


import com.ecommerce.authentication_service.model.Customer;

import com.ecommerce.authentication_service.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@Service
@Transactional
@Slf4j
@NoArgsConstructor
public class CustomerServiceImpl {

    @Autowired
    CustomerRepository Cr;
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    public Customer getUserByUsername(String username) throws RuntimeException {
        log.info("getting Customer by username");
        Customer customer = Cr.findByCustomerUserName(username);
        if (customer == null) {
            log.error("User not found");
            throw new RuntimeException("UserNot found");
        }
        return customer;
    }
}
