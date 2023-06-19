package uz.raqamli_markaz.ikkinchi_talim.model.response;

import java.util.List;

public interface StatisEduFormResponse {

    Integer getEduFormId();
    String getEduFormName();
    List<StatisLanguageResponse> getStatisLanguageResponses();

}
