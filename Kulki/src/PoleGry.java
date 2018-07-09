import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JOptionPane;

@SuppressWarnings("serial")

public class PoleGry extends Canvas implements MouseListener {

    public BufferedImage image = new BufferedImage(Kulki.PANEL_SZE, Kulki.PANEL_WYS, BufferedImage.TYPE_INT_RGB);
    public Graphics2D grafika = (Graphics2D) image.getGraphics();

    public int[][] tabPlansza, tabKulki;

    public byte selectedX, selectedY;
    public int tempX, tempY, x1, y1, x2, y2, x3, y3;
    public boolean selected;
    public Random los = new Random();

    public PoleGry()
    {
        super();
        addMouseListener(this);
        tabPlansza = new int[Kulki.PLANSZA_SZE][Kulki.PLANSZA_WYS];
        tabKulki = new int[Kulki.PLANSZA_SZE][Kulki.PLANSZA_WYS];
        nowaGra();
    }

    public void nowaGra() {
        for (int x = 0; x < Kulki.PLANSZA_SZE; x++)
            for (int y = 0; y < Kulki.PLANSZA_WYS; y++) {
                tabKulki[x][y] = 0;
                tabPlansza[x][y] = 0;
            }
        nowaKulka();
        nowaKulka();
        nowaKulka();
        nowaKulka();
        Kulki.punkty = 0;
        selected = false;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drukPlansza();
    }

    public void drukPlansza() {
        plansza();
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
    }

    public void plansza()
    {
        int index = 0;
        for (int x = 0; x < Kulki.PLANSZA_SZE; x++)
            for (int y = 0; y < Kulki.PLANSZA_WYS; y++)
            {
                if (tabKulki[x][y] == 0) index++;
                grafika.setColor(Kulki.K_PLANSZY[tabPlansza[x][y]]);
                grafika.fillRect(x * Kulki.KULKA_SIZE, y * Kulki.KULKA_SIZE, Kulki.KULKA_SIZE, Kulki.KULKA_SIZE);
                grafika.setColor(Kulki.K_TLO);
                grafika.drawRect(x * Kulki.KULKA_SIZE, y * Kulki.KULKA_SIZE, Kulki.KULKA_SIZE, Kulki.KULKA_SIZE);
                if (tabKulki[x][y] > 0) kulka(x, y, tabKulki[x][y]);
            }
        if (index < 3) gameOver();
    }

    public void gameOver() {
        JOptionPane.showMessageDialog(null, "Twój wynik:" + String.valueOf(Kulki.punkty), "GAME OVER", JOptionPane.INFORMATION_MESSAGE);
        nowaGra();
        Kulki.lPunkty.setText("0");
    }

    public void kulka(int x, int y, int k) {
        grafika.setColor(Color.BLACK);
        grafika.fillOval((x * Kulki.KULKA_SIZE) + 2, (y * Kulki.KULKA_SIZE) + 2, Kulki.KULKA_SIZE - 4, Kulki.KULKA_SIZE - 4);

        grafika.setColor(Kulki.K_KULKI[k - 1]);
        grafika.fillOval((x * Kulki.KULKA_SIZE) + 4, (y * Kulki.KULKA_SIZE) + 3, Kulki.KULKA_SIZE - 7, Kulki.KULKA_SIZE - 6);

        grafika.setColor(Color.WHITE);
        grafika.fillOval((x * Kulki.KULKA_SIZE) + 8, (y * Kulki.KULKA_SIZE) + 8, 7, 7);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @SuppressWarnings("static-access")
    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX() / Kulki.KULKA_SIZE;
        int y = e.getY() / Kulki.KULKA_SIZE;

        if (e.getButton() == e.BUTTON1) {
            if (tabKulki[x][y] > 0 && !selected)
            {
                selected = true;
                selectedX = (byte)x;
                selectedY = (byte)y;
                tabPlansza[x][y] = 2;
                drukPlansza();
            }
            else if (tabPlansza[x][y] == 1)
            {
                tabKulki[x][y] = tabKulki[selectedX][selectedY];
                tabKulki[selectedX][selectedY] = 0;
                clearSelected();
                if (!sprawdzam())
                {
                    nowaKulka();x1 = tempX;y1 = tempY;
                    nowaKulka();x2 = tempX;y2 = tempY;
                    nowaKulka();x3 = tempX;y3 = tempY;
                    zaznaczNowe();
                } else Kulki.lPunkty.setText(String.valueOf(Kulki.punkty));
                drukPlansza();
                clearSelected();
            }
            else
                {
                clearSelected();
                drukPlansza();
            }
        }
    }

