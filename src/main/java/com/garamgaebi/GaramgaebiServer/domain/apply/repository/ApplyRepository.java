package com.garamgaebi.GaramgaebiServer.domain.apply.repository;


import com.garamgaebi.GaramgaebiServer.domain.apply.entitiy.Apply;
import com.garamgaebi.GaramgaebiServer.domain.member.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.program.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyRepository extends JpaRepository<Apply, Long> {

    public boolean existsByProgramAndMember(Program program, Member member );

    public Apply findByProgramAndMember(Program program, Member member);
}
