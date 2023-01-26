package com.garamgaebi.GaramgaebiServer.admin.program.repository;


import com.garamgaebi.GaramgaebiServer.domain.entity.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminPresentationRepository extends JpaRepository<Presentation, Long> {
}
