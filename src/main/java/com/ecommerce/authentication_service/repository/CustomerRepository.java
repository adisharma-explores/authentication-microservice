package com.ecommerce.authentication_service.repository;


import com.ecommerce.authentication_service.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>
{
    Customer findByCustomerUserName(String customerUserName);
}
