package com.featureflag.features;

import com.featureflag.api.EvaluationEngine;
import com.featureflag.api.FeatureFlag;

public class LoginFeature extends FeatureFlag {
    public LoginFeature(EvaluationEngine engine) {
        super("login_v2", engine);
    }

    @Override
    protected boolean getFallbackValue() {
        return true; // Login is critical, fail open if engine dies
    }
}
