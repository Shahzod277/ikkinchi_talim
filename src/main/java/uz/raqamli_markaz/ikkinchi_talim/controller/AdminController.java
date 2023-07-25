package uz.raqamli_markaz.ikkinchi_talim.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.raqamli_markaz.ikkinchi_talim.model.request.*;
import uz.raqamli_markaz.ikkinchi_talim.model.response.*;
import uz.raqamli_markaz.ikkinchi_talim.service.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/admin/")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final ExcelHelper excelHelper;

    @PostMapping("confirmDiploma")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasAnyRole('UADMIN','ADMIN')")
    public ResponseEntity<?> confirmDiploma(Principal principal, @RequestBody ConfirmDiplomaRequest request) {
        Result result = adminService.confirmDiploma(principal, request);
        return ResponseEntity.status(result.isSuccess() ? 200 : 400).body(result);
    }

    @GetMapping("diplomaByUAdmin")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasAnyRole('UADMIN','ADMIN')")
    public ResponseEntity<?> getAllDiplomaByUAdmin(Principal principal,
                                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "size", defaultValue = "20") int size,
                                                   @RequestParam(value = "status") String status,
                                                   @RequestParam(value = "search", defaultValue = "null") String search) {
        Page<DiplomaResponseProjection> responses = adminService.getAllDiplomaByUAdmin(principal, page, size, status, search);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("diplomaForeignByUAdmin")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasAnyRole('UADMIN','ADMIN')")
    public ResponseEntity<?> getAllDiplomaForeignByUAdmin(Principal principal,
                                                          @RequestParam(value = "page", defaultValue = "0") int page,
                                                          @RequestParam(value = "size", defaultValue = "20") int size,
                                                          @RequestParam(value = "status") String status,
                                                          @RequestParam(value = "search", defaultValue = "null") String search) {
        Page<DiplomaResponseProjection> responses = adminService.getAllDiplomaForeignByUAdmin(principal, page, size, status, search);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("getDiplomaByIdUAdmin/{diplomaId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasAnyRole('UADMIN','ADMIN')")
    public ResponseEntity<?> getDiplomaByIdUAdmin(Principal principal, @PathVariable Integer diplomaId) {
        DiplomaResponse response = adminService.getDiplomaByIdUAdmin(principal, diplomaId);
        return ResponseEntity.status(response != null ? 200 : 404).body(response);
    }

    @PostMapping("confirmApplication")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasAnyRole('UADMIN','ADMIN')")
    public ResponseEntity<?> confirmApplication(Principal principal,
                                                @RequestBody ConfirmAppRequest request) {
        Result result = adminService.confirmApplication(principal, request);
        return ResponseEntity.status(result.isSuccess() ? 200 : 400).body(result);
    }

    @GetMapping("getAllApplicationByUAdmin")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasAnyRole('UADMIN','ADMIN')")
    public ResponseEntity<?> getAllApplicationByUAdmin(Principal principal,
                                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                                       @RequestParam(value = "size", defaultValue = "20") int size,
                                                       @RequestParam(value = "status") String status,
                                                       @RequestParam(value = "search", defaultValue = "null") String search
    ) {
        Page<AppResponseProjection> responses = adminService.getAllApplicationByUAdmin(principal, page, size, status, search);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("getApplicationByIdUAdmin/{applicationId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasAnyRole('UADMIN','ADMIN')")
    public ResponseEntity<?> getApplicationByIdUAdmin(Principal principal, @PathVariable Integer applicationId) {
        ApplicationResponse response = adminService.getApplicationByIdUAdmin(principal, applicationId);
        return ResponseEntity.status(response != null ? 200 : 404).body(response);
    }

    //////////////////////////statistic........................
    @GetMapping("getStatisticCount")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasAnyRole('UADMIN','ADMIN')")
    public ResponseEntity<?> getStatistic(Principal principal) {
        StatisticCountUAdmin statistic = adminService.getStatistic(principal);
        return ResponseEntity.ok(statistic);
    }
    @GetMapping("getAllDateAndGenderStatistic")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasAnyRole('UADMIN','ADMIN')")
    public ResponseEntity<?> getAllDateStatic(Principal principal) {
        CountAllDateStatistic statistic = adminService.getAllDateStatic(principal);
        return ResponseEntity.ok(statistic);
    }
    @GetMapping("allStatisticUniversity")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> allStatisticUniversity() {
        List<StatisticCountUAdmin> statistic = adminService.getAllUniversityStatistic();
        return ResponseEntity.ok(statistic);
    }

    @PatchMapping("updateDiplomaNumber")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('UADMIN')")
    public ResponseEntity<?> updateDiplomaNumber(Principal principal,
                                          @RequestParam(value = "diplomaId") int diplomaId,
                                          @RequestParam(value = "diplomaNumber") String diplomaNumber) {
        Result statistic = adminService.updateDiplomaNumber(principal, diplomaId, diplomaNumber);
        return ResponseEntity.status(statistic != null ? 200 : 404).body(statistic);
    }

    /////////////////////////// Export To Excel

    @GetMapping("reportToExcel")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasAnyRole('UADMIN','ADMIN')")
    public ResponseEntity<Resource> reportToExcel(Principal principal,
                                                  @RequestParam(value = "status", defaultValue = "null") String status,
                                                  @RequestParam(value = "key") String key) {
        String filename = "report.xlsx";
        InputStreamResource file = new InputStreamResource(excelHelper.loadReportsToExcel(principal, status, key));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
    }
}
