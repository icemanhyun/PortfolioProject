
/**
 * {@code LogicGateKernel} enhanced with secondary methods.
 */
public interface LogicGate {

    /**
     * Creates a readable diagram representing the circuit leading up to
     * {@code this}.
     *
     * @return a diagram of this
     */
    String circuitDiagram();

    /**
     * Inverts the truth table of {@code this}, so it returns "FALSE" when it
     * would return "TRUE" and "TRUE" when it would return "FALSE".
     *
     * @update this
     */
    void invertGate();

    /**
     * Inverts the truth table of all of the LogicGates in the circuit
     * {@code this} is a member of.
     *
     * @update this, all LogicGates in the chain
     */
    void invertChain();

    /**
     * Changes the truth table and gate name of {@code this} to that of {@code
     * gateToCopy}.
     *
     * @param gateToCopy
     *            The gate to copy the truth table and name of
     * @update this
     */
    void changeGate(LogicGate gateToCopy);

    /**
     * Returns the values of the ends of the circuit {@code this} is a member
     * of.
     *
     * @return the values of the ends as Strings
     */
    String[] totalValue();

}
