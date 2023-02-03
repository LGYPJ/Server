package com.garamgaebi.GaramgaebiServer.domain.notification.controller;

import com.garamgaebi.GaramgaebiServer.domain.notification.dto.GetNotificationDto;
import com.garamgaebi.GaramgaebiServer.domain.notification.service.NotificationService;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "NotificationController", description = "알림 컨트롤러(담당자:로니)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "유저 알림 화면 조회", description = "유저의 알림 목록을 10개씩 페이징하여 조회합니다.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "2001", description = "존재하지 않는 회원", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버에러", content = @Content())
    })
    @GetMapping("/{member-idx}")
    public BaseResponse<List<GetNotificationDto>> getMemberNotification(@PathVariable("member-idx") Long memberIdx,
                                                                        @PageableDefault(size=10, page=0) Pageable pageable) {

        return new BaseResponse<>(notificationService.getMemberNotificationList(memberIdx, pageable));
    }
}
