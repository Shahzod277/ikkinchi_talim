package uz.raqamli_markaz.ikkinchi_talim.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uz.raqamli_markaz.ikkinchi_talim.domain.User;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.University;
import uz.raqamli_markaz.ikkinchi_talim.model.response.AppResponseProjection;
import uz.raqamli_markaz.ikkinchi_talim.model.response.DiplomaResponseProjection;
import uz.raqamli_markaz.ikkinchi_talim.model.response.ReportsAppsFullExcel;
import uz.raqamli_markaz.ikkinchi_talim.repository.ApplicationRepository;
import uz.raqamli_markaz.ikkinchi_talim.repository.DiplomaRepository;
import uz.raqamli_markaz.ikkinchi_talim.repository.UniversityRepository;
import uz.raqamli_markaz.ikkinchi_talim.repository.UserRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExcelHelper {
    private final UniversityRepository universityRepository;
    private final DiplomaRepository diplomaRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] DIPLOMA_HEADERS = {"Id", "Speciality", "EduForm", "Diploma Number and Serial", "Full Name", "Phone Number", "Institution Name"};
    static String[] APP_HEADERS = {"Id", "Speciality", "Full Name", "Phone Number", "University", "Create Date", "edu form", "edu language","diploma serial number","diploma speciality","diploma edu form","diploma degree","diploma given date"};
    static String[] APP_LAST = {"Speciality", "Full Name", "Phone Number", "University", "Create Date", "edu form", "edu language", "pinfl", "passport_serial", "passport_number","given_date"};

    static String SHEET = "Report";

    @Transactional
    public ByteArrayInputStream loadReportsToExcel(Principal principal, String status, String key) throws IOException {

        User user = userRepository.findUserByPinfl(principal.getName()).get();
        if (!user.getRole().getName().equals("ROLE_ADMIN")) {
            switch (key) {
                case "nationalDiploma" -> {
                    if (status.equals("null")) {
                        return reportsDiplomasToExcel(diplomaRepository.allDiplomaToExcel(user.getDiplomaInstitutionId()));
                    }
                    List<DiplomaResponseProjection> projections = diplomaRepository.allDiplomaToExcelByStatus(user.getDiplomaInstitutionId(), status);
                    return reportsDiplomasToExcel(projections);
                }
                case "foreignDiploma" -> {
                    if (status.equals("null")) {
                        return reportsDiplomasToExcel(diplomaRepository.allForeignDiplomaToExcel(user.getUniversityCode()));
                    }
                    List<DiplomaResponseProjection> projections = diplomaRepository.allForeignDiplomaToExcelByStatus(user.getUniversityCode(), status);
                    return reportsDiplomasToExcel(projections);
                }
                case "application" -> {
                    if (status.equals("null")) {
                        return reportsAppsToExcel(applicationRepository.applicationToExcel(user.getUniversityCode()));
                    }
                    return reportsAppsToExcel(applicationRepository.applicationToExcelByStatus(user.getUniversityCode(), status));
                }

                default -> {
                    return null;
                }
            }
        }

        // ADMIN ////////////////
        switch (key) {
            case "nationalDiploma" -> {
                if (status.equals("null")) {
                    return reportsDiplomasToExcel(diplomaRepository.allDiplomaToExcelAdmin());
                }
                List<DiplomaResponseProjection> projections = diplomaRepository.allDiplomaToExcelByStatusAdmin(status);
                return reportsDiplomasToExcel(projections);
            }
            case "foreignDiploma" -> {
                if (status.equals("null")) {
                    return reportsDiplomasToExcel(diplomaRepository.allForeignDiplomaToExcelAdmin());
                }
                List<DiplomaResponseProjection> projections = diplomaRepository.allForeignDiplomaToExcelByStatusAdmin(status);
                return reportsDiplomasToExcel(projections);
            }
            case "application" -> {
                if (status.equals("null")) {
                    return reportsAppsToExcel(applicationRepository.applicationToExcelAdmin());
                }
                return reportsAppsToExcel(applicationRepository.applicationToExcelByStatusAdmin(status));
            }
            default -> {
                return null;
            }
        }
    }

    public ReportsAppsFullExcel getFullApp(String code) throws IOException {
        return reportsAppsFullToExcel(applicationRepository.applicationToExcelLast(code));
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
                row.createCell(0).setCellValue(responses.getId() == null ? 0 : responses.getId());
                row.createCell(1).setCellValue(responses.getSpeciality());
                row.createCell(2).setCellValue(responses.getTalimShakli());
                row.createCell(3).setCellValue(responses.getDiplomaAndSerial());
                row.createCell(4).setCellValue(responses.getFullName());
                row.createCell(5).setCellValue(responses.getPhoneNumber());
                row.createCell(6).setCellValue(responses.getInstitutionName());
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    private ByteArrayInputStream reportsAppsToExcel(List<AppResponseProjection> appResponseProjections) throws IOException {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

//        try () {
            Workbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
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
                row.createCell(0).setCellValue(responses.getId() == null ? 0 : responses.getId());
                row.createCell(1).setCellValue(responses.getSpeciality());
                row.createCell(2).setCellValue(responses.getFullName());
                row.createCell(3).setCellValue(responses.getPhoneNumber());
                row.createCell(4).setCellValue(responses.getUniversity());
                row.createCell(5).setCellValue(responses.getCreateDate().format(dateTimeFormatter));
                if (responses.getEduForm() != null) {
                    row.createCell(6).setCellValue(responses.getEduForm());
                }
                if (responses.getLang() != null) {
                    row.createCell(7).setCellValue(responses.getLang());
                }
                if (responses.getDiplomaEduForm() != null) {
                    row.createCell(8).setCellValue(responses.getDiplomaSerialNumber());
                }
                if (responses.getDiplomaSpeciality() != null) {
                    row.createCell(9).setCellValue(responses.getDiplomaSpeciality());
                }
                if (responses.getDiplomaSerialNumber() != null) {
                    row.createCell(10).setCellValue(responses.getDiplomaEduForm());
                }
                if (responses.getDiplomaDegree() != null) {
                    row.createCell(11).setCellValue(responses.getDiplomaDegree());
                }
                if (responses.getDiplomaGivenDate() != null) {
                    row.createCell(12).setCellValue(responses.getDiplomaGivenDate());
                }
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
//        } catch (IOException e) {
//            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
//        }
    }

    private ReportsAppsFullExcel reportsAppsFullToExcel(List<AppResponseProjection> appResponseProjections) throws IOException {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String univerName = null;
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET);
            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < APP_LAST.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(APP_LAST[col]);
            }
            int rowIdx = 1;
            for (AppResponseProjection responses : appResponseProjections) {
                Row row = sheet.createRow(rowIdx++);
                univerName = responses.getUniversity();
                row.createCell(0).setCellValue(responses.getSpeciality());
                row.createCell(1).setCellValue(responses.getFullName());
                row.createCell(2).setCellValue(responses.getPhoneNumber());
                row.createCell(3).setCellValue(responses.getUniversity());
                row.createCell(4).setCellValue(responses.getCreateDate().format(dateTimeFormatter));

                if (responses.getEduForm() != null) {
                    row.createCell(5).setCellValue(responses.getEduForm());
                }
                if (responses.getEduForm() != null) {
                    row.createCell(6).setCellValue(responses.getLang());
                }
                row.createCell(7).setCellValue(responses.getPinfl());
                row.createCell(8).setCellValue(responses.getPassportSerial());
                row.createCell(9).setCellValue(responses.getPassportNumber());
                if (responses.getGivenDate() != null) {
                    row.createCell(10).setCellValue(responses.getGivenDate());
                }
            }
            workbook.write(out);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(out.toByteArray());
            return new ReportsAppsFullExcel(byteArrayInputStream, univerName);
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}
