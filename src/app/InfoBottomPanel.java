package app;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Color;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InfoBottomPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static JTextField infoTF,dbInfoTF,userInfoTF;
    private JLabel infoLabel,dbLabel,userLabel;
    /**
     * Konstruktor bezparametrowy klasy <CODE>InfoBottomPanel<CODE>
     */
    public InfoBottomPanel() {
        createGUI();
    }
    /**
     * Metoda tworzÄ…ca graficzny interfejs uĹĽytkownika
     */
    public void createGUI() {
        this.setBackground(new Color(210,210,210));
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // Utworzenie etykiet tekstowych
        infoLabel = new JLabel("Info:");
        dbLabel = new JLabel("Status:");
        userLabel= new JLabel("User:");

        // Utworzenie obiektow TextField
        infoTF = new JTextField("Start aplikacji");
        infoTF.setMinimumSize(new Dimension(200,20));
        infoTF.putClientProperty("JComponent.sizeVariant", "small");
        dbInfoTF = new JTextField("ON");
        dbInfoTF.putClientProperty("JComponent.sizeVariant", "small");
        dbInfoTF.setMinimumSize(new Dimension(30,20));
        userInfoTF = new JTextField("");
        userInfoTF.putClientProperty("JComponent.sizeVariant", "small");
        userInfoTF.setMinimumSize(new Dimension(100,20));

        this.add(infoLabel);
        this.add(Box.createHorizontalStrut(10));
        this.add(infoTF);
        this.add(Box.createHorizontalStrut(20));
        this.add(dbLabel);
        this.add(Box.createHorizontalStrut(10));
        this.add(dbInfoTF);
        this.add(Box.createHorizontalStrut(20));
        //this.add(userLabel);
        //this.add(Box.createHorizontalStrut(10));
        //this.add(userInfoTF);
    }
    /**
     * Publiczna metoda ustawiajaca zmienna infoString
     * @param infoString nowa wartosc zmiennej infoString
     */
    public static void setInfoString(String infoString) {
        infoTF.setText(infoString);
    }
    /**
     * Publiczna metoda ustawiajaca zmienna… dbStatus
     * @param dbStatus nowa wartosc zmiennej dbStatus
     */
    public static void setDbStatus(boolean dbStatus) {
        if(dbStatus) dbInfoTF.setText("ON");
        else dbInfoTF.setText("OFF");
    }
    /**
     * Publiczna metoda ustawiajaca zmienna userInfoString
     * @param userInfoString nowa wartosc zmiennej userInfoString
     */
    public static void setUserInfoString(String userInfoString) {
        userInfoTF.setText(userInfoString);
    }
    /**
     * Metoda okreslajaca wartosci odstepow od krawedzi panelu
     * (top,left,bottom,right)
     */
    public Insets getInsets() {
        return new Insets(5,5,3,5);
    }
}
