package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Customer;

import java.util.List;

public interface CustomerDao {

    Customer selectByName(String company);

    int add(Customer customer);

    List<String> getCustomerName(String name);

    Customer getCustomerIdByName(String customerName);
}
