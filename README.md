# Feature Flag System (Bridge Design Pattern)

A robust, decoupled Feature Flag system built in Java using the **Bridge Design Pattern**. This system separates feature definitions from evaluation logic, allowing both to evolve independently without class explosion.

## 🚀 Key Features

- **Bridge Architecture**: Decouples `FeatureFlag` (Abstraction) from `EvaluationEngine` (Implementation).
- **Runtime Flexibility**: Switch evaluation strategies (Boolean, Rollout, Rule-based) at runtime.
- **Deterministic Rollout**: Consistency across sessions using UserID-based hashing.
- **Rule Engine**: Dynamic attribute-based evaluation (e.g., User Tier, Country).
- **Performance Optimized**: Built-in caching decorator for evaluation results.
- **Analytics Hooks**: Plug-and-play support for logging and analytics.
- **Safe Fallbacks**: Per-feature "fail-open" or "fail-closed" configurations.

## 🏗️ Project Structure

```text
src/main/java/com/featureflag/
├── api/            # Abstractions & Interfaces
├── engines/        # Evaluation Strategies (Implementors)
├── features/       # Specific Feature Definitions
├── model/          # Data Transfer Objects (Context, Result)
└── Main.java       # System Demonstration
```

## 🛠️ Design Pattern: Bridge

The **Bridge Pattern** is used to decouple an abstraction from its implementation so that the two can vary independently. 

- **Why Bridge?** In Feature Flagging, you often have many features (Login, Search, UI-V2) and many evaluation ways (50% rollout, beta users only, internal staff only). Without Bridge, you would need `LoginBetaFeature`, `LoginRolloutFeature`, etc. Bridge allows any feature to use any engine via composition.

## 🚦 How to Run

### Compilation
Compile the project using standard Java compiler (compatible with Java 8+):
```bash
# Get all sources and compile into bin directory
$sources = (Get-ChildItem -Recurse -Filter *.java).FullName
javac -d bin -sourcepath src/main/java $sources
```

### Execution
Run the demo class to see the system in action:
```bash
java -cp bin com.featureflag.Main
```

## 📝 Example Usage

```java
// Define a feature with a specific engine
FeatureFlag searchFeature = new SearchFeature(new PercentageRolloutEngine(20));

// Evaluate for a user
UserContext user = new UserContext("user_123");
EvaluationResult result = searchFeature.isEnabled(user);

if (result.isEnabled()) {
    // Show AI Search
}
```
