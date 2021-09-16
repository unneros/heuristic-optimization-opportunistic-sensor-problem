import java.util.ArrayList;
import java.util.List;

public class HeuristicResult {
    Variant bestFoundVariant = new Variant();
    List<Integer> evaluationResultProgress = new ArrayList<>();
    List<Integer> evaluationResultProgressWorst = new ArrayList<>();
    int bestFoundVariantEvaluation;
}
