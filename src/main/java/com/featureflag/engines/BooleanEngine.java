package com.featureflag.engines;

import com.featureflag.api.EvaluationEngine;
import com.featureflag.model.EvaluationResult;
import com.featureflag.model.UserContext;

public class BooleanEngine implements EvaluationEngine {
    private final boolean enabled;

    public BooleanEngine(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public EvaluationResult evaluate(String featureKey, UserContext context) {
        return new EvaluationResult(enabled, "Static boolean configuration", getEngineName());
    }

    @Override
    public String getEngineName() {
        return "BooleanEngine";
    }
}
