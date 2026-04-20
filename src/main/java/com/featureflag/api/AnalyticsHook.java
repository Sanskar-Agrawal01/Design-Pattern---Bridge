package com.featureflag.api;

import com.featureflag.model.EvaluationResult;
import com.featureflag.model.UserContext;

public interface AnalyticsHook {
    void record(String featureKey, UserContext context, EvaluationResult result);
}
