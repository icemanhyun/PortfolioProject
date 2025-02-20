import components.map.Map;
import components.map.Map1L;

/**
 *
 */
public final class MVPLogicGate {

    /**
     * Prevents instantiation.
     */
    private MVPLogicGate() {
    }

    /**
     *
     * @param inputOne
     * @param inputTwo
     * @param truthTableMap
     * @return the truth value
     */
    public static String evaluateValue(String inputOne, String inputTwo,
            Map<String, String> truthTableMap) {
        String fullInput = inputOne + " " + inputTwo;

        return truthTableMap.value(fullInput);
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        // using Java's map for now because I don't want to go through the work
        // of getting OSU's library imported at this immediate moment
        Map<String, String> truthTableForAND = new Map1L<String, String>();
        // This is a bit cumbersome but it works for an MVP
        truthTableForAND.add("TRUE TRUE", "TRUE");
        truthTableForAND.add("TRUE FALSE", "FALSE");
        truthTableForAND.add("FALSE TRUE", "FALSE");
        truthTableForAND.add("FALSE FALSE", "FALSE");

        Map<String, String> truthTableForOR = new Map1L<String, String>();
        // This is a bit cumbersome but it works for an MVP
        truthTableForOR.add("TRUE TRUE", "TRUE");
        truthTableForOR.add("TRUE FALSE", "FALSE");
        truthTableForOR.add("FALSE TRUE", "FALSE");
        truthTableForOR.add("FALSE FALSE", "FALSE");

        String inputOne = "TRUE";
        String inputTwo = "FALSE";
        // String inputThree = "TRUE";
        String output = evaluateValue(inputOne, inputTwo, truthTableForAND);
        output = evaluateValue(output, inputOne, truthTableForOR);
    }
}
