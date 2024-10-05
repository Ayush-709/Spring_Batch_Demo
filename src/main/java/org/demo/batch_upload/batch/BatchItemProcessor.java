package org.demo.batch_upload.batch;

import org.demo.batch_upload.entity.Customer;
import org.demo.batch_upload.entity.CustomerReadDTO;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class BatchItemProcessor implements ItemProcessor<CustomerReadDTO, Customer> {
    @Override
    public Customer process(CustomerReadDTO item) throws Exception {
        Customer customer = new Customer();
        customer.setFullName(item.getFullName());
        customer.setEmailId(item.getEmailId());
        customer.setDateOfBirth(new SimpleDateFormat("dd-MM-yyyy").parse(item.getDateOfBirth()));
        customer.setAddress(item.getAddress());
        customer.setMobileNumber(Long.valueOf(item.getMobileNumber()));
        return customer;
    }
}
