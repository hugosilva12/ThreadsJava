package LavagemdeCarros.Interfaces;

import LavagemdeCarros.AppMain;
import LavagemdeCarros.CloseApp;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextHitInfo;

public class RoloUI extends JFrame {
    private JPanel myRolo;
    private JLabel myJLabelEstado, myJLabelTitle;

    public RoloUI(CloseApp closeAction) {

        this.setSize(AppMain.WINDOW_WIDTH_ROLO, AppMain.WINDOW_HEIGHT_ROLO);
        this.setTitle("Rolo");
        this.setResizable(false);

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(closeAction);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int) (dim.getWidth() / 2 - this.getWidth() / 2) + AppMain.WINDOW_WIDTH_TAPETE + 50, ((int) dim.getHeight() / 2 - this.getHeight() / 2) + AppMain.WINDOW_HEIGHT_TAPETE - 200);
        initComponents();
    }

    public void showRoloWindow() {
        this.setVisible(true);
    }

    private void initComponents() {
        String aberta = "imgs/aberta.jpg";
        String fechada = "imgs/fechada.jpg";
        myRolo = new JPanel();
        myJLabelTitle = new JLabel("MAQUINA DO ROLO");
        myJLabelEstado = new JLabel("Estado: Parado!");

        myJLabelEstado.setHorizontalAlignment(JLabel.CENTER);
        myJLabelTitle.setHorizontalAlignment(JLabel.CENTER);
        myJLabelTitle.setFont(new Font(myJLabelTitle.getFont().getFontName(), Font.BOLD, 18));
        myJLabelEstado.setFont(new Font(myJLabelEstado.getFont().getFontName(), Font.BOLD, 16));
        myJLabelEstado.setVisible(true);
        myRolo.add(myJLabelTitle);
        myRolo.add(myJLabelEstado);
        this.add(myRolo);
    }

    public void updateEstadoAtivo() {
        myJLabelEstado.setText("Estado: Ativo!");
    }

    public void updateEstadoParado() {
        myJLabelEstado.setText("Estado: Parado!");
    }
}
