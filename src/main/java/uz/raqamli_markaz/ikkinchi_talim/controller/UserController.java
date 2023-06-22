package uz.raqamli_markaz.ikkinchi_talim.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.raqamli_markaz.ikkinchi_talim.model.request.DiplomaRequest;
import uz.raqamli_markaz.ikkinchi_talim.model.response.DiplomaResponse;
import uz.raqamli_markaz.ikkinchi_talim.model.response.Result;
import uz.raqamli_markaz.ikkinchi_talim.service.DiplomaService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/enrolleeInfo/")
@RequiredArgsConstructor
@SecurityRequirement(name = "second")
public class UserController {

    //    private final UserService userService;
    private final DiplomaService diplomaService;

//    @GetMapping
//    public ResponseEntity<?> getUserInfo(Principal principal) {
//        EnrolleeResponse enrolleeResponse = userService.getEnrolleeResponse(principal);
//        return ResponseEntity.ok(enrolleeResponse);
//    }

    @PostMapping("createDiploma")
    public ResponseEntity<?> createDiploma(Principal principal,
                                           @RequestBody DiplomaRequest request) {
        Result result = diplomaService.createDiploma(principal, request);
        return ResponseEntity.status(result.isSuccess() ? 201 : 400).body(result);
    }

    @GetMapping("getDiplomaByDARXIV")
    public ResponseEntity<?> getDiplomaByDARXIV(Principal principal) {
        Result result = diplomaService.saveAndGetDiplomaByDiplomaApi(principal);
        return ResponseEntity.status(result.isSuccess() ? 201 : 400).body(result);
    }


    @PutMapping("{diplomaId}")
    public ResponseEntity<?> updateDiploma(Principal principal,
                                           @RequestBody DiplomaRequest request) {
        Result result = diplomaService.updateDiploma(principal, request);
        return ResponseEntity.status(result.isSuccess() ? 200 : 400).body(result);
    }

//    @GetMapping("diplomas/profile")
//    public ResponseEntity<?> getDiplomaProfile(Principal principal) {
//        DiplomaResponse diplomaByIdAndEnrolleInfo = userService.getDiplomaProfile(principal);
//        return ResponseEntity.ok(diplomaByIdAndEnrolleInfo);
//    }

    @DeleteMapping("deleteDiploma/{id}")
    public ResponseEntity<?> deleteDiploma(@PathVariable Integer id, Principal principal) {
        Result result = diplomaService.deleteDiploma(id, principal);
        return ResponseEntity.status(result.isSuccess() ? 200 : 400).body(result);
    }

}
