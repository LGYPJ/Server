package com.garamgaebi.GaramgaebiServer.domain.ice_breaking.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageDto {
    public enum MessageType {
        ENTER, NEXT, TALK, EXIT
    }

    private MessageType type; // message type
    private String roomId; // 게임방 ID
    private String sender; // 보내는 사람
    private String message; // 사진 index
    private String profileUrl; // profile image url

    @Builder
    public MessageDto(MessageType type, String roomId, String sender, String message, String profileUrl) {
        this.type = type;
        this.roomId = roomId;
        this.sender = sender;
        this.message = message;
        this.profileUrl = profileUrl;
    }
}
