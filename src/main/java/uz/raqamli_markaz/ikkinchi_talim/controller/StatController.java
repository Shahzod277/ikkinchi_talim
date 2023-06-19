package uz.raqamli_markaz.ikkinchi_talim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.raqamli_markaz.ikkinchi_talim.api.iib_api.IIBServiceApi;
import uz.raqamli_markaz.ikkinchi_talim.model.request.IIBRequest;
import uz.raqamli_markaz.ikkinchi_talim.model.response.*;
import uz.raqamli_markaz.ikkinchi_talim.repository.ApplicationRepository;
import uz.raqamli_markaz.ikkinchi_talim.repository.DirectionRepository;
import uz.raqamli_markaz.ikkinchi_talim.service.StatService;
import java.util.List;

@RestController
@RequestMapping("/api/statistic/")
@RequiredArgsConstructor
public class StatController {

    private final ApplicationRepository applicationRepository;
    private final StatService statService;
    private final IIBServiceApi iibServiceApi;
    private final DirectionRepository directionRepository;

    @GetMapping("count")
    public ResponseEntity<?> getCountAndTodayCount() {
        GetStatAllCountAndToday getStatAllCountAndToday = applicationRepository.getCountTodayAndAllCount().get();
        return ResponseEntity.ok(getStatAllCountAndToday);
    }

    @GetMapping("{futureInstId}")
    public ResponseEntity<?> getStatByFutureInst(@PathVariable int futureInstId) {
        List<StatisDirectionResponse> allStatis = statService.getAllStatis(futureInstId);
        return ResponseEntity.ok(allStatis);
    }

    @PostMapping("IIBCheck")
    public String checkIIB(@RequestBody IIBRequest request) {
        return statService.checkIIB(request);
    }

    ////STATISTIC ALL UNIVERSITY

    @GetMapping("acceptAndRejectAndDiploma")
    public List<AcceptAndRejectAndCheckDiploma> getAllStatisticByUniver() {
        return statService.statisticAllUniversity();
    }

    @GetMapping("getAllStatistic")
    public AcceptAndRejectAndCheckDiploma getAllStatistic() {
        return statService.allCountAppAdmin();
    }

    @GetMapping("countAppAndToday")
    public ResponseEntity<?> countAppAndToday() {
        List<GetCountAppallDate> countAppandTodayByUAdmin = statService.getCountAppandTodayAdmin();
        return ResponseEntity.ok(countAppandTodayByUAdmin);
    }

    @GetMapping("countAppAndGender")
    public ResponseEntity<?> countAppAndGender() {
        List<GetAppByGender> countAppandTodayByUAdmin = statService.getCountAppandGenderUAdmin();
        return ResponseEntity.ok(countAppandTodayByUAdmin);
    }

    @GetMapping("countDiploma")
    public ResponseEntity<?> countDiploma() {
        List<DiplomaAdminResponse> diplomaCountByAdmin = statService.getDiplomaCountByAdmin();
        return ResponseEntity.ok(diplomaCountByAdmin);
    }

    @GetMapping("countForeignDiploma")
    public ResponseEntity<?> countForeignDiploma() {
        List<DiplomaAdminResponse> diplomaCountByAdmin = statService.getForeignDConunt();
        return ResponseEntity.ok(diplomaCountByAdmin);
    }

    @GetMapping("countForeignDiplomaAndDiplomaGAndToday")
    public ResponseEntity<?> countForeignDiplomaAndDiplomaGAndToday() {
        CountGenderAndDiplomaAndApp countForeignAndDiplomaandGenderAndTodayByAdmin = statService.getCountForeignAndDiplomaandGenderAndTodayByAdmin();
        return ResponseEntity.ok(countForeignAndDiplomaandGenderAndTodayByAdmin);
    }

    @GetMapping("countAllDiplomaAndForeign")
    public ResponseEntity<?> countAllDiplomaAndForeign() {
        ForeignAndDiplomaAllCount allCount = statService.allCountDiplomaAndForeignAdmin();
        return ResponseEntity.ok(allCount);
    }

    @GetMapping("test")
    public void test() {
      statService.test2();
    }
}
