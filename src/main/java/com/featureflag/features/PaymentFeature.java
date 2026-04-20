package com.featureflag.features;

import com.featureflag.api.EvaluationEngine;
import com.featureflag.api.FeatureFlag;

public class PaymentFeature extends FeatureFlag {
    public PaymentFeature(EvaluationEngine engine) {
        super("crypto_payment", engine);
    }

    @Override
    protected boolean getFallbackValue() {
        return false; // Payments are risky, fail closed if engine dies
    }
}
