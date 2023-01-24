package com.garamgaebi.GaramgaebiServer.admin.program.repository;

import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminProgramRepository extends JpaRepository<Program, Long> {
}
