package uz.raqamli_markaz.ikkinchi_talim.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.raqamli_markaz.ikkinchi_talim.model.request.*;
import uz.raqamli_markaz.ikkinchi_talim.model.response.*;
import uz.raqamli_markaz.ikkinchi_talim.service.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/admin/")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("confirmDiploma/{diplomaId}")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @PreAuthorize("hasRole('UADMIN')")
    public ResponseEntity<?> confirmDiploma(Principal principal, @PathVariable Integer diplomaId) {
        Result result = adminService.confirmDiploma(principal, diplomaId);
        return ResponseEntity.status(result.isSuccess() ? 200 : 400).body(result);
    }

    @GetMapping("getAllDiplomaByUAdmin")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @PreAuthorize("hasRole('UADMIN')")
    public ResponseEntity<?> getAllDiplomaByUAdmin(Principal principal,
                                            @RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "size", defaultValue = "20") int size) {
        Page<DiplomaResponse> responses = adminService.getAllDiplomaByUAdmin(principal, page, size);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("getDiplomaByIdUAdmin/{diplomaId}")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @PreAuthorize("hasRole('UADMIN')")
    public ResponseEntity<?> getDiplomaByIdUAdmin(Principal principal, @PathVariable Integer diplomaId) {
        DiplomaResponse response = adminService.getDiplomaByIdUAdmin(principal, diplomaId);
        return ResponseEntity.status(response != null ? 200 : 404).body(response);
    }

    @PostMapping("confirmApplication/{applicationId}")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @PreAuthorize("hasRole('UADMIN')")
    public ResponseEntity<?> confirmApplication(Principal principal,
                                                @PathVariable Integer applicationId,
                                                @RequestParam(value = "message") String message) {
        Result result = adminService.confirmApplication(principal, applicationId, message);
        return ResponseEntity.status(result.isSuccess() ? 200 : 400).body(result);
    }

    @GetMapping("getAllApplicationByUAdmin")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @PreAuthorize("hasRole('UADMIN')")
    public ResponseEntity<?> getAllApplicationByUAdmin(Principal principal,
                                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "size", defaultValue = "20") int size) {
        Page<ApplicationResponse> responses = adminService.getAllApplicationByUAdmin(principal, page, size);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("getApplicationByIdUAdmin/{applicationId}")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @PreAuthorize("hasRole('UADMIN')")
    public ResponseEntity<?> getApplicationByIdUAdmin(Principal principal, @PathVariable Integer applicationId) {
        ApplicationResponse response = adminService.getApplicationByIdUAdmin(principal, applicationId);
        return ResponseEntity.status(response != null ? 200 : 404).body(response);
    }

}
