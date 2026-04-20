package com.featureflag.engines;

import com.featureflag.api.EvaluationEngine;
import com.featureflag.model.EvaluationResult;
import com.featureflag.model.UserContext;
import java.util.Set;

public class UserTargetingEngine implements EvaluationEngine {
    private final Set<String> targetUserIds;

    public UserTargetingEngine(Set<String> targetUserIds) {
        this.targetUserIds = targetUserIds;
    }

    @Override
    public EvaluationResult evaluate(String featureKey, UserContext context) {
        boolean enabled = targetUserIds.contains(context.getUserId());
        return new EvaluationResult(
            enabled, 
            enabled ? "User is in targeting list" : "User is not in targeting list", 
            getEngineName()
        );
    }

    @Override
    public String getEngineName() {
        return "UserTargetingEngine";
    }
}
