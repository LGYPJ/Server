package com.garamgaebi.GaramgaebiServer;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class PresentationDetail {

    @Column(name = "content")
    private String content;

    @Column(name = "presentation_url")
    private String presentationUrl;
}
