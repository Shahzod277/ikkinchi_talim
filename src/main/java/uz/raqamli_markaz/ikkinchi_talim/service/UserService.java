//package uz.raqamli_markaz.ikkinchi_talim.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//import uz.raqamli_markaz.ikkinchi_talim.api.iib_api.Data;
//import uz.raqamli_markaz.ikkinchi_talim.api.iib_api.IIBResponse;
//import uz.raqamli_markaz.ikkinchi_talim.api.iib_api.IIBServiceApi;
//import uz.raqamli_markaz.ikkinchi_talim.domain.Application;
//import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.Diploma;
//import uz.raqamli_markaz.ikkinchi_talim.domain.Document;
//import uz.raqamli_markaz.ikkinchi_talim.domain.StoryMessage;
//import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.University;
//import uz.raqamli_markaz.ikkinchi_talim.model.request.IIBRequest;
//import uz.raqamli_markaz.ikkinchi_talim.model.response.*;
//import uz.raqamli_markaz.ikkinchi_talim.repository.*;
//
//import java.security.Principal;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class UserService {
//
//    private final DiplomaRepository diplomaRepository;
//    private final DocumentService documentService;
//    private final ApplicationRepository applicationRepository;
//    private final DiplomaOldInstitutionRepository diplomaOldInstitutionRepository;
//    private final IIBServiceApi iibServiceApi;
//    private final FileService fileService;
//    private final DocumentRepository documentRepository;
//    private final StoryMessageRepository storyMessageRepository;
//    private final UserRepository userRepository;

