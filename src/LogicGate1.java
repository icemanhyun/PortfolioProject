import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

/**
 * {@code LogicGate} represented as a connectable node. Interacts with other gates.
 * 
 * 
 * 
 * @convention this is empty and not connected to any other gates or this connects to and/or is connected to by other gates and has a full truth table 
 * @correspondance this is represented as a nonhierarchical connection of gates
 */
public class LogicGate1 {

    /*
     * The logic gates leading into this one.
     */
    private ArrayList<LogicGate1> inputs = new ArrayList<>();

    /**
     * All of the gates in the chain of this.
     */
    private HashSet<LogicGate1> allGates = new HashSet<>();

    /**
     * The truth table {@code this} consults.
     */
    Map<String, String> truthTable;

    /**
     * The name of {@code this}. 
     */
    String name;

    /**
     * The value of {@code this} ONLY if this is a true or false gate. This does not store the temporary value of a gate with a truth table.
     */
    String value;

    private void createNewRep(){
        this.name = "Default";
        this.inputs = null;
        this.truthTable = null;
        this.allGates = null;
    }

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
        this.name = gateNameString;
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
        this.name = gateNameString;
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(LogicGate a) {
        LogicGate1 localA = (logicGate1) a;
        this.name = localA.name;
        this.truthTable = localA.truthTable;
        this.inputs = localA.inputs;
        this.allGates = localA.allGates;
        localA.createNewRep();
    }

    /**
     * Modifies the truth table for this by just replacing one pair of values.
     *
     * @param inputToModify
     * @param newOutput
     */
    public void modifyTruthTable(String inputToModify, String newOutput) {
        this.truthTable.replace(inputToModify, newOutput);
    }

    /**
     * Change the name of {@code this}.
     * @param newName
     */
    public void changeName(String newName){
        this.name = newName;
    }

    /**
     * Change the truth table of {@code this}.
     * @param newTruthTable
     */
    public void changeTruthTable(Map<String, String> newTruthTable){
        this.truthTable = newTruthTable;
    }

    /**
     * Returns the truth table of {@code this}.
     *
     * @return the truth table of this
     */
    public Map<String, String> truthTable(){
        return this.truthTable;
    }

    public String value(){
        String value = "";
        if (this.inputs.length == 2) {
            String tempValue = this.firstInput.value() + " "
                    + this.secondInput.value();
            value = this.truthTable.value(tempValue);
        } else {
            value = this.value;
        }
        return value;
    }

    /**
     * Returns the name of this gate.
     *
     * @return the name of the gate {@code this} represents.
     */
    public String gateName(){
        return this.name;
    }

    /**
     * Reports whether {@code this} has other logic gates connected to its
     * input.
     *
     * @return whether {@code this} is connected
     */
    public boolean isConnected(){
        return this.inputs.size() > 0;
    }

    /**
     *
     * @return the number of gates in the chain {@code this} is in.s
     */
    public int size() {
        return this.allGates.size();
    }
    /**
     * Connects {@code a} and {@code b} to the input of this.
     * @param a
     * @param b
     */
    public void connect(LogicGate1 a, LogicGate1 b){
        this.inputs.add(a);
        this.inputs.add(b);
        // make sure the set of all gates is comprehensive
        LogicGate1 localA = (LogicGate1) a;
        LogicGate1 localB = (LogicGate1) b;

        localA.allGates.addAll(localB.allGates);
        localA.allGates.add(this);
        localB.allGates = localA.allGates.clone();
        this.allGates = localA.allGates.clone();
    }

    /**
     * Disconnects {@code this} from the two gates that plugged into it and
     * returns an array with references to them.
     *
     * @updates this, this.inputs
     * @return An array list of the previously connected LogicGates
     */
    public ArrayList<LogicGate1> disconnect(){
        ArrayList<LogicGate1> temp = this.inputs.clone();
        this.inputs.clear();
        return temp;
    }
}
