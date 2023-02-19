package com.garamgaebi.GaramgaebiServer.domain.admin.program.repository;

import com.garamgaebi.GaramgaebiServer.domain.program.entity.Program;
import com.garamgaebi.GaramgaebiServer.domain.program.entity.QProgram;
import com.garamgaebi.GaramgaebiServer.domain.program.entity.vo.ProgramPayStatus;
import com.garamgaebi.GaramgaebiServer.domain.program.entity.vo.ProgramStatus;
import com.garamgaebi.GaramgaebiServer.domain.program.entity.vo.ProgramType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminProgramRepositoryCustomImpl implements AdminProgramRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Transactional(readOnly = true)
    @Override
    public List<Program> findAdminSearchList(ProgramType programType,
                                             ProgramPayStatus payment,
                                             ProgramStatus status,
                                             LocalDateTime start,
                                             LocalDateTime end) {
        QProgram program = QProgram.program;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(program.programType.eq(programType));


        if(payment != null) {
            if(payment == ProgramPayStatus.FREE)
                builder.and(program.fee.eq(0));
            else if(payment == ProgramPayStatus.PREMIUM)
                builder.and(program.fee.gt(0));
        }

        if(status != null) {
            builder.and(program.status.eq(status));
        }
        else {
            builder.and(program.status.eq(ProgramStatus.DELETE).not());
        }

        if(start != null) {
            builder.and(program.date.goe(start));
        }
        if(end != null) {
            builder.and(program.date.loe(end));
        }

        return queryFactory
                .selectFrom(program)
                .where(builder)
                .fetch();
    }
}
