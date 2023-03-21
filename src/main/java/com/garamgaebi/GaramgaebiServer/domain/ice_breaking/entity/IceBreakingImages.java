package com.garamgaebi.GaramgaebiServer.domain.ice_breaking.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "IceBreakingImages")
public class IceBreakingImages {
    @Id
    @Column(name = "ice_breaking_images_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iceBreakingImagesIdx;

    @Column(nullable = false)
    private String url;

    @Builder
    public IceBreakingImages(String url) {
        this.url = url;
    }
}
