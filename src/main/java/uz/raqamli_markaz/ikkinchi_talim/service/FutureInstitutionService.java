package uz.raqamli_markaz.ikkinchi_talim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.raqamli_markaz.ikkinchi_talim.domain.classificator.FutureInstitution;
import uz.raqamli_markaz.ikkinchi_talim.model.request.FutureInstitutionRequest;
import uz.raqamli_markaz.ikkinchi_talim.model.response.FutureInstitutionResponse;
import uz.raqamli_markaz.ikkinchi_talim.model.response.ResponseMessage;
import uz.raqamli_markaz.ikkinchi_talim.model.response.Result;
import uz.raqamli_markaz.ikkinchi_talim.repository.FutureInstitutionRepository;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FutureInstitutionService {

    private final FutureInstitutionRepository futureInstitutionRepository;

    public Result createFutureInstitution(FutureInstitutionRequest request) {
        try {
            FutureInstitution futureInstitution = new FutureInstitution();
            futureInstitution.setName(request.getName());
            futureInstitutionRepository.save(futureInstitution);
            return new Result(ResponseMessage.SUCCESSFULLY_SAVED.getMessage(), true, new FutureInstitutionResponse(futureInstitution));
        } catch (Exception ex) {
            return new Result(ResponseMessage.ERROR_SAVED.getMessage(), false);
        }
    }

    public Result updateFutureInstitution(int id, FutureInstitutionRequest request) {
        try {
            FutureInstitution futureInstitution = futureInstitutionRepository.findById(id).get();
            futureInstitution.setName(request.getName());
            futureInstitution.setModifiedDate(LocalDateTime.now());
            futureInstitutionRepository.save(futureInstitution);
            return new Result(ResponseMessage.SUCCESSFULLY_UPDATE.getMessage(), true, new FutureInstitutionResponse(futureInstitution));
        } catch (Exception ex) {
            return new Result(ResponseMessage.ERROR_UPDATE.getMessage(), false);
        }
    }

    public List<FutureInstitutionResponse> getAllFutureInstitution() {
        return futureInstitutionRepository.findAll().stream()
                .map(FutureInstitutionResponse::new).toList();
    }

    public FutureInstitutionResponse getFutureInstitutionById(int futureInstitutionId) {
        try {
            FutureInstitution futureInstitution = futureInstitutionRepository.findById(futureInstitutionId).get();
            return new FutureInstitutionResponse(futureInstitution);
        } catch (Exception ex) {
            return new FutureInstitutionResponse();
        }
    }
    public Result deleteFutureInstitution(int futureInstitutionId) {
        try {
            futureInstitutionRepository.deleteById(futureInstitutionId);
            return new Result(ResponseMessage.SUCCESSFULLY_DELETED.getMessage(), true);
        } catch (Exception ex) {
            return new Result(ResponseMessage.ERROR_DELETED.getMessage(), false);
        }
    }
    @Transactional(readOnly = true)
    public Page<FutureInstitutionResponse> getAllFutureInstitution(int page, int size) {
        if (page > 0) page = page - 1;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return futureInstitutionRepository.findAll(pageable)
                .map(FutureInstitutionResponse::new);
    }
    @Transactional(readOnly = true)
    public Page<FutureInstitutionResponse> searchFutureInstitution(String text, int page, int size) {
        if (page > 0) page = page - 1;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return futureInstitutionRepository.searchFuturuInst(text, pageable)
                .map(FutureInstitutionResponse::new);
    }
}
