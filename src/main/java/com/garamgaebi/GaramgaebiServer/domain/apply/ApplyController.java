package com.garamgaebi.GaramgaebiServer.domain.apply;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/program/{path}")
@RequiredArgsConstructor
public class ApplyController {

    @PostMapping("/")
}
