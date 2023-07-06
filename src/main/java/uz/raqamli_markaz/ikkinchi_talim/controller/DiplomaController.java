package uz.raqamli_markaz.ikkinchi_talim.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.raqamli_markaz.ikkinchi_talim.model.request.DiplomaRequest;
import uz.raqamli_markaz.ikkinchi_talim.model.response.DiplomaResponse;
import uz.raqamli_markaz.ikkinchi_talim.model.response.Result;
import uz.raqamli_markaz.ikkinchi_talim.service.ApplicationService;
import uz.raqamli_markaz.ikkinchi_talim.service.DiplomaService;

import java.security.Principal;

@RestController
@RequestMapping("/api/diplomaAndApp/")
@RequiredArgsConstructor
public class DiplomaController {

    private final DiplomaService diplomaService;
    private final ApplicationService applicationService;

    @PostMapping("createApplication")
    public ResponseEntity<?> createApplication(@RequestParam(value = "token") String token,
                                           @RequestParam(value = "kvotaId") Integer kvotaId) {
        Result result = applicationService.createApplication(token, kvotaId);
        return ResponseEntity.status(result.isSuccess() ? 201 : 400).body(result);
    }    @PostMapping("createDiploma")
    public ResponseEntity<?> createDiploma(@RequestParam(value = "token") String token,
                                           @RequestBody DiplomaRequest request) {
        Result result = diplomaService.createDiploma(token, request);
        return ResponseEntity.status(result.isSuccess() ? 201 : 400).body(result);
    }

    @PutMapping("updateApplication")
    public ResponseEntity<?> updateApplication(@RequestParam(value = "token") String token,
                                           @RequestParam(value = "kvotaId") Integer kvotaId) {
        Result result = applicationService.updateApplication(token, kvotaId);
        return ResponseEntity.status(result.isSuccess() ? 200 : 400).body(result);
    }

    @PutMapping("updateDiploma")
    public ResponseEntity<?> updateDiploma(@RequestParam(value = "token") String token,
                                           @RequestBody DiplomaRequest request) {
        Result result = diplomaService.updateDiploma(token, request);
        return ResponseEntity.status(result.isSuccess() ? 200 : 400).body(result);
    }
    @DeleteMapping("deleteDiploma/{id}")
    public ResponseEntity<?> deleteDiploma(@PathVariable Integer id,
                                           @RequestParam(value = "token") String token) {
        Result result = diplomaService.deleteDiploma(id, token);
        return ResponseEntity.status(result.isSuccess() ? 200 : 400).body(result);
    }


    @GetMapping("getDiplomaByPrincipal/{diplomaId}")
    public ResponseEntity<?> getAllDiplomaByPrincipal(@PathVariable Integer diplomaId,
                                                      @RequestParam(value = "token") String token) {
        Result diplomaResponse = diplomaService.getDiplomaByPrincipal(diplomaId, token);
        return ResponseEntity.status(diplomaResponse.isSuccess() ? 200 : 404).body(diplomaResponse);
    }

    @PatchMapping("diplomaIsActive/{diplomaId}")
    public ResponseEntity<?> diplomaIsActive(@PathVariable Integer diplomaId,
                                             @RequestParam(value = "token") String token,
                                             @RequestParam(value = "isActive") Boolean isActive) {
        Result result = diplomaService.diplomaIsActive(token, diplomaId, isActive);
        return ResponseEntity.status(result.isSuccess() ? 200 : 400).body(result);
    }

    @GetMapping("getDiplomaApi")
    public ResponseEntity<?> saveDiplomaApi(@RequestParam(value = "token") String token) {
        Result result = diplomaService.saveAndGetDiplomaByDiplomaApi(token);
        return ResponseEntity.status(result.isSuccess() ? 200 : 400).body(result);
    }

    @PostMapping("changeDiplomaStatusApi")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @PreAuthorize("hasRole('MODERATOR')") //shohijahon uchun
    public ResponseEntity<?> changeDiplomaStatusApi(@RequestParam(value = "diplomaId") Integer diplomaId,
                                                    @RequestParam(value = "statusId") Integer statusId,
                                                    @RequestParam(value = "statusName") String statusName) {
        Result result = diplomaService.changeDiplomaStatusApi(diplomaId, statusId, statusName);
        return ResponseEntity.status(result.isSuccess() ? 200 : 400).body(result);
    }

}
