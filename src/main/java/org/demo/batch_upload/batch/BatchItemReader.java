package org.demo.batch_upload.batch;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.demo.batch_upload.entity.CustomerReadDTO;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
@CommonsLog
public class BatchItemReader implements ItemReader<CustomerReadDTO> {

    private Iterator<Row> rowIterator;
    private Iterator<String[]> csvRowIterator;
    private boolean isCSV;


    public void setIterator(InputStream inputStream, String fileType) throws IOException, CsvException {
        if (StringUtils.equalsAnyIgnoreCase(fileType, ".csv")) {
            try (CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream))) {
                List<String[]> csvRecords = csvReader.readAll();
                this.csvRowIterator = csvRecords.iterator();
                if (csvRowIterator.hasNext()) {
                    csvRowIterator.next(); // Skip header
                }
                isCSV = true;
            }
        } else if (StringUtils.equalsAnyIgnoreCase(fileType, ".xlsx", ".xls")) {
            try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream);) {
                XSSFSheet sheet = workbook.getSheetAt(0);
                this.rowIterator = sheet.iterator();
                if (rowIterator.hasNext()) {
                    rowIterator.next();
                }
            }
        }
    }


    @Override
    public CustomerReadDTO read() throws Exception{
        return isCSV ? createCustomerFromCSV() : createCustomerFromExcel();
    }

    private CustomerReadDTO createCustomerFromCSV() throws java.text.ParseException {
        if (csvRowIterator != null && csvRowIterator.hasNext()) {
            String[] csvRecord = csvRowIterator.next();
            CustomerReadDTO customer = new CustomerReadDTO();
            customer.setFullName(csvRecord[1]); // Assuming name is in column 1
            customer.setMobileNumber(csvRecord[2]); // Assuming mobile is in column 2
            customer.setEmailId(csvRecord[3]); // Assuming email is in column 3
            customer.setAddress(csvRecord[4]); // Assuming address is in column 4
            customer.setDateOfBirth(csvRecord[5]);
            return customer;
        }
        return null;
    }


    private CustomerReadDTO createCustomerFromExcel() {
        if (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            CustomerReadDTO customer = new CustomerReadDTO();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                switch (cell.getColumnIndex()) {
                    case 1:
                        String name = cell.getStringCellValue();
                        customer.setFullName(name);
                        break;
                    case 2:
                        String mobile = cell.getStringCellValue();
                        customer.setMobileNumber(mobile);
                        break;
                    case 3:
                        String email = cell.getStringCellValue();
                        customer.setEmailId(email);
                        break;
                    case 4:
                        String address = cell.getStringCellValue();
                        customer.setAddress(address);
                        break;
                    case 5:
                        Date dateOfBirth = cell.getDateCellValue();
                        customer.setDateOfBirth(new SimpleDateFormat("dd-MM-yyyy").format(dateOfBirth));
                        break;
                    default:
                }
            }
            return customer;
        }
        return null;
    }
}
