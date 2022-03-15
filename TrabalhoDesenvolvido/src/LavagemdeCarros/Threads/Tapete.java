package LavagemdeCarros.Threads;

import LavagemdeCarros.CloseApp;
import LavagemdeCarros.Main;
import LavagemdeCarros.Interfaces.TapeteUI;
import LavagemdeCarros.Model.EmLavagem;
import LavagemdeCarros.Model.Emergencia;

import java.util.concurrent.Semaphore;

public class Tapete extends Thread {
    private static Semaphore semTapete;
    private Semaphore semMain;
    private TapeteUI uiInstance;
    private int duracaoTapete;
    public static int action;
    public static final int PARADO = 0;
    public static final int MOV_FRENTE = 1;


    public Tapete(Semaphore semTeclado, Semaphore semMain, CloseApp closeAction, int duracaoTapete, Emergencia emergencia) {
        this.semTapete = semTeclado;
        this.uiInstance = new TapeteUI(this, closeAction);
        action = PARADO;
        this.duracaoTapete = duracaoTapete;
        this.semMain = semMain;
    }

    @Override
    public void run() {
        this.uiInstance.showMoedeiroWindow();
        while (Main.running) {
            try {
                semTapete.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch (action) {
                case MOV_FRENTE:
                    uiInstance.updateLabelEstado(1);

                    break;
                case PARADO:
                    uiInstance.updateLabelEstado(0);
                    break;
                case -1:
                    break;
            }
        }
    }

    public void releaseSemaforoTapete(int estado) {
        action = estado;
        semTapete.release();
    }

    public void updateLabelEstado() {
        uiInstance.updateLabelEstado(2);
    }

}
