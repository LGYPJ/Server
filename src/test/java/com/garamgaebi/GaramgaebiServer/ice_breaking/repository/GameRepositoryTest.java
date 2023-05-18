package com.garamgaebi.GaramgaebiServer.ice_breaking.repository;

import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GameRepositoryTest {

    private final GameRepository gameRepository;

    public GameRepositoryTest(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Test
    public void findRoomsByProgramIdx() {

    }

    @Test
    public void deleteByProgramIdx() {

    }
}
