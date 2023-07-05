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

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StoryMessageRepository storyMessageRepository;
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
            Kvota kvota = kvotaRepository.findById(kvotaId).get();
            Application application = new Application();
            application.setUser(user);
            application.setKvota(kvota);
            Diploma diploma = diplomaRepository.findActiveDiplomaByUser(id).get();
            application.setApplicationStatus(diploma.getStatusName());
            application.setKvota(kvota);
            Thread thread = new Thread(() -> {
                try {
                    String encode = userService.encode(user.getPinfl());
                    CreateAppRequestMyEdu request = new CreateAppRequestMyEdu();
                    request.setExternalId(application.getId().toString());
                    request.setStatus(application.getApplicationStatus());
                    request.setData(kvota);
                    myEduApiService.createApp(encode, request);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            thread.start();
            thread.join(5000);
            applicationRepository.save(application);
            return new Result(ResponseMessage.SUCCESSFULLY_SAVED.getMessage(), false);

        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(ResponseMessage.ERROR_SAVED.getMessage(), false);
        }

    }

    @Transactional
    public Result updateApplication(String token, Integer kvotaId) {
        try {

            Result result = userService.checkUser(token);
            if (!result.isSuccess()) {
                return result;
            }
            Integer id = (Integer) result.getObject();
            User user = userRepository.findById(id).get();
            Application application = user.getApplication();
            Kvota kvota = kvotaRepository.findById(kvotaId).get();
            application.setUser(user);
            application.setKvota(kvota);
            Diploma diploma = diplomaRepository.findActiveDiplomaByUser(id).get();
            application.setApplicationStatus(diploma.getStatusName());
            application.setKvota(kvota);
            Thread thread = new Thread(() -> {
                try {
                    String encode = userService.encode(user.getPinfl());
                    CreateAppRequestMyEdu request = new CreateAppRequestMyEdu();
                    request.setExternalId(application.getId().toString());
                    request.setStatus(application.getApplicationStatus());
                    request.setData(kvota);
                    myEduApiService.updateApp(encode, request);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            thread.start();
            thread.join(5000);
            applicationRepository.save(application);
            return new Result(ResponseMessage.SUCCESSFULLY_UPDATE.getMessage(), true);
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(ResponseMessage.ERROR_UPDATE.getMessage(), false);
        }
    }

    @Transactional(readOnly = true)
    public Result getApplicationByPrincipal(String token) {

        Result result = userService.checkUser(token);
        if (!result.isSuccess()) {
            return result;
        }
        Integer id = (Integer) result.getObject();
        User user = userRepository.findById(id).get();
        Application userApplication = user.getApplication();
        Diploma diploma = diplomaRepository.findActiveDiplomaByUser(id).get();
        ApplicationResponse applicationResponse = new ApplicationResponse();

        applicationResponse.setDiplomaResponse(new DiplomaResponse(diploma));
        applicationResponse.setUserResponse(new UserResponse(user));
        applicationResponse.setStatus(userApplication.getApplicationStatus());
        applicationResponse.setMessage(userApplication.getApplicationMessage());
        applicationResponse.setKvota(userApplication.getKvota());
        return new  Result(ResponseMessage.SUCCESSFULLY.getMessage(),true,applicationResponse);
    }

}
