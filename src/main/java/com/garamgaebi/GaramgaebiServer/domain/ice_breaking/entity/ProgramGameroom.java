package com.garamgaebi.GaramgaebiServer.domain.ice_breaking.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ProgramGameroom")
public class ProgramGameroom {
    @Id
    @Column(name = "program_game_room_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long programGameRoomIdx;

    @Column(name = "program_idx", nullable = false)
    private Long programIdx;

    @Column(name = "room_id", nullable = false)
    private String roomId;

    @Column(name = "current_img_idx", nullable = false)
    private int currentImgIdx;

    @Builder
    public ProgramGameroom(Long programIdx, String roomId, int currentImgIdx) {
        this.programIdx = programIdx;
        this.roomId = roomId;
        this.currentImgIdx = currentImgIdx;
    }

    public void increaseCurrentImgIdx() {
        this.currentImgIdx += 1;
    }

    public void initCurrentImgIdx() {
        this.currentImgIdx = 0;
    }
}
