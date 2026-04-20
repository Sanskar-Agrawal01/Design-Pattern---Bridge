package com.featureflag.api;

import com.featureflag.model.EvaluationResult;
import com.featureflag.model.UserContext;

/**
 * The Implementor interface in the Bridge Pattern.
 * This defines the decision-making logic for feature flags.
 */
public interface EvaluationEngine {
    EvaluationResult evaluate(String featureKey, UserContext context);
    String getEngineName();
}
