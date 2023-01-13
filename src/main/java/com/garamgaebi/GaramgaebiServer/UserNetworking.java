package com.garamgaebi.GaramgaebiServer;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "UserNetworking")
public class UserNetworking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long user_networking_idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "networking_idx")
    private Networking networking;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserNetworkingStatus status;

    // == 연관관계 편의 메서드 == //
    public void setUser(User user) {
        this.user = user;
        user.getUserNetworking().add(this);
    }

    public void setNetworking(Networking networking) {
        this.networking = networking;
        networking.getUserNetworkings().add(this);
    }
}
