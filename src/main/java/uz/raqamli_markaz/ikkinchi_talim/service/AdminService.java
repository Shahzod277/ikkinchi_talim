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
import uz.raqamli_markaz.ikkinchi_talim.model.response.*;
import uz.raqamli_markaz.ikkinchi_talim.repository.*;
import java.security.Principal;

@Service
@AllArgsConstructor
public class AdminService {

    private final DiplomaRepository diplomaRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final DiplomaApi diplomaApi;

    @Transactional
    public Result confirmDiploma(Principal principal, Integer diplomaId) {
        try {
            User user = userRepository.findUserByPinfl(principal.getName()).get();
            Integer institutionId = user.getDiplomaInstitution().getClassificatorId();
            Diploma diploma = diplomaRepository.findDiplomaByInstitutionAndId(institutionId, diplomaId).get();
            diploma.setStatusId(1);
            diploma.setStatusName("Tasdiqlangan");//d arxivni statusi
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
    public Page<DiplomaResponse> getAllDiplomaByUAdmin(Principal principal, int page, int size, String status) {
            if (page > 0) page = page - 1;
            Pageable pageable = PageRequest.of(page, size);
            User user = userRepository.findUserByPinfl(principal.getName()).get();
            Integer institutionId = user.getDiplomaInstitution().getClassificatorId();
           return diplomaRepository.findAllDiplomaByInstitution(institutionId, status, pageable)
                   .map(DiplomaResponse::new);
    }

    @Transactional(readOnly = true)
    public DiplomaResponse getDiplomaByIdUAdmin(Principal principal, Integer diplomaId) {
        User user = userRepository.findUserByPinfl(principal.getName()).get();
        Integer institutionId = user.getDiplomaInstitution().getClassificatorId();
        return diplomaRepository
                .findDiplomaByInstitutionAndId(institutionId, diplomaId)
                .map(DiplomaResponse::new).orElse(null);
    }

    @Transactional
    public Result confirmApplication(Principal principal, Integer applicationId, String message) {
        try {
            User user = userRepository.findUserByPinfl(principal.getName()).get();
            String universityCode = user.getUniversity().getCode();
            Application application = applicationRepository
                    .findApplicationByUniversityAndId(universityCode, applicationId).get();
            User appUser = application.getUser();
            Diploma diploma = diplomaRepository.findActiveDiplomaByUser(appUser.getId()).get();
            if (diploma.getStatusId()== 1) {
                application.setApplicationStatus("Ariza tasdiqlandi");
                application.setApplicationMessage(message);
                applicationRepository.save(application);
                return new Result(ResponseMessage.SUCCESSFULLY.getMessage(), true);
            }
            return new Result("Diplom tasdiqlanmagan ", false);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(ResponseMessage.ERROR.getMessage(), false);
        }
    }

    @Transactional(readOnly = true)
    public Page<ApplicationResponse> getAllApplicationByUAdmin(Principal principal, int page, int size, String status) {
        if (page > 0) page = page - 1;
        Pageable pageable = PageRequest.of(page, size);
        User user = userRepository.findUserByPinfl(principal.getName()).get();
        String universityCode = user.getUniversity().getCode();
        return applicationRepository.findAllApplicationByUniversity(universityCode, status, pageable)
                .map(a -> new ApplicationResponse(a, a.getUser()));
    }

    @Transactional(readOnly = true)
    public ApplicationResponse getApplicationByIdUAdmin(Principal principal, Integer applicationId) {
        User user = userRepository.findUserByPinfl(principal.getName()).get();
        String universityCode = user.getUniversity().getCode();
        Application application = applicationRepository
                .findApplicationByUniversityAndId(universityCode, applicationId).get();
        User appUser = application.getUser();
        Diploma diploma = diplomaRepository.findActiveDiplomaByUser(appUser.getId()).get();
        ApplicationResponse response = new ApplicationResponse();
        response.setId(application.getId());
        response.setStatus(application.getApplicationStatus());
        response.setMessage(application.getApplicationMessage());
        response.setKvota(application.getKvota());
        response.setUserResponse(new UserResponse(appUser));
        response.setDiplomaResponse(new DiplomaResponse(diploma));
        return response;
    }
}
