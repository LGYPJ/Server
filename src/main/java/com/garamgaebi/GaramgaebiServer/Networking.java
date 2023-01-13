package com.garamgaebi.GaramgaebiServer;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "Networking")
public class Networking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long networking_idx;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Integer fee;

    @Column(nullable = false)
    private LocalDateTime end_date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NetworkingStatus status;

    @OneToMany(mappedBy = "networking")
    private List<UserNetworking> userNetworkings = new ArrayList<>();


    // == 연관관계 편의 메서드 == //
    public void addUserNetworking(UserNetworking userNetworking) {
        userNetworkings.add(userNetworking);
        userNetworking.setNetworking(this);
    }

    // == 비즈니스 로직 == //
    public List<User> getParticipantList() {
        List<User> users = new ArrayList<>();

        for(UserNetworking userNetworking : userNetworkings) {
            if(userNetworking.getStatus() == UserNetworkingStatus.APPLY)
                users.add(userNetworking.getUser());
        }
        return users;
    }

}
