package uz.raqamli_markaz.ikkinchi_talim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.raqamli_markaz.ikkinchi_talim.api.diplom_api.DiplomaApi;
import uz.raqamli_markaz.ikkinchi_talim.model.response.FutureInstitutionResponse;
import uz.raqamli_markaz.ikkinchi_talim.model.response.UniversityResponse;
import uz.raqamli_markaz.ikkinchi_talim.service.FutureInstitutionService;
import java.util.List;

@RestController
@RequestMapping("/api/public/")
@RequiredArgsConstructor
public class PublicController {

    private final DiplomaApi diplomaApi;
    private final FutureInstitutionService futureInstitutionService;

    @GetMapping("getUniversities")
    public ResponseEntity<?> getUniversities() {
        List<UniversityResponse> universities = diplomaApi.getUniversities();
        return ResponseEntity.status(!universities.isEmpty() ? 200 : 404).body(universities);
    }

    @GetMapping("futureInstitution")
    public ResponseEntity<?> getAllFutureInstitution() {
        List<FutureInstitutionResponse> allFutureInstitution = futureInstitutionService.getAllFutureInstitution();
        return ResponseEntity.ok(allFutureInstitution);
    }




}
