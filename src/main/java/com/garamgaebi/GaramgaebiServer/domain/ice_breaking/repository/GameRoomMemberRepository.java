package com.garamgaebi.GaramgaebiServer.domain.ice_breaking.repository;

import com.garamgaebi.GaramgaebiServer.domain.entity.GameroomMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameRoomMemberRepository extends JpaRepository<GameroomMember, Long> {
    public Optional<List<GameroomMember>> findByRoomId(String roomId);
    public Optional<GameroomMember> findByMemberIdx(Long memberIdx);
    public Optional<GameroomMember> deleteByMemberIdx(Long memberIdx);

    public Optional<GameroomMember> findByRoomIdAndMemberIdx(String roomId, Long memberIdx);
}