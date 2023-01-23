package com.garamgaebi.GaramgaebiServer.admin.program.repository;


import com.garamgaebi.GaramgaebiServer.admin.program.dto.PresentationDto;
import com.garamgaebi.GaramgaebiServer.domain.entity.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminPresentationRepository extends JpaRepository<Presentation, Long> {
}
