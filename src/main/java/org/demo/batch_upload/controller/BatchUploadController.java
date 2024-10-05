package org.demo.batch_upload.controller;


import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.demo.batch_upload.batch.BatchItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class BatchUploadController {

    private final JobLauncher jobLauncher;
    private final Job job;
    private final BatchItemReader itemReader;

    @GetMapping("/upload-excel")
    public ResponseEntity<String> uploadExcel(@RequestPart MultipartFile file) {
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String ext = fileName.substring(fileName.lastIndexOf("."));
        if (!StringUtils.equalsAnyIgnoreCase(ext, ".xlsx", ".xls", ".csv")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong file format!");
        }
        try {
            itemReader.setIterator(file.getInputStream(), ext);

            jobLauncher.run(job, new JobParametersBuilder()
                    .addLong("startAt", System.currentTimeMillis())
                    .toJobParameters());

            return ResponseEntity.ok("SUCCESS");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
