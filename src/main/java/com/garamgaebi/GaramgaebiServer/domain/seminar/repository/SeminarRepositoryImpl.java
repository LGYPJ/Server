package com.garamgaebi.GaramgaebiServer.domain.seminar.repository;

import com.garamgaebi.GaramgaebiServer.domain.seminar.User;
import com.garamgaebi.GaramgaebiServer.domain.seminar.entity.Seminar;
import com.garamgaebi.GaramgaebiServer.domain.seminar.entity.UserSeminar;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SeminarRepositoryImpl implements SeminarRepository {

    private final EntityManager em;
    public Optional<User> findUser(Long userIdx) {
        return Optional.of(em.find(User.class, userIdx));
    }

    public Optional<Seminar> findSeminar(Long seminarIdx) {
        return Optional.of(em.find(Seminar.class, seminarIdx));

    }

    public Optional<UserSeminar> findUserSeminar(Long userSeminarIdx) {
        return Optional.of(em.find(UserSeminar.class, userSeminarIdx));
    }

    public Optional<UserSeminar> findUserSeminarByUserAndSeminar(User user, Seminar seminar) {
        String jpql = "select us from UserSeminar us where us.user = :userIdx and us.seminar = :seminarIdx";
        TypedQuery<UserSeminar> query = em.createQuery(jpql, UserSeminar.class);
        query.setParameter("userIdx", user.getIdx());
        query.setParameter("seminarIdx", seminar.getIdx());

        return Optional.of(query.getSingleResult());
    }

    public void saveUserSeminar(UserSeminar userSeminar) {
        if(userSeminar.getIdx() == null)
            em.persist(userSeminar);
        else
            em.merge(userSeminar);
    }

}
