package com.garamgaebi.GaramgaebiServer.domain.admin.program.repository;


import com.garamgaebi.GaramgaebiServer.domain.program.entity.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminPresentationRepository extends JpaRepository<Presentation, Long> {
}
