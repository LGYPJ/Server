package com.garamgaebi.GaramgaebiServer.domain.ice_breaking.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "GameroomMember")
public class GameroomMember {
    @Id
    @Column(name = "game_room_member_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameRoomMemberIdx;

    @Column(name = "room_id")
    private String roomId;

    @Column(name = "member_idx")
    private Long memberIdx;

    @Builder
    public GameroomMember(String roomId, Long memberIdx) {
        this.roomId = roomId;
        this.memberIdx = memberIdx;
    }
}