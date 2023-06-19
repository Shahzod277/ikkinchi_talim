package uz.raqamli_markaz.ikkinchi_talim.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.raqamli_markaz.ikkinchi_talim.model.response.DiplomaResponse;
import uz.raqamli_markaz.ikkinchi_talim.model.response.Result;
import uz.raqamli_markaz.ikkinchi_talim.service.UserService;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/enrolleeInfo/")
@RequiredArgsConstructor
@SecurityRequirement(name = "second")
public class EnrolleeInfoController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getEnrolleInfo(Principal principal) {
        EnrolleeResponse enrolleeResponse = userService.getEnrolleeResponse(principal);
        return ResponseEntity.ok(enrolleeResponse);
    }

 /*@PostMapping
    public ResponseEntity<?> createDiploma(Principal principal,
                                           @RequestParam(value = "countryName", required = false) String countryName,
                                           @RequestParam(value = "institutionId", required = false) Integer institutionId,
                                           @RequestParam(value = "id", required = false) Integer id,
                                           @RequestParam(value = "eduFormName", required = false) String eduFormName,
                                           @RequestParam(value = "eduFinishingDate", required = false) String eduFinishingDate,
                                           @RequestParam(value = "speciality", required = false) String speciality,
                                           @RequestParam(value = "diplomaNumberAndSerial", required = false) String diplomaNumberAndSerial,
                                           @RequestParam(value = "diploma", required = false) MultipartFile diploma,
                                           @RequestParam(value = "diplomaIlova", required = false) MultipartFile diplomaIlova) {
        Result result = enrolleeService.createDiploma(principal, countryName,
                institutionId, id, eduFormName, eduFinishingDate, speciality,
                diplomaNumberAndSerial, diploma, diplomaIlova);
        return ResponseEntity.status(result.isSuccess() ? 201 : 400).body(result);
    }

    @PostMapping("foreignDiploma")
    public ResponseEntity<?> createForeignDiploma(Principal principal,
                                                  @RequestParam(value = "countryName", required = false) String countryName,
                                                  @RequestParam(value = "institutionName", required = false) String institutionName,
                                                  @RequestParam(value = "eduFormName", required = false) String eduFormName,
                                                  @RequestParam(value = "eduFinishingDate", required = false) String eduFinishingDate,
                                                  @RequestParam(value = "speciality", required = false) String speciality,
                                                  @RequestParam(value = "diplomaNumberAndSerial", required = false) String diplomaNumberAndSerial,
                                                  @RequestParam(value = "diploma", required = false) MultipartFile diploma,
                                                  @RequestParam(value = "diplomaIlova", required = false) MultipartFile diplomaIlova) {
        DiplomaResponse response = enrolleeService.createForeignDiploma(principal, countryName,
                institutionName, eduFormName, eduFinishingDate, speciality,
                diplomaNumberAndSerial, diploma, diplomaIlova);
        return ResponseEntity.ok(response);
    }

    @PutMapping("update/foreignDiploma/{diplomaId}")
    public ResponseEntity<?> updateForeignDiploma(@PathVariable Integer diplomaId,
                                                  @RequestParam(value = "countryName", required = false) String countryName,
                                                  @RequestParam(value = "institutionName", required = false) String institutionName,
                                                  @RequestParam(value = "eduFormName", required = false) String eduFormName,
                                                  @RequestParam(value = "eduFinishingDate", required = false) String eduFinishingDate,
                                                  @RequestParam(value = "speciality", required = false) String speciality,
                                                  @RequestParam(value = "diplomaNumberAndSerial", required = false) String diplomaNumberAndSerial,
                                                  @RequestParam(value = "diplomaCopyId", required = false) Integer diplomaCopyId,
                                                  @RequestParam(value = "diploma", required = false) MultipartFile diploma,
                                                  @RequestParam(value = "diplomaIlovaId", required = false) Integer diplomaIlovaId,
                                                  @RequestParam(value = "diplomaIlova", required = false) MultipartFile diplomaIlova) {
        DiplomaResponse response = enrolleeService.updateForeignDiploma(diplomaId, countryName,
                institutionName, eduFormName, eduFinishingDate, speciality,
                diplomaNumberAndSerial, diplomaCopyId, diploma, diplomaIlovaId, diplomaIlova);
        return ResponseEntity.ok(response);
    }

    @PutMapping("{diplomaId}")
    public ResponseEntity<?> updateDiploma(Principal principal,
                                           @PathVariable int diplomaId,
                                           @RequestParam(value = "countryName", required = false) String countryName,
                                           @RequestParam(value = "institutionId", required = false) Integer institutionId,
                                           @RequestParam(value = "id", required = false) Integer id,
                                           @RequestParam(value = "eduFormName", required = false) String eduFormName,
                                           @RequestParam(value = "eduFinishingDate", required = false) String eduFinishingDate,
                                           @RequestParam(value = "speciality", required = false) String speciality,
                                           @RequestParam(value = "diplomaNumberAndSerial", required = false) String diplomaNumberAndSerial,
                                           @RequestParam(value = "diplomaCopyId", required = false) Integer diplomaCopyId,
                                           @RequestParam(value = "diploma", required = false) MultipartFile diploma,
                                           @RequestParam(value = "diplomaIlovaId", required = false) Integer diplomaIlovaId,
                                           @RequestParam(value = "diplomaIlova", required = false) MultipartFile diplomaIlova) {
        Result result = enrolleeService.updateDiploma(principal, diplomaId, countryName, institutionId, id, eduFormName, eduFinishingDate,
                speciality, diplomaNumberAndSerial, diplomaCopyId, diploma, diplomaIlovaId, diplomaIlova);
        return ResponseEntity.status(result.isSuccess() ? 200 : 400).body(result);
    }*/

    @PatchMapping("check/diplomas/{diplomaId}")
    public ResponseEntity<?> checkDiploma(Principal principal, @PathVariable int diplomaId) {
        Result result = userService.checkDiploma(principal, diplomaId);
        return ResponseEntity.status(result.isSuccess() ? 200 : 400).body(result);
    }

    @GetMapping("diplomas")
    public ResponseEntity<?> getDiplomas(Principal principal) {
        List<DiplomaResponse> diplomasByEnrolleeInfo = userService.getDiplomasByEnrolleeInfo(principal);
        return ResponseEntity.ok(diplomasByEnrolleeInfo);
    }

    @GetMapping("diplomas/{diplomaId}")
    public ResponseEntity<?> getDiplomaById(Principal principal, @PathVariable int diplomaId) {
        DiplomaResponse diplomaByIdAndEnrolleInfo = userService.getDiplomaByIdAndEnrolleInfo(principal, diplomaId);
        return ResponseEntity.ok(diplomaByIdAndEnrolleInfo);
    }

    @GetMapping("diplomas/profile")
    public ResponseEntity<?> getDiplomaProfile(Principal principal) {
        DiplomaResponse diplomaByIdAndEnrolleInfo = userService.getDiplomaProfile(principal);
        return ResponseEntity.ok(diplomaByIdAndEnrolleInfo);
    }

    @DeleteMapping("deleteDiploma/{dipplomaId}")
    public ResponseEntity<?> deleteDiploma(@PathVariable Integer dipplomaId, Principal principal) {
        Result result = userService.deleteDiploma(dipplomaId, principal);
        return ResponseEntity.status(result.isSuccess() ? 200 : 400).body(result);
    }
}
