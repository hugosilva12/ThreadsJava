package LavagemdeCarros.Interfaces;

import LavagemdeCarros.AppMain;
import LavagemdeCarros.CloseApp;
import LavagemdeCarros.Threads.AspiradorSecador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AspiradorSecadorUI extends JFrame {
    private AspiradorSecador aspirador;
    private JPanel myAspiradorSecador;
    private JLabel myJLabeEstado, myJLabelTitle;

    public static final int PARADO = 0;
    public static final int EM_ASPIRACAO = 1;
    public static final int EM_SECAGEM = 2;

    public AspiradorSecadorUI(AspiradorSecador aspirador, CloseApp closeAction) {
        this.aspirador = aspirador;
        this.setTitle("Aspirador e secador");
        this.addWindowListener(closeAction);
        initComponents();
        this.setSize(AppMain.WINDOW_WIDTH_TAPETE, AppMain.WINDOW_HEIGHT_TAPETE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(closeAction);
        this.setLocation((int) (dim.getWidth() / 2 - this.getWidth() / 2) + AppMain.WINDOW_WIDTH_TAPETE - 300, ((int) dim.getHeight() / 2 - this.getHeight() / 2) + AppMain.WINDOW_HEIGHT_TAPETE - 200);
    }

    public void showMoedeiroWindow() {
        this.setVisible(true);
    }

    private void initComponents() {
        myJLabelTitle = new JLabel("ASPIRADOR /SECADOR");
        myJLabeEstado = new JLabel("Estado: Parado");
        this.myJLabeEstado.setVisible(true);
        myJLabeEstado.setHorizontalAlignment(JLabel.CENTER);
        myJLabelTitle.setHorizontalAlignment(JLabel.CENTER);
        myJLabelTitle.setFont(new Font(myJLabeEstado.getFont().getFontName(), Font.BOLD, 18));
        myJLabeEstado.setFont(new Font(myJLabeEstado.getFont().getFontName(), Font.BOLD, 16));

        myAspiradorSecador = new JPanel();

        this.myAspiradorSecador.add(myJLabelTitle);
        this.myAspiradorSecador.add(myJLabeEstado);
        this.add(myAspiradorSecador);
    }

    public void updateLabel(int option) {
        switch (option) {
            case PARADO:
                myJLabeEstado.setText("Estado: Parado");
                break;
            case EM_ASPIRACAO:
                myJLabeEstado.setText("Estado: Em aspiração");
                break;
            case EM_SECAGEM:
                myJLabeEstado.setText("Estado: Em secagem");
                break;
        }

    }

}