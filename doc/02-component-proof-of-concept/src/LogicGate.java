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
     * This whole thing is unfortunately perhaps a hack job, it will get better
     * or I'll just do a music playlist!
     */

    /**
     * Maybe could make implementation more flexible with multiple inputs in the
     * future, but for now it's hardcoded to two inputs.
     */
    private String gateType;

    /**
     * The first gate this is connected to.
     */
    private LogicGate firstInput;

    /**
     * The first gate this is connected to.
     */
    private LogicGate secondInput;

    /**
     * The head of the chain this is connected to. A gate can be connected to
     * multiple top parts of a chain, maybe could track all of them, or track
     * the highest chain it's a member of?
     */
    private LogicGate headOfChain;

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
     * just be a boolean.
     */
    private String value;

    // Have some default accessible truth tables.
    private Map<String, String> truthTable;
    private String[] defaultGates;

    /**
     *
     */
    private void createNewRep() {
        this.gateType = new String();
        this.firstInput = null;
        this.secondInput = null;
        this.headOfChain = this;
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
     * @param gateTypeString
     */
    public LogicGate(Map inputTruthTable, String gateTypeString) {
        this.createNewRep();
        this.truthTable = inputTruthTable;
        this.gateType = gateTypeString;
    }

    /**
     * Constructor for a TRUE or FALSE gate.
     *
     * @param inputTruthTable
     * @param gateTypeString
     */
    public LogicGate(String value, String gateTypeString) {
        this.createNewRep();
        this.value = value;
        this.gateType = gateTypeString;
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
        this.gateType = n.gateType;
        this.truthTable = n.truthTable;
        this.firstInput = n.firstInput;
        this.secondInput = n.secondInput;
        this.height = n.height;
        this.size = n.size;
        n.createNewRep();
    }

    /*
     * Kernel methods ------------------------------
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
    }

    /**
     * Copies the type of gate gateToCopy is. Different from transferFrom,
     * because it doesn't copy its connections.
     *
     * @param gateToCopy
     */
    public void changeGateType(LogicGate gateToCopy) {
        this.truthTable = gateToCopy.truthTable;
        this.gateType = gateToCopy.gateType;
    }

    /**
     *
     * @param inputTruthTable
     * @param gateTypeString
     */
    public void changeGateType(Map<String, String> inputTruthTable,
            String gateTypeString) {
        this.truthTable = inputTruthTable;
        this.gateType = gateTypeString;
    }

    // I have to think more about how I'm going to do the initial gates

    /**
     * Recursive value method. Checks if this gate is a gate with connections.
     *
     * @return
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
     * Requires that this be connected
     *
     * @return The gates connected to this
     */
    public LogicGate[] connections() {
        LogicGate[] connectedGates = { this.firstInput, this.secondInput };
        return connectedGates;
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
        truthTableForOR.add("TRUE FALSE", "FALSE");
        truthTableForOR.add("FALSE TRUE", "FALSE");
        truthTableForOR.add("FALSE FALSE", "FALSE");

        LogicGate ANDGate = new LogicGate(truthTableForAND, "AND Gate");
        LogicGate ORGate = new LogicGate(truthTableForOR, "OR Gate");
        LogicGate trueGate = new LogicGate("TRUE", "TRUE Input");
        LogicGate falseGate = new LogicGate("FALSE", "FALSE Input");
        // Weird behavior probably happens if you connect gates recursively.
        // I'll have to think about how to handle that in the bigger implementation
        ANDGate.connect(ORGate, trueGate);
        ORGate.connect(trueGate, falseGate);
        out.println(ORGate.value());
        out.println(ANDGate.value());

        out.close();
    }
}
