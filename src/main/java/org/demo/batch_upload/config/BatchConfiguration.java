package org.demo.batch_upload.config;


import lombok.extern.apachecommons.CommonsLog;
import org.demo.batch_upload.batch.BatchItemProcessor;
import org.demo.batch_upload.batch.BatchItemReader;
import org.demo.batch_upload.batch.BatchItemWriter;
import org.demo.batch_upload.entity.Customer;
import org.demo.batch_upload.entity.CustomerReadDTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@CommonsLog
@EnableBatchProcessing
public class BatchConfiguration {

    @Bean
    public Job excelToDBJob(JobRepository jobRepository, Step stepForExcelToDB) {
        return new JobBuilder("excelToDBJob", jobRepository)
                .start(stepForExcelToDB)
                .build();
    }

    @Bean
    public Step stepForExcelToDB(JobRepository jobRepository,
                                 BatchItemReader itemReader,
                                 BatchItemWriter itemWriter,
                                 BatchItemProcessor processor,
                                 PlatformTransactionManager transactionManager) {
        return new StepBuilder("stepForExcelToDB", jobRepository)
                .<CustomerReadDTO, Customer>chunk(500, transactionManager)
                .reader(itemReader)
                .processor(processor)
                .writer(itemWriter)
                .build();
    }


}
