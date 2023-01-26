package com.garamgaebi.GaramgaebiServer.domain.apply;

import com.garamgaebi.GaramgaebiServer.domain.entity.Apply;
import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import com.garamgaebi.GaramgaebiServer.domain.program.service.ProgramServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applies")
public class ApplyController {

    private final ApplyService applyService;

    @Autowired
    public ApplyController(ApplyService applyService) {this.applyService = applyService;}

    @PostMapping("/programs/{id}/enroll")
    public Long enroll(Member member,@PathVariable String path, @PathVariable("id") Program program, @RequestBody ApplyDto applyDto) {
        Program program1 = applyService.getProgramToEnroll(path);
        applyService.enroll(program, member);
        return applyService.enrollMember(applyDto);
    }

    @PostMapping("/programs/{id}/leave")
    public Long leave(Member member, @PathVariable String path, @PathVariable("id") Program program, @RequestBody ApplyDto applyDto) {
        Program program1 = applyService.getProgramToEnroll(path);
        applyService.leave(program, member);
        return applyService.enrollMember(applyDto);
    }
}

