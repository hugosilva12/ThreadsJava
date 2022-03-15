package LavagemdeCarros;

import LavagemdeCarros.InformacaoApp.EmergenciaThread;
import LavagemdeCarros.InformacaoApp.WriteLogs;
import LavagemdeCarros.Model.*;
import LavagemdeCarros.Threads.*;

import java.util.concurrent.Semaphore;

public class Main extends Thread {

    private ClientesEmEspera clientesEmEspera;
    private ValoresMoedeiro valoresMoedeiro;
    private MensagemLog mensagemLog;
    private EmLavagem emLavagem;
    private ValueConfigs readConfigs;

    public static boolean running; //Close the app


    //THREADS
    private Moedeiro moedeiro;
    private Teclado teclado;
    private Tapete tapete;
    private AspiradorSecador aspiradorSecador;
    private Rolo rolo;
    private WriteLogs writeLogs;
    private Display display;
    private CloseApp closeAction;
    private Main_Auxiliar main_auxiliar;
    private EmergenciaThread emergenciaThread;
    //Semaforos
    private Semaphore semaforoMain, semMoedeiro, semTeclado, semTapete, semDisplay, semRolo, semLogs, semAspiradorSecador, semMainMoedeiro, semEmergencia;
    private EstadoLavandaria estadoLavandaria;

    private Emergencia emergencia;
    public Boolean ISSTOP = true;
    //Static Variables

    public static final int ACTION_INIT_LAVAGEM = 0;
    public static final int ACTION_ESTADO_TAPETE = 1;
    public static final int ACTION_ROLO = 2;
    public static int action = -1;
    public static final int ACTION_ASPIRADOR = 4;
    public static final int ACTION_SECADOR = 6;
    public static int reset = 0;

    public Main(ValueConfigs readConfigs) { //Construtor Main
        this.readConfigs = readConfigs;
        emergencia = new Emergencia();

        //Log
        this.valoresMoedeiro = new ValoresMoedeiro();
        this.mensagemLog = new MensagemLog();
        this.closeAction = new CloseApp(this);
        this.clientesEmEspera = new ClientesEmEspera();
        this.emLavagem = new EmLavagem();
        this.estadoLavandaria = new EstadoLavandaria();
        //Init Semaphores
        this.semMoedeiro = new Semaphore(0);
        this.semTeclado = new Semaphore(0);
        this.semTapete = new Semaphore(0);
        this.semDisplay = new Semaphore(0);
        this.semaforoMain = new Semaphore(0);
        this.semRolo = new Semaphore(0);
        this.semLogs = new Semaphore(0);
        this.semAspiradorSecador = new Semaphore(0);
        this.semMainMoedeiro = new Semaphore(0);
        this.semEmergencia = new Semaphore(0);
        //Init Threads
        this.rolo = new Rolo(semRolo, closeAction, emLavagem, this.readConfigs.getDuracaoRolo(), emergencia);
        this.tapete = new Tapete(semTapete, semaforoMain, closeAction, readConfigs.getDuracaoTapete(), emergencia);
        this.moedeiro = new Moedeiro(semMoedeiro, closeAction, valoresMoedeiro);
        this.teclado = new Teclado(semTeclado, readConfigs, closeAction, semaforoMain, semMainMoedeiro, Thread.currentThread());
        this.display = new Display(semDisplay, closeAction, clientesEmEspera);
        this.aspiradorSecador = new AspiradorSecador(semAspiradorSecador, closeAction, emLavagem, this.readConfigs.getDuracaoAspirador(), this.readConfigs.getMinDuracaoSecador(), this.readConfigs.getMaxDuracaoSecador(), emergencia);
        this.writeLogs = new WriteLogs(semLogs, mensagemLog);
        this.emergenciaThread = new EmergenciaThread(emergencia, semEmergencia);
        // Main Auxiliar
        this.main_auxiliar = new Main_Auxiliar(this, valoresMoedeiro, clientesEmEspera, display, moedeiro, estadoLavandaria, teclado, writeLogs, semLogs, mensagemLog, this.readConfigs.getCustomonetario(), semaforoMain, semMainMoedeiro,
                tapete, rolo, aspiradorSecador, semAspiradorSecador, emergencia, emergenciaThread, semEmergencia);

    }

