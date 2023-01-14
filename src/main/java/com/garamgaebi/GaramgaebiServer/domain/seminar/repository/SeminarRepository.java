package com.garamgaebi.GaramgaebiServer.domain.seminar.repository;

import com.garamgaebi.GaramgaebiServer.domain.seminar.User;
import com.garamgaebi.GaramgaebiServer.domain.seminar.entity.Seminar;
import com.garamgaebi.GaramgaebiServer.domain.seminar.entity.UserSeminar;

import java.util.Optional;

public interface SeminarRepository {
    public Optional<User> findUser(Long userIdx);

    public Optional<Seminar> findSeminar(Long seminarIdx);

    public Optional<UserSeminar> findUserSeminar(Long userSeminarIdx);

    public Optional<UserSeminar> findUserSeminarByUserAndSeminar(User user, Seminar seminar);

    public void saveUserSeminar(UserSeminar userSeminar);
}
