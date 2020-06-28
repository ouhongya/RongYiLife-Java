package com.rysh.module.beOmnipotentScore.service;

import com.rysh.module.beOmnipotentScore.beans.ScoreDetail;
import com.rysh.module.beOmnipotentScore.beans.UserScoreDetail;

import java.util.List;

public interface ScoreService {
    List<ScoreDetail> findMarketScoreSource(String marketId);

    List<UserScoreDetail> findUserScoreSource(String uid) throws Exception;
}
