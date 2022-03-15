package LavagemdeCarros.Threads;


import LavagemdeCarros.CloseApp;
import LavagemdeCarros.Main;
import LavagemdeCarros.Interfaces.MoedeiroUI;
import LavagemdeCarros.Model.ValoresMoedeiro;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.concurrent.Semaphore;

public class Moedeiro extends Thread{

    private ValoresMoedeiro valoresMoedeiro;
    public static int action = -1;
    private MoedeiroUI uiInstance;
    private Semaphore semaforo;

    public static final int ACTION_ADD_1 = 0;
    public static final int ACTION_ADD_2  = 1;
    public static final int ACTION_ADD_3 = 2;
    public static final int ACTION_ADD_4  = 3;
    public static final int ACTION_ADD_5 = 4;
    public static final int ACTION_ADD_10 = 5;
    public static final int ACTION_RETIRAR = 6;
    public static final int ACTION_ATUALIZAR = 7;

    public Moedeiro(Semaphore semMoedeiro, CloseApp closeAction,ValoresMoedeiro valoresMoedeiro)
    {
        this.valoresMoedeiro = valoresMoedeiro;
        this.semaforo = semMoedeiro;
        this.uiInstance = new MoedeiroUI(closeAction,this,valoresMoedeiro);
    }
    @Override
    public void run() {
        this.uiInstance.showMoedeiroWindow();
        while(Main.running) {
            try {
                semaforo.acquire();//Usa um recurso

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch(action) {
                case ACTION_ADD_1:
                    Main.action = Main.ACTION_INIT_LAVAGEM;
                    valoresMoedeiro.addSaldo(1);
                    break;
                case ACTION_ADD_2:
                    valoresMoedeiro.addSaldo(2);
                    break;
                case ACTION_ADD_3:
                    valoresMoedeiro.addSaldo(3);
                    break;
                case ACTION_ADD_4:
                    valoresMoedeiro.addSaldo(4);
                    break;
                case ACTION_ADD_5:
                    valoresMoedeiro.addSaldo(5);
                    break;
                case ACTION_ADD_10:
                    valoresMoedeiro.addSaldo(10);
                    break;
                case ACTION_RETIRAR:
                    valoresMoedeiro.retirarSaldo();
                    break;
                case  ACTION_ATUALIZAR:
                    uiInstance.updateUI();
                    break;
            }
        }

    }


    public void desativar(){
        uiInstance.desativarUI();
    }

    public void ativar(){
            uiInstance.ativarUI();
    }

    public void closeApp() {
        this.semaforo.release();
    }

    public  void releaseSemaforo(int mod){
        action = mod;
        this.semaforo.release();
    }

}
