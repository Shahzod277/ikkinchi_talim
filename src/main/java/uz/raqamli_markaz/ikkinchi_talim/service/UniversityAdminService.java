//package uz.raqamli_markaz.ikkinchi_talim.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.*;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import uz.raqamli_markaz.ikkinchi_talim.api.iib_api.Data;
//import uz.raqamli_markaz.ikkinchi_talim.api.iib_api.IIBResponse;
//import uz.raqamli_markaz.ikkinchi_talim.api.iib_api.IIBServiceApi;
//import uz.raqamli_markaz.ikkinchi_talim.domain.AdminEntity;
//import uz.raqamli_markaz.ikkinchi_talim.domain.Application;
//import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.Diploma;
//import uz.raqamli_markaz.ikkinchi_talim.domain.StoryMessage;
//import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.University;
//import uz.raqamli_markaz.ikkinchi_talim.model.request.IIBRequest;
//import uz.raqamli_markaz.ikkinchi_talim.model.request.UpdateDiplomaStatus;
//import uz.raqamli_markaz.ikkinchi_talim.model.response.*;
//import uz.raqamli_markaz.ikkinchi_talim.repository.*;
//import java.security.Principal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class UniversityAdminService {
//
//    private final DiplomaRepository diplomaRepository;
//    private final IIBServiceApi iibServiceApi;
//    private final StoryMessageRepository storyMessageRepository;
//    private final ApplicationRepository applicationRepository;
//    private final DocumentRepository documentRepository;
//    private final AdminEntityRepository adminEntityRepository;
//
//    @Transactional(readOnly = true)
//    public Page<DiplomResponseAdmin> getDiplomas(Principal principal, String status, int page, int size) {
//        if (page > 0) page = page - 1;
//        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
//        AdminEntity adminEntity = adminEntityRepository.getAdminUniversity(principal.getName()).get();
//        List<University> universities = adminEntity.getUniversities();
//        Integer institutionId = universities.stream().map(University::getInstitutionId).findFirst().get();
//        List<DiplomResponseAdmin> diplomResponseAdmins = new ArrayList<>();
//        if (status.equals("true") || status.equals("false")) {
//            Boolean aBoolean = Boolean.valueOf(status);
//            Page<Application> allDiplomebyUAdmin = applicationRepository.getAppDiplomaByEnrollId(institutionId, aBoolean, pageable);
//            allDiplomebyUAdmin.forEach(application -> {
//                Diploma diploma = diplomaRepository.getDiplomaByEnrolleeInfoId(application.getEnrolleeInfo().getId()).get();
//                //   FileResponse fileResponse = getFileResponse(diploma.getId());
//                DiplomResponseAdmin diplomResponseAdmin = new DiplomResponseAdmin(diploma);
//                diplomResponseAdmin.setEnrolleeResponse(enrolleeResponse);
//                diplomResponseAdmin.setDiplomaStatus(String.valueOf(application.getDiplomaStatus()));
//
//                diplomResponseAdmins.add(diplomResponseAdmin);
//            });
//            return new PageImpl<>(diplomResponseAdmins, pageable, allDiplomebyUAdmin.getTotalElements());
//
//        } else {
//            Page<Application> allDiplomebyUAdmin = applicationRepository.getAppDiplomaByEnrollAppDiplomStatusNull(institutionId, pageable);
//            allDiplomebyUAdmin.forEach(application -> {
//                Diploma diploma = diplomaRepository.getDiplomaByEnrolleeInfoId(application.getEnrolleeInfo().getId()).get();
//                FileResponse fileResponse = getFileResponse(diploma.getId());
//                DiplomResponseAdmin diplomResponseAdmin = new DiplomResponseAdmin(diploma, fileResponse);
//                diplomResponseAdmin.setEnrolleeResponse(enrolleeResponse);
//                diplomResponseAdmin.setDiplomaStatus(String.valueOf(application.getDiplomaStatus()));
//                diplomResponseAdmins.add(diplomResponseAdmin);
//            });
//            return new PageImpl<>(diplomResponseAdmins, pageable, allDiplomebyUAdmin.getTotalElements());
//        }
//
//    }
//
//
//    //horijiy diplomlarni obshiysi
//    @Transactional(readOnly = true)
//    public Page<DiplomResponseAdmin> getForeignDiplomas(Principal principal, String status, int page, int size) {
//        if (page > 0) page = page - 1;
//        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
//        List<DiplomResponseAdmin> diplomResponseAdmins = new ArrayList<>();
//        AdminEntity adminEntity = adminEntityRepository.getAdminUniversity(principal.getName()).get();
//        if (adminEntity.getDiplomaInstitution().getId() != null) {
//            if (status.equals("true") || status.equals("false")) {
//                Boolean aBoolean = Boolean.valueOf(status);
//                Page<Application> allDiplomebyUAdmin = applicationRepository.getAppDipForeign(adminEntity.getDiplomaInstitution().getId(), aBoolean, pageable);
//                allDiplomebyUAdmin.forEach(application -> {
//                    Diploma diploma = diplomaRepository.getDiplomaByEnrolleeInfoId(application.getEnrolleeInfo().getId()).get();
//                    FileResponse fileResponse = getFileResponse(diploma.getId());
//                    DiplomResponseAdmin diplomResponseAdmin = new DiplomResponseAdmin(diploma, fileResponse);
//                    diplomResponseAdmin.setEnrolleeResponse(enrolleeResponse);
//                    diplomResponseAdmin.setDiplomaStatus(String.valueOf(application.getDiplomaStatus()));
//                    diplomResponseAdmins.add(diplomResponseAdmin);
//                });
//                return new PageImpl<>(diplomResponseAdmins, pageable, allDiplomebyUAdmin.getTotalElements());
//            } else {
//                Page<Application> allDiplomebyUAdmin = applicationRepository.getAppForeignDipStatusNull(adminEntity.getDiplomaInstitution().getId(), pageable);
//                allDiplomebyUAdmin.forEach(application -> {
//                    Diploma diploma = diplomaRepository.getDiplomaByEnrolleeInfoId(application.getEnrolleeInfo().getId()).get();
//                    FileResponse fileResponse = getFileResponse(diploma.getId());
//                    DiplomResponseAdmin diplomResponseAdmin = new DiplomResponseAdmin(diploma, fileResponse);
//                    diplomResponseAdmin.setEnrolleeResponse(enrolleeResponse);
//                    diplomResponseAdmin.setDiplomaStatus(String.valueOf(application.getDiplomaStatus()));
//
//                    diplomResponseAdmins.add(diplomResponseAdmin);
//                });
//                return new PageImpl<>(diplomResponseAdmins, pageable, allDiplomebyUAdmin.getTotalElements());
//            }
//        }
//        return new PageImpl<>(new ArrayList<>(), pageable, 0);
//    }
//
//    //horijiy diplomning bir donasi id orqali
//    @Transactional(readOnly = true)
//    public Result getForeignDiplomaById(Integer diplomaId, Principal principal) {
//        AdminEntity adminEntity = adminEntityRepository.getAdminUniversity(principal.getName()).get();
//        Optional<Application> application = applicationRepository.getAppAndForeignDiplomaById(adminEntity.getDiplomaInstitution().getId(), diplomaId);
//        if (application.isEmpty()) {
//            return new Result(ResponseMessage.NOT_FOUND.getMessage(), false);
//        }
//        IIBRequest iibRequest = new IIBRequest();
//        iibRequest.setPinfl(application.get().getEnrolleeInfo().getPinfl());
//        iibRequest.setGiven_date(application.get().getEnrolleeInfo().getPassportGivenDate());
//        IIBResponse iibResponse = iibServiceApi.iibResponse(iibRequest);
//        Data data = iibResponse.getData();
//        ImageResponse imageResponse = new ImageResponse();
//        if (!data.getPhoto().isEmpty()) {
//            imageResponse.setImage(data.getPhoto());
//        }
//
//        Diploma diploma = diplomaRepository.getDiplomaByEnrolleeInfoId(application.get().getEnrolleeInfo().getId()).get();
//
//        FileResponse fileResponse = getFileResponse(diploma.getId());
//        DiplomResponseAdmin diplomResponseAdmin = new DiplomResponseAdmin(diploma, fileResponse);
//        diplomResponseAdmin.setEnrolleeResponse(enrolleeResponse);
//        diplomResponseAdmin.setDiplomaStatus(String.valueOf(application.get().getDiplomaStatus()));
//
//        return new Result("diploma", true, diplomResponseAdmin);
//    }
//
//
//    @Transactional(readOnly = true)
//    public Result getDiplomaById(Integer diplomaId, Principal principal) {
//        AdminEntity adminEntity = adminEntityRepository.getAdminUniversity(principal.getName()).get();
//        Integer institutionId = adminEntity.getUniversities().stream().map(University::getInstitutionId).findFirst().get();
//        Optional<Application> application = applicationRepository.getAppAndDiplomaById(institutionId, diplomaId);
//        if (application.isEmpty()) {
//            return new Result(ResponseMessage.NOT_FOUND.getMessage(), false);
//        }
//        IIBRequest iibRequest = new IIBRequest();
//        iibRequest.setPinfl(application.get().getEnrolleeInfo().getPinfl());
//        iibRequest.setGiven_date(application.get().getEnrolleeInfo().getPassportGivenDate());
//        IIBResponse iibResponse = iibServiceApi.iibResponse(iibRequest);
//        Data data = iibResponse.getData();
//        ImageResponse imageResponse = new ImageResponse();
//        if (!data.getPhoto().isEmpty()) {
//            imageResponse.setImage(data.getPhoto());
//        }
//        Diploma diploma = diplomaRepository.getDiplomaByEnrolleeInfoId(application.get().getEnrolleeInfo().getId()).get();
//
//        FileResponse fileResponse = getFileResponse(diploma.getId());
//        DiplomResponseAdmin diplomResponseAdmin = new DiplomResponseAdmin(diploma, fileResponse);
//        diplomResponseAdmin.setEnrolleeResponse(enrolleeResponse);
//        diplomResponseAdmin.setDiplomaStatus(String.valueOf(application.get().getDiplomaStatus()));
//
//
//        return new Result("diploma", true, diplomResponseAdmin);
//    }
//
//    @Transactional(readOnly = true)
//    public Page<AppResponse> getAllAppByUAdmin(Principal principal, String status, int page, int size) {
//        if (page > 0) page = page - 1;
//        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
//        AdminEntity adminEntity = adminEntityRepository.getAdminUniversity(principal.getName()).get();
//        List<AppResponse> responses = new ArrayList<>();
//        if (adminEntity.getDiplomaInstitution().getId() != null) {
//            Page<Application> allApp = applicationRepository.getAllApp(adminEntity.getDiplomaInstitution().getId(), status, pageable);
//            allApp.forEach(application -> {
//                AppResponse appResponse = new AppResponse(application);
//                Diploma diploma = diplomaRepository.getDiplomaByEnrolleeInfoId(application.getEnrolleeInfo().getId()).get();
//                FileResponse fileResponse = getFileResponse(diploma.getId());
//                appResponse.setDiplomaResponse(new DiplomaResponse(diploma, fileResponse));
//                responses.add(appResponse);
//            });
//            return new PageImpl<>(responses, pageable, allApp.getTotalElements());
//        }
//        return new PageImpl<>(new ArrayList<>(), pageable, 0);
//    }
//
//    @Transactional(readOnly = true)
//    public Page<AppResponse> getAllAppDplomaStatusByUAdmin(Principal principal, String diplomaStatus, String appStatus, int page, int size) {
//        if (page > 0) page = page - 1;
//        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
//        AdminEntity adminEntity = adminEntityRepository.getAdminUniversity(principal.getName()).get();
//        List<AppResponse> responses = new ArrayList<>();
//        if (adminEntity.getDiplomaInstitution().getId() != null) {
//            if (diplomaStatus.equals("true") || diplomaStatus.equals("false")) {
//                Boolean aBoolean = Boolean.valueOf(diplomaStatus);
//                Page<Application> allApp = applicationRepository.getAllAppByDiplomaStatusAndAppstatus(adminEntity.getDiplomaInstitution().getId(), aBoolean, appStatus, pageable);
//                allApp.forEach(application -> {
//                    AppResponse appResponse = new AppResponse(application);
//                    appResponse.setEnrolleeResponse(new EnrolleeResponse(application.getEnrolleeInfo()));
//                    Diploma diploma = diplomaRepository.getDiplomaByEnrolleeInfoId(application.getEnrolleeInfo().getId()).get();
//                    FileResponse fileResponse = getFileResponse(diploma.getId());
//                    appResponse.setDiplomaResponse(new DiplomaResponse(diploma, fileResponse));
//                    responses.add(appResponse);
//                });
//                return new PageImpl<>(responses, pageable, allApp.getTotalElements());
//            } else {
//                List<AppResponse> responsese = new ArrayList<>();
//                Page<Application> allApp = applicationRepository.getAllAppByDiplomaStatusIsNull(adminEntity.getDiplomaInstitution().getId(), appStatus, pageable);
//                allApp.forEach(application -> {
//                    AppResponse appResponse = new AppResponse(application);
//                    appResponse.setEnrolleeResponse(new EnrolleeResponse(application.getEnrolleeInfo()));
//                    Diploma diploma = diplomaRepository.getDiplomaByEnrolleeInfoId(application.getEnrolleeInfo().getId()).get();
//                    FileResponse fileResponse = getFileResponse(diploma.getId());
//                    appResponse.setDiplomaResponse(new DiplomaResponse(diploma, fileResponse));
//                    responsese.add(appResponse);
//                });
//                return new PageImpl<>(responsese, pageable, allApp.getTotalElements());
//            }
//        }
//        return new PageImpl<>(new ArrayList<>(), pageable, 0);
//    }
//
//    @Transactional(readOnly = true)
//    public Result getAppById(Integer AppId, Principal principal) {
//        AdminEntity adminEntity = adminEntityRepository.getAdminUniversity(principal.getName()).get();
//        Optional<Application> application = applicationRepository.getAppOne(adminEntity.getDiplomaInstitution().getId(), AppId);
//        if (application.isEmpty()) {
//            return new Result(ResponseMessage.NOT_FOUND.getMessage(), false);
//        }
//        AppResponse appResponse = new AppResponse(application.get());
//        IIBRequest iibRequest = new IIBRequest();
//        iibRequest.setPinfl(application.get().getEnrolleeInfo().getPinfl());
//        iibRequest.setGiven_date(application.get().getEnrolleeInfo().getPassportGivenDate());
//        IIBResponse iibResponse = iibServiceApi.iibResponse(iibRequest);
//        Data data = iibResponse.getData();
//        ImageResponse imageResponse = new ImageResponse();
//        if (!data.getPhoto().isEmpty()) {
//            imageResponse.setImage(data.getPhoto());
//        }
//        StoryMessageResponse response = new StoryMessageResponse();
//        List<StoryM> app = new ArrayList<>();
//        List<StoryM> diplomaResponse = new ArrayList<>();
//        List<StoryMessage> messages = storyMessageRepository.getAllStoryByAppId(application.get().getId());
//        if (messages.size() > 0) {
//            messages.forEach(storyMessage -> {
//                if (storyMessage.getStatus().equals("Ariza qabul qilindi") || storyMessage.getStatus().equals("Ariza rad etildi")) {
//                    StoryM storyMessageResponse = new StoryM();
//                    storyMessageResponse.setMessage(storyMessage.getMessage());
//                    storyMessageResponse.setStatus(storyMessage.getStatus());
//                    storyMessageResponse.setTime(storyMessage.getCreatedDate());
//                    storyMessageResponse.setCreateBy(storyMessage.getFirstname() + " " + storyMessage.getLastname());
//                    app.add(storyMessageResponse);
//                } else if (storyMessage.getStatus().equals("true") || storyMessage.getStatus().equals("false")) {
//                    StoryM storyMessageResponse = new StoryM();
//                    storyMessageResponse.setMessage(storyMessage.getMessage());
//                    storyMessageResponse.setStatus(storyMessage.getStatus());
//                    storyMessageResponse.setTime(storyMessage.getCreatedDate());
//                    storyMessageResponse.setCreateBy(storyMessage.getFirstname() + " " + storyMessage.getLastname());
//                    diplomaResponse.add(storyMessageResponse);
//                }
//            });
//            response.setApp(app);
//            response.setDiploma(diplomaResponse);
//            appResponse.setStoryMessageResponse(response);
//        }
//        appResponse.setEnrolleeResponse(new EnrolleeResponse(application.get().getEnrolleeInfo(), imageResponse));
//        Diploma diploma = diplomaRepository.getDiplomaByEnrolleeInfoId(application.get().getEnrolleeInfo().getId()).get();
//        FileResponse fileResponse = getFileResponse(diploma.getId());
//        appResponse.setDiplomaResponse(new DiplomaResponse(diploma, fileResponse));
//        return new Result("one app", true, appResponse);
//    }
//
//    public FileResponse getFileResponse(Integer diplomaId) {
//        List<Document> documents = documentRepository.findAllByDiplomaId(diplomaId);
//        FileResponse fileResponse = new FileResponse();
//        documents.forEach(document -> {
//            if (document.getFileName() != null && document.getFileName().startsWith("Diplom")) {
//                DiplomaCopyResponse diplomaCopyResponse = new DiplomaCopyResponse();
//                diplomaCopyResponse.setId(document.getId());
//                diplomaCopyResponse.setUrl(document.getUrl());
//                fileResponse.setDiplomaCopyResponse(diplomaCopyResponse);
//            }
//            if (document.getFileName() != null && document.getFileName().startsWith("Ilova")) {
//                DiplomaIlovaResponse diplomaIlovaResponse = new DiplomaIlovaResponse();
//                diplomaIlovaResponse.setId(document.getId());
//                diplomaIlovaResponse.setUrl(document.getUrl());
//                fileResponse.setDiplomaIlovaResponse(diplomaIlovaResponse);
//            }
//        });
//        return fileResponse;
//    }
//
//    @Transactional(readOnly = true)
//    public UAdminInfoResponse getUAdmin(Principal principal) {
//        AdminEntity adminEntity = adminEntityRepository.getAdminUniversity(principal.getName()).get();
//        return new UAdminInfoResponse(adminEntity);
//    }
//
//    @Transactional
//    public Result updateStatusDiploma(Principal principal, UpdateDiplomaStatus updateDiplomaStatus, Integer diplomaId) {
//        try {
//            AdminEntity adminEntity = adminEntityRepository.getAdminUniversity(principal.getName()).get();
//            Integer institutionId = adminEntity.getUniversities().stream().map(University::getInstitutionId).findFirst().get();
//            Optional<Application> application = applicationRepository.getAppAndDiplomaById(institutionId, diplomaId);
//            if (application.isPresent()) {
//                application.get().setDiplomaStatus(updateDiplomaStatus.getDiplomStatus());
//                application.get().setDiplomaMessage(updateDiplomaStatus.getDiplomMessage());
//                Application save = applicationRepository.save(application.get());
//                StoryMessage storyMessage = new StoryMessage();
//                storyMessage.setMessage(updateDiplomaStatus.getDiplomMessage());
//                String status1 = String.valueOf(updateDiplomaStatus.getDiplomStatus());
//                storyMessage.setStatus(status1);
//                storyMessage.setFirstname(adminEntity.getFistName());
//                storyMessage.setLastname(adminEntity.getLastname());
//                storyMessage.setPinfl(principal.getName());
//                storyMessage.setApplication(save);
//                storyMessageRepository.save(storyMessage);
//                return new Result("Muvaffaqiyatli o'zgartirildi", true);
//            }
//            return new Result(ResponseMessage.NOT_FOUND.getMessage(), false);
//        } catch (Exception e) {
//            return new Result("O'zgartirishda xatolik", false);
//        }
//    }
//
//    @Transactional
//    public Result updateDiplomStatusbyApp(Principal principal, UpdateDiplomaStatus updateDiplomaStatus, Integer diplomaId) {
//        try {
//            AdminEntity adminEntity = adminEntityRepository.getAdminUniversity(principal.getName()).get();
//            Optional<Application> app = applicationRepository.getAppByUadmin(adminEntity.getDiplomaInstitution().getId(), diplomaId);
//            if (app.isPresent()) {
//                app.get().setDiplomaStatus(updateDiplomaStatus.getDiplomStatus());
//                app.get().setDiplomaMessage(updateDiplomaStatus.getDiplomMessage());
//                Application save = applicationRepository.save(app.get());
//                StoryMessage storyMessage = new StoryMessage();
//                storyMessage.setMessage(updateDiplomaStatus.getDiplomMessage());
//                String status1 = String.valueOf(updateDiplomaStatus.getDiplomStatus());
//                storyMessage.setStatus(status1);
//                storyMessage.setFirstname(adminEntity.getFistName());
//                storyMessage.setLastname(adminEntity.getLastname());
//                storyMessage.setPinfl(principal.getName());
//                storyMessage.setApplication(save);
//                storyMessageRepository.save(storyMessage);
//                return new Result("Muvaffaqiyatli o'zgartirildi", true);
//            }
//            return new Result(ResponseMessage.NOT_FOUND.getMessage(), false);
//        } catch (Exception e) {
//            return new Result("O'zgartirishda xatolik", false);
//        }
//    }
//
//    @Transactional
//    public Result updateStatusForeignDiploma(Principal principal, UpdateDiplomaStatus updateDiplomaStatus, Integer diplomaId) {
//        try {
//            AdminEntity adminEntity = adminEntityRepository.getAdminUniversity(principal.getName()).get();
//            Integer institutionId = adminEntity.getUniversities().stream().map(University::getInstitutionId).findFirst().get();
//            Optional<Application> application = applicationRepository.getAppAndForeignDiplomaById(adminEntity.getDiplomaInstitution().getId(), diplomaId);
//            if (application.isPresent()) {
//                String status = String.valueOf(application.get().getDiplomaStatus());
//                application.get().setDiplomaStatus(updateDiplomaStatus.getDiplomStatus());
//                application.get().setDiplomaMessage(updateDiplomaStatus.getDiplomMessage());
//                Application save = applicationRepository.save(application.get());
//                StoryMessage storyMessage = new StoryMessage();
//                storyMessage.setMessage(updateDiplomaStatus.getDiplomMessage());
//                String status1 = String.valueOf(updateDiplomaStatus.getDiplomStatus());
//                storyMessage.setStatus(status1);
//                storyMessage.setFirstname(adminEntity.getFistName());
//                storyMessage.setLastname(adminEntity.getLastname());
//                storyMessage.setPinfl(principal.getName());
//                storyMessage.setApplication(save);
//                storyMessageRepository.save(storyMessage);
//                return new Result("Muvaffaqiyatli o'zgartirildi", true);
//            }
//            return new Result(ResponseMessage.NOT_FOUND.getMessage(), false);
//        } catch (Exception e) {
//            return new Result("O'zgartirishda xatolik", false);
//        }
//    }
//
//    @Transactional
//    public Result updateStatusApp(Principal principal, UpdateAppStatus updateAppStatus, Integer appId) {
//        try {
//            AdminEntity adminEntity = adminEntityRepository.getAdminUniversity(principal.getName()).get();
//            Optional<Application> application = applicationRepository.getAppOne(adminEntity.getDiplomaInstitution().getId(), appId);
//            if (application.isPresent()) {
//                String status = String.valueOf(application.get().getDiplomaStatus());
//                switch (status) {
//                    case "true":
//                        application.get().setStatus(updateAppStatus.getAppStatus());
//                        application.get().setMessage(updateAppStatus.getAppMessage());
//                        applicationRepository.save(application.get());
//                        StoryMessage storyMessage = new StoryMessage();
//                        storyMessage.setMessage(updateAppStatus.getAppMessage());
//                        storyMessage.setStatus(updateAppStatus.getAppStatus());
//                        storyMessage.setFirstname(adminEntity.getFistName());
//                        storyMessage.setLastname(adminEntity.getLastname());
//                        storyMessage.setPinfl(principal.getName());
//                        storyMessage.setApplication(application.get());
//                        storyMessageRepository.save(storyMessage);
//                        return new Result("Muvaffaqiyatli tasdiqlandi", true);
//                    case "false":
//                        return new Result("bu arizaning diplomi rad etilgan", false);
//                    case "null":
//                        return new Result("ariza diplomi hali tasdiqlanmagan ", false);
//                }
//            }
//            return new Result(ResponseMessage.NOT_FOUND.getMessage(), false);
//        } catch (Exception e) {
//            return new Result("Tasdiqlashda xatolik", false);
//        }
//    }
//
//    @Transactional(readOnly = true)
//    public Page<AppResponse> searchAllAppByUAdmin(Principal principal, String status, String search, int page, int size) {
//        String s = search.toUpperCase();
//        if (page > 0) page = page - 1;
//        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
//        AdminEntity adminEntity = adminEntityRepository.getAdminUniversity(principal.getName()).get();
//        List<AppResponse> responses = new ArrayList<>();
//        Page<Application> allApp = applicationRepository.searchAppByFirstnameAndLastname(adminEntity.getDiplomaInstitution().getId(), status, s, pageable);
//        allApp.forEach(application -> {
//            AppResponse appResponse = new AppResponse(application);
//
//            appResponse.setEnrolleeResponse(new EnrolleeResponse(application.getEnrolleeInfo()));
//            Diploma diploma = diplomaRepository.getDiplomaByEnrolleeInfoId(application.getEnrolleeInfo().getId()).get();
//            FileResponse fileResponse = getFileResponse(diploma.getId());
//            appResponse.setDiplomaResponse(new DiplomaResponse(diploma, fileResponse));
//            responses.add(appResponse);
//        });
//        return new PageImpl<>(responses, pageable, allApp.getTotalElements());
//    }
//
//    @Transactional(readOnly = true)
//    public Page<AppResponse> searchAllAppByStatus(Principal principal, String diplomaStatus, String appStatus, String search, int page, int size) {
//        String s = search.toUpperCase();
//        if (page > 0) page = page - 1;
//        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
//        AdminEntity adminEntity = adminEntityRepository.getAdminUniversity(principal.getName()).get();
//        List<AppResponse> responses = new ArrayList<>();
//        if (diplomaStatus.equals("true") || diplomaStatus.equals("false")) {
//            Boolean aBoolean = Boolean.valueOf(diplomaStatus);
//
//            Page<Application> allApp = applicationRepository.
//                    searchAppByFirstnameAndLastnameByDiplomastatus(adminEntity.getDiplomaInstitution().getId(), appStatus, aBoolean, s, pageable);
//            allApp.forEach(application -> {
//                AppResponse appResponse = new AppResponse(application);
//                appResponse.setEnrolleeResponse(new EnrolleeResponse(application.getEnrolleeInfo()));
//                Diploma diploma = diplomaRepository.getDiplomaByEnrolleeInfoId(application.getEnrolleeInfo().getId()).get();
//                FileResponse fileResponse = getFileResponse(diploma.getId());
//                appResponse.setDiplomaResponse(new DiplomaResponse(diploma, fileResponse));
//                responses.add(appResponse);
//            });
//            return new PageImpl<>(responses, pageable, allApp.getTotalElements());
//        } else {
//            Page<Application> applications = applicationRepository.
//                    searchAppByFirstnameAndLastnameByDiplomastatusIsNull(adminEntity.getDiplomaInstitution().getId(), appStatus, s, pageable);
//            applications.forEach(application -> {
//                AppResponse appResponse = new AppResponse(application);
//                appResponse.setEnrolleeResponse(new EnrolleeResponse(application.getEnrolleeInfo()));
//                Diploma diploma = diplomaRepository.getDiplomaByEnrolleeInfoId(application.getEnrolleeInfo().getId()).get();
//                FileResponse fileResponse = getFileResponse(diploma.getId());
//                appResponse.setDiplomaResponse(new DiplomaResponse(diploma, fileResponse));
//                responses.add(appResponse);
//            });
//            return new PageImpl<>(responses, pageable, applications.getTotalElements());
//        }
//    }
//
//    @Transactional(readOnly = true)
//    public Page<DiplomResponseAdmin> searchDiplomasByUAdmin(Principal principal, String status, String search, int page, int size) {
//        String s = search.toUpperCase();
//
//        if (page > 0) page = page - 1;
//        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
//        AdminEntity adminEntity = adminEntityRepository.getAdminUniversity(principal.getName()).get();
//        Integer institutionId = adminEntity.getUniversities().stream().map(University::getInstitutionId).findFirst().get();
//        List<DiplomResponseAdmin> diplomResponseAdmins = new ArrayList<>();
//        if (status.equals("true") || status.equals("false")) {
//            Boolean aBoolean = Boolean.valueOf(status);
//            Page<Application> allDiplomebyUAdmin = applicationRepository.searchDiplomaByUAdmin(institutionId, aBoolean, s, pageable);
//            allDiplomebyUAdmin.forEach(application -> {
//                Diploma diploma = diplomaRepository.getDiplomaByEnrolleeInfoId(application.getEnrolleeInfo().getId()).get();
//                FileResponse fileResponse = getFileResponse(diploma.getId());
//                DiplomResponseAdmin diplomResponseAdmin = new DiplomResponseAdmin(diploma, fileResponse);
//                diplomResponseAdmin.setEnrolleeResponse(enrolleeResponse);
//                if (application.getDiplomaStatus() != null) {
//                    diplomResponseAdmin.setDiplomaStatus(application.getDiplomaStatus().toString());
//                }
//                diplomResponseAdmins.add(diplomResponseAdmin);
//            });
//            return new PageImpl<>(diplomResponseAdmins, pageable, allDiplomebyUAdmin.getTotalElements());
//
//        } else {
//            Page<Application> allDiplomebyUAdmin = applicationRepository.searchDiplomStatusNull(institutionId, s, pageable);
//            allDiplomebyUAdmin.forEach(application -> {
//                Diploma diploma = diplomaRepository.getDiplomaByEnrolleeInfoId(application.getEnrolleeInfo().getId()).get();
//                FileResponse fileResponse = getFileResponse(diploma.getId());
//                DiplomResponseAdmin diplomResponseAdmin = new DiplomResponseAdmin(diploma, fileResponse);
//                diplomResponseAdmin.setEnrolleeResponse(enrolleeResponse);
//                if (application.getDiplomaStatus() != null) {
//                    diplomResponseAdmin.setDiplomaStatus(application.getDiplomaStatus().toString());
//                }
//                diplomResponseAdmins.add(diplomResponseAdmin);
//            });
//            return new PageImpl<>(diplomResponseAdmins, pageable, allDiplomebyUAdmin.getTotalElements());
//        }
//    }
//
//    //horijiy diplomlarni obshiysi
//    @Transactional(readOnly = true)
//    public Page<DiplomResponseAdmin> searchForeignDiplomas(Principal principal, String status, String search, int page, int size) {
//        String s = search.toUpperCase();
//        if (page > 0) page = page - 1;
//        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
//        List<DiplomResponseAdmin> diplomResponseAdmins = new ArrayList<>();
//        AdminEntity adminEntity = adminEntityRepository.getAdminUniversity(principal.getName()).get();
//        if (status.equals("true") || status.equals("false")) {
//            Boolean aBoolean = Boolean.valueOf(status);
//            Page<Application> allDiplomebyUAdmin = applicationRepository.searchForeignDiplomas(adminEntity.getDiplomaInstitution().getId(), aBoolean, s, pageable);
//            allDiplomebyUAdmin.forEach(application -> {
//                IIBRequest iibRequest = new IIBRequest();
//                iibRequest.setPinfl(application.getEnrolleeInfo().getPinfl());
//                iibRequest.setGiven_date(application.getEnrolleeInfo().getPassportGivenDate());
//                IIBResponse iibResponse = iibServiceApi.iibResponse(iibRequest);
//                Data data = iibResponse.getData();
//                ImageResponse imageResponse = new ImageResponse();
//                if (!data.getPhoto().isEmpty()) {
//                    imageResponse.setImage(data.getPhoto());
//                }
//                Diploma diploma = diplomaRepository.getDiplomaByEnrolleeInfoId(application.getEnrolleeInfo().getId()).get();
//                FileResponse fileResponse = getFileResponse(diploma.getId());
//                DiplomResponseAdmin diplomResponseAdmin = new DiplomResponseAdmin(diploma, fileResponse);
//                diplomResponseAdmin.setEnrolleeResponse(enrolleeResponse);
//                if (application.getDiplomaStatus() != null) {
//                    diplomResponseAdmin.setDiplomaStatus(application.getDiplomaStatus().toString());
//                }
//                diplomResponseAdmins.add(diplomResponseAdmin);
//            });
//            return new PageImpl<>(diplomResponseAdmins, pageable, allDiplomebyUAdmin.getTotalElements());
//        } else {
//            Page<Application> allDiplomebyUAdmin = applicationRepository.searchForeignDiplomaStatusNull(adminEntity.getDiplomaInstitution().getId(), s, pageable);
//            allDiplomebyUAdmin.forEach(application -> {
//                IIBRequest iibRequest = new IIBRequest();
//                iibRequest.setPinfl(application.getEnrolleeInfo().getPinfl());
//                iibRequest.setGiven_date(application.getEnrolleeInfo().getPassportGivenDate());
//                IIBResponse iibResponse = iibServiceApi.iibResponse(iibRequest);
//                Data data = iibResponse.getData();
//                ImageResponse imageResponse = new ImageResponse();
//                if (!data.getPhoto().isEmpty()) {
//                    imageResponse.setImage(data.getPhoto());
//                }
//                Diploma diploma = diplomaRepository.getDiplomaByEnrolleeInfoId(application.getEnrolleeInfo().getId()).get();
//                FileResponse fileResponse = getFileResponse(diploma.getId());
//                DiplomResponseAdmin diplomResponseAdmin = new DiplomResponseAdmin(diploma, fileResponse);
//                diplomResponseAdmin.setEnrolleeResponse(enrolleeResponse);
//                if (application.getDiplomaStatus() != null) {
//                    diplomResponseAdmin.setDiplomaStatus(application.getDiplomaStatus().toString());
//                }
//                diplomResponseAdmins.add(diplomResponseAdmin);
//            });
//            return new PageImpl<>(diplomResponseAdmins, pageable, allDiplomebyUAdmin.getTotalElements());
//        }
//    }
//
//    @Transactional(readOnly = true)
//    public CountByUAdmin getAllCountByUAdmin(Principal principal) {
//        AdminEntity adminEntity = adminEntityRepository.getAdminUniversity(principal.getName()).get();
//        Integer institutionId = adminEntity.getUniversities().stream().map(University::getInstitutionId).findFirst().get();
//        List<CountApp> countDiploma = applicationRepository.getCountDiploma(institutionId);
//        CountByUAdmin response = new CountByUAdmin();
//        if (adminEntity.getDiplomaInstitution() != null) {
//            List<CountApp> countApp = applicationRepository.getCountApp(adminEntity.getDiplomaInstitution().getId());
//            List<CountApp> countAppByDiplomaStatus = applicationRepository.getCountAppByDiplomaStatus(adminEntity.getDiplomaInstitution().getId());
//            List<CountApp> countForeignDiploma = applicationRepository.getCountForeignDiploma(adminEntity.getDiplomaInstitution().getId());
//            response.setCountApp(countApp);
//            response.setCountForeignDiploma(countForeignDiploma);
//            response.setCountAppByDiplomaStatus(countAppByDiplomaStatus);
//        }
//        response.setCountDiploma(countDiploma);
//        return response;
//    }
//
//    @Transactional(readOnly = true)
//    public List<GetCountAppallDate> getCountAppandTodayByUAdmin(Principal principal) {
//        AdminEntity adminEntity = adminEntityRepository.getAdminUniversity(principal.getName()).get();
//        return applicationRepository.getAppCountTodayByUAdmin(adminEntity.getDiplomaInstitution().getId());
//    }
//
//    @Transactional(readOnly = true)
//    public List<GetAppByGender> getCountAppandGenderByUAdmin(Principal principal) {
//        AdminEntity adminEntity = adminEntityRepository.getAdminUniversity(principal.getName()).get();
//        List<GetAppByGender> counAppAndGenderByUAdmin = applicationRepository.getCounAppAndGenderByUAdmin(adminEntity.getDiplomaInstitution().getId());
//
//        return counAppAndGenderByUAdmin;
//    }
//
//    @Transactional(readOnly = true)
//    public CountGenderAndDiplomaAndApp getCountForeignAndDiplomaandGenderAndTodayByUAdmin(Principal principal) {
//        AdminEntity adminEntity = adminEntityRepository.getAdminUniversity(principal.getName()).get();
//        Integer institutionId = adminEntity.getUniversities().stream().map(University::getInstitutionId).findFirst().get();
//        List<GetAppByGender> diplomaAndGender = applicationRepository.getCountDiplomaAndGender(institutionId);
//        CountGenderAndDiplomaAndApp statistik = new CountGenderAndDiplomaAndApp();
//        List<GetCountAppallDate> diplomaCountTodayByUAdmin = applicationRepository.getDiplomaCountTodayByUAdmin(institutionId);
//        statistik.setDiplomaGenderCount(diplomaAndGender);
//        statistik.setDiplomaCountToday(diplomaCountTodayByUAdmin);
//        List<GetCountAppallDate> foreignDiplomaCountTodayByUAdmin = applicationRepository.getForeignDiplomaCountTodayByUAdmin(adminEntity.getDiplomaInstitution().getId());
//        List<GetAppByGender> countForeingDiplomaAndGender = applicationRepository.getCountForeingDiplomaAndGender(adminEntity.getDiplomaInstitution().getId());
//        statistik.setForeigndiplomaGenderCount(countForeingDiplomaAndGender);
//        statistik.setForeigndiplomaCountToday(foreignDiplomaCountTodayByUAdmin);
//        return statistik;
//    }
//
//    @Transactional(readOnly = true)
//    public List<GetDiplomasToExcel> getDiplomasToExcel(Principal principal, String status) {
//        AdminEntity adminEntity = adminEntityRepository.getAdminUniversity(principal.getName()).get();
//        Integer institutionId = adminEntity.getUniversities().stream().map(University::getInstitutionId).findFirst().get();
//        if (status.equals("true") || status.equals("false")) {
//            Boolean aBoolean = Boolean.valueOf(status);
//            return applicationRepository.exportDiplomaToExcel(institutionId, aBoolean);
//        }
//        return applicationRepository.exportDiplomaNullToExcel(institutionId);
//    }
//
//    @Transactional(readOnly = true)
//    public List<GetDiplomasToExcel> getForeignDiplomasToExcel(Principal principal, String status) {
//        AdminEntity adminEntity = adminEntityRepository.getAdminUniversity(principal.getName()).get();
//        Integer institutionId = adminEntity.getUniversities().stream().map(University::getInstitutionId).findFirst().get();
//        if (status.equals("true") || status.equals("false")) {
//            Boolean aBoolean = Boolean.valueOf(status);
//            return applicationRepository.exportForeignDiplomaToExcel(adminEntity.getDiplomaInstitution().getId(), aBoolean);
//        }
//        return applicationRepository.exportForeignDiplomaNullToExcel(adminEntity.getDiplomaInstitution().getId());
//    }
//
//    @Transactional(readOnly = true)
//    public List<GetAppToExcel> getAppToExcel(Principal principal, String appStatus, String diplomaStatus) {
//        AdminEntity adminEntity = adminEntityRepository.getAdminUniversity(principal.getName()).get();
//        Integer institutionId = adminEntity.getUniversities().stream().map(University::getInstitutionId).findFirst().get();
//        if (diplomaStatus.equals("true") || diplomaStatus.equals("false")) {
//            Boolean aBoolean = Boolean.valueOf(diplomaStatus);
//            return applicationRepository.exportAppDiplomaTrueToExcel(adminEntity.getDiplomaInstitution().getId());
//        } else if (diplomaStatus.equals("null")) {
//            return applicationRepository.exportAppByDiplomaNullToExcel(adminEntity.getDiplomaInstitution().getId());
//        } else {
//            return applicationRepository.exportAppToExcel(adminEntity.getDiplomaInstitution().getId(), appStatus);
//        }
//    }
//
//
//
//
//
//
//
//
//
//
//
//}