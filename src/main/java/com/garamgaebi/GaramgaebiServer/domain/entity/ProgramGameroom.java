package com.garamgaebi.GaramgaebiServer.domain.entity;

import com.garamgaebi.GaramgaebiServer.domain.entity.status.member.MemberStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
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

    public void increaseCurrentImgIdx() {
        this.currentImgIdx += 1;
    }

    public void initCurrentImgIdx() {
        this.currentImgIdx = 0;
    }
}
