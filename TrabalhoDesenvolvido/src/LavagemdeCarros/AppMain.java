package LavagemdeCarros;


import LavagemdeCarros.InformacaoApp.ReadConfigs;
import LavagemdeCarros.Model.ValueConfigs;

public class AppMain {
    public static final int MAIN_WINDOW_WIDTH = 400;
    public static final int MAIN_WINDOW_HEIGHT = 250;

    public static final int WINDOW_WIDTH_TECLADO = 280;
    public static final int WINDOW_HEIGHT_TECLADO = 400;

    public static final int WINDOW_WIDTH_MOEDEIRO = 250;
    public static final int WINDOW_HEIGHT_MOEDEIRO = 400;

    public static final int WINDOW_WIDTH_TAPETE = 290;
    public static final int WINDOW_HEIGHT_TAPETE = 350;

    public static final int WINDOW_WIDTH_DISPLAY = 350;
    public static final int WINDOW_HEIGHT_DISPLAY = 300;

    public static final int WINDOW_WIDTH_ROLO = 290;
    public static final int WINDOW_HEIGHT_ROLO = 350;

    public static void main(String[] args) {
        //Ler Configurações
        ValueConfigs configs = new ValueConfigs();
        Thread readFile = new Thread(new ReadConfigs(configs), "Main");
        readFile.start();
        try {
            readFile.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Algo falha na leitura
        if (configs.getDuracaoRolo() == 0 || configs.getDuracaoAspirador() == 0 || configs.getMaxDuracaoSecador() == 0 || configs.getDuracaoTapete() == 0 || configs.getMinDuracaoSecador() == 0 || configs.getCustomonetario() == 0) {
            configs.setDuracaoRolo(5);
            configs.setDuracaoTapete(2);
            configs.setCustomonetario(5);
            configs.setDuracaoAspirador(5);
            configs.setMinDuracaoSecador(3);
            configs.setMaxDuracaoSecador(6);

        }
        //Inicia Projecto
        Thread mainThread = new Thread(new Main(configs), "Main");
        mainThread.start();

        try {
            mainThread.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

}
