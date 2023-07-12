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
import uz.raqamli_markaz.ikkinchi_talim.model.request.ConfirmAppRequest;
import uz.raqamli_markaz.ikkinchi_talim.model.request.ConfirmDiplomaRequest;
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
    public Result confirmDiploma(Principal principal, ConfirmDiplomaRequest request) {
        try {
            User user = userRepository.findUserByPinfl(principal.getName()).get();
            if (request.getIsNational() == 1) {
                Integer institutionId = user.getDiplomaInstitution().getClassificatorId();
                Diploma diploma = diplomaRepository.findDiplomaByInstitutionAndId(institutionId, request.getDiplomaId()).get();
                Integer userId = diploma.getUser().getId();
                Application application = applicationRepository.findByUserId(userId).get();
                if (request.getIsConfirm() == 1) {
                    diploma.setStatusId(1);
                    diploma.setStatusName("Tasdiqlangan");//d arxivni statusi
                    application.setApplicationStatus("Diplom Tasdiqlangan");
                } else {
                    diploma.setStatusName("Rad etildi");//d arxivni statusi
                    application.setApplicationStatus("Diplom Rad etildi");
                }
                diplomaRepository.save(diploma);
                applicationRepository.save(application);
                return new Result(ResponseMessage.SUCCESSFULLY.getMessage(), true);
            }
            String code = user.getUniversity().getCode();
            Diploma diploma = diplomaRepository.findDiplomaBykvotaUniverCodeAndId(code, request.getDiplomaId()).get();
            Integer userId = diploma.getUser().getId();
            Application application = applicationRepository.findByUserId(userId).get();
            if (request.getIsConfirm() == 1) {
                diploma.setStatusId(1);
                diploma.setStatusName("Tasdiqlangan");//d arxivni statusi
                application.setApplicationStatus("Diplom Tasdiqlangan");
            } else {
                diploma.setStatusName("Rad etildi");//d arxivni statusi
                application.setApplicationStatus("Diplom Rad etildi");
            }
            diplomaRepository.save(diploma);
            applicationRepository.save(application);
            return new Result(ResponseMessage.SUCCESSFULLY.getMessage(), true);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(ResponseMessage.ERROR.getMessage(), false);
        }
    }

    @Transactional(readOnly = true)
    public Page<DiplomaResponseProjection> getAllDiplomaByUAdmin(Principal principal, int page, int size, String status, String search) {
        if (page > 0) page = page - 1;
        Pageable pageable = PageRequest.of(page, size);
        User user = userRepository.findUserByPinfl(principal.getName()).get();
        Integer institutionId = user.getDiplomaInstitution().getClassificatorId();

        if (search.equals("null")) {
            return diplomaRepository.getAllDiplomaByStatus(institutionId, status, pageable);
        }
        return diplomaRepository.getAllDiplomaSearch(institutionId, status, search, pageable);
    }


    @Transactional(readOnly = true)
    public Page<DiplomaResponseProjection> getAllDiplomaForeignByUAdmin(Principal principal, int page, int size, String status, String search) {
        if (page > 0) page = page - 1;
        Pageable pageable = PageRequest.of(page, size);
        User user = userRepository.findUserByPinfl(principal.getName()).get();

        String code = user.getUniversity().getCode();
        if (search.equals("null")) {
            return diplomaRepository.getAllForeignDiplomaByStatus(code, status, pageable);
        }
        return diplomaRepository.getAllForeignDiplomaSearch(code, status, search, pageable);
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
    public Result confirmApplication(Principal principal, ConfirmAppRequest request) {
        try {
            User user = userRepository.findUserByPinfl(principal.getName()).get();
            String universityCode = user.getUniversity().getCode();
            Application application = applicationRepository
                    .findApplicationByUniversityAndId(universityCode, request.getApplicationId()).get();
            if (request.getIsConfirm() == 1 && application.getApplicationStatus().equals("Diplom Tasdiqlangan")) {
                application.setApplicationStatus("Ariza tasdiqlandi");
                application.setApplicationMessage(request.getMessage());
                applicationRepository.save(application);
                return new Result(ResponseMessage.SUCCESSFULLY.getMessage(), true);
            } else if (request.getIsConfirm() == 0 && application.getApplicationStatus().equals("Diplom Tasdiqlangan")) {
                application.setApplicationStatus("Ariza rad etildi");
                application.setApplicationMessage(request.getMessage());
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
    public Page<AppResponseProjection> getAllApplicationByUAdmin(Principal principal, int page, int size, String status, String search) {
        if (page > 0) page = page - 1;
        Pageable pageable = PageRequest.of(page, size);
        User user = userRepository.findUserByPinfl(principal.getName()).get();
        String universityCode = user.getUniversity().getCode();
        if (search.equals("null")) {
            return applicationRepository.findAllApplicationByUniversity(universityCode, status, pageable);
        }
        return applicationRepository.findAllSearchApplicationByUniversity(universityCode, status, search, pageable);
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
