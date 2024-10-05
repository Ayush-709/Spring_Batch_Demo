package org.demo.batch_upload.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.demo.batch_upload.entity.Customer;
import org.demo.batch_upload.entity.CustomerRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@CommonsLog
@Component
@RequiredArgsConstructor
public class BatchItemWriter implements ItemWriter<Customer> {
    private final CustomerRepository customerRepository;

    @Override
    public void write(Chunk<? extends Customer> chunk) throws Exception {
        customerRepository.saveAll(chunk);
    }
}
