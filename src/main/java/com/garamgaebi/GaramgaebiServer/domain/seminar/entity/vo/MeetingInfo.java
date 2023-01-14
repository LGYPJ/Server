package com.garamgaebi.GaramgaebiServer.domain.seminar.entity.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MeetingInfo {
    @Column(name = "title")
    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "location")
    private String location;

    @Column(name = "fee")
    private Integer fee;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    private LocalDateTime endDate;
}
