package com.garamgaebi.GaramgaebiServer.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "GameRoom")
public class GameRoom {
    @Id
    @Column(name = "game_room_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameRoomIdx;

    @Column(name = "program_idx", nullable = false)
    private Long programIdx;

    @Column(name = "room_id", nullable = false)
    private String roomId;
}
