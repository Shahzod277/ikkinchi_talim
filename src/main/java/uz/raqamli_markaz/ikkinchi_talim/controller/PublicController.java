package uz.raqamli_markaz.ikkinchi_talim.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaSerial;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaSpeciality;
import uz.raqamli_markaz.ikkinchi_talim.model.response.ReportsAppsFullExcel;
import uz.raqamli_markaz.ikkinchi_talim.model.response.ResponseMessage;
import uz.raqamli_markaz.ikkinchi_talim.model.response.Result;
import uz.raqamli_markaz.ikkinchi_talim.model.response.SpecialityProjection;
import uz.raqamli_markaz.ikkinchi_talim.service.ClassificatorService;
import uz.raqamli_markaz.ikkinchi_talim.service.ExcelHelper;
import uz.raqamli_markaz.ikkinchi_talim.service.FileServiceImpl;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/public/")
@RequiredArgsConstructor
public class PublicController {

    private final FileServiceImpl fileService;
    private final ClassificatorService classificatorService;
    private final ExcelHelper excelHelper;

    @GetMapping("allDiplomaSerials")
    public ResponseEntity<?> getAllDiplomaSerials() {
        List<DiplomaSerial> list = classificatorService.getAllDiplomaSerials();
        return ResponseEntity.ok(list);
    }

    @GetMapping("allSpecialitiesInstitution/{id}")
    public ResponseEntity<?> getAllSpecialitiesInstitution(@PathVariable Integer id) {
        List<SpecialityProjection> list = classificatorService.getSpecialitiesByInstitutionId(id);
        return ResponseEntity.ok(list);
    }

    @GetMapping("countries")
    public ResponseEntity<?> getAllCountry() {
        return ResponseEntity.ok(classificatorService.getAllCountry());
    }


    @GetMapping("allAppUnivesity")
    public ResponseEntity<?> getOtmByKvota() {
        return ResponseEntity.ok(classificatorService.getOtmByKvota());
    }

    @GetMapping("getEduFormApp/{otmCode}")
    public ResponseEntity<?> getEduForm(@PathVariable String otmCode) {
        return ResponseEntity.ok(classificatorService.getEduForm(otmCode));
    }

    @GetMapping("getLanguageApp/{eduFormCode}/{otmCode}")
    public ResponseEntity<?> getLanguageByOtmCodeAndEduFormCode(@PathVariable String eduFormCode,
                                                                @PathVariable String otmCode) {
        return ResponseEntity.ok(classificatorService.getLanguageByOtmCodeAndEduFormCode(eduFormCode, otmCode));
    }

    @GetMapping("getSpecialityApp/{eduFormCode}/{otmCode}/{languageCode}")
    public ResponseEntity<?> getSpeciality(@PathVariable String eduFormCode,
                                           @PathVariable String otmCode,
                                           @PathVariable String languageCode) {
        return ResponseEntity.ok(classificatorService.getSpeciality(languageCode, eduFormCode, otmCode));
    }

    @GetMapping("allDiplomaInstitutions")
    public ResponseEntity<?> getAllDiplomaInstitutions() {
        return ResponseEntity.ok(classificatorService.getAllDiplomaInstitutionResponse());
    }

    @GetMapping("getAllEduFormDiploma")
    public ResponseEntity<?> getAllEduFormDiploma() {
        return ResponseEntity.ok(classificatorService.getAllEduFormResponses());
    }


    @GetMapping("allDuration")
    public ResponseEntity<?> getAllDuration() {
        return ResponseEntity.ok(classificatorService.getAllDuration());
    }

    @PostMapping(value = "uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @RequestParam("key") String key) {
        return ResponseEntity.ok(fileService.upload(file, key));
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<?> downloadDiploma(@PathVariable String fileName) {
        try {
            Resource resource = fileService.download(fileName);
            HttpHeaders headers = new HttpHeaders();
            MediaType contType;
            headers.add("content-disposition", "inline; filename=" + resource.getFilename());
            if (Objects.requireNonNull(resource.getFilename()).endsWith("pdf") || Objects.requireNonNull(resource.getFilename()).endsWith("PDF")) {
                headers.setContentType(MediaType.parseMediaType("application/pdf"));
                headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
                contType = MediaType.APPLICATION_PDF;
            } else {
                contType = MediaType.IMAGE_JPEG;
            }
            return ResponseEntity.ok().contentType(contType).
                    headers(headers)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.ok(new Result(ResponseMessage.ERROR.getMessage(), false));
        }
    }

    @GetMapping("reportToExcel")
    public ResponseEntity<Resource> reportToExcel(@RequestParam(value = "universityCode") String universityCode) throws IOException {

        ReportsAppsFullExcel fullApp = excelHelper.getFullApp(universityCode);
        String filename = fullApp.getUniverName() + ".xlsx";
        InputStreamResource file = new InputStreamResource(fullApp.getByteArrayInputStream());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
    }


}
