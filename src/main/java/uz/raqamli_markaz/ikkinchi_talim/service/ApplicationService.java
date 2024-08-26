package uz.raqamli_markaz.ikkinchi_talim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import uz.raqamli_markaz.ikkinchi_talim.api.my_edu.CreateAppRequestMyEdu;
import uz.raqamli_markaz.ikkinchi_talim.api.my_edu.MyEduApiService;
import uz.raqamli_markaz.ikkinchi_talim.domain.Application;
import uz.raqamli_markaz.ikkinchi_talim.domain.Kvota;
import uz.raqamli_markaz.ikkinchi_talim.domain.User;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.Diploma;
import uz.raqamli_markaz.ikkinchi_talim.model.response.*;
import uz.raqamli_markaz.ikkinchi_talim.repository.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final KvotaRepository kvotaRepository;
    private final DiplomaRepository diplomaRepository;
    private final MyEduApiService myEduApiService;

    @Transactional
    public Result createApplication(String token, Integer kvotaId) {
        try {
            Result result = userService.checkUser(token);
            if (!result.isSuccess()) {
                return result;
            }
            Integer id = (Integer) result.getObject();
            User user = userRepository.findById(id).get();
            List<String> list = List.of("31207807020024","30201940220085", "32506580220014", "31012840630062", "32507920172420");
            boolean contain = list.contains(user.getPinfl());
            if (!contain) {
                return new Result("Ikkinchi oliy ta'limga ariza topshirish muddati 10-avgustgacha belgilangan", false);
            }
            Optional<Application> applicationOptional = applicationRepository.findByUserId(user.getId());
            Kvota kvota = kvotaRepository.findById(kvotaId).get();

            if (applicationOptional.isPresent()) {
                Application application = applicationOptional.get();
                application.setKvota(kvota);
                Application save = applicationRepository.save(application);
                String encode = userService.encode(user.getPinfl());
                CreateAppRequestMyEdu request = new CreateAppRequestMyEdu();
                request.setExternalId(save.getId().toString());
                request.setStatus(save.getApplicationStatus());
                request.setData(kvota);
                myEduApiService.createApp(encode, request);
                return new Result(ResponseMessage.SUCCESSFULLY_SAVED.getMessage(), true, save.getId());

            }

            Application application = new Application();
            application.setUser(user);
            application.setKvota(kvota);
            Diploma diploma = diplomaRepository.findActiveDiplomaByUser(id).get();
            application.setApplicationStatus("Diplom " + diploma.getStatusName());
            application.setKvota(kvota);
            Application save = applicationRepository.save(application);
            Thread thread = new Thread(() -> {
                try {
                    String encode = userService.encode(user.getPinfl());
                    CreateAppRequestMyEdu request = new CreateAppRequestMyEdu();
                    request.setExternalId(save.getId().toString());
                    request.setStatus(save.getApplicationStatus());
                    request.setData(kvota);
                    myEduApiService.createApp(encode, request);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            thread.start();
            thread.join(5000);
            return new Result(ResponseMessage.SUCCESSFULLY_SAVED.getMessage(), true, save.getId());
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(ResponseMessage.ERROR_SAVED.getMessage(), false);
        }
    }


    @Transactional
    public Result updateApplication(String token, Integer kvotaId) {
//        try {
//            Result result = userService.checkUser(token);
//            if (!result.isSuccess()) {
//                return result;
//            }
//            Integer id = (Integer) result.getObject();
//            User user = userRepository.findById(id).get();
//            Application application = user.getApplication();
//            Kvota kvota = kvotaRepository.findById(kvotaId).get();
//            application.setUser(user);
//            application.setKvota(kvota);
//            application.setModifiedDate(LocalDateTime.now());
//            Diploma diploma = diplomaRepository.findActiveDiplomaByUser(id).get();
//            application.setApplicationStatus("Diplom " + diploma.getStatusName());
//            application.setKvota(kvota);
//            application.setApplicationMessage(null);
//            application.setDiplomaMessage(null);
//            Application save = applicationRepository.save(application);
//            Thread thread = new Thread(() -> {
//                try {
//                    String encode = userService.encode(user.getPinfl());
//                    CreateAppRequestMyEdu request = new CreateAppRequestMyEdu();
//                    request.setExternalId(application.getId().toString());
//                    request.setStatus(application.getApplicationStatus());
//                    request.setData(kvota);
//                    myEduApiService.updateApp(encode, request);
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            });
//            thread.start();
//            thread.join(5000);
//            return new Result(ResponseMessage.SUCCESSFULLY_UPDATE.getMessage(), true, application.getId());
//        } catch (Exception ex) {
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            return new Result(ResponseMessage.ERROR_UPDATE.getMessage(), false);
//        }
        return new Result("Ikkinchi oliy ta'limga ariza tahrirlash  muddati 16-avgust 18:00 gacha belgilangan", false);
    }

    @Transactional(readOnly = true)
    public Result getApplicationByPrincipal(String token) {

        Result result = userService.checkUser(token);
        if (!result.isSuccess()) {
            return result;
        }
        Integer id = (Integer) result.getObject();
        User user = userRepository.findById(id).get();
        if (user.getApplication() == null) {
            return new Result(ResponseMessage.NOT_FOUND.getMessage(), false);
        }
        Application userApplication = user.getApplication();
        Diploma diploma = diplomaRepository.findActiveDiplomaByUser(id).get();
        ApplicationResponse applicationResponse = new ApplicationResponse();
        applicationResponse.setDiplomaResponse(new DiplomaResponse(diploma, new UserResponse(user)));
        applicationResponse.setStatus(userApplication.getApplicationStatus());
        if (userApplication.getApplicationMessage() != null) {
            applicationResponse.setApplicationMessage(userApplication.getApplicationMessage());
        }
        if (userApplication.getDiplomaMessage() != null) {
            applicationResponse.setDiplomaMessage(userApplication.getDiplomaMessage());
        }
        applicationResponse.setKvota(userApplication.getKvota());
        return new Result(ResponseMessage.SUCCESSFULLY.getMessage(), true, applicationResponse);
    }

    @Transactional
    public void updateAllStatus() {
        List<Application> applications = applicationRepository.editStatusAll();
        applications.forEach(application -> {
            try {
                String encode = userService.encode(application.getUser().getPinfl());
                CreateAppRequestMyEdu requestMyEdu = new CreateAppRequestMyEdu();
                requestMyEdu.setExternalId(application.getId().toString());
                requestMyEdu.setStatus(application.getApplicationStatus());
                requestMyEdu.setData(application.getKvota());
                myEduApiService.updateApp(encode, requestMyEdu);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
