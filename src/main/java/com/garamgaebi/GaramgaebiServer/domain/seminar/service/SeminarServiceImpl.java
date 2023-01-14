package com.garamgaebi.GaramgaebiServer.domain.seminar.service;

import com.garamgaebi.GaramgaebiServer.domain.seminar.User;
import com.garamgaebi.GaramgaebiServer.domain.seminar.UserStatus;
import com.garamgaebi.GaramgaebiServer.domain.seminar.dto.SeminarApplyReq;
import com.garamgaebi.GaramgaebiServer.domain.seminar.dto.SeminarCancelReq;
import com.garamgaebi.GaramgaebiServer.domain.seminar.entity.Seminar;
import com.garamgaebi.GaramgaebiServer.domain.seminar.entity.UserSeminar;
import com.garamgaebi.GaramgaebiServer.domain.seminar.entity.status.SeminarStatus;
import com.garamgaebi.GaramgaebiServer.domain.seminar.entity.status.UserSeminarStatus;
import com.garamgaebi.GaramgaebiServer.domain.seminar.entity.vo.BankAccount;
import com.garamgaebi.GaramgaebiServer.domain.seminar.repository.SeminarRepository;
import com.garamgaebi.GaramgaebiServer.domain.seminar.repository.SeminarRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeminarServiceImpl implements SeminarService {

    private final SeminarRepository seminarRepository;

    // 세미나 신청 메서드
    @Override
    public void apply(SeminarApplyReq seminarApplyReq) {

        Optional<User> user = seminarRepository.findUser(seminarApplyReq.getUserIdx());
        Optional<Seminar> seminar = seminarRepository.findSeminar(seminarApplyReq.getSeminarIdx());

        if(!isUserInvalid(user) || !isSeminarInvalid(seminar)) {
            // 잘못된 요청
        }

        Optional<UserSeminar> findUserSeminar = seminarRepository.findUserSeminarByUserAndSeminar(user.get(), seminar.get());

        if(findUserSeminar.isPresent() && findUserSeminar.get().getStatus() != UserSeminarStatus.CANCEL) {
            // 잘못된 요청
        }

        UserSeminar userSeminar = findUserSeminar.get();

        if(userSeminar == null)
            userSeminar = new UserSeminar();

        userSeminar.create(user.get(), seminar.get(), seminarApplyReq.getPhoneNumber());

        seminarRepository.saveUserSeminar(userSeminar);

    }

    // 세미나 신청 취소 메서드
    @Override
    public void cancel(SeminarCancelReq seminarCancelReq) {

        Optional<UserSeminar> findUserSeminar = seminarRepository.findUserSeminar(seminarCancelReq.getUserSeminarIdx());

        if(findUserSeminar.isEmpty() || findUserSeminar.get().getStatus() != UserSeminarStatus.APPLY) {
            // 잘못된 요청
        }

        UserSeminar userSeminar = findUserSeminar.get();

        userSeminar.cancel(new BankAccount(seminarCancelReq.getBank(), seminarCancelReq.getAccount()));
        seminarRepository.saveUserSeminar(userSeminar);

    }

    @Override
    public void findSeminarList() {

    }

    @Override
    public void findSeminarPage(Long seminarIdx) {

    }

    private boolean isUserInvalid(Optional<User> user) {
        if(user.isEmpty() || user.get().getStatus() == UserStatus.DELETE)
            return true;
        return false;
    }

    private boolean isSeminarInvalid(Optional<Seminar> seminar) {
        if(seminar.isEmpty() || seminar.get().getStatus() != SeminarStatus.OPEN)
            return true;
        return false;
    }

}
