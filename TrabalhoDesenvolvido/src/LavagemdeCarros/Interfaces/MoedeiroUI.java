package LavagemdeCarros.Interfaces;

import LavagemdeCarros.AppMain;
import LavagemdeCarros.CloseApp;
import LavagemdeCarros.Model.ValoresMoedeiro;
import LavagemdeCarros.Threads.Moedeiro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;


public class MoedeiroUI extends JFrame {

    private ValoresMoedeiro valoresMoedeiro;
    public static JLabel myJLabeSaldo;
    public static JLabel myJLabeTroco;
    private JLabel myJLabelFotoInicio;
    private JPanel myJPanelMoedeiro, myJPanelButtons2, myJPanelTrocoSaldo, myJPanelRetirarDinheiro;
    private JButton myJButton5, myJButton2, myJButton20, myJButton10, myJButton1, myJButton50, myJButtonRetirar;
    private Moedeiro moedeiro;
    public static JFrame parent = new JFrame();


    public MoedeiroUI(CloseApp closeAction, Moedeiro moedeiro, ValoresMoedeiro valoresMoedeiro) {
        this.setSize(AppMain.WINDOW_WIDTH_MOEDEIRO, AppMain.WINDOW_HEIGHT_MOEDEIRO);
        this.setTitle("Money");
        this.setResizable(false);

        this.moedeiro = moedeiro;
        this.valoresMoedeiro = valoresMoedeiro;
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(closeAction);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int) (dim.getWidth() / 2 - this.getWidth() / 2) + AppMain.WINDOW_WIDTH_MOEDEIRO - 650, ((int) dim.getHeight() / 2 - this.getHeight() / 2) + AppMain.MAIN_WINDOW_HEIGHT - 475);

        initComponents();
    }

    private void initComponents() {
        Color green = new Color(0, 255, 0);
        Color red = new Color(255, 0, 0);
        Color white = new Color(255, 255, 244);
        String foto = "imgs/moeda.png"; // "imgs/moeda.png"

        myJPanelMoedeiro = new JPanel(new GridLayout(3, 1));
        myJPanelTrocoSaldo = new JPanel(new GridLayout(3, 1));
        myJPanelButtons2 = new JPanel(new GridLayout(3, 2));
        myJPanelRetirarDinheiro = new JPanel(new GridLayout(1, 1));
        myJLabelFotoInicio = new JLabel(new ImageIcon(foto), JLabel.CENTER);
        myJLabeSaldo = new JLabel("Saldo: 0 ");
        myJLabeSaldo.setHorizontalAlignment(JLabel.CENTER);
        myJLabeSaldo.setFont(new Font(myJLabeSaldo.getFont().getFontName(), Font.BOLD, 16));
        myJLabeSaldo.setFont(new Font(myJLabeSaldo.getFont().getFontName(), Font.BOLD, 16));


        myJLabeTroco = new JLabel("Troco: 0");
        myJLabeTroco.setHorizontalAlignment(JLabel.CENTER);
        myJLabeTroco.setFont(new Font(myJLabeTroco.getFont().getFontName(), Font.BOLD, 16));

        myJButton5 = new JButton("1.00");
        myJButton5.setBackground(green);
        myJButton5.addActionListener(my5);


        myJButton10 = new JButton("2.00");
        myJButton10.setBackground(green);
        myJButton10.addActionListener(my10);

        myJButton20 = new JButton("3.00");
        myJButton20.setBackground(green);
        myJButton20.addActionListener(my20);

        myJButton50 = new JButton("4.00");
        myJButton50.setBackground(green);
        myJButton50.addActionListener(my50);

        myJButton1 = new JButton("5.00");
        myJButton1.setBackground(green);
        myJButton1.addActionListener(my1);

        myJButton2 = new JButton("10.00");

        myJButton2.setBackground(green);
        myJButton2.addActionListener(my2);


        myJButtonRetirar = new JButton("Retirar Dinheiro");
        myJButtonRetirar.setFont(new Font(myJButtonRetirar.getFont().getFontName(), Font.BOLD, 16));
        myJButtonRetirar.setForeground(white);
        myJButtonRetirar.addActionListener(myretirar);
        myJButtonRetirar.setBackground(red);

        myJPanelTrocoSaldo.add(myJLabelFotoInicio);
        myJPanelTrocoSaldo.add(myJLabeSaldo);
        myJPanelTrocoSaldo.add(myJLabeTroco);


        myJPanelButtons2.add(myJButton5);
        myJPanelButtons2.add(myJButton10);
        myJPanelButtons2.add(myJButton20);
        myJPanelButtons2.add(myJButton50);
        myJPanelButtons2.add(myJButton1);
        myJPanelButtons2.add(myJButton2);
        myJPanelRetirarDinheiro.add(myJButtonRetirar);

        myJPanelMoedeiro.add(myJPanelTrocoSaldo); //add label dinheiro
        myJPanelMoedeiro.add(myJPanelButtons2); //add label botoes myJPanelRetirarDinheiro
        myJPanelMoedeiro.add(myJPanelRetirarDinheiro);
        this.add(myJPanelMoedeiro);

    }

    private ActionListener my5 = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            moedeiro.releaseSemaforo(0);
            updateUI();

        }
    };

    private ActionListener my10 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            moedeiro.releaseSemaforo(1);
            updateUI();
        }
    };

    private ActionListener my20 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            moedeiro.releaseSemaforo(2);
            updateUI();
        }
    };

    private ActionListener my50 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            moedeiro.releaseSemaforo(3);
            updateUI();
        }
    };

    private ActionListener my1 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            moedeiro.releaseSemaforo(4);
            updateUI();
        }
    };

    private ActionListener my2 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            moedeiro.releaseSemaforo(5);
            updateUI();
        }
    };


    private ActionListener myretirar = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            moedeiro.releaseSemaforo(6);
            updateUI();
        }
    };

    public static void errorMessage(String string) {
        JOptionPane.showMessageDialog(parent, string);
    }


    public void updateUI() {
        DecimalFormat df = new DecimalFormat("#.##");
        MoedeiroUI.myJLabeSaldo.setText("Saldo: " + df.format(valoresMoedeiro.getSaldo()));
        MoedeiroUI.myJLabeTroco.setText("Troco: " + df.format(valoresMoedeiro.getTroco()));
    }

    public void showMoedeiroWindow() {
        this.setVisible(true);
    }

    public void desativarUI() {
        myJButton5.setEnabled(false);
        myJButton10.setEnabled(false);
        myJButton20.setEnabled(false);
        myJButton50.setEnabled(false);
        myJButton1.setEnabled(false);
        myJButton2.setEnabled(false);
        myJButtonRetirar.setEnabled(false);
    }

    public void ativarUI() {
        myJButton5.setEnabled(true);
        myJButton10.setEnabled(true);
        myJButton20.setEnabled(true);
        myJButton50.setEnabled(true);
        myJButton1.setEnabled(true);
        myJButton2.setEnabled(true);
        myJButtonRetirar.setEnabled(true);
    }


}