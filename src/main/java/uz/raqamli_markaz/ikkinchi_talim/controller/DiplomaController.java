package uz.raqamli_markaz.ikkinchi_talim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.raqamli_markaz.ikkinchi_talim.model.request.DiplomaRequest;
import uz.raqamli_markaz.ikkinchi_talim.model.response.DiplomaResponse;
import uz.raqamli_markaz.ikkinchi_talim.model.response.Result;
import uz.raqamli_markaz.ikkinchi_talim.service.DiplomaService;
import java.security.Principal;

@RestController
@RequestMapping("/api/diploma/")
@RequiredArgsConstructor
public class DiplomaController {

    private final DiplomaService diplomaService;

    @PostMapping("createDiploma")
    public ResponseEntity<?> createDiploma(@RequestParam(value = "pinfl") String pinfl,
                                           Principal principal,
                                           @RequestBody DiplomaRequest request) {
        Result result = diplomaService.createDiploma(principal, request);
        return ResponseEntity.status(result.isSuccess() ? 201 : 400).body(result);
    }

    @PutMapping("updateDiploma")
    public ResponseEntity<?> updateDiploma(@RequestParam(value = "pinfl") String pinfl,
                                           Principal principal,
                                           @RequestBody DiplomaRequest request) {
        Result result = diplomaService.updateDiploma(principal, request);
        return ResponseEntity.status(result.isSuccess() ? 200 : 400).body(result);
    }

    @DeleteMapping("deleteDiploma")
    public ResponseEntity<?> deleteDiploma(@PathVariable Integer id,
                                           Principal principal) {
        Result result = diplomaService.deleteDiploma(id, principal);
        return ResponseEntity.status(result.isSuccess() ? 200 : 400).body(result);
    }

    @GetMapping("allDiplomaByPrincipal")
    public ResponseEntity<?> getAllDiplomaByPrincipal(@RequestParam(value = "pinfl") String pinfl) {
        return ResponseEntity.ok( diplomaService.getAllDiplomaByPrincipal(pinfl));
    }

    @GetMapping("getDiplomaByPrincipal/{diplomaId}")
    public ResponseEntity<?> getAllDiplomaByPrincipal(@PathVariable Integer diplomaId,
                                                      @RequestParam(value = "pinfl") String pinfl) {
        DiplomaResponse diplomaResponse = diplomaService.getDiplomaByPrincipal(diplomaId, pinfl);
        return ResponseEntity.status(diplomaResponse != null ? 200 : 404).body(diplomaResponse);
    }

    @PatchMapping("diplomaIsActive/{diplomaId}")
    public ResponseEntity<?> diplomaIsActive(@PathVariable Integer diplomaId,
                                             @RequestParam(value = "pinfl") String pinfl,
                                             @RequestParam(value = "isActive") Boolean isActive) {
        Result result = diplomaService.diplomaIsActive(pinfl, diplomaId, isActive);
        return ResponseEntity.status(result.isSuccess() ? 200 : 400).body(result);
    }

    @PostMapping("saveDiplomaApi")
    public ResponseEntity<?> saveDiplomaApi(@RequestParam(value = "pinfl") String pinfl,
                                           Principal principal) {
        Result result = diplomaService.saveAndGetDiplomaByDiplomaApi(principal);
        return ResponseEntity.status(result.isSuccess() ? 200 : 400).body(result);
    }

    @PostMapping("changeDiplomaStatusApi")
    public ResponseEntity<?> changeDiplomaStatusApi(@RequestParam(value = "diplomaId") Integer diplomaId,
                                                    @RequestParam(value = "statusId") Integer statusId,
                                                    @RequestParam(value = "statusName") String statusName) {
        Result result = diplomaService.changeDiplomaStatusApi(diplomaId, statusId, statusName);
        return ResponseEntity.status(result.isSuccess() ? 200 : 400).body(result);
    }

}
