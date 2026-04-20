package com.featureflag.model;

public class EvaluationResult {
    private final boolean enabled;
    private final String reason;
    private final String engineName;

    public EvaluationResult(boolean enabled, String reason, String engineName) {
        this.enabled = enabled;
        this.reason = reason;
        this.engineName = engineName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getReason() {
        return reason;
    }

    public String getEngineName() {
        return engineName;
    }

    @Override
    public String toString() {
        return "Result[enabled=" + enabled + ", reason='" + reason + "', engine=" + engineName + "]";
    }
}
