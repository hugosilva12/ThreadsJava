package LavagemdeCarros.Interfaces;

import LavagemdeCarros.AppMain;
import LavagemdeCarros.CloseApp;
import LavagemdeCarros.Main;
import LavagemdeCarros.Threads.Tapete;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TapeteUI extends JFrame {
    private Tapete tapete;
    private JPanel myJPanelButtons, myJanela;
    private JLabel myJLabelEstado;

    public TapeteUI(Tapete tapete, CloseApp closeAction) {
        this.tapete = tapete;
        this.setSize(AppMain.WINDOW_WIDTH_TAPETE, AppMain.WINDOW_HEIGHT_TAPETE);
        this.setTitle("Tapete");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        initComponents();

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(closeAction);
        this.setLocation((int) (dim.getWidth() / 2 - this.getWidth() / 2) + AppMain.WINDOW_WIDTH_TAPETE - 700, ((int) dim.getHeight() / 2 - this.getHeight() / 2) + AppMain.WINDOW_HEIGHT_TAPETE - 200);

    }

    public void showMoedeiroWindow() {
        this.setVisible(true);
    }

    private void initComponents() {


        myJLabelEstado = new JLabel("Estado: Parado!");
        myJLabelEstado.setHorizontalAlignment(JLabel.CENTER);
        myJLabelEstado.setFont(new Font(myJLabelEstado.getFont().getFontName(), Font.BOLD, 16));
        myJLabelEstado.setVisible(true);
        myJanela = new JPanel(new GridLayout(2, 2));
        myJPanelButtons = new JPanel(new GridLayout(2, 2));


        myJanela.add(myJLabelEstado);
        myJanela.add(myJPanelButtons);
        this.add(myJanela);
    }

    public void updateLabelEstado(int action) {
        switch (action) {
            case 0:
                myJLabelEstado.setText("Estado: Parado!");
                break;
            case 1:
                myJLabelEstado.setText("Estado: Mov.Frente!");
                break;
            case 2:
                myJLabelEstado.setText("Estado: Mov.Trás!");
                break;
        }

    }

}
