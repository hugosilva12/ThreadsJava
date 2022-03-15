package LavagemdeCarros.Interfaces;

import LavagemdeCarros.AppMain;
import LavagemdeCarros.CloseApp;
import LavagemdeCarros.Threads.Teclado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Semaphore;

public class TecladoUI extends JFrame {

    private JPanel myJPanelButtons;

    private JButton myJButtonInit;
    private JButton myJButtonCancelOperation;
    private JButton myJButtonKey;
    private JButton myJButtonResetSystem;
    private JButton myJButtonOpenClose;
    private Teclado teclado;

    private Semaphore sem;

    public TecladoUI(Teclado teclado, CloseApp closeAction) {

        this.teclado = teclado;
        this.setSize(AppMain.WINDOW_WIDTH_TECLADO, AppMain.WINDOW_HEIGHT_TECLADO);
        this.setTitle("Teclado");
        this.setResizable(false);

        initComponents();

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(closeAction);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int) (dim.getWidth() / 2 - this.getWidth() / 2) + AppMain.WINDOW_WIDTH_TECLADO + 60, ((int) dim.getHeight() / 2 - this.getHeight() / 2) + AppMain.MAIN_WINDOW_HEIGHT - 475);


    }

    private void initComponents() {

        myJPanelButtons = new JPanel(new GridLayout(5, 1));

        myJButtonInit = new JButton("I – Iniciar Lavagem");
        myJButtonInit.addActionListener(listennerInitLavagem);


        myJButtonCancelOperation = new JButton("C – Cancelar a lavagem");
        myJButtonCancelOperation.addActionListener(listennerCancelAction);

        myJButtonKey = new JButton("E – Botão de emergência");
        myJButtonKey.addActionListener(listennerEmergencyAction);

        myJButtonResetSystem = new JButton("R – Reset do sistema");
        myJButtonResetSystem.addActionListener(myResetSystemAction);

        myJButtonOpenClose = new JButton("A/F - Aberto/Fechado");
        myJButtonOpenClose.addActionListener(listennerEstadoLavagem);


        myJPanelButtons.add(myJButtonInit);
        myJPanelButtons.add(myJButtonCancelOperation);
        myJPanelButtons.add(myJButtonKey);
        myJPanelButtons.add(myJButtonResetSystem);
        myJPanelButtons.add(myJButtonOpenClose);

        this.add(myJPanelButtons);

    }

    private final ActionListener listennerInitLavagem = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Teclado.setAction(Teclado.ACTION_INIT_LAVAGEM);
        }

    };

    private ActionListener listennerEstadoLavagem = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Teclado.setAction(Teclado.ACTION_ESTADO);
        }
    };


    private ActionListener listennerCancelAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Teclado.setAction(Teclado.ACTION_CANCEL_OPERATION);
        }
    };

    private ActionListener listennerEmergencyAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Teclado.setAction(Teclado.ACTION_BOTTON_EMERGY);
        }
    };

    private ActionListener myResetSystemAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Teclado.setAction(Teclado.ACTION_RESET);
        }
    };


    public void desativarUI() {
        myJButtonInit.setEnabled(false);
        myJButtonCancelOperation.setEnabled(false);
    }

    public void ativarUI() {
        myJButtonInit.setEnabled(true);
        myJButtonCancelOperation.setEnabled(true);
    }

    public void showTecladoWindow() {
        this.setVisible(true);
    }


}

