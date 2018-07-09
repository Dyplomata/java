import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;


@SuppressWarnings("serial")
public class Kulki extends JFrame implements ActionListener {
//public class Kulki extends JFrame {
    static final int MAX_KULKI = 5;
    static final int PLANSZA_SZE = 9;
    static final int PLANSZA_WYS = 9;
    static final int KULKA_SIZE = 45;
    static final int PANEL_SZE = PLANSZA_SZE * KULKA_SIZE;
    static final int PANEL_WYS = PLANSZA_WYS * KULKA_SIZE;
    final int RAMKA_SZE = PANEL_SZE + 135;
    final int RAMKA_WYS = PANEL_WYS + 50;

    public static int punkty;

    final Font font1 = new Font("System",Font.BOLD,11);
    final Font font2 = new Font("System",Font.BOLD,22);

    static final Color K_TLO = new Color(0,128,255);

    static final Color[] K_PLANSZY =
            {
                    new Color(0,102,204),   //PUSTE
                    new Color(100,255,100), //DOSTEPNE
                    new Color(255,255,0),   //ZAZNACZONE
                    new Color(85,170,255)   //NOWA KULKA
            };

    static final Color[] K_KULKI =
            {
                    Color.RED,
                    Color.BLUE,
                    Color.GREEN,
                    Color.CYAN,
                    Color.ORANGE
            };



    JPanel panel;
    static JLabel lPunkty;
    JLabel lText, lLegend1, lLegend2, lLegend3;
    JButton bStart;
    static PoleGry poleGry;

    public Kulki() {
        super("Kulki");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        poleGry = new PoleGry();
        lText = new JLabel("PUNKTY",JLabel.CENTER);
        lPunkty = new JLabel("0",JLabel.CENTER);
        lLegend1 = new JLabel("Dostępne pola",JLabel.CENTER);
        lLegend2 = new JLabel("Zaznaczone pole",JLabel.CENTER);
        lLegend3 = new JLabel("Nowa Kulka",JLabel.CENTER);
        bStart = new JButton("Nowa Gra");
        panel.setLayout(null);
        panel.setBackground(K_TLO);

        poleGry.setBounds(10, 10, PANEL_SZE, PANEL_WYS);
        poleGry.setBackground(K_PLANSZY[0]);
        lText.setFont(font1);lText.setBounds(RAMKA_SZE-115,40,100,30);lText.setForeground(Color.WHITE);
        lPunkty.setFont(font2);lPunkty.setBounds(RAMKA_SZE-115,60,100,30);lPunkty.setForeground(Color.WHITE);
        lLegend1.setFont(font1);lLegend1.setBounds(RAMKA_SZE-115,PANEL_WYS-120,100,30);
        lLegend1.setForeground(K_PLANSZY[1]);
        lLegend2.setFont(font1);lLegend2.setBounds(RAMKA_SZE-115,PANEL_WYS-100,100,30);
        lLegend2.setForeground(K_PLANSZY[2]);
        lLegend3.setFont(font1);lLegend3.setBounds(RAMKA_SZE-115,PANEL_WYS-80,100,30);
        lLegend3.setForeground(K_PLANSZY[3]);

        bStart.setBounds(RAMKA_SZE-115, PANEL_WYS-20, 100, 30);


        panel.add(poleGry);
        panel.add(lText);
        panel.add(lPunkty);
        panel.add(lLegend1);
        panel.add(lLegend2);
        panel.add(lLegend3);
        panel.add(bStart);
        add(panel);bStart.addActionListener(this);
        setSize(RAMKA_SZE, RAMKA_WYS);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {new Kulki();}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (punkty>0) {
            Object[] opcje = {"Nowa Gra", "Powrót"};
            int opcja = JOptionPane.showOptionDialog(null, "Napewno chcesz przerwać istniejącą rozgrywkę?", "Rozpoczęcie o",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcje, opcje[0]);
            if (opcja == 0) {
                poleGry.nowaGra();
                poleGry.drukPlansza();
                lPunkty.setText("0");
            }
        } else {
            poleGry.nowaGra();
            poleGry.drukPlansza();
            lPunkty.setText("0");
        }
    }

}