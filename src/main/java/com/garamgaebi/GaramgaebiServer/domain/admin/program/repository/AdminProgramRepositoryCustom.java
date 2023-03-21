package com.garamgaebi.GaramgaebiServer.domain.admin.program.repository;

import com.garamgaebi.GaramgaebiServer.domain.program.entity.Program;
import com.garamgaebi.GaramgaebiServer.domain.program.entity.vo.ProgramPayStatus;
import com.garamgaebi.GaramgaebiServer.domain.program.entity.vo.ProgramStatus;
import com.garamgaebi.GaramgaebiServer.domain.program.entity.vo.ProgramType;

import java.time.LocalDateTime;
import java.util.List;


public interface AdminProgramRepositoryCustom {

    public List<Program> findAdminSearchList(ProgramType programType,
                                             ProgramPayStatus payment,
                                             ProgramStatus status,
                                             LocalDateTime start,
                                             LocalDateTime end);

}
