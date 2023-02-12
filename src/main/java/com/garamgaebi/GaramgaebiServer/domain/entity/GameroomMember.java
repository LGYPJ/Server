package com.garamgaebi.GaramgaebiServer.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
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
}