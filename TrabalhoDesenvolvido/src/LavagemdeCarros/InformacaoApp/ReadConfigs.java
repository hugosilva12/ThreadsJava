package LavagemdeCarros.InformacaoApp;

import LavagemdeCarros.Model.ValueConfigs;


import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ReadConfigs extends Thread {
    private ValueConfigs readConfigs;

    public ReadConfigs(ValueConfigs readConfigs) {
        this.readConfigs = readConfigs;
    }

    @Override
    public void run() {
        super.run();
        File file = new File("src/LavagemdeCarros/InformacaoApp/JsonFile.json"); // src/LavagemdeCarros/InformacaoApp/JsonFile.json

        FileReader fr = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fr);
        String line;
        try {

            while ((line = br.readLine()) != null) {
                String[] list = line.split(":");
                String[] listAux = list[1].split(",");
                readConfigs.setDuracaoRolo(Integer.parseInt(listAux[0]));
                listAux = list[2].split(",");
                readConfigs.setMaxDuracaoSecador(Integer.parseInt(listAux[0]));
                listAux = list[3].split(",");
                readConfigs.setCustomonetario(Integer.parseInt(listAux[0]));
                listAux = list[4].split(",");
                readConfigs.setDuracaoTapete(Integer.parseInt(listAux[0]));
                listAux = list[5].split(",");
                readConfigs.setDuracaoAspirador(Integer.parseInt(listAux[0]));
                listAux = list[6].split("}");
                readConfigs.setMinDuracaoSecador(Integer.parseInt(listAux[0]));
            }

        } catch (IOException ex) {

        }
    }

}



