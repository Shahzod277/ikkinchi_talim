package uz.raqamli_markaz.ikkinchi_talim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.EduForm;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.Language;
import uz.raqamli_markaz.ikkinchi_talim.model.request.EduFormRequest;
import uz.raqamli_markaz.ikkinchi_talim.repository.LanguageRepository;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EduFormService {

    private final LanguageRepository languageRepository;


    private List<Language> createLanguageAndQuota(EduFormRequest request, EduForm saveEduform) {
        List<Language> languages = new ArrayList<>();
        request.getLanguages().forEach(l -> {
            Language language = new Language();
            language.setName(l.getName());
            languages.add(language);
        });
        return languageRepository.saveAll(languages);
    }
}
