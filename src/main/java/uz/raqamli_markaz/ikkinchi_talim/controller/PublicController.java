package uz.raqamli_markaz.ikkinchi_talim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaSerial;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaSpeciality;
import uz.raqamli_markaz.ikkinchi_talim.model.response.ResponseMessage;
import uz.raqamli_markaz.ikkinchi_talim.model.response.Result;
import uz.raqamli_markaz.ikkinchi_talim.service.ClassificatorService;
import uz.raqamli_markaz.ikkinchi_talim.service.FileServiceImpl;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/public/")
@RequiredArgsConstructor
public class PublicController {

    private final FileServiceImpl fileService;
    private final ClassificatorService classificatorService;

    @GetMapping("allDiplomaSerials")
    public ResponseEntity<?> getAllDiplomaSerials() {
        List<DiplomaSerial> list = classificatorService.getAllDiplomaSerials();
        return ResponseEntity.ok(list);
    }

    @GetMapping("allSpecialitiesInstitution/{classificatorId}")
    public ResponseEntity<?> getAllSpecialitiesInstitution(@PathVariable Integer classificatorId) {
        List<DiplomaSpeciality> list = classificatorService.getSpecialitiesByInstitutionId(classificatorId);
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
        return ResponseEntity.ok(classificatorService.getSpeciality(languageCode,eduFormCode, otmCode));
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


}
