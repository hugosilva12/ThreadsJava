package LavagemdeCarros;

import LavagemdeCarros.InformacaoApp.EmergenciaThread;
import LavagemdeCarros.InformacaoApp.WriteLogs;
import LavagemdeCarros.Model.*;
import LavagemdeCarros.Threads.*;

import javax.swing.*;
import java.util.concurrent.Semaphore;

public class Main_Auxiliar extends Thread {

    public static final int ACTION_INIT_LAVAGEM = 0;
    public static final int ACTION_RESET = 2;
    public static final int ACTION_CANCELAR = 1;
    public static final int ACTION_ESTADO = 5;
    public static final int ACTION_EMERGENCIA = 6;
    public static int action = -1;

    private ValoresMoedeiro valoresMoedeiro;
    private ClientesEmEspera clientesEmEspera;
    private Display display;
    private Moedeiro moedeiro;
    private EstadoLavandaria estadoLavandaria;
    private Teclado teclado;
    private WriteLogs writeLogs;
    private Semaphore semLogs, semMain, sem_Main_Moedeiro, semRolo, semEmergencia;
    private MensagemLog mensagemLog;
    private int precoLavagem;
    private Rolo rolo;
    private AspiradorSecador aspiradorSecador;
    private Tapete tapete;
    private Emergencia emergencia;
    private EmergenciaThread emergenciaThread;
    private Main main;

    public void semDinheiro() {
        JOptionPane.showMessageDialog(null, "Dinheiro Insuficiente", "Aviso...",
                JOptionPane.WARNING_MESSAGE);
    }

    public Main_Auxiliar(Main main, ValoresMoedeiro valoresMoedeiro, ClientesEmEspera clientesEmEspera, Display display, Moedeiro moedeiro, EstadoLavandaria estadoLavandaria, Teclado teclado, WriteLogs writeLogs, Semaphore semLogs, MensagemLog mensagemLog, int precoLavagem, Semaphore semMain, Semaphore sem_Main_Moedeiro
            , Tapete tapete, Rolo rolo, AspiradorSecador aspiradorSecador, Semaphore semRolo, Emergencia emergencia, EmergenciaThread emergenciaThread, Semaphore semEmergencia) {
        this.valoresMoedeiro = valoresMoedeiro;
        this.clientesEmEspera = clientesEmEspera;
        this.display = display;
        this.moedeiro = moedeiro;
        this.sem_Main_Moedeiro = sem_Main_Moedeiro;
        this.estadoLavandaria = estadoLavandaria;
        this.teclado = teclado;
        this.writeLogs = writeLogs;
        this.semLogs = semLogs;
        this.mensagemLog = mensagemLog;
        this.precoLavagem = precoLavagem;
        this.semMain = semMain;
        this.tapete = tapete;
        this.rolo = rolo;
        this.semRolo = semRolo;
        this.aspiradorSecador = aspiradorSecador;
        this.tapete = tapete;
        this.emergencia = emergencia;
        this.emergenciaThread = emergenciaThread;
        this.semEmergencia = semEmergencia;
        this.main = main;
    }

    @Override
    public void run() {
        while (Main.running) {
            try {
                this.sem_Main_Moedeiro.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch (action) {

                case ACTION_INIT_LAVAGEM:
                    if (valoresMoedeiro.getSaldo() >= precoLavagem) {
                        Main.reset = 0;
                        valoresMoedeiro.pagarLavagem(precoLavagem);
                        moedeiro.releaseSemaforo(7); //Atualiza valor moedeiro
                        clientesEmEspera.addClient();
                        display.estadoOcupado();
                        display.releaseSemaforo(0);
                        if (estadoLavandaria.isEstadoLavandaria() != true) {
                            emergencia.setEmergent(false);
                            emergencia.setOperation(false);
                            Main.action = Main.ACTION_ESTADO_TAPETE;
                            this.semMain.release();
                        }
                    } else {
                        semDinheiro(); //informa utilizador
                    }
                    break;


                case ACTION_ESTADO:
                    if (display.getEstado() == 3 || display.getEstado() == 2) {
                        display.estadoRejeitaClientes();
                        moedeiro.desativar();
                        teclado.desativar();
                        display.blocked();
                        //Ativa semaforo logo e define mensagem
                        mensagemLog.setMensagem("Lavagem  passa a estado Estado Ocupado");
                        semLogs.release();
                    } else {
                        display.estadoAceitaClientes();
                        moedeiro.ativar();
                        teclado.ativar();
                        display.unBlocked();
                        //Ativa semaforo logo e define mensagem
                        mensagemLog.setMensagem("Lavagem passa a Estado livre");
                        semLogs.release();
                    }
                    break;

                case ACTION_CANCELAR:
                    if (estadoLavandaria.isEstadoLavandaria()) {
                    } else {
                        valoresMoedeiro.reset();
                        moedeiro.releaseSemaforo(7); //Atualiza valor moedeiro
                    }
                    break;

                case ACTION_RESET:
                    display.releaseSemaforo(3);
                    clientesEmEspera.setCount(0);
                    display.releaseSemaforo(0);
                    if (Main.action == Main.ACTION_ASPIRADOR) {
                        aspiradorSecador.interrupt();
                    } else if (Main.action == Main.ACTION_ROLO) {
                        rolo.interrupt();
                    } else if (Main.action == Main.ACTION_SECADOR) {
                        aspiradorSecador.interrupt();
                    }

                    Main.reset = 1;
                    tapete.releaseSemaforoTapete(0);
                    estadoLavandaria.setEstadoLavandaria(false);
                    emergencia.setOperation(false);
                    valoresMoedeiro.reset();
                    moedeiro.releaseSemaforo(7); //Atualiza valor moedeiro
                    clientesEmEspera.reset();
                    display.releaseSemaforo(3);
                    break;

                case ACTION_EMERGENCIA:
                    if (display.getEstado() == 1 && estadoLavandaria.isEstadoLavandaria() == false) {

                    } else {
                        if (emergencia.getEmergent() == true) {
                            this.emergenciaThread.alterarEstado(2);
                            semEmergencia.release();
                            emergencia.setEmergent(false);
                            tapete.releaseSemaforoTapete(1);
                            Main.action = emergencia.getMainAction();
                            main.ISSTOP = true;
                            this.semMain.release();
                        } else {
                            emergenciaThread.alterarEstado(0);
                            semEmergencia.release();

                            emergencia.setEmergent(true);
                            emergencia.setOperation(true);

                            //Verificar onde está atualmente a lavagem
                            if (Main.action == Main.ACTION_ESTADO_TAPETE) {
                                emergencia.setOperation(false);

                            } else if (Main.action == Main.ACTION_ASPIRADOR) {
                                aspiradorSecador.interrupt();
                                emergencia.setMainAction(Main.ACTION_ASPIRADOR);

                            } else if (Main.action == Main.ACTION_ROLO) {
                                rolo.interrupt();
                                emergencia.setMainAction(Main.ACTION_ROLO);

                            } else if (Main.action == Main.ACTION_SECADOR) {
                                aspiradorSecador.interrupt();
                                emergencia.setMainAction(Main.ACTION_SECADOR);

                                main.ISSTOP = false;
                            }
                            tapete.releaseSemaforoTapete(0);
                            semEmergencia.release();
                        }
                    }

                    break;
                default:
                    break;

            }
        }
    }
}
