package uz.raqamli_markaz.ikkinchi_talim.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uz.raqamli_markaz.ikkinchi_talim.domain.User;
import uz.raqamli_markaz.ikkinchi_talim.model.response.AppResponseProjection;
import uz.raqamli_markaz.ikkinchi_talim.model.response.DiplomaResponseProjection;
import uz.raqamli_markaz.ikkinchi_talim.repository.ApplicationRepository;
import uz.raqamli_markaz.ikkinchi_talim.repository.DiplomaRepository;
import uz.raqamli_markaz.ikkinchi_talim.repository.UserRepository;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExcelHelper {

    private final DiplomaRepository diplomaRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] DIPLOMA_HEADERS = { "Id", "Speciality", "Diploma Number and Serial", "Full Name", "Phone Number", "Institution Name" };
    static String[] APP_HEADERS = { "Id", "Speciality", "Full Name", "Phone Number", "University", "Create Date" };
    static String SHEET = "Report";

    @Transactional
    public ByteArrayInputStream loadReportsToExcel(Principal principal, String status, String key) {

        User user = userRepository.findUserByPinfl(principal.getName()).get();
        if (key.equals("nationalDiploma")){
            if (status.equals("null")) {
                return reportsDiplomasToExcel(diplomaRepository.allDiplomaToExcel(user.getDiplomaInstitutionId()));
            }
            List<DiplomaResponseProjection> projections = diplomaRepository.allDiplomaToExcelByStatus(user.getDiplomaInstitutionId(), status);
            return reportsDiplomasToExcel(projections);
        }
        else if (key.equals("foreignDiploma")) {
            if (status.equals("null")) {
               return reportsDiplomasToExcel(diplomaRepository.allForeignDiplomaToExcel(user.getUniversityCode()));
            }
            List<DiplomaResponseProjection> projections = diplomaRepository.allForeignDiplomaToExcelByStatus(user.getUniversityCode(), status);
            return reportsDiplomasToExcel(projections);
        }
        else if (key.equals("application")){
            if (status.equals("null")) {
               return reportsAppsToExcel(applicationRepository.applicationToExcel(user.getUniversityCode()));
            }
            return reportsAppsToExcel(applicationRepository.applicationToExcelByStatus(user.getUniversityCode(), status));
        }
        return null;
    }

    private ByteArrayInputStream reportsDiplomasToExcel(List<DiplomaResponseProjection> responseProjections) {

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET);
            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < DIPLOMA_HEADERS.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(DIPLOMA_HEADERS[col]);
            }
            int rowIdx = 1;
            for (DiplomaResponseProjection responses : responseProjections) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(responses.getId() == null? 0 : responses.getId());
                row.createCell(1).setCellValue(responses.getSpeciality());
                row.createCell(2).setCellValue(responses.getDiplomaAndSerial());
                row.createCell(3).setCellValue(responses.getFullName());
                row.createCell(4).setCellValue(responses.getPhoneNumber());
                row.createCell(5).setCellValue(responses.getInstitutionName());
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    private ByteArrayInputStream reportsAppsToExcel(List<AppResponseProjection> appResponseProjections) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET);
            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < APP_HEADERS.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(APP_HEADERS[col]);
            }
            int rowIdx = 1;
            for (AppResponseProjection responses : appResponseProjections) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(responses.getId() == null? 0 : responses.getId());
                row.createCell(1).setCellValue(responses.getSpeciality());
                row.createCell(2).setCellValue(responses.getFullName());
                row.createCell(3).setCellValue(responses.getPhoneNumber());
                row.createCell(4).setCellValue(responses.getUniversity());
                row.createCell(5).setCellValue(responses.getCreateDate().format(dateTimeFormatter));
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}