    public void zaznaczNowe() {
        tabPlansza[x1][y1] = 3;
        tabPlansza[x2][y2] = 3;
        tabPlansza[x3][y3] = 3;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (selected)
        {
            if (!dostepnePola()) clearSelected();
            drukPlansza();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    public void clearSelected() {
        selected = false;
        for (int x = 0; x < Kulki.PLANSZA_SZE; x++)
            for (int y = 0; y < Kulki.PLANSZA_WYS; y++)
                tabPlansza[x][y] = 0;
    }

    public boolean zaznaczPole(int x, int y)
    {
        if (tabKulki[x][y] == 0 && tabPlansza[x][y] == 0) return true;
        return false;
    }

    public boolean dostepnePola() {
        boolean pola = false;
        int index;

        do {
            index = 0;
            for (int x = 0; x < Kulki.PLANSZA_SZE; x++)
                for (int y = 0; y < Kulki.PLANSZA_WYS; y++)
                    if (tabPlansza[x][y] > 0)
                    {
                        if (x - 1 >= 0 && zaznaczPole(x - 1, y)) {
                            index++;
                            tabPlansza[x - 1][y] = 1;
                            pola = true;
                        }
                        if (x + 1 < Kulki.PLANSZA_SZE && zaznaczPole(x + 1, y)) {
                            index++;
                            tabPlansza[x + 1][y] = 1;
                            pola = true;
                        }
                        if (y - 1 >= 0 && zaznaczPole(x, y - 1)) {
                            index++;
                            tabPlansza[x][y - 1] = 1;
                            pola = true;
                        }
                        if (y + 1 < Kulki.PLANSZA_WYS && zaznaczPole(x, y + 1)) {
                            index++;
                            tabPlansza[x][y + 1] = 1;
                            pola = true;
                        }
                    }
        } while (index > 0);
        return pola;
    }

    public void nowaKulka()
    {
        int x, y;
        do {
            x = los.nextInt(Kulki.PLANSZA_SZE);
            y = los.nextInt(Kulki.PLANSZA_WYS);

        } while (tabKulki[x][y] > 0);
        tabKulki[x][y] = los.nextInt(Kulki.MAX_KULKI) + 1;
        tempX = x;tempY = y;
    }

    public boolean sprawdzam()
    {
        boolean stan = false;
        for (int x=0;x<Kulki.PLANSZA_SZE;x++)
            for (int y=0;y<Kulki.PLANSZA_WYS;y++)
            {
               if (tabKulki[x][y]>0) if (linia1(x,y)) stan = true;
               if (tabKulki[x][y]>0) if (linia2(x,y)) stan = true;
                }
                return stan;
        }

        public boolean linia1(int x, int y)
        {
            if (tabKulki[x][y]==0) return false;
            int index = 0;
            int kolor = tabKulki[x][y];
            for (int i=x;i<Kulki.PLANSZA_SZE;i++)
            {
                if (tabKulki[i][y]==kolor) index++; else
                {
                    if (index<5) return false; else {
                        for (int j=i-1;j>(x-1);j--) tabKulki[j][y]=0;
                        dodajPunkty(index);
                        return true;
                    }
                }
                if (i==Kulki.PLANSZA_SZE-1) {
                    if (index<5) return false; else{
                        for (int j=i;j>(x-1);j--) tabKulki[j][y]=0;
                        dodajPunkty(index);
                        return true;
                    }
                }
            }
            return false;
        }

    public boolean linia2(int x, int y)
    {
        if (tabKulki[x][y]==0) return false;
        int index = 0;
        int kolor = tabKulki[x][y];
        for (int i=y;i<Kulki.PLANSZA_WYS;i++)
        {
            if (tabKulki[x][i]==kolor) index++; else
            {
                if (index<5) return false; else {
                    for (int j=i-1;j>(y-1);j--) tabKulki[x][j]=0;
                    dodajPunkty(index);
                    return true;
                }
            }
                if (i == Kulki.PLANSZA_WYS - 1) {
                    if (index < 5) return false; else {
                        for (int j = i; j > (y - 1); j--) tabKulki[x][j] = 0;
                        dodajPunkty(index);
                        return true;
                    }
                }

            }
            return false;
        }

        public void dodajPunkty ( int p)
        {
            switch (p)
            {
                case 5:
                    Kulki.punkty += 5;
                    break;
                case 6:
                    Kulki.punkty += 8;
                    break;
                case 7:
                    Kulki.punkty += 15;
                    break;
                case 8:
                    Kulki.punkty += 30;
                    break;
                case 9:
                    Kulki.punkty += 50;
                    break;
            }
        }

}
