package com.garamgaebi.GaramgaebiServer.domain.ice_breaking.repository;

import com.garamgaebi.GaramgaebiServer.domain.entity.ProgramGameroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProgramGameroomRepository extends JpaRepository<ProgramGameroom, Long> {
    @Query("select r from ProgramGameroom r where r.programIdx = :programIdx")
    public Optional<List<ProgramGameroom>> findRoomsByProgramIdx(@Param("programIdx") Long programIdx);

    public Optional<List<ProgramGameroom>> findByRoomId(String roomId);

    public Optional<List<ProgramGameroom>> deleteByProgramIdx(Long programIdx);
}
