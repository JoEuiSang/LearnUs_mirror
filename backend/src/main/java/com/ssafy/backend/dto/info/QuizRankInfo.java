package com.ssafy.backend.dto.info;

import com.ssafy.backend.dto.QuizAnswer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizRankInfo {
    private List<QuizAnswer> quizAnswerList;
    private Map<String, Integer> rateMap;
}
