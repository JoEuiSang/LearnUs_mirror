package com.ssafy.backend.controller;

import com.ssafy.backend.dto.Attendance;
import com.ssafy.backend.dto.BroadcastInfo;
import com.ssafy.backend.service.BroadcastService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        broadcastService.insert(broadcastInfo);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @PatchMapping
    @ApiOperation(value = "방송 수정")
    public ResponseEntity<String> update(@RequestBody BroadcastInfo broadcastInfo) {
        broadcastService.update(broadcastInfo);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @DeleteMapping("/{broadcastId}")
    @ApiOperation(value = "방송 삭제")
    public ResponseEntity<String> delete(@PathVariable("broadcastId") int broadcastId) {
        broadcastService.delete(broadcastId);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/all")
    @ApiOperation(value = "실시간 방송 조회")
    public ResponseEntity<List<BroadcastInfo>> getBroadcastAll() {
        return new ResponseEntity<>(broadcastService.getBroadcastAll(), HttpStatus.OK);
    }

    @GetMapping("/attendance/{broadcastId}")
    public ResponseEntity<List<Attendance>> getAttendance(@PathVariable("broadcastId") int broadcastId) {
        return new ResponseEntity<>(broadcastService.getAttendance(broadcastId), HttpStatus.OK);
    }

    @PatchMapping("/{broadcastId}/{userId}")
    @ApiOperation(value = "출석 체크")
    public ResponseEntity<String> attend(@PathVariable("broadcastId") int broadcastId, @PathVariable("userId") int userId) {
        broadcastService.attend(broadcastId, userId);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }
}