package com.garamgaebi.GaramgaebiServer.domain.notification.controller;

import com.garamgaebi.GaramgaebiServer.domain.notification.dto.response.GetNotificationResDto;
import com.garamgaebi.GaramgaebiServer.domain.notification.service.NotificationService;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Tag(name = "NotificationController", description = "알림 컨트롤러(담당자:로니)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * 유저의 알림 리스트 조회
     * @Author 로니
     */
    @Operation(summary = "유저 알림 화면 조회", description = "유저의 알림 목록을 10개씩 페이징하여 조회합니다.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "2001", description = "존재하지 않는 회원", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버에러", content = @Content())
    })
    @GetMapping("/{member-idx}")
    public BaseResponse<GetNotificationResDto> getMemberNotification(@PathVariable("member-idx") Long memberIdx,
                                                                      @RequestParam(required = false) Long lastNotificationIdx) {

        return new BaseResponse<>(notificationService.getMemberNotificationList(memberIdx, lastNotificationIdx));
    }

    /**
     * 읽지 않은 알림 존재 여부 확인
     * @Author 로니
     */
    @Operation(summary = "유저의 읽지 않은 알림 존재 여부", description = "유저가 읽지 않은 알림이 존재한다면 TRUE, 그렇지 않으면 FALSE를 반환합니다.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "2001", description = "존재하지 않는 회원", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버에러", content = @Content())
    })
    @GetMapping("/unread/{member-idx}")
    public BaseResponse<Map<String, Object>> getIsExistUnreadNotification(@PathVariable("member-idx") Long memberIdx) {
        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("isUnreadExist", notificationService.isMemberNotificationExist(memberIdx));
        return new BaseResponse<>(result);
    }
}
