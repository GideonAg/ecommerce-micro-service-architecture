package com.redeemerlives.ecommerce.customer;

import com.redeemerlives.ecommerce.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public String createCustomer(CustomerRequest request) {
        Customer customer = customerRepository.save(customerMapper.toCustomer(request));
        return customer.getId();
    }

    public void updateCustomer(CustomerRequest request) {
        Customer customer = customerRepository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        "No customer found with ID:: " + request.id())
                );
        mergeCustomer(customer, request);
        customerRepository.save(customer);
    }

    private void mergeCustomer(Customer customer, CustomerRequest request) {
        if (StringUtils.isNotBlank(request.firstname()))
            customer.setFirstname(request.firstname());

        if (StringUtils.isNotBlank(request.lastname()))
            customer.setLastname(request.lastname());

        if (StringUtils.isNotBlank(request.email()))
            customer.setEmail(request.email());

        if (request.address() != null)
            customer.setAddress(request.address());
    }

    public List<CustomerResponse> findAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customerMapper::fromCustomer)
                .toList();
    }

    public Boolean customerExist(String customerId) {
        return customerRepository.findById(customerId).isPresent();
    }

    public CustomerResponse findCustomerById(String customerId) {
        return customerRepository.findById(customerId)
                .map(customerMapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException("No customer found with ID:: " + customerId));
    }

    public void deleteCustomerById(String customerId) {
        customerRepository.deleteById(customerId);
    }
}
