package com.garamgaebi.GaramgaebiServer.domain.ice_breaking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    public enum MessageType {
        ENTER, TALK
    }

    private MessageType type;
    private String roomId; // 게임방 ID
    private String sender; // 보내는 사람
    private String message; // 내용
}
