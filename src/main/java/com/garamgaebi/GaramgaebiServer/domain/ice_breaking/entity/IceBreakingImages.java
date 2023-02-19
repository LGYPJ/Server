package com.garamgaebi.GaramgaebiServer.domain.ice_breaking.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "IceBreakingImages")
public class IceBreakingImages {
    @Id
    @Column(name = "ice_breaking_images_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iceBreakingImagesIdx;

    @Column(nullable = false)
    private String url;
}
