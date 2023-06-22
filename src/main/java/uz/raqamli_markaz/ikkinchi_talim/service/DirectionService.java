//package uz.raqamli_markaz.ikkinchi_talim.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.Direction;
//import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaInstitution;
//import uz.raqamli_markaz.ikkinchi_talim.model.request.DirectionRequest;
//import uz.raqamli_markaz.ikkinchi_talim.model.response.DirectionResponse;
//import uz.raqamli_markaz.ikkinchi_talim.model.response.ResponseMessage;
//import uz.raqamli_markaz.ikkinchi_talim.model.response.Result;
//import uz.raqamli_markaz.ikkinchi_talim.repository.DirectionRepository;
//import uz.raqamli_markaz.ikkinchi_talim.repository.DiplomaInstitutionRepository;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class DirectionService {
//
//    private final DirectionRepository directionRepository;
//    private final DiplomaInstitutionRepository diplomaInstitutionRepository;
//
//    @Transactional
//    public Result createDirection(DirectionRequest request) {
//
//        try {
//            Direction direction = new Direction();
//            direction.setName(request.getName());
//            DiplomaInstitution diplomaInstitution = diplomaInstitutionRepository.findById(request.getFutureInstitutionId()).get();
//            direction.setDiplomaInstitution(diplomaInstitution);
//            directionRepository.save(direction);
//            return new Result(ResponseMessage.SUCCESSFULLY_SAVED.getMessage(), true, new DirectionResponse(direction));
//        } catch (Exception ex) {
//            return new Result(ResponseMessage.ERROR_SAVED.getMessage(), false);
//        }
//    }
//
//    @Transactional
//    public Result updateDirection(int directionId, DirectionRequest request) {
//        try {
//            Direction direction = directionRepository.findById(directionId).get();
//            direction.setName(request.getName());
//            directionRepository.save(direction);
//            return new Result(ResponseMessage.SUCCESSFULLY_SAVED.getMessage(), true, new DirectionResponse(direction));
//        } catch (Exception ex) {
//            return new Result(ResponseMessage.ERROR_SAVED.getMessage(), false);
//        }
//    }
//
//    @Transactional(readOnly = true)
//    public List<DirectionResponse> getAllDirectionByFutureInst(Integer futureInstitutionId) {
//        List<Direction> directions = directionRepository.findAllByFutureInstitutionId(futureInstitutionId);
//        List<DirectionResponse> directionResponses = new ArrayList<>();
//        directions.forEach(d -> {
//            DirectionResponse directionResponse = new DirectionResponse();
//            directionResponse.setId(d.getId());
//            directionResponse.setName(d.getName());
//            directionResponse.setFutureInstitutionId(d.getDiplomaInstitution().getId());
//            directionResponse.setFutureInstitutionName(d.getDiplomaInstitution().getName());
//            directionResponses.add(directionResponse);
//        });
//        return directionResponses;
//    }
//
//    @Transactional(readOnly = true)
//    public List<DirectionResponse> getAllDirection() {
//        List<Direction> directions = directionRepository.findAll();
//        List<DirectionResponse> directionResponses = new ArrayList<>();
//        directions.forEach(d -> {
//            DirectionResponse directionResponse = new DirectionResponse();
//            directionResponse.setId(d.getId());
//            directionResponse.setName(d.getName());
//            directionResponse.setFutureInstitutionId(d.getDiplomaInstitution().getId());
//            directionResponse.setFutureInstitutionName(d.getDiplomaInstitution().getName());
//            directionResponses.add(directionResponse);
//        });
//        return directionResponses;
//    }
//
//    @Transactional(readOnly = true)
//    public DirectionResponse getDirectionById(int directionId) {
//        try {
//            Direction direction = directionRepository.findById(directionId).get();
//            DirectionResponse directionResponse = new DirectionResponse();
//            directionResponse.setId(direction.getId());
//            directionResponse.setName(direction.getName());
//            directionResponse.setFutureInstitutionId(direction.getDiplomaInstitution().getId());
//            directionResponse.setFutureInstitutionName(direction.getDiplomaInstitution().getName());
//            return directionResponse;
//        } catch (Exception ex) {
//            return new DirectionResponse();
//        }
//    }
//
//    @Transactional
//    public Result deleteDirection(int directionId) {
//        try {
//            directionRepository.deleteById(directionId);
//            return new Result(ResponseMessage.SUCCESSFULLY_DELETED.getMessage(), true);
//        } catch (Exception ex) {
//            return new Result(ResponseMessage.ERROR_DELETED.getMessage(), false);
//        }
//    }
//
//    @Transactional(readOnly = true)
//    public Page<DirectionResponse> getDirectionPageable(int page, int size) {
//        if (page > 0) page = page - 1;
//        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
//        return directionRepository.findAll(pageable).map(DirectionResponse::new);
//    }
//
//    @Transactional(readOnly = true)
//    public Page<DirectionResponse> searchDirection(String text, int page, int size) {
//        if (page > 0) page = page - 1;
//        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
//        return directionRepository.findDirectionByNameLike(text, pageable).map(DirectionResponse::new);
//    }
//
//}
