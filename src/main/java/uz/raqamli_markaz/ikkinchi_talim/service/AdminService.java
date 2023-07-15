package uz.raqamli_markaz.ikkinchi_talim.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import uz.raqamli_markaz.ikkinchi_talim.api.my_edu.CreateAppRequestMyEdu;
import uz.raqamli_markaz.ikkinchi_talim.api.my_edu.MyEduApiService;
import uz.raqamli_markaz.ikkinchi_talim.domain.Application;
import uz.raqamli_markaz.ikkinchi_talim.domain.User;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.Diploma;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.University;
import uz.raqamli_markaz.ikkinchi_talim.model.request.ConfirmAppRequest;
import uz.raqamli_markaz.ikkinchi_talim.model.request.ConfirmDiplomaRequest;
import uz.raqamli_markaz.ikkinchi_talim.model.response.*;
import uz.raqamli_markaz.ikkinchi_talim.repository.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminService {

    private final DiplomaRepository diplomaRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final MyEduApiService myEduApiService;
    private final UserService userService;
    private final UniversityRepository universityRepository;

    @Transactional
    public Result confirmDiploma(Principal principal, ConfirmDiplomaRequest request)  {
        try {

        User user = userRepository.findUserByPinfl(principal.getName()).get();
        if (request.getIsNational() == 1) {

            Diploma diploma = diplomaRepository.findDiplomaByInstitutionAndId(user.getDiplomaInstitutionId(), request.getDiplomaId()).get();
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
            Application save = applicationRepository.save(application);
            String encode = userService.encode(save.getUser().getPinfl());
            CreateAppRequestMyEdu requestMyEdu = new CreateAppRequestMyEdu();
            requestMyEdu.setExternalId(save.getId().toString());
            requestMyEdu.setStatus(save.getApplicationStatus());
            requestMyEdu.setData(save.getKvota());
            myEduApiService.updateApp(encode, requestMyEdu);
            return new Result(ResponseMessage.SUCCESSFULLY.getMessage(), true);
        }
        Diploma diploma = diplomaRepository.findDiplomaBykvotaUniverCodeAndId(user.getUniversityCode(), request.getDiplomaId()).get();
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
        Application save = applicationRepository.save(application);
        String encode = userService.encode(save.getUser().getPinfl());
        CreateAppRequestMyEdu requestMyEdu = new CreateAppRequestMyEdu();
        requestMyEdu.setExternalId(save.getId().toString());
        requestMyEdu.setStatus(save.getApplicationStatus());
        requestMyEdu.setData(save.getKvota());
        myEduApiService.updateApp(encode, requestMyEdu);
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


        if (search.equals("null")) {
            return diplomaRepository.getAllDiplomaByStatus(user.getDiplomaInstitutionId(), status, pageable);
        }
        return diplomaRepository.getAllDiplomaSearch(user.getDiplomaInstitutionId(), status, search, pageable);
    }


    @Transactional(readOnly = true)
    public Page<DiplomaResponseProjection> getAllDiplomaForeignByUAdmin(Principal principal, int page, int size, String status, String search) {
        if (page > 0) page = page - 1;
        Pageable pageable = PageRequest.of(page, size);
        User user = userRepository.findUserByPinfl(principal.getName()).get();

        if (search.equals("null")) {
            return diplomaRepository.getAllForeignDiplomaByStatus(user.getUniversityCode(), status, pageable);
        }
        return diplomaRepository.getAllForeignDiplomaSearch(user.getUniversityCode(), status, search, pageable);
    }

    @Transactional(readOnly = true)
    public DiplomaResponse getDiplomaByIdUAdmin(Principal principal, Integer diplomaId) {
        Optional<Diploma> diploma = diplomaRepository
                .findById(diplomaId);
        DiplomaResponse diplomaResponse = diploma.map(DiplomaResponse::new).get();
        diplomaResponse.setUserResponse(new UserResponse(diploma.get().getUser()));
        return diplomaResponse;
    }


    @Transactional
    public Result confirmApplication(Principal principal, ConfirmAppRequest request) {
        try {
            User user = userRepository.findUserByPinfl(principal.getName()).get();
            Application application = applicationRepository
                    .findApplicationByUniversityAndId(user.getUniversityCode(), request.getApplicationId()).get();
            if (request.getIsConfirm() == 1 && application.getApplicationStatus().equals("Diplom Tasdiqlangan")) {
                application.setApplicationStatus("Ariza tasdiqlandi");
                application.setApplicationMessage(request.getMessage());
                Application save = applicationRepository.save(application);
                String encode = userService.encode(save.getUser().getPinfl());
                CreateAppRequestMyEdu requestMyEdu = new CreateAppRequestMyEdu();
                requestMyEdu.setExternalId(save.getId().toString());
                requestMyEdu.setStatus(save.getApplicationStatus());
                requestMyEdu.setData(save.getKvota());
                myEduApiService.updateApp(encode, requestMyEdu);
                return new Result(ResponseMessage.SUCCESSFULLY.getMessage(), true);
            } else if (request.getIsConfirm() == 0 && application.getApplicationStatus().equals("Diplom Tasdiqlangan")) {
                application.setApplicationStatus("Ariza rad etildi");
                application.setApplicationMessage(request.getMessage());
                Application save = applicationRepository.save(application);
                String encode = userService.encode(save.getUser().getPinfl());
                CreateAppRequestMyEdu requestMyEdu = new CreateAppRequestMyEdu();
                requestMyEdu.setExternalId(save.getId().toString());
                requestMyEdu.setStatus(save.getApplicationStatus());
                requestMyEdu.setData(save.getKvota());
                myEduApiService.updateApp(encode, requestMyEdu);
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
        if (search.equals("null")) {
            return applicationRepository.findAllApplicationByUniversity(user.getUniversityCode(), status, pageable);
        }
        return applicationRepository.findAllSearchApplicationByUniversity(user.getUniversityCode(), status, search, pageable);
    }

    @Transactional(readOnly = true)
    public ApplicationResponse getApplicationByIdUAdmin(Principal principal, Integer applicationId) {
        User user = userRepository.findUserByPinfl(principal.getName()).get();

        Application application = applicationRepository
                .findApplicationByUniversityAndId(user.getUniversityCode(), applicationId).get();
        User appUser = application.getUser();
        Diploma diploma = diplomaRepository.findActiveDiplomaByUser(appUser.getId()).get();
        ApplicationResponse response = new ApplicationResponse();
        response.setId(application.getId());
        response.setStatus(application.getApplicationStatus());
        response.setMessage(application.getApplicationMessage());
        response.setKvota(application.getKvota());
        response.setDiplomaResponse(new DiplomaResponse(diploma, new UserResponse(appUser)));
        return response;
    }

    /////////////////////................statistic
    @Transactional(readOnly = true)
    public StatisticCountUAdmin getStatistic(Principal principal) {
        StatisticCountUAdmin statisticCountUAdmin = new StatisticCountUAdmin();

        User user = userRepository.findUserByPinfl(principal.getName()).get();
        if (user.getUniversityCode() != null) {
            University university = universityRepository.findByCode(user.getUniversityCode()).get();
            statisticCountUAdmin.setUniversity(university.getName());
            statisticCountUAdmin.setFullName(user.getFullName());
        }
        List<DiplomaStatisticProjection> diplomaStatisticProjections = diplomaRepository.diplomaStatisticCount(user.getDiplomaInstitutionId());
        Map<String, Integer> diploma = new HashMap<>();
        diploma.put("Haqiqiyligi tekshirilmoqda", 0);
        diploma.put("Rad etildi", 0);
        diploma.put("Tasdiqlangan", 0);
        diploma.put("total", 0);

        diplomaStatisticProjections.forEach(d -> {
            diploma.put(d.getStatus(), d.getCount());
        });
        int sum = diploma.values().stream().mapToInt(d -> d).sum();
        diploma.put("total", sum);
        List<DiplomaStatisticProjection> appStatisticCount = applicationRepository.appStatisticCount(user.getUniversityCode());
        Map<String, Integer> app = new HashMap<>();
        app.put("Diplom Haqiqiyligi tekshirilmoqda", 0);
        app.put("Diplom Rad etildi", 0);
        app.put("Diplom Tasdiqlangan", 0);
        app.put("Ariza tasdiqlandi", 0);
        app.put("Ariza rad etildi", 0);
        app.put("total", 0);
        appStatisticCount.forEach(a -> {
            app.put(a.getStatus(), a.getCount());
        });
        int appSum = app.values().stream().mapToInt(d -> d).sum();
        app.put("total", appSum);

        List<DiplomaStatisticProjection> diplomaForeignStatisticCount = diplomaRepository.diplomaForeignStatisticCount(user.getUniversityCode());
        Map<String, Integer> diplomaForeign = new HashMap<>();
        diplomaForeign.put("Haqiqiyligi tekshirilmoqda", 0);
        diplomaForeign.put("Rad etildi", 0);
        diplomaForeign.put("Tasdiqlangan", 0);
        diplomaForeign.put("total", 0);
        diplomaForeignStatisticCount.forEach(df -> {
            diplomaForeign.put(df.getStatus(), df.getCount());
        });
        int appForeignSum = diplomaForeign.values().stream().mapToInt(d -> d).sum();
        diplomaForeign.put("total", appForeignSum);

        statisticCountUAdmin.setNationalDiploma(diploma);
        statisticCountUAdmin.setForeignDiploma(diplomaForeign);
        statisticCountUAdmin.setApplication(app);
        return statisticCountUAdmin;
    }
}
