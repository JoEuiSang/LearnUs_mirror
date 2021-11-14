package com.ssafy.backend.service.broadcast;

import com.ssafy.backend.dto.Attendance;
import com.ssafy.backend.dto.info.BroadcastInfo;
import com.ssafy.backend.dto.info.ChatInfo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface BroadcastService {
    boolean insert(BroadcastInfo broadcastInfo);
    boolean update(BroadcastInfo broadcastInfo);
    boolean delete(int broadcastId);
    boolean attend(int broadcastId, int userId);
    Map<String, List<Attendance>> getAttendance(int broadcastId);
    List<BroadcastInfo> getBroadcastAll(String liveCode);
    BroadcastInfo getBroadcast(int broadcastId);
    boolean start(int broadcastId);
    Map<String, List<Attendance>> end(int broadcastId);
    boolean endAttendance(int broadcastId);
    boolean endAttendanceDownload(int broadcastId, HttpServletResponse response) throws IOException;
    boolean endGifticon(int broadcastId);
    boolean endGifticonDownload(int broadcastId, HttpServletResponse response) throws IOException;
    boolean endReplayInsert(int broadcastId, String autoUploadYn);

    List<ChatInfo> getChatInfoList(int broadcastId);
}
