package com.garamgaebi.GaramgaebiServer.domain.apply;

import com.garamgaebi.GaramgaebiServer.domain.entity.Apply;
import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import com.garamgaebi.GaramgaebiServer.domain.program.service.ProgramServiceImpl;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Tag(name = "ApplyController", description = "프로그램 신청 및 취소 컨트롤러(담당자:줄리아)")
@RestController
@RequestMapping("/applies")
public class ApplyController {

    private final ApplyService applyService;

    @Autowired
    public ApplyController(ApplyService applyService) {this.applyService = applyService;}

    @PostMapping("/programs/enroll")
    public BaseResponse<Long> enroll(@RequestBody @Valid ApplyDto applyDto) {


        return new BaseResponse<>(applyService.enroll(applyDto));
    }

    @PostMapping("/programs/leave")
    public BaseResponse<Long> leave(@RequestBody @Valid ApplyCancelDto applyCancelDto) {

        return new BaseResponse<>(applyService.leave(applyCancelDto));
    }

    @GetMapping("/{member-idx}/{program-idx}/info")
    public BaseResponse<GetApplyRes> getApplyInfo(@PathVariable("member-idx") Long memberIdx, @PathVariable("program-idx") Long programIdx) {

        return new BaseResponse<GetApplyRes>(applyService.findApplyInfo(memberIdx, programIdx));
    }
}


