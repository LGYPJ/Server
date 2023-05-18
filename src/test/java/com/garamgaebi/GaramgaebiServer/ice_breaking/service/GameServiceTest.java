package com.garamgaebi.GaramgaebiServer.ice_breaking.service;

import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.service.GameServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class GameServiceTest {
    @Autowired
    GameServiceImpl gameService;

    @Test
    public void getRoomsByProgram() {

    }

    @Test
    public void createRooms() {

    }

    @Test
    public void deleteRooms() {

    }

    @Test
    public void getMembersByGameRoomIdx() {

    }

    @Test
    public void registerMemberToGameRoom() {

    }

    @Test
    public void deleteMemberFromGameRoom() {

    }

    @Test
    public void getImageUrls() {

    }

    @Test
    public void patchCurrentImgIdx() {

    }

    @Test
    public void getIsStarted() {

    }

    @Test
    public void patchIsStarted() {

    }
}
