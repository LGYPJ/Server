package com.garamgaebi.GaramgaebiServer.domain.apply;

import com.garamgaebi.GaramgaebiServer.domain.entity.Apply;
import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import com.garamgaebi.GaramgaebiServer.domain.program.service.ProgramServiceImpl;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/applies")
public class ApplyController {

    private final ApplyService applyService;

    @Autowired
    public ApplyController(ApplyService applyService) {this.applyService = applyService;}

    @PostMapping("/programs/{id}/enroll")
    public BaseResponse<Long> enroll(@RequestBody @Valid ApplyDto applyDto) {


        return new BaseResponse<>(applyService.enroll(applyDto));
    }

    @PostMapping("/programs/{id}/leave")
    public BaseResponse<Long> leave(@RequestBody @Valid ApplyCancelDto applyCancelDto) {

        return new BaseResponse<>(applyService.leave(applyCancelDto));
    }
}


