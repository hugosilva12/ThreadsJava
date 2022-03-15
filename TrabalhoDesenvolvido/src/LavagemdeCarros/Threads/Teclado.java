package LavagemdeCarros.Threads;

import LavagemdeCarros.CloseApp;
import LavagemdeCarros.Main;
import LavagemdeCarros.Interfaces.TecladoUI;
import LavagemdeCarros.Main_Auxiliar;
import LavagemdeCarros.Model.ValueConfigs;

import java.util.concurrent.Semaphore;

public class Teclado extends Thread {
    private TecladoUI uiInstance;
    public static int action = -1;
    private static Semaphore semTeclado;
    private ValueConfigs configs = new ValueConfigs();
    private Semaphore semMain, semMain2;


    public static final int ACTION_INIT_LAVAGEM = 0;
    public static final int ACTION_BOTTON_EMERGY = 1;
    public static final int ACTION_CANCEL_OPERATION = 2;
    public static final int ACTION_ESTADO = 5;
    public static final int ACTION_RESET = 4;

    public Teclado(Semaphore semTeclado, ValueConfigs config, CloseApp closeAction, Semaphore semMain, Semaphore semMain2, Thread threadStop) {
        this.semTeclado = semTeclado;
        this.uiInstance = new TecladoUI(this, closeAction);
        this.semMain2 = semMain2;
        this.semMain = semMain;
        this.configs = config;
    }


    @Override
    public void run() {
        this.uiInstance.showTecladoWindow();
        while (Main.running) {
            try {
                semTeclado.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            switch (action) {
                case ACTION_INIT_LAVAGEM:
                    Main_Auxiliar.action = Main_Auxiliar.ACTION_INIT_LAVAGEM;
                    this.semMain2.release();
                    break;
                case ACTION_BOTTON_EMERGY:
                    Main_Auxiliar.action = Main_Auxiliar.ACTION_EMERGENCIA;
                    this.semMain2.release();
                    break;
                case ACTION_CANCEL_OPERATION:
                    Main_Auxiliar.action = Main_Auxiliar.ACTION_RESET;
                    this.semMain2.release();
                    break;

                case ACTION_RESET:
                    Main_Auxiliar.action = Main_Auxiliar.ACTION_RESET;
                    this.semMain2.release();
                    break;
                case ACTION_ESTADO:
                    Main_Auxiliar.action = Main_Auxiliar.ACTION_ESTADO;
                    this.semMain2.release();
                    break;
            }
        }


    }

    public static void setAction(int action) {
        Teclado.action = action;
        semTeclado.release();
    }

    public void closeApp() {
        semTeclado.release();
    }

    public void desativar() {
        uiInstance.desativarUI();
    }

    public void ativar() {
        uiInstance.ativarUI();
    }
}
