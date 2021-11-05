package com.ssafy.backend.service;

import com.ssafy.backend.dao.*;
import com.ssafy.backend.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BroadcastReplayServiceImpl implements BroadcastReplayService {

    @Autowired
    private BroadcastDao broadcastDao;
    @Autowired
    private BroadcastReplayDao broadcastReplayDao;
    @Autowired
    private TextbookDao textbookDao;
    @Autowired
    private BroadcastTrackDao broadcastTrackDao;

    @Override
    public boolean insert(BroadcastReplayInfo broadcastReplayInfo) {
        Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(broadcastReplayInfo.getBroadcastId());
        if (broadcast == null) return false;

        broadcast.setLiveYn("N");
        broadcastDao.save(broadcast);

        BroadcastReplay broadcastReplay = BroadcastReplay.builder().replayUrl(broadcastReplayInfo.getReplayUrl())
                .openYn(broadcastReplayInfo.getOpenYn()).broadcast(broadcast).build();
        broadcastReplayDao.save(broadcastReplay);
        return true;
    }

    @Override
    public boolean update(BroadcastReplayInfo broadcastReplayInfo) {
        BroadcastReplay broadcastReplay = broadcastReplayDao.findBroadcastReplayByBroadcastReplayId(broadcastReplayInfo.getBroadcastReplyId());
        if (broadcastReplay == null) return false;

        broadcastReplay.setReplayUrl(broadcastReplayInfo.getReplayUrl());
        broadcastReplay.setOpenYn(broadcastReplayInfo.getOpenYn());

        broadcastReplayDao.save(broadcastReplay);
        return true;
    }

    @Override
    public boolean delete(int broadcastReplayId) {
        BroadcastReplay broadcastReplay = broadcastReplayDao.findBroadcastReplayByBroadcastReplayId(broadcastReplayId);
        if (broadcastReplay == null) return false;

        broadcastReplayDao.delete(broadcastReplay);
        return true;
    }

    @Override
    public List<BroadcastReplayInfo> getBroadcastReplayAll() {
        List<BroadcastReplayInfo> broadcastReplayInfoList = new ArrayList<>();
        List<BroadcastReplay> broadcastReplayList = broadcastReplayDao.findAll();

        for (int i=0;i<broadcastReplayList.size();i++) {
            BroadcastReplay broadcastReplay = broadcastReplayList.get(i);
            Broadcast broadcast = broadcastReplay.getBroadcast();
            List<Textbook> textbookList = textbookDao.findTextbooksByBroadcast(broadcast);
            Map<String, String> textbookMap = new HashMap<>();
            // 교재 저장
            for (int j=0;j<textbookList.size();j++) {
                Textbook textbook = textbookList.get(j);
                textbookMap.put(textbook.getName(), textbook.getTextbookUrl());
            }
            BroadcastReplayInfo broadcastReplayInfo = BroadcastReplayInfo.builder().broadcastReplyId(broadcastReplay.getBroadcastReplayId())
                    .replayUrl(broadcastReplay.getReplayUrl()).openYn(broadcastReplay.getOpenYn()).broadcastId(broadcast.getBroadcastId())
                    .broadcast(broadcast).textbook(textbookMap).build();
            broadcastReplayInfoList.add(broadcastReplayInfo);
        }

        return broadcastReplayInfoList;
    }

    @Override
    public List<BroadcastReplayInfo> getBroadcastReplayTrack(String trackName) {
        List<BroadcastReplayInfo> broadcastReplayInfoList = new ArrayList<>();
        List<BroadcastReplay> broadcastReplayList = broadcastReplayDao.findAll();

        for (int i=0;i<broadcastReplayList.size();i++) {
            Boolean flag = false;
            BroadcastReplay broadcastReplay = broadcastReplayList.get(i);
            Broadcast broadcast = broadcastReplay.getBroadcast();

            // 방송과 관련된 트랙 정보 가져오기
            List<BroadcastTrack> broadcastTrackList = broadcastTrackDao.findBroadcastTracksByBroadcast(broadcast);
            for (int j=0;j<broadcastTrackList.size();j++) {
                BroadcastTrack broadcastTrack = broadcastTrackList.get(j);
                // 방송과 관련된 트랙 이름과 일치하면 객체 넣기
                if (broadcastTrack.getTrack().getTrackName().equals(trackName)) flag = true;
            }

            if (flag) {
                List<Textbook> textbookList = textbookDao.findTextbooksByBroadcast(broadcast);
                Map<String, String> textbookMap = new HashMap<>();

                // 방송 관련 교재 가져오기
                for (int j=0;j<textbookList.size();j++) {
                    Textbook textbook = textbookList.get(j);
                    textbookMap.put(textbook.getName(), textbook.getTextbookUrl());
                }

                BroadcastReplayInfo broadcastReplayInfo = BroadcastReplayInfo.builder().broadcastReplyId(broadcastReplay.getBroadcastReplayId())
                        .broadcastId(broadcast.getBroadcastId()).replayUrl(broadcastReplay.getReplayUrl()).openYn(broadcastReplay.getOpenYn())
                        .textbook(textbookMap).broadcast(broadcast).build();

                broadcastReplayInfoList.add(broadcastReplayInfo);
            }
        }

        return broadcastReplayInfoList;
    }

    @Override
    public BroadcastReplayInfo getBroadcastReplay(int broadcastReplayId) {
        BroadcastReplay broadcastReplay = broadcastReplayDao.findBroadcastReplayByBroadcastReplayId(broadcastReplayId);
        Broadcast broadcast = broadcastReplay.getBroadcast();
        if (broadcastReplay == null || broadcast == null) return null;

        List<Textbook> textbookList = textbookDao.findTextbooksByBroadcast(broadcast);
        Map<String, String> textbookMap = new HashMap<>();

        for (int i=0;i<textbookList.size();i++) {
            Textbook textbook = textbookList.get(i);
            textbookMap.put(textbook.getName(), textbook.getTextbookUrl());
        }
        System.out.println("textbookMap : "+textbookMap);

        BroadcastReplayInfo broadcastReplayInfo = BroadcastReplayInfo.builder().broadcastReplyId(broadcastReplayId)
                .broadcastId(broadcast.getBroadcastId()).replayUrl(broadcastReplay.getReplayUrl()).openYn(broadcastReplay.getOpenYn())
                .textbook(textbookMap).broadcast(broadcast).build();

        return broadcastReplayInfo;
    }
}