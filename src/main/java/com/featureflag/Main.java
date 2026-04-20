package com.featureflag;

import com.featureflag.model.EvaluationResult;
import com.featureflag.api.FeatureFlag;
import com.featureflag.engines.*;
import com.featureflag.features.*;
import com.featureflag.model.UserContext;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Feature Flag System (Bridge Pattern) ===\n");

        // 1. Setup Analytics Hook (Logging implementation)
        com.featureflag.api.AnalyticsHook logger = (key, ctx, res) -> {
            System.out.printf("[ANALYTICS] Feature: %s | User: %s | Result: %s%n", 
                key, ctx.getUserId(), res.isEnabled());
        };

        // 2. Define Contexts
        UserContext alice = new UserContext("user_101");
        alice.addAttribute("tier", "premium");
        alice.addAttribute("country", "US");

        UserContext bob = new UserContext("user_102");
        bob.addAttribute("tier", "free");
        bob.addAttribute("country", "UK");

        // 3. Demonstrate Bridge: Different features with different engines
        
        // Login: 50% Rollout
        FeatureFlag loginFeature = new LoginFeature(new PercentageRolloutEngine(50));
        loginFeature.addAnalyticsHook(logger);

        // Payment: Specific Targeting
        java.util.Set<String> targetUsers = new java.util.HashSet<>();
        targetUsers.add("user_101");
        FeatureFlag paymentFeature = new PaymentFeature(new UserTargetingEngine(targetUsers));
        paymentFeature.addAnalyticsHook(logger);

        // Search: Rule Based (Premium users in US)
        java.util.Map<String, String> searchRules = new java.util.HashMap<>();
        searchRules.put("tier", "premium");
        searchRules.put("country", "US");
        FeatureFlag searchFeature = new SearchFeature(new RuleBasedEngine(searchRules));
        searchFeature.addAnalyticsHook(logger);

        // 4. Evaluate
        System.out.println("--- Evaluation Round 1 ---");
        evaluate(loginFeature, alice);
        evaluate(loginFeature, bob);
        evaluate(paymentFeature, alice);
        evaluate(paymentFeature, bob);
        evaluate(searchFeature, alice);
        evaluate(searchFeature, bob);

        // 5. Demonstrate Flexibility: Swapping engines at runtime
        System.out.println("\n--- Swapping Engines Dynamically ---");
        System.out.println("Turning SearchFeature ON for everyone using BooleanEngine...");
        searchFeature.setEngine(new BooleanEngine(true));
        evaluate(searchFeature, bob);

        // 6. Demonstrate Caching (The 'Better' part)
        System.out.println("\n--- Demonstrating Caching Decorator ---");
        CachedEvaluationEngine cachedEngine = new CachedEvaluationEngine(new PercentageRolloutEngine(10));
        loginFeature.setEngine(cachedEngine);
        
        System.out.println("First call (Cache Miss):");
        evaluate(loginFeature, alice);
        System.out.println("Second call (Cache Hit - No evaluation output):");
        evaluate(loginFeature, alice);

        // 7. Demonstrate Fallback
        System.out.println("\n--- Demonstrating Fallback (No Engine) ---");
        paymentFeature.setEngine(null);
        evaluate(paymentFeature, alice);
    }

    private static void evaluate(FeatureFlag feature, UserContext context) {
        EvaluationResult result = feature.isEnabled(context);
        System.out.printf("Feature [%s] for User [%s] -> %s (Reason: %s, Engine: %s)%n",
            feature.getFeatureKey(), context.getUserId(), result.isEnabled() ? "ENABLED" : "DISABLED",
            result.getReason(), result.getEngineName());
    }
}
