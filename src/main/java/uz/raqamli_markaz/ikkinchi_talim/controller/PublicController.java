package uz.raqamli_markaz.ikkinchi_talim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.DiplomaApi;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaOldInstitution;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaSerial;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaSpeciality;
import uz.raqamli_markaz.ikkinchi_talim.model.response.FutureInstitutionResponse;
import uz.raqamli_markaz.ikkinchi_talim.model.response.UniversityResponse;
import uz.raqamli_markaz.ikkinchi_talim.service.ClasificatorService;
import uz.raqamli_markaz.ikkinchi_talim.service.DiplomaService;

import java.util.List;

@RestController
@RequestMapping("/api/public/")
@RequiredArgsConstructor
public class PublicController {

    private final DiplomaApi diplomaApi;
        private final DiplomaService diplomaService;
        private final ClasificatorService clasificatorService;


    @GetMapping("getAllOldNameInstitution")
    public ResponseEntity<?> getAllOldNameInstitution() {
        List<DiplomaOldInstitution> list = clasificatorService.getAllOldNameInstitution();
        return ResponseEntity.ok(list);
    }    @GetMapping("getAllDiplomaSerials")
    public ResponseEntity<?> getAllDiplomaSerials() {
        List<DiplomaSerial> list = clasificatorService.getAllDiplomaSerials();
        return ResponseEntity.ok(list);
    }
    @GetMapping("getAllOldNameInstitution/{classificatorId}")
    public ResponseEntity<?> getAllOldNameInstitution(@PathVariable Integer classificatorId) {
        List<DiplomaSpeciality> list = clasificatorService.getSpecialitiesByInstitutionId(classificatorId);
        return ResponseEntity.ok(list);
    }
    @GetMapping("countries")
    public ResponseEntity<?> getAllCountry() {
        return ResponseEntity.ok(diplomaService.getAllCountry());
    }




}
