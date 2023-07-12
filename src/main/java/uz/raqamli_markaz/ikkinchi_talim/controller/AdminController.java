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
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('UADMIN')")
    public ResponseEntity<?> confirmDiploma(Principal principal, @RequestBody ConfirmDiplomaRequest request) {
        Result result = adminService.confirmDiploma(principal, request);
        return ResponseEntity.status(result.isSuccess() ? 200 : 400).body(result);
    }

    @GetMapping("diplomaByUAdmin")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('UADMIN')")
    public ResponseEntity<?> getAllDiplomaByUAdmin(Principal principal,
                                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "size", defaultValue = "20") int size,
                                                   @RequestParam(value = "status") String status,
                                                   @RequestParam(value = "search",defaultValue = "null") String search) {
        Page<DiplomaResponseProjection> responses = adminService.getAllDiplomaByUAdmin(principal, page, size, status,search);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("diplomaForeignByUAdmin")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('UADMIN')")
    public ResponseEntity<?> getAllDiplomaForeignByUAdmin(Principal principal,
                                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "size", defaultValue = "20") int size,
                                                   @RequestParam(value = "status") String status,
                                                   @RequestParam(value = "search",defaultValue = "null") String search) {
        Page<DiplomaResponseProjection> responses = adminService.getAllDiplomaForeignByUAdmin(principal, page, size, status,search);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("getDiplomaByIdUAdmin/{diplomaId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('UADMIN')")
    public ResponseEntity<?> getDiplomaByIdUAdmin(Principal principal, @PathVariable Integer diplomaId) {
        DiplomaResponse response = adminService.getDiplomaByIdUAdmin(principal, diplomaId);
        return ResponseEntity.status(response != null ? 200 : 404).body(response);
    }

    @PostMapping("confirmApplication/{applicationId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('UADMIN')")
    public ResponseEntity<?> confirmApplication(Principal principal,
                                                @RequestBody ConfirmAppRequest request) {
        Result result = adminService.confirmApplication(principal, request);
        return ResponseEntity.status(result.isSuccess() ? 200 : 400).body(result);
    }

    @GetMapping("getAllApplicationByUAdmin")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('UADMIN')")
    public ResponseEntity<?> getAllApplicationByUAdmin(Principal principal,
                                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                                       @RequestParam(value = "size", defaultValue = "20") int size,
                                                       @RequestParam(value = "status") String status,
                                                       @RequestParam(value = "search", defaultValue = "null") String search
                                                       ) {
        Page<AppResponseProjection> responses = adminService.getAllApplicationByUAdmin(principal, page, size, status,search);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("getApplicationByIdUAdmin/{applicationId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('UADMIN')")
    public ResponseEntity<?> getApplicationByIdUAdmin(Principal principal, @PathVariable Integer applicationId) {
        ApplicationResponse response = adminService.getApplicationByIdUAdmin(principal, applicationId);
        return ResponseEntity.status(response != null ? 200 : 404).body(response);
    }

}
