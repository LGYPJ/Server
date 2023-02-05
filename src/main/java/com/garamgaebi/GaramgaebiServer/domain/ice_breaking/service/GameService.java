package com.garamgaebi.GaramgaebiServer.domain.ice_breaking.service;

import com.garamgaebi.GaramgaebiServer.domain.entity.GameRoom;
import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.repository.GameRepository;
import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;

    // Program index로 게임방 불러오기
    public List<GameRoom> getRoomsByProgram(Long programIdx) {
        List<GameRoom> rooms = gameRepository.findRoomsByProgramIdx(programIdx)
                .orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_PROGRAM)); // 방 생성되는 시점에 따라 에러 나중에 바꿔주기

        return rooms;
    }

    // 게임방 생성
    public List<GameRoom> createRooms(Long programIdx) {
        List<GameRoom> rooms = null;
        String roomId;

        for (int i = 0; i < 5; i++) { // 방 5개씩 생성
            roomId = UUID.randomUUID().toString();

            GameRoom room = GameRoom.builder().programIdx(programIdx).roomId(roomId).build();

            gameRepository.save(room);
            rooms.add(room);
        }

        return rooms;
    }

    // 해당 Program의 게임방 삭제
    public List<GameRoom> deleteRooms(Long programIdx) {
        List<GameRoom> deletedRooms= gameRepository.deleteByProgramIdx(programIdx)
                .orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_PROGRAM));

        return deletedRooms;
    }

}
