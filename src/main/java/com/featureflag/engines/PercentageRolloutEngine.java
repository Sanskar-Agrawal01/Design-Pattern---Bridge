package com.featureflag.engines;

import com.featureflag.api.EvaluationEngine;
import com.featureflag.model.EvaluationResult;
import com.featureflag.model.UserContext;

public class PercentageRolloutEngine implements EvaluationEngine {
    private final int rolloutPercentage;

    public PercentageRolloutEngine(int rolloutPercentage) {
        if (rolloutPercentage < 0 || rolloutPercentage > 100) {
            throw new IllegalArgumentException("Rollout percentage must be between 0 and 100");
        }
        this.rolloutPercentage = rolloutPercentage;
    }

    @Override
    public EvaluationResult evaluate(String featureKey, UserContext context) {
        String userId = context.getUserId();
        // Deterministic hashing of featureKey + userId
        int hash = Math.abs((featureKey + userId).hashCode()) % 100;
        boolean enabled = hash < rolloutPercentage;
        
        return new EvaluationResult(
            enabled, 
            String.format("Rollout: %d%%. User hash: %d", rolloutPercentage, hash), 
            getEngineName()
        );
    }

    @Override
    public String getEngineName() {
        return "PercentageRolloutEngine";
    }
}
