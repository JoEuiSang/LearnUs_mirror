package com.ssafy.backend.service.track;

import com.ssafy.backend.dto.Track;
import com.ssafy.backend.dto.info.TrackInfo;

import java.util.List;

public interface TrackService {
    public boolean insert(TrackInfo trackInfo);
    public boolean update(TrackInfo trackInfo);
    public boolean delete(int trackId);
    public List<Track> getTrackAll();
    public List<Track> getTrackSubject(int subjectId);
    public List<Track> getCurrentTrackSubject();
}