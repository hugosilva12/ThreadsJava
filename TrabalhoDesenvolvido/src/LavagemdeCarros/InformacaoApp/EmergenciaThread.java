package LavagemdeCarros.InformacaoApp;

import LavagemdeCarros.Main;
import LavagemdeCarros.Model.Emergencia;

import java.io.*;
import java.util.concurrent.Semaphore;

public class EmergenciaThread extends Thread {
    private Emergencia emergencia;
    private Semaphore semEmergenciaThread;
    private int estado;

    public EmergenciaThread(Emergencia emergencia, Semaphore semEmergenciaThread) {
        this.semEmergenciaThread = semEmergenciaThread;
        this.emergencia = emergencia;
        this.estado = 0;
    }

    @Override
    public void run() {
        while (Main.running) {
            try {
                semEmergenciaThread.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (estado == 0) {
                try {
                    writeEstado(emergencia);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    emergencia = readEstado();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void writeEstado(Emergencia emergencia) throws IOException {
        File file = new File("src/LavagemdeCarros/InformacaoApp/estado.txt"); //
        FileOutputStream f = new FileOutputStream(file);
        FileWriter fr = new FileWriter(file, true);
        ObjectOutputStream o = new ObjectOutputStream(f);
        // Write objects to file
        o.writeObject(emergencia);
        o.close();
        fr.close();
    }

    public Emergencia readEstado() throws IOException {
        File file = new File("src/LavagemdeCarros/InformacaoApp/estado.txt"); //
        FileInputStream fi = new FileInputStream(file);
        ObjectInputStream oi = new ObjectInputStream(fi);

        // Read objects
        Emergencia pr1 = null;
        try {
            pr1 = (Emergencia) oi.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        oi.close();
        fi.close();
        return pr1;
    }

    public void alterarEstado(int estado) {
        this.estado = estado;
    }
}
