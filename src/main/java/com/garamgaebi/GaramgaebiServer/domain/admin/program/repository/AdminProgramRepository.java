package com.garamgaebi.GaramgaebiServer.domain.admin.program.repository;

import com.garamgaebi.GaramgaebiServer.domain.program.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminProgramRepository extends JpaRepository<Program, Long>, AdminProgramRepositoryCustom {

}
