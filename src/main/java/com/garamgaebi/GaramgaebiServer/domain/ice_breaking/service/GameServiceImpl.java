package com.garamgaebi.GaramgaebiServer.domain.ice_breaking.service;

import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.entity.GameroomMember;
import com.garamgaebi.GaramgaebiServer.domain.member.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.entity.ProgramGameroom;
import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.dto.MemberRoomReq;
import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.dto.MemberRoomRes;
import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.dto.MembersGetRes;
import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.repository.GameRoomMemberRepository;
import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.repository.IceBreakingImagesRepository;
import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.repository.ProgramGameroomRepository;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberRepository;
import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameServiceImpl implements GameService{
    private final ProgramGameroomRepository programGameroomRepository;
    private final GameRoomMemberRepository gameRoomMemberRepository;
    private final MemberRepository memberRepository;
    private final IceBreakingImagesRepository iceBreakingImagesRepository;

    // Program index로 게임방 불러오기
    public List<ProgramGameroom> getRoomsByProgram(Long programIdx) {
        List<ProgramGameroom> rooms = programGameroomRepository.findRoomsByProgramIdx(programIdx)
                .orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_PROGRAM)); // 방 생성되는 시점에 따라 에러 나중에 바꿔주기

        return rooms;
    }

    // 게임방 생성
    public List<ProgramGameroom> createRooms(Long programIdx) {
        List<ProgramGameroom> rooms = new ArrayList<>();
        String roomId;

        for (int i = 0; i < 8; i++) { // 방 8개씩 생성
            roomId = UUID.randomUUID().toString();

            ProgramGameroom room = ProgramGameroom.builder().programIdx(programIdx).roomId(roomId).currentImgIdx(0).build();

            programGameroomRepository.save(room);
            rooms.add(room);
        }

        return rooms;
    }

    // 해당 Program의 게임방 삭제
    public List<ProgramGameroom> deleteRooms(Long programIdx) {
        List<ProgramGameroom> deletedRooms= programGameroomRepository.deleteByProgramIdx(programIdx)
                .orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_PROGRAM));

        return deletedRooms;
    }

    // game room idx로 member 찾기
    public List<MembersGetRes> getMembersByGameRoomIdx(String roomId) {
        List<MembersGetRes> members = new ArrayList<>();
        MembersGetRes res = null;

        List<GameroomMember> gameRoomMembers = gameRoomMemberRepository.findByRoomId(roomId)
                .orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_GAME_ROOM));

        for(GameroomMember gameroomMember : gameRoomMembers) {
            Member member = memberRepository.findById(gameroomMember.getMemberIdx())
                    .orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_MEMBER));

            res = new MembersGetRes(member.getMemberIdx(), member.getNickname(), member.getProfileUrl());

            members.add(res);
        }

        return members;
    }

    // roomId에 member 등록
    public MemberRoomRes registerMemberToGameRoom(MemberRoomReq memberRoomReq) {
        memberRepository.findById(memberRoomReq.getMemberIdx())
                .orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_MEMBER));

        ProgramGameroom room = programGameroomRepository.findByRoomId(memberRoomReq.getRoomId())
                .orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_GAME_ROOM));

        Optional<GameroomMember> member = gameRoomMemberRepository.findByRoomIdAndMemberIdx(memberRoomReq.getRoomId(), memberRoomReq.getMemberIdx());
        if (!member.isEmpty()) {
            throw new RestApiException(ErrorCode.ALREADY_ENTER_GAME);
        }

        GameroomMember gameroomMember = GameroomMember.builder().roomId(memberRoomReq.getRoomId()).memberIdx(memberRoomReq.getMemberIdx()).build();
        gameRoomMemberRepository.save(gameroomMember);

        MemberRoomRes memberRoomRes = new MemberRoomRes("게임방 입장에 성공하였습니다.", room.getCurrentImgIdx());

        return memberRoomRes;
    }

    // roomId에 member 삭제
    @Transactional
    public String deleteMemberFromGameRoom(MemberRoomReq memberRoomReq) {
        memberRepository.findById(memberRoomReq.getMemberIdx())
                .orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_MEMBER));

        programGameroomRepository.findByRoomId(memberRoomReq.getRoomId())
                .orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_GAME_ROOM));

        gameRoomMemberRepository.findByRoomIdAndMemberIdx(memberRoomReq.getRoomId(), memberRoomReq.getMemberIdx())
                        .orElseThrow(() -> new RestApiException(ErrorCode.NOT_REGISTERED_MEMBER_FROM_GAME_ROOM));

        gameRoomMemberRepository.deleteByMemberIdx(memberRoomReq.getMemberIdx());

        return "게임방 퇴장에 성공하였습니다.";
    }

    // 이미지 url 리스트 조회
    public List<String> getImageUrls(Long seed) {
        List<String> images = iceBreakingImagesRepository.findAllImages();
        Collections.shuffle(images, new Random(seed));

        return images;
    }

    // 게임방의 current image index 증가
    @Transactional
    public String patchCurrentImgIdx(String roomId) {
        ProgramGameroom room = programGameroomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_GAME_ROOM));

        System.out.println("초기화 전 " + room.getCurrentImgIdx());
        if (room.getCurrentImgIdx() >= 29) {
            room.initCurrentImgIdx();
            System.out.println("초기화 후 " + room.getCurrentImgIdx());
        } else {
            room.increaseCurrentImgIdx();
        }

        return "current image index 증가가 완료되었습니다.";
    }
}