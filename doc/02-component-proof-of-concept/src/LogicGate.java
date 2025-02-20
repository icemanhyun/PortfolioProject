import components.map.Map;

public class LogicGate {

    /**
     * Maybe could make implementation more flexible with multiple inputs in the
     * future and the ability to pass in a truth table, but for now it's
     * hardcoded to two inputs and all the gates that are possible with that.
     */
    private String gateType;
    private LogicGate firstInput;
    private LogicGate secondInput;
    private int height;
    private int size;
    // Have some default accessible truth tables.
    private Map truthTable;
    private String[] defaultGates;

    private void createNewRep() {
        this.gateType = new String();
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
     * @param truthTable
     */
    public LogicGate(Map truthTable) {

    }

    /*
     * Standard methods ------------------------------
     */

    @Override
    public final LogicGate newInstance() {
        return this.getClass().getConstructor().newInstance();
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    // I'm not totally sure transferFrom makes sense for this kind of object
    @Override
    public final void transferFrom(LogicGate n) {
        this.gateType = n.gateType;
        this.truthTable = n.truthTable;
        this.firstInput = n.firstInput;
        this.secondInput = n.secondInput;
        this.height = n.height;
        this.size = n.size;
        n.createNewRep();
    }

    public void connect(LogicGate first, LogicGate second) {

    }

    /*
     * Kernel methods ------------------------------
     */

}
