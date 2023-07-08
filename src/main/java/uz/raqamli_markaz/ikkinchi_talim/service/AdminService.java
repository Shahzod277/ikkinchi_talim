package uz.raqamli_markaz.ikkinchi_talim.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.DiplomaApi;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi.Citizen;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi.CreateDiplomaRequest;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi.CreateDiplomaResponse;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi.DiplomaRequestApi;
import uz.raqamli_markaz.ikkinchi_talim.domain.Application;
import uz.raqamli_markaz.ikkinchi_talim.domain.User;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.Diploma;
import uz.raqamli_markaz.ikkinchi_talim.model.response.ApplicationResponse;
import uz.raqamli_markaz.ikkinchi_talim.model.response.DiplomaResponse;
import uz.raqamli_markaz.ikkinchi_talim.model.response.ResponseMessage;
import uz.raqamli_markaz.ikkinchi_talim.model.response.Result;
import uz.raqamli_markaz.ikkinchi_talim.repository.*;
import java.security.Principal;

@Service
@AllArgsConstructor
public class AdminService {

    private final DiplomaRepository diplomaRepository;
    private final DiplomaOldInstitutionRepository diplomaOldInstitutionRepository;
    private final UniversityRepository universityRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final DiplomaApi diplomaApi;

    @Transactional
    public Result confirmDiploma(Principal principal, Integer diplomaId) {
        try {
            User user = userRepository.findUserByPinfl(principal.getName()).get();
            Integer institutionDbId = user.getDiplomaOldInstitution().getId();
            Diploma diploma = diplomaRepository.findDiplomaByInstitution(institutionDbId, diplomaId).get();
            diploma.setStatusId(3);
            diploma.setStatusName("Diplom tasdiqlangan");
            diplomaRepository.save(diploma);
            DiplomaRequestApi diplomaRequestApi = new DiplomaRequestApi(diploma);
            Citizen citizen = new Citizen(diploma.getUser());
            CreateDiplomaRequest createDiplomaRequest = new CreateDiplomaRequest(diplomaRequestApi, citizen);
            CreateDiplomaResponse response = diplomaApi.createDiploma(createDiplomaRequest);
            return new Result(ResponseMessage.SUCCESSFULLY.getMessage(), true);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(ResponseMessage.ERROR.getMessage(), false);
        }
    }

    @Transactional(readOnly = true)
    public Page<DiplomaResponse> getAllDiplomaByUAdmin(Principal principal, int page, int size) {
            if (page > 0) page = page - 1;
            Pageable pageable = PageRequest.of(page, size);
            User user = userRepository.findUserByPinfl(principal.getName()).get();
            Integer institutionDbId = user.getDiplomaOldInstitution().getId();
           return diplomaRepository.findAllDiplomaByInstitution(institutionDbId, pageable)
                   .map(DiplomaResponse::new);
    }

    @Transactional
    public Result confirmApplication(Principal principal, Integer applicationId, String message) {
        try {
            User user = userRepository.findUserByPinfl(principal.getName()).get();
            String universityCode = user.getUniversity().getCode();
            Application application = applicationRepository
                    .findApplicationByUniversityAndId(universityCode, applicationId).get();
            //diplomni statusiga qarab check qilish kerak
            application.setApplicationStatus("Ariza tasdiqlandi");
            application.setApplicationMessage(message);
            applicationRepository.save(application);
            return new Result(ResponseMessage.SUCCESSFULLY.getMessage(), true);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(ResponseMessage.ERROR.getMessage(), false);
        }
    }

    @Transactional(readOnly = true)
    public Page<ApplicationResponse> getAllApplicationByUAdmin(Principal principal, int page, int size) {
        if (page > 0) page = page - 1;
        Pageable pageable = PageRequest.of(page, size);
        User user = userRepository.findUserByPinfl(principal.getName()).get();
        String universityCode = user.getUniversity().getCode();
        return applicationRepository.findAllApplicationByUniversity(universityCode, pageable)
                .map(a -> new ApplicationResponse(a, a.getUser()));
    }
}
