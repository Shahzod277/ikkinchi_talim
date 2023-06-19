package uz.raqamli_markaz.ikkinchi_talim.model.response;

import java.util.List;
public interface StatisDirectionResponseByFutureInst {

    String getFutureInstName();
    Integer getDirectionId();
    String getDirectionName();
    List<StatisEduFormResponse> getStatisEduFormResponses();

}
