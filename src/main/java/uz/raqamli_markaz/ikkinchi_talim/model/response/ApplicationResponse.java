package uz.raqamli_markaz.ikkinchi_talim.model.response;

public interface ApplicationResponse {

    Integer getId();
    Integer getTilId();
    String getTilName();
    Integer getShaklId();
    String getShaklName();
    Integer getDirectionId();
    String getDirectionName();
    Integer getFutureInstitutionId();
    String getFutureInstitutionName();
    String getAppStatus();
    String getDiplomaStatus();
    String getAppMessage();
    String getDiplomaMessage();
    String getCreatedDate();
/*
     List<StoryMessageResponse> getStoryMessageResponse();
*/
}
