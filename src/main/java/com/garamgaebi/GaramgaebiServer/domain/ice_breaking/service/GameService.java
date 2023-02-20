package com.garamgaebi.GaramgaebiServer.domain.ice_breaking.service;

import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.dto.MemberRoomReq;
import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.dto.MemberRoomRes;
import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.dto.MembersGetRes;
import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.entity.ProgramGameroom;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GameService {
    /* Program index로 게임방 불러오기 */
    public List<ProgramGameroom> getRoomsByProgram(Long programIdx);

    /* 게임방 생성 */
    public List<ProgramGameroom> createRooms(Long programIdx);

    /* 해당 Program의 게임방 삭제 */
    public List<ProgramGameroom> deleteRooms(Long programIdx);

    /* game room idx로 member 찾기 */
    public List<MembersGetRes> getMembersByGameRoomIdx(String roomId);

    /* roomId에 member 등록 */
    public MemberRoomRes registerMemberToGameRoom(MemberRoomReq memberRoomReq);

    /* roomId에 member 삭제 */
    public String deleteMemberFromGameRoom(MemberRoomReq memberRoomReq);

    /* 이미지 url 리스트 조회 */
    public List<String> getImageUrls(Long seed);

    /* 게임방의 current image index 증가 */
    public String patchCurrentImgIdx(String roomId, Long memberIdx);
}
