package org.demo.batch_upload.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReadDTO {

    private String fullName;
    private String mobileNumber;
    private String emailId;
    private String address;
    private String dateOfBirth;
}
