package com.ssafy.backend.controller;

import com.ssafy.backend.dto.Attendance;
import com.ssafy.backend.dto.info.BroadcastInfo;
import com.ssafy.backend.dto.info.ChatInfo;
import com.ssafy.backend.service.broadcast.BroadcastService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/broadcast")
@CrossOrigin("*")
public class BroadcastController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private BroadcastService broadcastService;

    @PostMapping
    @ApiOperation(value = "방송 생성")
    public ResponseEntity<String> insert(@RequestBody BroadcastInfo broadcastInfo) {
        if (!broadcastService.insert(broadcastInfo)) return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @PatchMapping
    @ApiOperation(value = "방송 수정")
    public ResponseEntity<String> update(@RequestBody BroadcastInfo broadcastInfo) {
        if (!broadcastService.update(broadcastInfo)) return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @DeleteMapping("/{broadcastId}")
    @ApiOperation(value = "방송 삭제")
    public ResponseEntity<String> delete(@PathVariable("broadcastId") int broadcastId) {
        if (!broadcastService.delete(broadcastId)) return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/all/{liveCode}")
    @ApiOperation(value = "방송 조회 (onAir, 방송 전)")
    public ResponseEntity<List<BroadcastInfo>> getBroadcastAll(@PathVariable("liveCode") String liveCode) {
        List<BroadcastInfo> broadcastInfoList = broadcastService.getBroadcastAll(liveCode);
        if (broadcastInfoList == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(broadcastInfoList, HttpStatus.OK);
    }

    @GetMapping("/{broadcastId}")
    @ApiOperation(value = "실시간 방송 1개 조회")
    public ResponseEntity<BroadcastInfo> getBroadcast(@PathVariable("broadcastId") int broadcastId) {
        BroadcastInfo broadcastInfo = broadcastService.getBroadcast(broadcastId);
        if (broadcastInfo == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(broadcastInfo, HttpStatus.OK);
    }

    @GetMapping("/attendance/{broadcastId}")
    @ApiOperation(value = "참석 명단 가져오기")
    public ResponseEntity<Map<String, List<Attendance>>> getAttendance(@PathVariable("broadcastId") int broadcastId) {
        Map<String, List<Attendance>> map = broadcastService.getAttendance(broadcastId);
        if (map == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PatchMapping("/{broadcastId}/{userId}")
    @ApiOperation(value = "출석 체크")
    public ResponseEntity<String> attend(@PathVariable("broadcastId") int broadcastId, @PathVariable("userId") int userId) {
        if (!broadcastService.attend(broadcastId, userId)) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @PostMapping("/start")
    @ApiOperation(value = "방송 시작 -> 관련 MM에 메시지 전송")
    public ResponseEntity<String> broadcastStart(@RequestParam("broadcastId") int broadcastId) {
        if (!broadcastService.start(broadcastId)) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/end/king/{broadcastId}")
    @ApiOperation(value = "방송 종료 -> 방송 종료 창으로 이동")
    public ResponseEntity<Map<String, List<Attendance>>> end(@PathVariable("broadcastId") int broadcastId) {
        Map<String, List<Attendance>> map = broadcastService.end(broadcastId);
        if (map == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/end/attendance")
    @ApiOperation(value = "방송 종료 -> 미참석 명단 전송")
    public ResponseEntity<String> endAttendance(@RequestParam("broadcastId") int broadcastId) {
        if (!broadcastService.endAttendance(broadcastId)) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @PostMapping("/end/attendance/download")
    @ApiOperation(value = "방송 종료 -> 미참석 명단 엑셀 파일 다운로드")
    public ResponseEntity<String> endAttendanceDownload(@RequestParam("broadcastId") int broadcastId, HttpServletResponse response) throws IOException {
        if (!broadcastService.endAttendanceDownload(broadcastId, response)) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @PostMapping("/end/gifticon")
    @ApiOperation(value = "방송 종료 -> 기프티콘 명단 전송")
    public ResponseEntity<String> endGifticon(@RequestParam("broadcastId") int broadcastId) {
        if (!broadcastService.endGifticon(broadcastId)) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @PostMapping("/end/gifticon/download")
    @ApiOperation(value = "방송 종료 -> 기프티콘 명단 엑셀 파일 다운로드")
    public ResponseEntity<String> endGifticonDownload(@RequestParam("broadcastId") int broadcastId, HttpServletResponse response) throws IOException {
        if (!broadcastService.endGifticonDownload(broadcastId, response)) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @PostMapping("/end/replay")
    @ApiOperation(value = "방송 종료 -> 다시보기 정보 생성, 공개 비공개 여부로 제공")
    public ResponseEntity<String> endReplayInsert(@RequestParam("broadcastId") int broadcastId, @RequestParam("autoUploadYn") String autoUploadYn) throws IOException {
        if (!broadcastService.endReplayInsert(broadcastId, autoUploadYn)) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/chat/{broadcastId}")
    @ApiOperation(value = "이전 채팅 정보 가져오기")
    public ResponseEntity<List<ChatInfo>> getChatInfoList(@PathVariable("broadcastId") int broadcastId) {
        List<ChatInfo> chatInfoList = broadcastService.getChatInfoList(broadcastId);
        if (chatInfoList == null || chatInfoList.size() == 0) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(chatInfoList, HttpStatus.OK);
    }
}
