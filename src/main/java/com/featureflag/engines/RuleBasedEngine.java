package com.featureflag.engines;

import com.featureflag.api.EvaluationEngine;
import com.featureflag.model.EvaluationResult;
import com.featureflag.model.UserContext;
import java.util.Map;

/**
 * A Rule Based Engine that evaluates attributes.
 * This simulates dynamic configuration (JSON/DB style).
 */
public class RuleBasedEngine implements EvaluationEngine {
    private final Map<String, String> requiredAttributes;

    public RuleBasedEngine(Map<String, String> requiredAttributes) {
        this.requiredAttributes = requiredAttributes;
    }

    @Override
    public EvaluationResult evaluate(String featureKey, UserContext context) {
        for (Map.Entry<String, String> entry : requiredAttributes.entrySet()) {
            String actualValue = context.getAttribute(entry.getKey());
            if (actualValue == null || !actualValue.equalsIgnoreCase(entry.getValue())) {
                return new EvaluationResult(
                    false, 
                    "Attribute mismatch: Expected " + entry.getKey() + "=" + entry.getValue() + ", got " + actualValue, 
                    getEngineName()
                );
            }
        }
        return new EvaluationResult(true, "All rules satisfied", getEngineName());
    }

    @Override
    public String getEngineName() {
        return "RuleBasedEngine";
    }
}
