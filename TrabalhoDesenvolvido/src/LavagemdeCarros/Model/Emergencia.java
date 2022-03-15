package LavagemdeCarros.Model;

import java.io.Serializable;

public class Emergencia implements Serializable {
    private Boolean isEmergent;

    private Boolean operation; //True só executa x tempo da thread
    private int mainAction;
    private long tempoExecucao;

    public Emergencia() {
        this.isEmergent = false;
        this.operation = false;
    }

    public Boolean getOperation() {
        return operation;
    }

    public void setOperation(Boolean operation) {
        this.operation = operation;
    }

    public synchronized long getTempoExecucao() {
        return tempoExecucao;
    }

    public synchronized void setTempoExecucao(long tempoExecucao) {
        this.tempoExecucao = tempoExecucao;
    }

    public synchronized int getMainAction() {
        return mainAction;
    }

    public synchronized void setMainAction(int mainAction) { this.mainAction = mainAction; }

    public synchronized Boolean getEmergent() {
        return isEmergent;
    }

    public synchronized void setEmergent(Boolean emergent) {
        isEmergent = emergent;
    }


    @Override
    public String toString() {
        return "Emergencia{" +
                "isEmergent=" + isEmergent +
                ", ondeEstava=" + mainAction +
                ", tempoExecucao=" + tempoExecucao +
                '}';
    }
}
