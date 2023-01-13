package com.garamgaebi.GaramgaebiServer;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.sql.Date;

@Embeddable
@Getter
public class MeetingInfo {
    @Column(name = "title")
    private String title;

    @Column(name = "date")
    private Date date;

    @Column(name = "location")
    private String location;

    @Column(name = "fee")
    private Integer fee;

    @Column(name = "end_date")
    private Date endDate;
}
