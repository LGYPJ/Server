package com.garamgaebi.GaramgaebiServer.domain.apply;


import com.garamgaebi.GaramgaebiServer.domain.entity.Apply;
import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ApplyRepository extends JpaRepository<Apply, Long> {

    boolean existsByProgramAndMember(Program program, Member member );

    Apply findByProgramAndMember(Program program, Member member);
}
