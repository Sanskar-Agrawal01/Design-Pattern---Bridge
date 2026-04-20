package com.featureflag.features;

import com.featureflag.api.EvaluationEngine;
import com.featureflag.api.FeatureFlag;

public class SearchFeature extends FeatureFlag {
    public SearchFeature(EvaluationEngine engine) {
        super("ai_search", engine);
    }

    @Override
    protected boolean getFallbackValue() {
        return false; // AI search is expensive, fail closed
    }
}
