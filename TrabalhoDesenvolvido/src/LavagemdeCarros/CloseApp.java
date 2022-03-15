package LavagemdeCarros;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CloseApp extends WindowAdapter {
    private Main main;

    public CloseApp(Main main) {
        this.main = main;
    }

    @Override
    public void windowClosing(WindowEvent e) {

        int opt = JOptionPane.showOptionDialog(null, "Sair da  aplicação?", "Fechar Aplicação", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]{"Sim", "Não"}, "Sim");

        if (opt == 0) {
            main.closeApp();
        }
    }
}
