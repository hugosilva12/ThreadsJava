package LavagemdeCarros.Interfaces;

import LavagemdeCarros.AppMain;
import LavagemdeCarros.CloseApp;
import LavagemdeCarros.Threads.Display;

import javax.swing.*;
import java.awt.*;

public class DisplayUI extends JFrame {

    private JPanel myJPanelDisplay;
    private JLabel myJLabelRecebeClientes;
    private JLabel myJLabelNaoRecebeClientes;
    private JLabel myJLabelOcupado;
    private JLabel myJLabelNumeroClientesEspera;
    private Display display;

    public DisplayUI(Display display, CloseApp closeAction) {
        this.setSize(AppMain.WINDOW_WIDTH_DISPLAY, AppMain.WINDOW_HEIGHT_DISPLAY);

        this.setTitle("Lavagem de Carros");
        this.setResizable(false);
        this.display = display;
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(closeAction);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int) (dim.getWidth() / 2 - this.getWidth() / 2) + AppMain.WINDOW_WIDTH_DISPLAY - 370, ((int) dim.getHeight() / 2 - this.getHeight() / 2) + AppMain.MAIN_WINDOW_HEIGHT - 475);
        initComponents();

    }

    public void initComponents() {
        String img = "imgs/img.png";
        myJPanelDisplay = new JPanel(new GridLayout(4, 1));

        ///Estado da Lavagem de carros
        myJLabelRecebeClientes = new JLabel("Estado: Aceita Clientes");
        myJLabelRecebeClientes.setHorizontalAlignment(JLabel.CENTER);
        myJLabelRecebeClientes.setFont(new Font(myJLabelRecebeClientes.getFont().getFontName(), Font.BOLD, 16));
        myJLabelRecebeClientes.setVisible(false);

        myJLabelNaoRecebeClientes = new JLabel("Estado: Fechado!");
        myJLabelNaoRecebeClientes.setHorizontalAlignment(JLabel.CENTER);
        myJLabelNaoRecebeClientes.setFont(new Font(myJLabelNaoRecebeClientes.getFont().getFontName(), Font.BOLD, 16));
        myJLabelNaoRecebeClientes.setVisible(false);

        myJLabelOcupado = new JLabel("Estado: Ocupado!");
        myJLabelOcupado.setHorizontalAlignment(JLabel.CENTER);
        myJLabelOcupado.setFont(new Font(myJLabelOcupado.getFont().getFontName(), Font.BOLD, 16));
        myJLabelOcupado.setVisible(false);

        myJLabelNumeroClientesEspera = new JLabel("Número Clientes em espera: " + display.getNumberClientsemEspera());
        myJLabelNumeroClientesEspera.setVisible(true);
        myJLabelNumeroClientesEspera.setHorizontalAlignment(JLabel.CENTER);
        myJLabelNumeroClientesEspera.setFont(new Font(myJLabelOcupado.getFont().getFontName(), Font.ITALIC, 16));

        myJPanelDisplay.add(myJLabelRecebeClientes);
        myJPanelDisplay.add(myJLabelNaoRecebeClientes);
        myJPanelDisplay.add(myJLabelOcupado);
        myJPanelDisplay.add(myJLabelNumeroClientesEspera);
        this.add(myJPanelDisplay);

    }

    public void setDisplay(int choise) {

        switch (choise) {

            case Display.ACEITACLIENTES:
                myJLabelRecebeClientes.setVisible(true);
                myJLabelNaoRecebeClientes.setVisible(false);
                myJLabelOcupado.setVisible(false);
                break;
            case Display.OCUPADO:
                myJLabelNaoRecebeClientes.setVisible(false);
                myJLabelRecebeClientes.setVisible(false);
                myJLabelOcupado.setVisible(true);
                break;

            case Display.NAOACEITACLIENTES:
                myJLabelNaoRecebeClientes.setVisible(true);
                myJLabelRecebeClientes.setVisible(false);
                myJLabelOcupado.setVisible(false);
                break;

        }
    }

    public void showDisplayWindow() {
        this.setVisible(true);
    }

    public void atualizarNumeroClientes() throws InterruptedException {
        Thread.sleep(100);
        myJLabelNumeroClientesEspera.setText("Número Clientes em espera: " + display.getNumberClientsemEspera());


    }
}
