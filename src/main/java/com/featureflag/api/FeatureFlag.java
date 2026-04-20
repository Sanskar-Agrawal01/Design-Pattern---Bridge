package com.featureflag.api;

import com.featureflag.model.EvaluationResult;
import com.featureflag.model.UserContext;
import java.util.ArrayList;
import java.util.List;

/**
 * The Abstraction in the Bridge Pattern.
 * This class maintains a reference to the implementor (EvaluationEngine).
 */
public abstract class FeatureFlag {
    protected EvaluationEngine engine;
    protected final String featureKey;
    private final List<AnalyticsHook> hooks = new ArrayList<>();

    protected FeatureFlag(String featureKey, EvaluationEngine engine) {
        this.featureKey = featureKey;
        this.engine = engine;
    }

    public void setEngine(EvaluationEngine engine) {
        this.engine = engine;
    }

    public void addAnalyticsHook(AnalyticsHook hook) {
        this.hooks.add(hook);
    }

    /**
     * The bridge method that delegates to the engine.
     */
    public EvaluationResult isEnabled(UserContext context) {
        EvaluationResult result;
        try {
            if (engine == null) {
                result = new EvaluationResult(getFallbackValue(), "No engine set, using fallback", "None");
            } else {
                result = engine.evaluate(featureKey, context);
            }
        } catch (Exception e) {
            result = new EvaluationResult(getFallbackValue(), "Engine error: " + e.getMessage(), "Fallback");
        }

        // Trigger hooks
        for (AnalyticsHook hook : hooks) {
            hook.record(featureKey, context, result);
        }

        return result;
    }

    // Concrete features can override this to define their own safety default
    protected abstract boolean getFallbackValue();
    
    public String getFeatureKey() {
        return featureKey;
    }
}
