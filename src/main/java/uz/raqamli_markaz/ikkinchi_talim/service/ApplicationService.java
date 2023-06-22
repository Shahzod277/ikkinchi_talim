package uz.raqamli_markaz.ikkinchi_talim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import uz.raqamli_markaz.ikkinchi_talim.domain.Application;
import uz.raqamli_markaz.ikkinchi_talim.domain.StoryMessage;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.EduForm;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaInstitution;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.Language;
import uz.raqamli_markaz.ikkinchi_talim.model.request.ApplicationRequest;
import uz.raqamli_markaz.ikkinchi_talim.model.request.ApplicationStatus;
import uz.raqamli_markaz.ikkinchi_talim.model.response.ApplicationResponse;
import uz.raqamli_markaz.ikkinchi_talim.model.response.ResponseMessage;
import uz.raqamli_markaz.ikkinchi_talim.model.response.Result;
import uz.raqamli_markaz.ikkinchi_talim.repository.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final EduFormRepository eduFormRepository;
    private final LanguageRepository languageRepository;
    private final ApplicationRepository applicationRepository;
    private final DiplomaInstitutionRepository diplomaInstitutionRepository;
    private final StoryMessageRepository storyMessageRepository;

    @Transactional
    public Result createApplication(String pinfl, ApplicationRequest request) {

        try {
            Optional<Application> application = applicationRepository.findAppByUser(pinfl);
            if (application.isEmpty()) {
                Application applicationNew = new Application();
                Language language = languageRepository.findById(request.getLanguageId()).get();
                applicationNew.setLanguage(language);
                EduForm eduForm = eduFormRepository.findById(request.getEduFormId()).get();
                applicationNew.setEduForm(eduForm);
                DiplomaInstitution diplomaInstitution = diplomaInstitutionRepository.findById(request.getClassificatorId()).get();
                applicationNew.setDiplomaInstitution(diplomaInstitution);
                applicationNew.setStatus(ApplicationStatus.DEFAULT_STATUS.getMessage());
                applicationRepository.save(applicationNew);
                return new Result(ResponseMessage.SUCCESSFULLY_SAVED.getMessage(), true);
            }
            return new Result(ResponseMessage.ALREADY_EXISTS.getMessage(), false);
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(ResponseMessage.ERROR_SAVED.getMessage(), false);
        }
    }

    @Transactional
    public Result updateApplication(String pinfl, ApplicationRequest request) {
        try {
            Application application = applicationRepository.findAppByUser(pinfl).get();
            Language language = languageRepository.findById(request.getLanguageId()).get();
            application.setLanguage(language);
            EduForm eduForm = eduFormRepository.findById(request.getEduFormId()).get();
            application.setEduForm(eduForm);
            DiplomaInstitution diplomaInstitution = diplomaInstitutionRepository.findById(request.getClassificatorId()).get();
            application.setDiplomaInstitution(diplomaInstitution);
            application.setStatus(ApplicationStatus.DEFAULT_STATUS.getMessage());
            application.setMessage(null);
            application.setModifiedDate(LocalDateTime.now());
            Application save = applicationRepository.save(application);
            StoryMessage storyMessage = new StoryMessage();
            storyMessage.setMessage(save.getMessage());
            storyMessage.setStatus(save.getStatus());
            storyMessage.setPinfl(pinfl);
            storyMessage.setApplication(save);
            storyMessageRepository.save(storyMessage);
            return new Result(ResponseMessage.SUCCESSFULLY_UPDATE.getMessage(), true);
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(ResponseMessage.ERROR_UPDATE.getMessage(), false);
        }
    }

    @Transactional
    public ApplicationResponse getApplicationByPrincipal(String pinfl) {
       return applicationRepository.findAppByUser(pinfl).map(ApplicationResponse::new).get();
    }

}
