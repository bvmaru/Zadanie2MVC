package app;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import org.freixas.jcalendar.JCalendarCombo;


public class CenterPanel extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel northPanel, southPanel;
    private static JTextField paramTextField;
    private static JTextArea resultTextArea;
    private JLabel paramLabel, paramLabel1, paramLabel2, listaLabel;
    private JButton dodajButton, wyzerujButton, wypelnijButton, saveButton, listaButton;
    // deklaracja zmiennej typu JCalendarCombo o nazwie jccData
    private JCalendarCombo jccData;
    private static JTable table;   //static do testów
    private TitledBorder titledBorder;
    private Border blackLine;
    private static JSpinner spinnerRow, spinnerCol;

    private Object[][] data = {
            {(float)0,(float)0,(float)0,(float)0,(float)0},
            {(float)0,(float)0,(float)0,(float)0,(float)0},
            {(float)0,(float)0,(float)0,(float)0,(float)0},
            {(float)0,(float)0,(float)0,(float)0,(float)0},
            {(float)0,(float)0,(float)0,(float)0,(float)0},
    };

    private SpinnerModel value1 =
            new SpinnerNumberModel(1, //initial value
                    1, //minimum value
                    5, //maximum value
                    1); //step
    private SpinnerModel value2 =
            new SpinnerNumberModel(1, //initial value
                    1, //minimum value
                    5, //maximum value
                    1); //step
    private String[] nazwyKolumn = {"1", "2", "3", "4", "5"};
    String[] obliczenia= { "Srednia","Suma","Wartość minimalna",
            "Wartość maksymalna"};

    static String saveInfo = "Start aplikacji";

    private JList lista;

    /**
     * Konstruktor bezparametrowy klasy <CODE>InfoBottomPanel<CODE>
     */
    public CenterPanel() {
        createGUI();
    }
    /**
     * Metoda tworzacaca graficzny interfejs uzyytkownika
     */
    public void createGUI() {
        this.setLayout(new GridLayout(2,1,5,5));

        // Utworzenie panelu z paramtrami i wynikiem
        northPanel = createNorthPanel();
        southPanel = createSouthPanel();

        // Utworzenie obiektow TextField
        this.add(northPanel);
        this.add(southPanel);
    }
    /**
     * Metoda tworzaca panel z parametrami
     */
    public JPanel createNorthPanel() {
        JPanel jp = new JPanel();
        blackLine = BorderFactory.createLineBorder(Color.gray);
        titledBorder = BorderFactory.createTitledBorder(blackLine,
                "Parametry wejsciowe");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        jp.setBorder(titledBorder);
        jp.setLayout(new FlowLayout());

        paramLabel = new JLabel("Wprowadź liczbę");
        paramTextField = new JTextField(10);
        spinnerRow = new JSpinner(value1);
        paramLabel1 = new JLabel("Numer Wiersza");
        spinnerCol= new JSpinner(value2);
        paramLabel2 = new JLabel("Numer Kolumny");
        dodajButton = new JButton("Dodaj");
        dodajButton.addActionListener(this);
        wyzerujButton = new JButton("Wyzeruj");
        wyzerujButton.addActionListener(this);
        wypelnijButton = new JButton("Wypelnij");
        wypelnijButton.addActionListener(this);
        saveButton = new JButton("Zapisz");
        saveButton.addActionListener(this);
        listaLabel = new JLabel("Wybierz operację");
        lista = new JList(obliczenia);
        lista.setBackground(Color.lightGray.brighter());
        listaButton = new JButton("Oblicz");
        listaButton.addActionListener(this);

        // utworzenie instacji obiektu JCalendar
        jccData = new JCalendarCombo(
                Calendar.getInstance(),
                Locale.getDefault(),
                JCalendarCombo.DISPLAY_DATE,
                false
        );
        // ustawienie formatu daty
        jccData.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

        table = new JTable(data, nazwyKolumn);

        jp.add(paramLabel);
        jp.add(paramTextField);
        jp.add(spinnerRow);
        jp.add(paramLabel1);
        jp.add(spinnerCol);
        jp.add(paramLabel2);
        jp.add(table);
        jp.add(dodajButton);
        jp.add(wyzerujButton);
        jp.add(wypelnijButton);
        jp.add(saveButton);
        jp.add(listaLabel);
        jp.add(lista);
        jp.add(listaButton);
        return jp;
    }
    /**
     * Metoda tworzaca panel z wynikami
     */
    public JPanel createSouthPanel() {
        JPanel jp = new JPanel();
        blackLine = BorderFactory.createLineBorder(Color.gray);
        titledBorder = BorderFactory.createTitledBorder(blackLine,
                "Uzyskany rezultat");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        jp.setBorder(titledBorder);
        jp.setLayout(new BorderLayout());

        resultTextArea = new JTextArea();
        // zawijanie wierszy
        resultTextArea.setLineWrap(true);
        // edycja pola TextArea
        // resulTextAreat.setEditable(false);
        resultTextArea.append("Start aplikacji\n");
        jp.add(new JScrollPane(resultTextArea),BorderLayout.CENTER);
        return jp;
    }
    /**
     * Metoda obsługujaca zdarzenie akcji
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == dodajButton) {
            changeData();
        } else if (ae.getSource() == wyzerujButton) {
            clearTable();
        } else if (ae.getSource() == wypelnijButton) {
            fillTable();
        } else if (ae.getSource() == saveButton) {
            saveToFile();
        } else if (ae.getSource() == listaButton) {
            if(lista.getSelectedValue() == obliczenia[0]){
                averageValue();
            } else if (lista.getSelectedValue() == obliczenia[1]) {
                summedValue();
            } else if (lista.getSelectedValue() == obliczenia[2]) {
                minValue();
            } else if (lista.getSelectedValue() == obliczenia[3]) {
                maxValue();
            }
        }
    }
    /**
     * Metoda okreslajaca wartosci odstepow od krawedzi panelu
     * (top,left,bottom,right) b.getSelectedValue()
     */
    public Insets getInsets() {
        return new Insets(5,10,10,10);
    }

    static void changeData(){
        String inputText = paramTextField.getText();
        try{
            float inputNumber = Float.parseFloat(inputText);
            table.getModel().setValueAt(inputNumber, ((Integer)spinnerRow.getValue())-1, ((Integer)spinnerCol.getValue())-1);
            resultTextArea.append("Wartość "+inputNumber+" została wpisana do komórki w: "+((Integer)spinnerRow.getValue()) + " k: " + ((Integer)spinnerCol.getValue()) +"\n");
        } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(null,"wprowadzona wartość nie jest liczbą","Błąd", JOptionPane.WARNING_MESSAGE);
        }
    }

    static void clearTable(){
        for(int w=0; w<5; w++){
            for(int k=0; k<5; k++){
                table.getModel().setValueAt((float)0, w, k);
            }
        }
        resultTextArea.append("Tabela została wyzerowana\n");
    }

    static void fillTable(){
        Random rand = new Random();
        for(int w=0; w<5; w++){
            for(int k=0; k<5; k++){
                table.getModel().setValueAt(((float)rand.nextInt(100)), w, k);
            }
        }
        resultTextArea.append("Tabela została wypełniona losowymi wartościami\n");
    }

    static void minValue(){
        float bufor = ((float)table.getModel().getValueAt(0,0));
        for(int w=0; w<5; w++){
            for(int k=0; k<5; k++){
                if(bufor>((float)table.getModel().getValueAt(w,k))){
                    bufor=((float)table.getModel().getValueAt(w,k));
                }
            }
        }
        resultTextArea.append("Najmniejsza wartość w tabeli to: "+bufor+"\n");
    }

    static void maxValue(){
        float bufor = ((float)table.getModel().getValueAt(0,0));
        for(int w=0; w<5; w++){
            for(int k=0; k<5; k++){
                if(bufor<((float)table.getModel().getValueAt(w,k))){
                    bufor=((float)table.getModel().getValueAt(w,k));
                }
            }
        }
        resultTextArea.append("Największa wartość w tabeli to: "+bufor+"\n");
    }

    static void averageValue(){
        float bufor = 0;
        float div = 25;

        for(int w=0; w<5; w++){
            for(int k=0; k<5; k++){
                bufor += ((float)table.getModel().getValueAt(w,k));
            }
        }
        bufor = bufor/div;
        resultTextArea.append("Średnia wartość: "+bufor+"\n");
    }

    static void summedValue(){
        float bufor = 0;

        for(int w=0; w<5; w++){
            for(int k=0; k<5; k++){
                bufor += ((float)table.getModel().getValueAt(w,k));
            }
        }
        resultTextArea.append("Suma wartości: "+bufor+"\n");
    }

    static void saveToFile(){

        String toSave = "";
        for(int w=0; w<5; w++){
            for(int k=0; k<5; k++){
                toSave +=(table.getModel().getValueAt(w,k))+"  ";
            }
            toSave+="\n\n";
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));

        int response = fileChooser.showSaveDialog(null);

        if(response == JFileChooser.APPROVE_OPTION){
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            if(!fileChooser.getSelectedFile().getAbsolutePath().toLowerCase().endsWith(".txt"))
            {
                file = new File(fileChooser.getSelectedFile().getAbsolutePath() + ".txt");
            }
            try{
                FileWriter writer = new FileWriter(file);
                writer.write(toSave);
                writer.close();
                InfoBottomPanel.setInfoString("Zapisano dane do pliku");
            }
            catch (IOException e){
                e.printStackTrace();
                InfoBottomPanel.setInfoString("Zapis danych nie powiódł się");
            }
        }

    }

}
