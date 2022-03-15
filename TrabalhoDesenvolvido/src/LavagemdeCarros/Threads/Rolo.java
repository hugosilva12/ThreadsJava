package LavagemdeCarros.Threads;

import LavagemdeCarros.CloseApp;
import LavagemdeCarros.Interfaces.RoloUI;
import LavagemdeCarros.Main;
import LavagemdeCarros.Model.EmLavagem;
import LavagemdeCarros.Model.Emergencia;

import java.util.concurrent.Semaphore;

public class Rolo extends Thread{

    private RoloUI uiInstance;
    private int action = -1;
    private Semaphore semRolo;
    private int duracaoRolo;
    private  EmLavagem emLavagem;
    private Emergencia emergencia;
    public static final int PARADO = 0;
    public static final int ATIVADO = 1;
    public Rolo(Semaphore semRolo, CloseApp closeAction, EmLavagem emLavagem, int duracaoRolo, Emergencia emergencia)
    {
        this.semRolo = semRolo;
        this.uiInstance = new RoloUI(closeAction);
        this.emLavagem = emLavagem;
        this.duracaoRolo = duracaoRolo;
        this.emergencia = emergencia;
    }

    @Override
    public void run() {
        this.uiInstance.showRoloWindow();

        while (Main.running) {
            try {
                semRolo.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            switch(action) {
                case ATIVADO:
                    uiInstance.updateEstadoAtivo();
                    long startTime = System.currentTimeMillis();
                    int timeexecut  = duracaoRolo;
                    if(emergencia.getOperation() == true){
                        emergencia.setOperation(false);
                        timeexecut= duracaoRolo - (int) emergencia.getTempoExecucao();
                    }
                    try {
                        Thread.sleep(timeexecut *1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    long endTime = System.currentTimeMillis();
                    long seconds = (endTime - startTime) / 1000;
                    emergencia.setTempoExecucao(seconds);
                    emLavagem.acorda();
                    break;
                case PARADO:
                    uiInstance.updateEstadoParado();
                    break;
            }
        }

    }

    public void setAction(int action) {
        this.action = action;
    }



}
