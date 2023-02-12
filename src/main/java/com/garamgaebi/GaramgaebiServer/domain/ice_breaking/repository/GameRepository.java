package com.garamgaebi.GaramgaebiServer.domain.ice_breaking.repository;

import com.garamgaebi.GaramgaebiServer.domain.entity.GameRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<GameRoom, Long> {
    @Query("select r from GameRoom r where r.programIdx = :programIdx")
    public Optional<List<GameRoom>> findRoomsByProgramIdx(@Param("programIdx") Long programIdx);

    public Optional<List<GameRoom>> deleteByProgramIdx(Long programIdx);
}
