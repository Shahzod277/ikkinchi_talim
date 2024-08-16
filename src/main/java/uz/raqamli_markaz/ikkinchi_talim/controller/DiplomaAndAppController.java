package uz.raqamli_markaz.ikkinchi_talim.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.raqamli_markaz.ikkinchi_talim.model.request.DiplomaAndIlovaRequest;
import uz.raqamli_markaz.ikkinchi_talim.model.request.DiplomaRequest;
import uz.raqamli_markaz.ikkinchi_talim.model.response.Result;
import uz.raqamli_markaz.ikkinchi_talim.service.ApplicationService;
import uz.raqamli_markaz.ikkinchi_talim.service.DiplomaService;

@RestController
@RequestMapping("/api/diplomaAndApp/")
@RequiredArgsConstructor
public class DiplomaAndAppController {

    private final DiplomaService diplomaService;
    private final ApplicationService applicationService;

    @PostMapping("createApplication")
    public ResponseEntity<?> createApplication(@RequestParam(value = "token") String token,
                                               @RequestParam(value = "kvotaId") Integer kvotaId) {
        Result result = applicationService.createApplication(token, kvotaId);
        return ResponseEntity.status(result.isSuccess() ? 201 : 400).body(result);
    }

    @PostMapping("createDiploma")
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

    @PutMapping("addDiplomaAndIlova")
    public ResponseEntity<?> addDiplomaAndIlova(@RequestParam(value = "token") String token,
                                               @RequestBody DiplomaAndIlovaRequest request) {
        Result result = diplomaService.addDiplomaAndIlova(token, request);
        return ResponseEntity.status(result.isSuccess() ? 200 : 400).body(result);
    }

    @PutMapping("updateDiploma")
    public ResponseEntity<?> updateDiploma(@RequestParam(value = "token") String token,
                                           @RequestParam(value = "id") Integer id,
                                           @RequestBody DiplomaRequest request) {
        Result result = diplomaService.updateDiploma(token, id, request);
        return ResponseEntity.status(result.isSuccess() ? 200 : 400).body(result);
    }

    @DeleteMapping("deleteDiploma/{id}")
    public ResponseEntity<?> deleteDiploma(@PathVariable Integer id,
                                           @RequestParam(value = "token") String token) {
        Result result = diplomaService.deleteDiploma(id, token);
        return ResponseEntity.status(result.isSuccess() ? 200 : 400).body(result);
    }

    @PatchMapping("diplomaIsActive/{diplomaId}")
    public ResponseEntity<?> diplomaIsActive(@PathVariable Integer diplomaId,
                                             @RequestParam(value = "token") String token) {
        Result result = diplomaService.diplomaIsActive(token, diplomaId);
        return ResponseEntity.status(result.isSuccess() ? 200 : 400).body(result);
    }

//    @GetMapping("getDiplomaApi")
//    public ResponseEntity<?> saveDiplomaApi(@RequestParam(value = "token") String token) throws Exception {
//        Result result = diplomaService.saveAndGetDiplomaByDiplomaApi(token);
//        return ResponseEntity.status(result.isSuccess() ? 200 : 400).body(result);
//    }

    @GetMapping("applicationDetails")
    public ResponseEntity<?> getApplicationByPrincipal(@RequestParam(value = "token") String token) throws Exception {
        Result result = applicationService.getApplicationByPrincipal(token);
        return ResponseEntity.status(result.isSuccess() ? 200 : 400).body(result);
    }


}
