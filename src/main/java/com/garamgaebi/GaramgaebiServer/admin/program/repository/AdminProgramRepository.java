package com.garamgaebi.GaramgaebiServer.admin.program.repository;

import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface AdminProgramRepository extends JpaRepository<Program, Long>, AdminProgramRepositoryCustom {

}
