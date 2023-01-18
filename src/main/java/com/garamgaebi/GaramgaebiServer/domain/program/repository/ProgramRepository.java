package com.garamgaebi.GaramgaebiServer.domain.program.repository;

import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import com.garamgaebi.GaramgaebiServer.domain.entity.ProgramType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    public List<Program> findAllByDateBeforeAndProgramTypeOrderByDateDesc(LocalDateTime now, ProgramType programType);

    public Optional<Program> findOneByDateBetweenAndProgramTypeOrderByDateAsc(LocalDateTime start, LocalDateTime end, ProgramType programType);

    public Optional<Program> findFirstByDateAfterAndProgramTypeOrderByDateAsc(LocalDateTime start, ProgramType programType);
}