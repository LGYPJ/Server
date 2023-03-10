package com.garamgaebi.GaramgaebiServer.domain.program.repository;

import com.garamgaebi.GaramgaebiServer.domain.member.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.program.entity.Program;
import com.garamgaebi.GaramgaebiServer.domain.program.entity.vo.ProgramStatus;
import com.garamgaebi.GaramgaebiServer.domain.program.entity.vo.ProgramType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    @Query("select p from Program p where p.date < :now and p.programType = :programType and p not in (select tmp from Program tmp where tmp.status = 'DELETE') order by p.date desc")
    public List<Program> findClosedProgramList(@Param("now")LocalDateTime now, @Param("programType") ProgramType programType);

    @Query(value = "select p from Program p where p.date between :now and :end and p.programType = :programType and p not in (select tmp from Program tmp where tmp.status = 'DELETE') order by p.date asc")
    public List<Program> findThisMonthProgram(@Param("now")LocalDateTime now, @Param("end")LocalDateTime end, @Param("programType") ProgramType programType, Pageable pageable);

    @Query(value = "select p from Program p where p.date > :start and p.programType = :programType and p not in (select tmp from Program tmp where tmp.status = 'DELETE') order by p.date asc")
    public List<Program> findReadyProgram(@Param("start")LocalDateTime start, @Param("programType") ProgramType programType, Pageable pageable);

    @Query("select p from Program p where p.date > :start and p.programType = :programType and p not in (select tmp from Program tmp where tmp.status = 'DELETE') order by p.date asc")
    public List<Program> findReadyProgramList(@Param("start") LocalDateTime now, @Param("programType") ProgramType programType);

    @Query("select p from Program p join Apply a on p = a.program where a.member = :member and (a.status = 'APPLY' or a.status = 'APPLY_CONFIRM') and p.date > current_timestamp and p not in (select tmp from Program tmp where tmp.status = 'DELETE') order by p.date asc")
    public List<Program> findMemberReadyPrograms(@Param("member") Member member);
    @Query("select p from Program p join Apply a on p = a.program where a.member = :member and (a.status = 'APPLY' or a.status = 'APPLY_CONFIRM') and p.date < current_timestamp and p not in (select tmp from Program tmp where tmp.status = 'DELETE') order by p.date desc")
    public List<Program> findMemberClosedPrograms(@Param("member") Member member);

    public List<Program> findByStatus(ProgramStatus programStatus);

}