//
//    @Transactional(readOnly = true)
//    public EnrolleeResponse getEnrolleeResponse(Principal principal) {
//        try {
//
//            IIBRequest iibRequest = new IIBRequest();
//            iibRequest.setPinfl(enrolleeInfo.getPinfl());
//            iibRequest.setGiven_date(enrolleeInfo.getPassportGivenDate());
//            IIBResponse iibResponse = iibServiceApi.iibResponse(iibRequest);
//            Data data = iibResponse.getData();
//            ImageResponse imageResponse = new ImageResponse();
//            if (!data.getPhoto().isEmpty()) {
//                imageResponse.setImage(data.getPhoto());
//            }
//            //            Optional<ApplicationResponse> applicationResponse = applicationRepository.findByAppByPrincipal(enrolleeInfo.getId());
////            if (applicationResponse.isPresent()) {
////                enrolleeResponse.setApplicationResponse(applicationResponse.get());
////            }else {
////                enrolleeResponse.setApplicationResponse(null);
////            }
//            return new EnrolleeResponse(enrolleeInfo, imageResponse);
//        } catch (Exception ex) {
//            return new EnrolleeResponse();
//        }
//    }
//
//    @Transactional(readOnly = true)
//    public List<DiplomaResponse> getDiplomasByEnrolleeInfo(Principal principal) {
//        List<Diploma> diplomas = diplomaRepository.findAllByEnrolleeInfo(principal.getName());
//        List<DiplomaResponse> diplomaResponses = new ArrayList<>();
//        diplomas.forEach(diploma -> {
//            DiplomaResponse diplomaResponse = new DiplomaResponse(diploma);
//            FileResponse fileResponse = documentService.getFileResponse(diploma.getId());
//            if (fileResponse != null) {
//                diplomaResponse.setFileResponse(fileResponse);
//            }
//            diplomaResponses.add(diplomaResponse);
//        });
//        return diplomaResponses;
//    }
//
//    @Transactional(readOnly = true)
//    public DiplomaResponse getDiplomaByIdAndEnrolleInfo(Principal principal, Integer diplomaId) {
//        try {
//            Diploma diploma = diplomaRepository.findDiplomaByDiplomaId(principal.getName(), diplomaId).get();
//            FileResponse fileResponse = documentService.getFileResponse(diploma.getId());
//            return new DiplomaResponse(diploma, fileResponse);
//        } catch (Exception ex) {
//            return new DiplomaResponse();
//        }
//    }
//
//    @Transactional
//    public Result documentSave(int diplomaId, MultipartFile diplomaCopy, MultipartFile diplomaIlova) {
//
//        try {
//            Diploma diploma = diplomaRepository.findById(diplomaId).get();
//            List<Document> documents = new ArrayList<>();
//            if (diplomaCopy != null) {
//                Document diplom = new Document();
//                String diplomName = fileService.upload(diplomaCopy, "Diplom");
//                String currentUrl = getCurrentUrl(diplomName);
//                diplom.setUrl(currentUrl);
//                diplom.setFileName(diplomName);
//                diplom.setDiploma(diploma);
//                documents.add(diplom);
//            }
//            if (diplomaIlova != null) {
//                Document diplomIlova = new Document();
//                String ilovaName = fileService.upload(diplomaIlova, "Ilova");
//                String currentUrl = getCurrentUrl(ilovaName);
//                diplomIlova.setUrl(currentUrl);
//                diplomIlova.setFileName(ilovaName);
//                diplomIlova.setDiploma(diploma);
//                documents.add(diplomIlova);
//            }
//            documentRepository.saveAll(documents);
//            return new Result(ResponseMessage.SUCCESSFULLY_SAVED.getMessage(), true);
//        } catch (Exception ex) {
//            return new Result(ResponseMessage.ERROR_SAVED.getMessage(), false);
//        }
//    }
//
//    @Transactional
//    public Result documentUpdate(Diploma diploma, Integer docDiplomId, Integer docIlovaId, MultipartFile docDiplom, MultipartFile docIlova) {
//        try {
//            List<Document> documents = new ArrayList<>();
//            if (docDiplomId != null) {
//                if (docDiplom != null) {
//                    Document document = documentRepository.findById(docDiplomId).get();
//                    fileService.deleteDocument(document);
//                    String diplomName = fileService.upload(docDiplom, "Diplom");
//                    String currentUrl = getCurrentUrl(diplomName);
//                    document.setUrl(currentUrl);
//                    document.setFileName(diplomName);
//                    document.setModifiedDate(LocalDateTime.now());
//                    documents.add(document);
//                }
//            } else {
//                Document document = new Document();
//                String diplomName = fileService.upload(docDiplom, "Diplom");
//                String currentUrl = getCurrentUrl(diplomName);
//                document.setFileName(diplomName);
//                document.setUrl(currentUrl);
//                document.setDiploma(diploma);
//                documents.add(document);
//            }
//            if (docIlovaId != null) {
//                if (docIlova != null) {
//                    Document documentIlova = documentRepository.findById(docIlovaId).get();
//                    fileService.deleteDocument(documentIlova);
//                    String diplomIlovaName = fileService.upload(docIlova, "Ilova");
//                    String currentUrl = getCurrentUrl(diplomIlovaName);
//                    documentIlova.setUrl(currentUrl);
//                    documentIlova.setFileName(diplomIlovaName);
//                    documentIlova.setModifiedDate(LocalDateTime.now());
//                    documents.add(documentIlova);
//                }
//            } else {
//                Document documentIlova = new Document();
//                String diplomIlovaName = fileService.upload(docIlova, "Ilova");
//                String currentUrl = getCurrentUrl(diplomIlovaName);
//                documentIlova.setFileName(diplomIlovaName);
//                documentIlova.setUrl(currentUrl);
//                documentIlova.setDiploma(diploma);
//                documents.add(documentIlova);
//            }
//
//            if (documents.size() > 0) {
//                documentRepository.saveAll(documents);
//            }
//            return new Result(ResponseMessage.SUCCESSFULLY_UPDATE.getMessage(), true);
//        } catch (Exception ex) {
//            return new Result(ResponseMessage.ERROR_UPDATE.getMessage(), false);
//        }
//    }

//}
