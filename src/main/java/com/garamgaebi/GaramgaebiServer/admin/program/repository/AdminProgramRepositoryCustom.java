package com.garamgaebi.GaramgaebiServer.admin.program.repository;

import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramPayStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramType;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


public interface AdminProgramRepositoryCustom {

    public List<Program> findAdminSearchList(ProgramType programType,
                                             ProgramPayStatus payment,
                                             ProgramStatus status,
                                             LocalDateTime start,
                                             LocalDateTime end);

}
