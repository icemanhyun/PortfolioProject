import components.map.Map;
import components.standard.Standard;

/**
 * Kernel interface for LogicGate component.
 */
public interface LogicGateKernel extends Standard<LogicGate> {

    /**
     * Disconnects {@code this} from the two gates that plugged into it and
     * returns an array with references to them.
     *
     * @updates this, disconnectedGate1, disconnectedGate2
     * @return An array of the previously connected LogicGates
     */
    LogicGate[] disconnect();

    /**
     * Changes the associated truth table and name of {@code this}.
     *
     * @param inputTruthTable
     *            The new truth table to make {@code this} use
     * @param newGateName
     *            The new name of {@code this}
     * @updates this
     */
    void changeGate(Map<String, String> inputTruthTable, String newGateName);

    /**
     * Connects LogicGates {@code first} and {@code second} to the inputs of
     * LogicGate {@code this}.
     *
     * @param first
     *            first LogicGate to connect to the input of {@code this}
     * @param second
     *            second LogicGate to connect to the input of {@code this}
     * @updates this
     */
    void connect(LogicGate first, LogicGate second);

    /**
     * Reports whether {@code this} has other logic gates connected to its
     * input.
     *
     * @return whether {@code this} is connected
     */
    boolean isConnected();

    /**
     * Reports the number of connections in the full chain {@code this} is a
     * member of.
     *
     * @return The number of connections between gates.
     */
    int numberOfConnections();

    /**
     * Returns the value of the output of this gate.
     *
     * @return the value of the output of this gate as a String
     */
    String value();

    /**
     * Returns the name of this gate.
     *
     * @return the name of the gate {@code this} represents.
     */
    String gateName();

    /**
     * Returns the truth table of {@code this}.
     *
     * @return the truth table of this
     */
    Map<String, String> truthTable();

}
