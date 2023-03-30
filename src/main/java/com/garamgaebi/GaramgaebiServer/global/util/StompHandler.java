package com.garamgaebi.GaramgaebiServer.global.util;

import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.dto.MemberRoomDeleteReq;
import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.dto.MessageDto;
import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.entity.GameroomMember;
import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.repository.GameRoomMemberRepository;
import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.service.GameService;
import com.garamgaebi.GaramgaebiServer.domain.member.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberRepository;
import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class StompHandler implements ChannelInterceptor {

    private final SimpMessageSendingOperations sendingOperations;
    private final GameService gameService;
    private final GameRoomMemberRepository gameRoomMemberRepository;

    @Override
    public void postSend(Message message, MessageChannel channel, boolean sent) {
        /* 엑세서, 세션 아이디 확인 */
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();
        System.out.println("DEBUG, message: " + message);
        System.out.println("DEBUG, accessor: " + accessor);

        /* 헤더 확인 */
        String memberIdx = accessor.getFirstNativeHeader("memberIdx");
        String roomId = accessor.getFirstNativeHeader("roomId");
        System.out.println("DEBUG, memberIdx: " + memberIdx);
        System.out.println("DEBUG, roomId: " + roomId);

        RequestContextHolder.getRequestAttributes();

        switch (accessor.getCommand()) {
            case CONNECT:
                break;
            case DISCONNECT:
                System.out.println("disconnected memberIdx = " + memberIdx);

                /**
                 * connectionHeader에 memberIdx, roomId 잘 오는지 확인
                 * 해당 room의 멤버를 시간순으로 정렬
                 * memberIdx 다음에 들어온 멤버를 nextMemberIdx로, deleteMemberFromGameRoom 메서드 호출
                 */

                /**
                 * 내일 테스트 때 이 코드도 넣어서 !
                 */
//                MessageDto messageDto = new MessageDto(MessageDto.MessageType.EXIT, roomId, memberIdx, "EXIT", "EXIT");
//                sendingOperations.convertAndSend("/topic/game/room/" + roomId, messageDto);

//                List<Member> members = new ArrayList<>();

//                List<GameroomMember> gameroomMembers = gameRoomMemberRepository.findByRoomId(roomId)
//                        .orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_GAME_ROOM));
//                for(GameroomMember member: gameroomMembers) {
//                    members =
//                }
//                gameService.deleteMemberFromGameRoom(new MemberRoomDeleteReq(roomId, Long.parseLong(nextMemberIdx)), Long.parseLong(memberIdx));
                break;
            case SUBSCRIBE:
                break;
            default:
                break;
        }
    }
}
