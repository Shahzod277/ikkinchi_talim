package uz.raqamli_markaz.ikkinchi_talim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import uz.raqamli_markaz.ikkinchi_talim.domain.Application;
import uz.raqamli_markaz.ikkinchi_talim.domain.StoryMessage;
import uz.raqamli_markaz.ikkinchi_talim.domain.classificator.EduForm;
import uz.raqamli_markaz.ikkinchi_talim.domain.classificator.FutureInstitution;
import uz.raqamli_markaz.ikkinchi_talim.domain.classificator.Language;
import uz.raqamli_markaz.ikkinchi_talim.model.request.ApplicationRequest;
import uz.raqamli_markaz.ikkinchi_talim.model.request.ApplicationStatus;
import uz.raqamli_markaz.ikkinchi_talim.model.response.ApplicationResponse;
import uz.raqamli_markaz.ikkinchi_talim.model.response.ResponseMessage;
import uz.raqamli_markaz.ikkinchi_talim.model.response.Result;
import uz.raqamli_markaz.ikkinchi_talim.repository.*;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final EduFormRepository eduFormRepository;
    private final LanguageRepository languageRepository;
    private final ApplicationRepository applicationRepository;
    private final FutureInstitutionRepository futureInstitutionRepository;
    private final StoryMessageRepository storyMessageRepository;

    @Transactional
    public Result createApplication(Principal principal, ApplicationRequest request) {

        try {
            Optional<Application> checkApp = applicationRepository.checkApp(principal.getName());
            if (checkApp.isEmpty()) {
                Application application = new Application();
                Language language = languageRepository.findById(request.getLanguageId()).get();
                application.setLanguage(language);
                EduForm eduForm = eduFormRepository.findById(request.getEduFormId()).get();
                application.setEduForm(eduForm);
                FutureInstitution futureInstitution = futureInstitutionRepository.findById(request.getFutureInstitutionId()).get();
                application.setFutureInstitution(futureInstitution);
                application.setStatus(ApplicationStatus.DEFAULT_STATUS.getMessage());
                applicationRepository.save(application);
                return new Result(ResponseMessage.SUCCESSFULLY_SAVED.getMessage(), true);
            }
            return new Result(ResponseMessage.ALREADY_EXISTS.getMessage(), false);
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(ResponseMessage.ERROR_SAVED.getMessage(), false);
        }
    }

    @Transactional
    public Result updateApplication(Principal principal, ApplicationRequest request) {
        try {
            Application application = applicationRepository.findByUserId(principal.getName()).get();
            Language language = languageRepository.findById(request.getLanguageId()).get();
            application.setLanguage(language);
            EduForm eduForm = eduFormRepository.findById(request.getEduFormId()).get();
            application.setEduForm(eduForm);
            FutureInstitution futureInstitution = futureInstitutionRepository.findById(request.getFutureInstitutionId()).get();
            application.setFutureInstitution(futureInstitution);
            application.setStatus(ApplicationStatus.DEFAULT_STATUS.getMessage());
            application.setMessage(null);
            application.setModifiedDate(LocalDateTime.now());
            Application save = applicationRepository.save(application);
            StoryMessage storyMessage = new StoryMessage();
            storyMessage.setMessage(save.getMessage());
            storyMessage.setStatus(save.getStatus());
            storyMessage.setPinfl(principal.getName());
            storyMessage.setApplication(save);
            storyMessageRepository.save(storyMessage);
            return new Result(ResponseMessage.SUCCESSFULLY_UPDATE.getMessage(), true);
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(ResponseMessage.ERROR_UPDATE.getMessage(), false);
        }
    }

    @Transactional
    public ApplicationResponse getApplicationByPrincipal(Principal principal) {
        Optional<Application> checkApp = applicationRepository.checkApp(principal.getName());
        if (checkApp.isEmpty()) {
            ApplicationResponse applicationResponse = null;
            return applicationResponse;
        }
        Optional<ApplicationResponse> applicationResponse = applicationRepository.findByAppByPrincipal(enrolleeInfo.getId());
        return applicationResponse.get();
    }
}
