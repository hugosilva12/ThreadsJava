package LavagemdeCarros.InformacaoApp;

import LavagemdeCarros.Main;
import LavagemdeCarros.Model.Emergencia;
import LavagemdeCarros.Model.MensagemLog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Semaphore;

public class WriteLogs extends Thread {
    private Semaphore semDisplay;
    private MensagemLog mensagem;


    public WriteLogs(Semaphore semDisplay, MensagemLog mensagem) {
        this.semDisplay = semDisplay;
        this.mensagem = mensagem;
    }

    @Override
    public void run() {
        super.run();

        while (Main.running) {
            try {
                semDisplay.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                log(mensagem.getMensagem());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void log(String message) throws IOException {
        File file = new File("src/LavagemdeCarros/InformacaoApp/logs.txt"); // src/LavagemdeCarros/InformacaoApp/logs.txt
        FileWriter fr = new FileWriter(file, true);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        fr.write("[" + dtf.format(now) + "] - " + message + "\n");
        fr.close();
    }


}
