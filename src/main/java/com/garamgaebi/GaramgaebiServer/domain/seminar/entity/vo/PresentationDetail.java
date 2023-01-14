package com.garamgaebi.GaramgaebiServer.domain.seminar.entity.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PresentationDetail {

    @Column(name = "content")
    private String content;

    @Column(name = "presentation_url")
    private String presentationUrl;
}
