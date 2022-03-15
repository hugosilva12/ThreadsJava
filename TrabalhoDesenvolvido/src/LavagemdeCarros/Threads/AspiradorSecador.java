package LavagemdeCarros.Threads;

import LavagemdeCarros.Interfaces.AspiradorSecadorUI;
import LavagemdeCarros.CloseApp;
import LavagemdeCarros.Main;
import LavagemdeCarros.Model.EmLavagem;
import LavagemdeCarros.Model.Emergencia;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class AspiradorSecador extends Thread{
    public static Semaphore semAspiradorSecador;
    private AspiradorSecadorUI uiInstance;
    private int estado;
    public static final int ASPIRAR = 0;
    public static final int SECAR = 1;
    public static final int PARADO = 2;
    private  EmLavagem emLavagem;
    private int duracaoAspirador, duracaominSecador, duracaoMaxSecador ;
    private Emergencia emergencia;
    public AspiradorSecador(Semaphore semAspiradorSecador, CloseApp closeAction, EmLavagem emLavagem,int duracaoAspirador,int duracaominSecador, int duracaoMaxSecador,Emergencia emergencia) {
        this.semAspiradorSecador = semAspiradorSecador;
        this.uiInstance = new AspiradorSecadorUI(this,closeAction);
        estado = PARADO;
        this.emLavagem = emLavagem;
        this.duracaoAspirador = duracaoAspirador;
        this.duracaominSecador = duracaominSecador;
        this.duracaoMaxSecador = duracaoMaxSecador;
        this.emergencia=emergencia;
    }
    @Override
    public void run() {
        this.uiInstance.showMoedeiroWindow();
        while(Main.running)
        {
            try {
                semAspiradorSecador.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch(estado) {
                case ASPIRAR:
                    uiInstance.updateLabel(1);
                    long startTime = System.currentTimeMillis();
                    int timeexecut  = duracaoAspirador;
                    if(emergencia.getOperation() == true){
                        emergencia.setOperation(false);
                        timeexecut= duracaoAspirador - (int)emergencia.getTempoExecucao();
                    }
                    try {
                        Thread.sleep(timeexecut * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    uiInstance.updateLabel(0);
                    long endTime = System.currentTimeMillis();
                    long seconds = (endTime - startTime) / 1000;
                    emergencia.setTempoExecucao(seconds);
                    emLavagem.acorda();
                    break;
                case SECAR:
                    uiInstance.updateLabel(2);
                    startTime = System.currentTimeMillis();
                     timeexecut  = geraValor();
                     //Verificar se vem da emergencia
                    if(emergencia.getOperation() == true){
                        emergencia.setOperation(false);
                        timeexecut = timeexecut - (int)emergencia.getTempoExecucao();
                    }
                    System.out.println(timeexecut);
                    try {
                        Thread.sleep(timeexecut * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    uiInstance.updateLabel(0);
                     endTime = System.currentTimeMillis();
                     seconds = (endTime - startTime) / 1000;
                    emergencia.setTempoExecucao(seconds);
                    emLavagem.acorda();
                    break;
                default:
                    break;
            }
        }
    }
    public void releaseSemorofo(int estado){
        this.estado = estado;
         semAspiradorSecador.release();
    }

 public int geraValor(){
     Random r = new Random();
     int low =   this.duracaominSecador;
     int high = this.duracaoMaxSecador;
     int result = r.nextInt(high-low) + low;
     return result;
 }
}
