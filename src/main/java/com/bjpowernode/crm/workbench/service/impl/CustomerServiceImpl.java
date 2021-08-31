package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.dao.CustomerDao;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;

    @Override
    public List<String> getCustomerName(String name) {
        List<String> strings = customerDao.getCustomerName(name);
        return strings;
    }

    @Override
    public Customer getCustomerIdByName(String customerName) {
        Customer customer = customerDao.getCustomerIdByName(customerName);
        return customer;
    }
}
