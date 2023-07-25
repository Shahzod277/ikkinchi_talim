package uz.raqamli_markaz.ikkinchi_talim.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaInstitution;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.University;
import uz.raqamli_markaz.ikkinchi_talim.model.request.ConfirmAppRequest;
import uz.raqamli_markaz.ikkinchi_talim.model.request.ConfirmDiplomaRequest;
import uz.raqamli_markaz.ikkinchi_talim.model.response.*;
import uz.raqamli_markaz.ikkinchi_talim.repository.*;

import javax.swing.plaf.PanelUI;
import java.security.Principal;
import java.util.*;

@Service
@AllArgsConstructor
public class AdminService {

    private final DiplomaRepository diplomaRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final MyEduApiService myEduApiService;
    private final UserService userService;
    private final UniversityRepository universityRepository;
    private final DiplomaOldInstitutionRepository diplomaOldInstitutionRepository;
    private final DiplomaInstitutionRepository diplomaInstitutionRepository;

    @Transactional
    public Result confirmDiploma(Principal principal, ConfirmDiplomaRequest request) {
        try {

            User user = userRepository.findUserByPinfl(principal.getName()).get();
            if (request.getIsNational() == 1) {

                if (!user.getRole().getName().equals("ROLE_ADMIN")) {
                    Diploma diploma = diplomaRepository.findDiplomaByInstitutionAndId(user.getDiplomaInstitutionId(), request.getDiplomaId()).get();
                    Integer userId = diploma.getUser().getId();
                    Application application = applicationRepository.findByUserId(userId).get();

                    if (request.getIsConfirm() == 1) {
                        diploma.setStatusId(1);
                        diploma.setStatusName("Tasdiqlangan");//d arxivni statusi
                        diplomaRepository.save(diploma);
                        application.setApplicationStatus("Diplom Tasdiqlangan");
                        application.setDiplomaMessage(request.getMessage());
                    } else {
                        diploma.setStatusName("Rad etildi");//d arxivni statusi
                        diplomaRepository.save(diploma);
                        application.setApplicationStatus("Diplom Rad etildi");
                        application.setDiplomaMessage(request.getMessage());
                    }

                    Application save = applicationRepository.save(application);
                    String encode = userService.encode(save.getUser().getPinfl());
                    CreateAppRequestMyEdu requestMyEdu = new CreateAppRequestMyEdu();
                    requestMyEdu.setExternalId(save.getId().toString());
                    requestMyEdu.setStatus(save.getApplicationStatus());
                    requestMyEdu.setData(save.getKvota());
                    myEduApiService.updateApp(encode, requestMyEdu);
                    return new Result(ResponseMessage.SUCCESSFULLY.getMessage(), true);
                }
                //Admin uchun
                Diploma diploma = diplomaRepository.findById(request.getDiplomaId()).get();
                Integer userId = diploma.getUser().getId();
                Application application = applicationRepository.findByUserId(userId).get();
                diploma.setStatusName("Rad etildi");//d arxivni statusi
                diplomaRepository.save(diploma);
                application.setApplicationStatus("Diplom Rad etildi");
                application.setDiplomaMessage(request.getMessage());
                Application save = applicationRepository.save(application);
                String encode = userService.encode(save.getUser().getPinfl());
                CreateAppRequestMyEdu requestMyEdu = new CreateAppRequestMyEdu();
                requestMyEdu.setExternalId(save.getId().toString());
                requestMyEdu.setStatus(save.getApplicationStatus());
                requestMyEdu.setData(save.getKvota());
                myEduApiService.updateApp(encode, requestMyEdu);
                return new Result(ResponseMessage.SUCCESSFULLY.getMessage(), true);
            }

            if (!user.getRole().getName().equals("ROLE_ADMIN")) {
                Diploma diploma = diplomaRepository.findDiplomaBykvotaUniverCodeAndId(user.getUniversityCode(), request.getDiplomaId()).get();
                Integer userId = diploma.getUser().getId();
                Application application = applicationRepository.findByUserId(userId).get();

                if (request.getIsConfirm() == 1) {
                    diploma.setStatusId(1);
                    diploma.setStatusName("Tasdiqlangan");//d arxivni statusi
                    diplomaRepository.save(diploma);
                    application.setApplicationStatus("Diplom Tasdiqlangan");
                    application.setDiplomaMessage(request.getMessage());
                } else {
                    diploma.setStatusName("Rad etildi");//d arxivni statusi
                    diplomaRepository.save(diploma);
                    application.setApplicationStatus("Diplom Rad etildi");
                    application.setDiplomaMessage(request.getMessage());
                }
                Application save = applicationRepository.save(application);
                String encode = userService.encode(save.getUser().getPinfl());
                CreateAppRequestMyEdu requestMyEdu = new CreateAppRequestMyEdu();
                requestMyEdu.setExternalId(save.getId().toString());
                requestMyEdu.setStatus(save.getApplicationStatus());
                requestMyEdu.setData(save.getKvota());
                myEduApiService.updateApp(encode, requestMyEdu);
                return new Result(ResponseMessage.SUCCESSFULLY.getMessage(), true);
            }
            //Admin uchun
            Diploma diploma = diplomaRepository.findById(request.getDiplomaId()).get();
            Integer userId = diploma.getUser().getId();
            Application application = applicationRepository.findByUserId(userId).get();
            diploma.setStatusName("Rad etildi");//d arxivni statusi
            diplomaRepository.save(diploma);
            application.setApplicationStatus("Diplom Rad etildi");
            application.setDiplomaMessage(request.getMessage());
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
        if (!user.getRole().getName().equals("ROLE_ADMIN")) {
            if (search.equals("null")) {
                return diplomaRepository.getAllDiplomaByStatus(user.getDiplomaInstitutionId(), status, pageable);
            }
            return diplomaRepository.getAllDiplomaSearch(user.getDiplomaInstitutionId(), status, search, pageable);
        }
        if (search.equals("null")) {
            return diplomaRepository.getAllDiplomaByStatusAdmin(status, pageable);
        }
        return diplomaRepository.getAllDiplomaSearchAdmin(status, search, pageable);

    }


    @Transactional(readOnly = true)
    public Page<DiplomaResponseProjection> getAllDiplomaForeignByUAdmin(Principal principal, int page, int size, String status, String search) {
        if (page > 0) page = page - 1;
        Pageable pageable = PageRequest.of(page, size);
        User user = userRepository.findUserByPinfl(principal.getName()).get();
        if (!user.getRole().getName().equals("ROLE_ADMIN")) {
            if (search.equals("null")) {
                return diplomaRepository.getAllForeignDiplomaByStatus(user.getUniversityCode(), status, pageable);
            }
            return diplomaRepository.getAllForeignDiplomaSearch(user.getUniversityCode(), status, search, pageable);
        }
        if (search.equals("null")) {
            return diplomaRepository.getAllForeignDiplomaByStatusAdmin(status, pageable);
        }
        return diplomaRepository.getAllForeignDiplomaSearchAdmin(status, search, pageable);
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

            if (!user.getRole().getName().equals("ROLE_ADMIN")) {
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
            }
            Application application = applicationRepository.findById(request.getApplicationId()).get();
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
        if (!user.getRole().getName().equals("ROLE_ADMIN")) {
            if (search.equals("null")) {
                return applicationRepository.findAllApplicationByUniversity(user.getUniversityCode(), status, pageable);
            }
            return applicationRepository.findAllSearchApplicationByUniversity(user.getUniversityCode(), status, search, pageable);
        }
        if (search.equals("null")) {
            return applicationRepository.findAllApplicationByUniversityAdmin(status, pageable);
        }
        return applicationRepository.findAllSearchApplicationByUniversityAdmin(status, search, pageable);
    }

    @Transactional(readOnly = true)
    public ApplicationResponse getApplicationByIdUAdmin(Principal principal, Integer applicationId) {
        User user = userRepository.findUserByPinfl(principal.getName()).get();
        if (!user.getRole().getName().equals("ROLE_ADMIN")) {
            Application application = applicationRepository
                    .findApplicationByUniversityAndId(user.getUniversityCode(), applicationId).get();
            User appUser = application.getUser();
            Diploma diploma = diplomaRepository.findActiveDiplomaByUser(appUser.getId()).get();
            ApplicationResponse response = new ApplicationResponse();
            response.setId(application.getId());
            response.setStatus(application.getApplicationStatus());
            if (application.getApplicationMessage() != null) {
                response.setApplicationMessage(application.getApplicationMessage());
            }
            if (application.getDiplomaMessage() != null) {
                response.setDiplomaMessage(application.getDiplomaMessage());
            }
            response.setKvota(application.getKvota());
            response.setDiplomaResponse(new DiplomaResponse(diploma, new UserResponse(appUser)));
            return response;
        }
        Application application = applicationRepository.findById(applicationId).get();
        User appUser = application.getUser();
        Diploma diploma = diplomaRepository.findActiveDiplomaByUser(appUser.getId()).get();
        ApplicationResponse response = new ApplicationResponse();
        response.setId(application.getId());
        response.setStatus(application.getApplicationStatus());
        if (application.getApplicationMessage() != null) {
            response.setApplicationMessage(application.getApplicationMessage());
        }
        if (application.getDiplomaMessage() != null) {
            response.setDiplomaMessage(application.getDiplomaMessage());
        }
        response.setKvota(application.getKvota());
        response.setDiplomaResponse(new DiplomaResponse(diploma, new UserResponse(appUser)));
        return response;
    }

    @Transactional
    public Result updateDiplomaNumber(Principal principal, Integer id, String diplomaNumber) {
        try {
            User user = userRepository.findUserByPinfl(principal.getName()).get();
            Optional<Diploma> diploma = diplomaRepository.findDiplomaByInstitutionAndId(user.getDiplomaInstitutionId(), id);
            if (diploma.isPresent()) {
                diploma.get().setDiplomaNumber(diplomaNumber);
                diplomaRepository.save(diploma.get());
                return new Result(ResponseMessage.SUCCESSFULLY.getMessage(), true);
            }
            return new Result(ResponseMessage.NOT_FOUND.getMessage(), false);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(ResponseMessage.ERROR.getMessage(), false);
        }
    }

    /////////////////////................statistic
    @Transactional(readOnly = true)
    public StatisticCountUAdmin getStatistic(Principal principal) {
        StatisticCountUAdmin statisticCountUAdmin = new StatisticCountUAdmin();

        User user = userRepository.findUserByPinfl(principal.getName()).get();
        if (!user.getRole().getName().equals("ROLE_ADMIN")) {

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
            appStatisticCount.forEach(a -> app.put(a.getStatus(), a.getCount()));
            int appSum = app.values().stream().mapToInt(d -> d).sum();
            app.put("total", appSum);

            List<DiplomaStatisticProjection> diplomaForeignStatisticCount = diplomaRepository.diplomaForeignStatisticCount(user.getUniversityCode());
            Map<String, Integer> diplomaForeign = new HashMap<>();
            diplomaForeign.put("Haqiqiyligi tekshirilmoqda", 0);
            diplomaForeign.put("Rad etildi", 0);
            diplomaForeign.put("Tasdiqlangan", 0);
            diplomaForeign.put("total", 0);
            diplomaForeignStatisticCount.forEach(df -> diplomaForeign.put(df.getStatus(), df.getCount()));
            int appForeignSum = diplomaForeign.values().stream().mapToInt(d -> d).sum();
            diplomaForeign.put("total", appForeignSum);

            statisticCountUAdmin.setNationalDiploma(diploma);
            statisticCountUAdmin.setForeignDiploma(diplomaForeign);
            statisticCountUAdmin.setApplication(app);
            return statisticCountUAdmin;
        }
        List<DiplomaStatisticProjection> diplomaStatisticProjections = diplomaRepository.diplomaStatisticCountAdmin();
        Map<String, Integer> diploma = new HashMap<>();
        diploma.put("Haqiqiyligi tekshirilmoqda", 0);
        diploma.put("Rad etildi", 0);
        diploma.put("Tasdiqlangan", 0);
        diploma.put("total", 0);

        diplomaStatisticProjections.forEach(d -> diploma.put(d.getStatus(), d.getCount()));
        int sum = diploma.values().stream().mapToInt(d -> d).sum();
        diploma.put("total", sum);
        List<DiplomaStatisticProjection> appStatisticCount = applicationRepository.appStatisticCountAdmin();
        Map<String, Integer> app = new HashMap<>();
        app.put("Diplom Haqiqiyligi tekshirilmoqda", 0);
        app.put("Diplom Rad etildi", 0);
        app.put("Diplom Tasdiqlangan", 0);
        app.put("Ariza tasdiqlandi", 0);
        app.put("Ariza rad etildi", 0);
        app.put("total", 0);
        appStatisticCount.forEach(a -> app.put(a.getStatus(), a.getCount()));
        int appSum = app.values().stream().mapToInt(d -> d).sum();
        app.put("total", appSum);

        List<DiplomaStatisticProjection> diplomaForeignStatisticCount = diplomaRepository.diplomaForeignStatisticCountAdmin();
        Map<String, Integer> diplomaForeign = new HashMap<>();
        diplomaForeign.put("Haqiqiyligi tekshirilmoqda", 0);
        diplomaForeign.put("Rad etildi", 0);
        diplomaForeign.put("Tasdiqlangan", 0);
        diplomaForeign.put("total", 0);
        diplomaForeignStatisticCount.forEach(df -> diplomaForeign.put(df.getStatus(), df.getCount()));
        int appForeignSum = diplomaForeign.values().stream().mapToInt(d -> d).sum();
        diplomaForeign.put("total", appForeignSum);

        statisticCountUAdmin.setNationalDiploma(diploma);
        statisticCountUAdmin.setForeignDiploma(diplomaForeign);
        statisticCountUAdmin.setApplication(app);
        statisticCountUAdmin.setFullName(user.getFullName());
        return statisticCountUAdmin;
    }

    //STATISTIC ADMIN
    @Transactional(readOnly = true)
    public List<StatisticCountUAdmin> getAllUniversityStatistic() {
        List<User> users = userRepository.findAllByUadmin();
        List<StatisticCountUAdmin> list = new ArrayList<>();
        users.forEach(user -> {
            StatisticCountUAdmin statisticCountUAdmin = new StatisticCountUAdmin();
            if (user.getUniversityCode() != null) {
                University university = universityRepository.findByCode(user.getUniversityCode()).get();
                statisticCountUAdmin.setUniversity(university.getName());
            } else {
                DiplomaInstitution diplomaInstitution = diplomaInstitutionRepository.findByClassificatorId(user.getDiplomaInstitutionId()).get();
                statisticCountUAdmin.setUniversity(diplomaInstitution.getInstitutionNameOz());
            }
            if (user.getDiplomaInstitutionId() != null) {
                List<DiplomaStatisticProjection> diplomaStatisticProjections = diplomaRepository.diplomaStatisticCount(user.getDiplomaInstitutionId());
                Map<String, Integer> diploma = new HashMap<>();
                diploma.put("Haqiqiyligi tekshirilmoqda", 0);
                diploma.put("Rad etildi", 0);
                diploma.put("Tasdiqlangan", 0);
                diploma.put("total", 0);
//                Thread thread = new Thread(() -> {
                diplomaStatisticProjections.forEach(d -> diploma.put(d.getStatus(), d.getCount()));
                int sum = diploma.values().stream().mapToInt(d -> d).sum();
                diploma.put("total", sum);
//                });
//                thread.start();
//                try {
//                    thread.join();
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
                statisticCountUAdmin.setNationalDiploma(diploma);

            }

            List<DiplomaStatisticProjection> appStatisticCount = applicationRepository.appStatisticCount(user.getUniversityCode());
            Map<String, Integer> app = new HashMap<>();
            app.put("Diplom Haqiqiyligi tekshirilmoqda", 0);
            app.put("Diplom Rad etildi", 0);
            app.put("Diplom Tasdiqlangan", 0);
            app.put("Ariza tasdiqlandi", 0);
            app.put("Ariza rad etildi", 0);
            app.put("total", 0);
//            Thread thread1 = new Thread(() -> {
            appStatisticCount.forEach(a -> app.put(a.getStatus(), a.getCount()));
            int appSum = app.values().stream().mapToInt(d -> d).sum();
            app.put("total", appSum);
//
//            });
//            thread1.start();
//            try {
//                thread1.join();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }

            List<DiplomaStatisticProjection> diplomaForeignStatisticCount = diplomaRepository.diplomaForeignStatisticCount(user.getUniversityCode());
            Map<String, Integer> diplomaForeign = new HashMap<>();
            diplomaForeign.put("Haqiqiyligi tekshirilmoqda", 0);
            diplomaForeign.put("Rad etildi", 0);
            diplomaForeign.put("Tasdiqlangan", 0);
            diplomaForeign.put("total", 0);
//            Thread thread = new Thread(() -> {
            diplomaForeignStatisticCount.forEach(df -> diplomaForeign.put(df.getStatus(), df.getCount()));
            int appForeignSum = diplomaForeign.values().stream().mapToInt(d -> d).sum();
            diplomaForeign.put("total", appForeignSum);
//
//
//            });
//            thread.start();
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
            statisticCountUAdmin.setForeignDiploma(diplomaForeign);
            statisticCountUAdmin.setApplication(app);
            list.add(statisticCountUAdmin);


        });

        return list;
    }


    @Transactional(readOnly = true)
    public CountAllDateStatistic getAllDateStatic(Principal principal) {

        User user = userRepository.findUserByPinfl(principal.getName()).get();
        if (!user.getRole().getName().equals("ROLE_ADMIN")) {

            List<GetCountAppallDate> countByForeignlDiplomaDate = diplomaRepository.getCountByForeignlDiplomaDate(user.getUniversityCode());
            List<GetCountAppallDate> countByNationalDiplomaDate = diplomaRepository.getCountByNationalDiplomaDate(user.getDiplomaInstitutionId());
            List<GetCountAppallDate> countAppallDate = applicationRepository.geetCountAppallDate(user.getUniversityCode());
            CountAllDateStatistic countAllDateStatistic = new CountAllDateStatistic();
            Map<String, List<GetCountAppallDate>> map = new HashMap();

            map.put("application", countAppallDate);
            map.put("foreignDiploma", countByForeignlDiplomaDate);
            map.put("nationalDiploma", countByNationalDiplomaDate);
            countAllDateStatistic.setStatisticByDate(map);

            Map<String, List<GetAppByGender>> hashMap = new HashMap();

            List<GetAppByGender> nationalDiplomaVByGender = diplomaRepository.getCountNationalDiplomaVByGender(user.getDiplomaInstitutionId());
            List<GetAppByGender> foreignDiplomaVByGender = diplomaRepository.getCountForeignDiplomaVByGender(user.getUniversityCode());
            List<GetAppByGender> appByGender = applicationRepository.getCountAppByGender(user.getUniversityCode());
            hashMap.put("application", appByGender);
            hashMap.put("foreignDiploma", foreignDiplomaVByGender);
            hashMap.put("nationalDiploma", nationalDiplomaVByGender);
            countAllDateStatistic.setStatisticByGender(hashMap);
            return countAllDateStatistic;
        }
        List<GetCountAppallDate> countByForeignlDiplomaDate = diplomaRepository.getCountByForeignDiplomaDateAdmin();
        List<GetCountAppallDate> countByNationalDiplomaDate = diplomaRepository.getCountByNationalDiplomaDateAdmin();
        List<GetCountAppallDate> countAppallDate = applicationRepository.getCountAppallDateAdmin();
        CountAllDateStatistic countAllDateStatistic = new CountAllDateStatistic();
        Map<String, List<GetCountAppallDate>> map = new HashMap();

        map.put("application", countAppallDate);
        map.put("foreignDiploma", countByForeignlDiplomaDate);
        map.put("nationalDiploma", countByNationalDiplomaDate);
        countAllDateStatistic.setStatisticByDate(map);

        Map<String, List<GetAppByGender>> hashMap = new HashMap();

        List<GetAppByGender> nationalDiplomaVByGender = diplomaRepository.getCountNationalDiplomaVByGenderAdmin();
        List<GetAppByGender> foreignDiplomaVByGender = diplomaRepository.getCountForeignDiplomaVByGenderAdmin();
        List<GetAppByGender> appByGender = applicationRepository.getCountAppByGenderAdmin();
        hashMap.put("application", appByGender);
        hashMap.put("foreignDiploma", foreignDiplomaVByGender);
        hashMap.put("nationalDiploma", nationalDiplomaVByGender);
        countAllDateStatistic.setStatisticByGender(hashMap);
        return countAllDateStatistic;
    }

}
