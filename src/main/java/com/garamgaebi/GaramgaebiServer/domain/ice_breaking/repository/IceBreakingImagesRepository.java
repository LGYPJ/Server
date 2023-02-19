package com.garamgaebi.GaramgaebiServer.domain.ice_breaking.repository;

import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.entity.IceBreakingImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IceBreakingImagesRepository extends JpaRepository<IceBreakingImages, Long> {
    @Query("select url from IceBreakingImages")
    public List<String> findAllImages();
}