    @Override
    public void run() {
        running = true;
        this.teclado.start();
        this.moedeiro.start();
        this.tapete.start();
        this.aspiradorSecador.start();
        this.display.start();
        this.rolo.start();
        this.writeLogs.start();
        this.main_auxiliar.start();
        this.emergenciaThread.start();

        //Ativa semaforo log e define mensagem
        mensagemLog.setMensagem("Programa  Iniciado");
        semLogs.release();
        while (running) {
            try {
                semaforoMain.acquire(); // Bloqueia até uma ação do utilizador
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (emergencia.getEmergent() == true) {

            } else {
                if (reset != 1) { // Reset Sistema
                    if (running == true) {
                        switch (action) {
                            case ACTION_ESTADO_TAPETE:
                                mensagemLog.setMensagem("Tapete iniciado");
                                semLogs.release();
                                this.estadoLavandaria.setEstadoLavandaria(true);
                                tapete.releaseSemaforoTapete(1);
                                clientesEmEspera.as();
                                display.releaseSemaforo(0); ///Atualiza numero Clientes
                                try {
                                    Thread.sleep(readConfigs.getDuracaoTapete() * 1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                action = Main.ACTION_ASPIRADOR; /////Responde à main
                                semaforoMain.release();
                                break;
                            case ACTION_ROLO:
                                mensagemLog.setMensagem("Carro no rolo");
                                semLogs.release();
                                rolo.setAction(1);
                                semRolo.release();
                                emLavagem.espera();//Aguarda e dá autorização pra atualiza a label do rolo
                                rolo.setAction(0);//termina
                                semRolo.release();
                                action = ACTION_SECADOR;
                                try {
                                    Thread.sleep(2000); // terminou aspirar, demora dois segundos a começar no secador
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                semaforoMain.release();
                                break;
                            case ACTION_ASPIRADOR:
                                mensagemLog.setMensagem("Carro em aspiração");
                                semLogs.release();
                                aspiradorSecador.releaseSemorofo(0);
                                emLavagem.espera();
                                action = ACTION_ROLO;
                                try {
                                    Thread.sleep(2000); // terminou aspirar, demora dois segundos a começar no rolo
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                semaforoMain.release();
                                break;
                            case ACTION_SECADOR:
                                mensagemLog.setMensagem("Secador iniciado");
                                semLogs.release();
                                aspiradorSecador.releaseSemorofo(1);
                                emLavagem.espera();
                                if (ISSTOP) {
                                    try {
                                        Thread.sleep(3000); //3 segundos e para o tapete
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    tapete.releaseSemaforoTapete(0);
                                }
                                if (ISSTOP) {
                                    try {
                                        Thread.sleep(5000); //5 segundos e ve se tem mais clientes
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    if (clientesEmEspera.getNumberClients() > 0) {
                                        action = ACTION_ESTADO_TAPETE;
                                        semaforoMain.release();
                                    } else if (display.getEstado() != 1) {
                                        action = -1;
                                        this.estadoLavandaria.setEstadoLavandaria(false);
                                        display.releaseSemaforo(3);
                                    } else {
                                        action = -1;
                                        this.estadoLavandaria.setEstadoLavandaria(false);
                                    }
                                }

                                break;

                            default:

                                break;
                        }
                    }
                }

            }

        }
    }

    public void closeApp() {
        Main.running = false;
        semaforoMain.release();
        moedeiro.closeApp();
        display.closeApp();
        mensagemLog.setMensagem("Fechou Programa!");
        semLogs.release();
        tapete.releaseSemaforoTapete(-1);
        Main_Auxiliar.action = -1;
        semMainMoedeiro.release();
        aspiradorSecador.releaseSemorofo(3);
    }

}
