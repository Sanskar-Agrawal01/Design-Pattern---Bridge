package com.featureflag.engines;

import com.featureflag.api.EvaluationEngine;
import com.featureflag.model.EvaluationResult;
import com.featureflag.model.UserContext;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A Decorator for EvaluationEngine that adds caching.
 * This demonstrates how to enhance the system without changing existing engines.
 */
public class CachedEvaluationEngine implements EvaluationEngine {
    private final EvaluationEngine wrapped;
    private final Map<String, EvaluationResult> cache = new ConcurrentHashMap<>();

    public CachedEvaluationEngine(EvaluationEngine wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public EvaluationResult evaluate(String featureKey, UserContext context) {
        String cacheKey = featureKey + ":" + context.getUserId();
        return cache.computeIfAbsent(cacheKey, k -> {
            System.out.println("[Cache Miss] Evaluating " + featureKey + " for user " + context.getUserId());
            return wrapped.evaluate(featureKey, context);
        });
    }

    @Override
    public String getEngineName() {
        return wrapped.getEngineName() + " (Cached)";
    }
    
    public void clearCache() {
        cache.clear();
    }
}
