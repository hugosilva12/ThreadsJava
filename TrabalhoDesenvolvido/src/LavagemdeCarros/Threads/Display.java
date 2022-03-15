package LavagemdeCarros.Threads;

import LavagemdeCarros.CloseApp;
import LavagemdeCarros.Interfaces.DisplayUI;
import LavagemdeCarros.Main;
import LavagemdeCarros.Model.ClientesEmEspera;

import javax.swing.*;
import java.util.concurrent.Semaphore;

public class Display extends Thread {

    private DisplayUI uiInstance;
    private Semaphore semDisplay;
    private ClientesEmEspera clientesEmEspera;
    public static int action = -1;
    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    private int estado = -1;
    public static final int ACTION_ATUALIZAR_NRCLIENTES= 0;
    public static final int ACEITACLIENTES = 3;
    public static final int NAOACEITACLIENTES = 1;
    public static final int OCUPADO = 2;

    public Display(Semaphore semDisplay, CloseApp closeAction,ClientesEmEspera clientesEmEspera)
    {
        this.clientesEmEspera = clientesEmEspera;
        this.semDisplay = semDisplay;
        this.uiInstance = new DisplayUI(this,closeAction);
    }


    @Override
    public void run() {
        this.uiInstance.showDisplayWindow();
        estadoAceitaClientes();

        while (Main.running){
            try {
                semDisplay.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch(action) {
                case ACTION_ATUALIZAR_NRCLIENTES:
                    try {
                        this.uiInstance.atualizarNumeroClientes();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case ACEITACLIENTES:
                        this.uiInstance.setDisplay(ACEITACLIENTES);
                    break;
                case NAOACEITACLIENTES:
                    this.uiInstance.setDisplay(NAOACEITACLIENTES);
                    break;
                case OCUPADO:
                    this.uiInstance.setDisplay(NAOACEITACLIENTES);
                    break;
            }
        }
    }

    public void estadoAceitaClientes(){
        this.uiInstance.setDisplay(ACEITACLIENTES);
        this.estado = ACEITACLIENTES;
    }

    public void estadoRejeitaClientes(){
        this.uiInstance.setDisplay(NAOACEITACLIENTES);
        this.estado = NAOACEITACLIENTES;
    }

    public void estadoOcupado(){
        this.uiInstance.setDisplay(OCUPADO);
        this.estado = OCUPADO;
    }

    public void reset(){
        this.uiInstance.setDisplay(ACEITACLIENTES);
    }

    public void closeApp() {
        this.semDisplay.release();
    }

    public  int getNumberClientsemEspera(){
        synchronized (clientesEmEspera) {
            return clientesEmEspera.getNumberClients();
        }
    }
    public void blocked() {
        JOptionPane.showMessageDialog(null, "Fechou a lavagem!", "Aviso...",
                JOptionPane.WARNING_MESSAGE);
    }
    public void unBlocked() {
        JOptionPane.showMessageDialog(null, "Reabriu a lavagem!", "Aviso...",
                JOptionPane.WARNING_MESSAGE);
    }
    public  void releaseSemaforo(int mod){
        action = mod;
        this.semDisplay.release();
    }

}
