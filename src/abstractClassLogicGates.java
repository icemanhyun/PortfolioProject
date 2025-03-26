import java.util.ArrayList;
import java.util.Map;

public class abstractClassLogicGates {
    String circuitDiagram(){
        String circuitDiagram = "";
        LogicGate[] connectedGates = this.disconnect();
        if (this.isConected()) {
            circuitDiagram = "(" + this.connectedGates[0].circuitDiagram()
                    + " + " + this.connectedGates[1].circuitDiagram() + ")"
                    + this.gateName();
        } else {
            circuitDiagram = this.gateName();
        }
        this.connect(connectedGates[0], connectedGates[1]);
        return circuitDiagram;
    }

    String toString(){
        return this.getName();
    }

    boolean equals(LogicGate gate){
        boolean isEqual = true;
        isEqual = this.truthTable().equals(gate.truthTable()) && this.getName().equals(gate.getName());
        if (this.isConnected()){
            if (gate.isConnected()){
                LogicGate[] connectedGates1 = this.disconnect();
                LogicGate[] connectedGates2 = gate.disconnect();
                isEqual = isEqual && connectedGates1.equals(connectedGates2);
                this.connect(connectedGates1[0], connectedGates1[1]);
                this.connect(connectedGates2[0], connectedGates2[1]);
            }
            else {
                isEqual = false;
            }
        }
        return isEqual;
    }

    void invertGate(){
        Map<String, String> reverseTruthTable = new Map1L();
        Map<String, String> originalTruthTable = this.truthTable();
        Map.Pair<String, String> tempMapPair;
        for (int i = 0; i < 4; i++) {
            tempMapPair = originalTruthTable.removeAny();
            if (tempMapPair.value() == "FALSE") {
                reverseTruthTable.add(tempMapPair.key(), "TRUE");
            } else {
                reverseTruthTable.add(tempMapPair.key(), "FALSE");
            }
        }
        changeGate(reverseTruthTable, this.gateName());
    }


    void changeGate(LogicGate gateToCopy){
        Map<String, String> copyTruthTable = gateToCopy.truthTable();
        String copyName = gateToCopy.getName();
        changeGate(copyTruthTable, copyName);
    }

}
