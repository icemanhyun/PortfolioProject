import components.map.Map;
import components.map.Map1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public class LogicGate {

    /**
     * It might make sense in the future to make a basic gate object, and have
     * things like TRUE or FALSE gates and NOT gates that only take one value be
     * extended from it?
     */

    /**
     * This whole thing is unfortunately a hack job!
     */

    /**
     * Maybe could make implementation more flexible with multiple inputs in the
     * future, but for now it's hardcoded to two inputs.
     */
    private String gateName;

    // Maybe I could use getClass() and make these generic objects, so value()
    // could also take booleans? That might be computationally expensive and
    // irresponsible, I don't know enough yet, it just seemed like it could look
    // nicer

    /**
     * The first gate this is connected to.
     */
    private LogicGate firstInput;

    /**
     * The first gate this is connected to.
     */
    private LogicGate secondInput;

    /**
     * Whether this is connected to two inputs.
     */
    private boolean connectedUp;

    /**
     * The height of the logic gate chain.
     */
    private int height;

    /**
     * The size of the logic gate chain.
     */
    private int size;

    /**
     * The truth value of this particular gate. Value is a string for now,
     * because I thought it could be possible to let this take more input values
     * than True or False, but that might change in the future and this could
     * just be a boolean or a 1 or 0.
     */
    private String value;

    // Have some default accessible truth tables.
    private Map<String, String> truthTable;
    private String[] defaultGates;

    /**
     *
     */
    private void createNewRep() {
        this.gateName = new String();
        this.firstInput = null;
        this.secondInput = null;
        this.height = 0;
        this.size = 0;
    }

    /*
     * Constructors -------------------------------------------------
     */

    /**
     * Constructor from a truth table.
     *
     * @param inputTruthTable
     * @param gateNameString
     */
    public LogicGate(Map<String, String> inputTruthTable,
            String gateNameString) {
        this.createNewRep();
        this.truthTable = inputTruthTable;
        this.gateName = gateNameString;
    }

    /**
     * Constructor for a TRUE or FALSE gate.
     *
     * @param value
     * @param gateNameString
     */
    public LogicGate(String value, String gateNameString) {
        this.createNewRep();
        this.value = value;
        this.gateName = gateNameString;
    }

    /*
     * Standard methods ------------------------------
     */

    // public final LogicGate newInstance() {
    //     return this.getClass().getConstructor().newInstance();
    // }

    public final void clear() {
        this.createNewRep();
    }

    // I'm not totally sure transferFrom makes sense for this kind of object
    public final void transferFrom(LogicGate n) {
        this.gateName = n.gateName;
        this.truthTable = n.truthTable;
        this.firstInput = n.firstInput;
        this.secondInput = n.secondInput;
        this.height = n.height;
        this.size = n.size;
        n.createNewRep();
    }

    /*
     * Methods ------------------------------
     */

    /**
     *
     * @param first
     * @param second
     */
    public void connect(LogicGate first, LogicGate second) {
        this.firstInput = first;
        this.secondInput = second;
        this.connectedUp = true;
        if (first.height > second.height) {
            this.height = 1 + first.height;
        } else {
            this.height = 1 + second.height;
        }
        this.size = 1 + first.size + second.size;
    }

    /**
     * Copies the type of gate gateToCopy is. Different from transferFrom,
     * because it doesn't copy its connections.
     *
     * @param gateToCopy
     */
    public void changeGate(LogicGate gateToCopy) {
        this.truthTable = gateToCopy.truthTable;
        this.gateName = gateToCopy.gateName;
    }

    /**
     *
     * @param inputTruthTable
     * @param gateNameString
     */
    public void changeGate(Map<String, String> inputTruthTable,
            String gateNameString) {
        this.truthTable = inputTruthTable;
        this.gateName = gateNameString;
    }

    // I have to think more about how I'm going to do the initial gates

    /**
     * Recursive value method. Checks if this gate is a gate with connections.
     *
     * @return the value of this gate
     */
    public String value() {
        String value = "";
        if (this.connectedUp) {
            String tempValue = this.firstInput.value() + " "
                    + this.secondInput.value();
            value = this.truthTable.value(tempValue);
        } else {
            value = this.value;
        }
        return value;
    }

    /**
     * Requires that this be connected.
     *
     * @return The gates connected to this
     */
    public LogicGate[] connections() {
        LogicGate[] connectedGates = { this.firstInput, this.secondInput };
        return connectedGates;
    }

    /**
     * Modifies the truth table for this.
     *
     * @param inputToModify
     * @param newOutput
     */
    public void modifyTruthTable(String inputToModify, String newOutput) {
        this.truthTable.replaceValue(inputToModify, newOutput);
    }

    /**
     *
     */
    public void invertGate() {
        Map<String, String> reverseTruthTable = new Map1L();
        Map.Pair<String, String> tempMapPair;
        for (int i = 0; i < 4; i++) {
            tempMapPair = this.truthTable.removeAny();
            if (tempMapPair.value() == "FALSE") {
                reverseTruthTable.add(tempMapPair.key(), "TRUE");
            } else {
                reverseTruthTable.add(tempMapPair.key(), "FALSE");
            }
        }
        this.truthTable.transferFrom(reverseTruthTable);
    }

    /**
     *
     * @return the size of this
     */
    public int size() {
        return this.size;
    }

    /**
     *
     * @return the height of this
     */
    public int height() {
        return this.height;
    }

    /**
     *
     * @return The name of this gate
     */
    public String gateName() {
        return this.gateName;
    }

    /**
     * Maybe this would just be the "to String" of this? Feels a bit excessively
     * artsy for that, though.
     *
     * @return a string picture of the circuit up to this gate.
     */
    private String circuitDiagramInternal() {
        String circuitDiagram = "";
        if (this.connectedUp) {
            circuitDiagram = "(" + this.firstInput.circuitDiagramInternal()
                    + " + " + this.secondInput.circuitDiagramInternal() + ")"
                    + this.gateName;
        } else {
            circuitDiagram = this.gateName;
        }
        return circuitDiagram;
    }

    /**
     *
     * @return A string picture with the full result
     *
     */
    public String circuitDiagram() {
        return this.circuitDiagramInternal() + " = " + this.value();
    }

    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        Map<String, String> truthTableForAND = new Map1L<>();
        truthTableForAND.add("TRUE TRUE", "TRUE");
        truthTableForAND.add("TRUE FALSE", "FALSE");
        truthTableForAND.add("FALSE TRUE", "FALSE");
        truthTableForAND.add("FALSE FALSE", "FALSE");
        Map<String, String> truthTableForOR = new Map1L<String, String>();
        truthTableForOR.add("TRUE TRUE", "TRUE");
        truthTableForOR.add("TRUE FALSE", "TRUE");
        truthTableForOR.add("FALSE TRUE", "TRUE");
        truthTableForOR.add("FALSE FALSE", "FALSE");
        Map<String, String> truthTableForXOR = new Map1L<String, String>();
        truthTableForXOR.add("TRUE TRUE", "FALSE");
        truthTableForXOR.add("TRUE FALSE", "TRUE");
        truthTableForXOR.add("FALSE TRUE", "TRUE");
        truthTableForXOR.add("FALSE FALSE", "FALSE");

        LogicGate ANDGate = new LogicGate(truthTableForAND, "AND Gate");
        LogicGate ORGate = new LogicGate(truthTableForOR, "OR Gate");
        LogicGate XORGate = new LogicGate(truthTableForXOR, "XOR Gate");
        LogicGate trueGate = new LogicGate("TRUE", "TRUE");
        LogicGate falseGate = new LogicGate("FALSE", "FALSE");

        // Extremely unresistant to mistakes
        out.println("Connecting ORGate to TRUE and FALSE");
        out.println("Connecting ANDGate to ORGate and TRUE");
        out.println("Connecting XORGate to ORGate and ANDGate");
        out.println("");

        ORGate.connect(trueGate, falseGate);
        ANDGate.connect(ORGate, trueGate);
        XORGate.connect(ORGate, ANDGate);

        // I'd like to show a version of this that progressively solves and then moves up levels of the diagram,
        // but I'm not sure how I would implement that.
        out.println(
                "Circuit diagram for the ANDGate:" + ANDGate.circuitDiagram());
        out.println(
                "Circuit diagram for the XORGate:" + XORGate.circuitDiagram());

        out.println("");

        out.println("ORGate value: " + ORGate.value());
        out.println("ANDGate value: " + ANDGate.value());
        out.println("XORGate value: " + XORGate.value());
        ANDGate.invertGate();
        out.println(
                "ANDGate value after ANDGate inversion: " + ANDGate.value());
        out.println(
                "XORGate value after ANDGate inversion: " + XORGate.value());

        out.println("");

        // The meaning of height is more ambigious for logic gates
        // Size too. It might not even be something we have to track, or maybe
        // this could track the number of connections between gates.
        out.println("Height of AND: " + ANDGate.height());
        out.println("Height of XOR: " + XORGate.height());
        out.println("Size of AND: " + ANDGate.size());
        out.println("Size of XOR: " + XORGate.size());

        out.close();
    }
}
