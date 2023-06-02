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

import com.l2fprod.common.swing.JButtonBar;
import org.freixas.jcalendar.JCalendarCombo;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import com.l2fprod.common.swing.plaf.ButtonBarButtonUI;
//import com.l2fprod.common.swing.plaf.JButtonBar;
import com.l2fprod.common.swing.plaf.JButtonBarAddon;
import com.l2fprod.common.swing.plaf.basic.BasicButtonBarUI;



public class CenterPanel extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel northPanel, southPanel;
    private static JTextField paramTextField;
    private static JTextArea resultTextArea;
    private JLabel paramLabel, paramLabel1, paramLabel2, listaLabel;
    private JButton dodajButton, wyzerujButton, wypelnijButton, saveButton, listaButton, dateButton, chartButton;
    // deklaracja zmiennej typu JCalendarCombo o nazwie jccData
    private JCalendarCombo jccData;
    private static JTable table;   //static do testów
    private TitledBorder titledBorder;
    private Border blackLine;
    private static JSpinner spinnerRow, spinnerCol;

    private static IntegerTableModel integerModel;

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
        chartButton = new JButton("Wykres");
        chartButton.addActionListener(this);
        listaLabel = new JLabel("Wybierz operację");
        lista = new JList(obliczenia);
        lista.setBackground(Color.lightGray.brighter());
        listaButton = new JButton("Oblicz");
        listaButton.addActionListener(this);
        dateButton = new JButton("Wybierz datę");
        dateButton.addActionListener(this);

        JButtonBar buttonBar = new JButtonBar(JButtonBar.VERTICAL);
        buttonBar.add(dodajButton);
        buttonBar.add(wyzerujButton);
        buttonBar.add(wypelnijButton);
        buttonBar.add(listaButton);
        buttonBar.add(saveButton);
        buttonBar.add(chartButton);



        // utworzenie instacji obiektu JCalendar
        jccData = new JCalendarCombo(
                Calendar.getInstance(),
                Locale.getDefault(),
                JCalendarCombo.DISPLAY_DATE,
                false
        );
        // ustawienie formatu daty
        jccData.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

        //table = new JTable(data, nazwyKolumn);

        integerModel = new IntegerTableModel();

        table = new JTable(integerModel);

        jp.add(paramLabel);
        jp.add(paramTextField);
        jp.add(spinnerRow);
        jp.add(paramLabel1);
        jp.add(spinnerCol);
        jp.add(paramLabel2);
        jp.add(table);
        //jp.add(dodajButton);
       //jp.add(wyzerujButton);
//        jp.add(wypelnijButton);
//        jp.add(saveButton);
        jp.add(listaLabel);
        jp.add(lista);
        jp.add(buttonBar);
        jp.add(jccData);
        jp.add(dateButton);
//        jp.add(listaButton);
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
        } else if(ae.getSource() == dateButton) {
            String param = paramTextField.getText();
            // Pobranie daty do obiektu typu String
            // Miesiace liczone sa od 0 wiec trzeba dodac 1
            Calendar cal = jccData.getCalendar();
            String data = ""+cal.get(Calendar.YEAR)+"-";
            int miesiac = cal.get(Calendar.MONTH)+1;
            if(miesiac <= 9) data = data+"0"+String.valueOf(miesiac)+"-";
            else data = data+String.valueOf(miesiac)+"-";
            int dzien = cal.get(Calendar.DAY_OF_MONTH);
            if(dzien <= 9) data = data+"0"+String.valueOf(dzien);
            else data = data+String.valueOf(dzien);

            // zapisanie danych w polu TextArea
            resultTextArea.append("Parametr: "+param+"\n");
            resultTextArea.append("Data: "+data+"\n");
        } else if (ae.getSource() == chartButton) {
            drawChart();
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
            integerModel.setValue(inputNumber, ((Integer)spinnerRow.getValue())-1, ((Integer)spinnerCol.getValue())-1);
            resultTextArea.append("Wartość "+inputNumber+" została wpisana do komórki w: "+((Integer)spinnerRow.getValue()) + " k: " + ((Integer)spinnerCol.getValue()) +"\n");
        } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(null,"wprowadzona wartość nie jest liczbą","Błąd", JOptionPane.WARNING_MESSAGE);
        }
    }

    static void clearTable(){
        integerModel.setZeroTable();
        resultTextArea.append("Tabela została wyzerowana\n");
    }

    static void fillTable(){
        integerModel.setRandomTable();
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
        float bufor = integerModel.calculateSum();
        resultTextArea.append("Suma wartości: "+bufor+"\n");
    }

    static void drawChart(){
        double data [] = new double[25];
        int i = 0;
        for(int w=0; w<5; w++){
            for(int k=0; k<5; k++){
                data[i] =(float)table.getModel().getValueAt(w,k);
                i++;
            }
        }

        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries("Histogram", data, 10); // Drugi parametr to liczba koszyków (bins)

        // Tworzenie histogramu
        JFreeChart chart = ChartFactory.createHistogram(
                "Histogram", // Tytuł wykresu
                "Wartość", // Etykieta osi X
                "Liczba obserwacji", // Etykieta osi Y
                dataset, // Zestaw danych
                PlotOrientation.HORIZONTAL, // Orientacja wykresu
                true, // Czy wyświetlać legendę
                true, // Czy wygenerować narzędzia wykresu
                false // Czy wygenerować adres URL w przypadku kliknięcia w wykres
        );

        // Konfiguracja wyglądu histogramu
        chart.setBackgroundPaint(Color.WHITE);
        chart.getPlot().setBackgroundPaint(Color.WHITE);

        // Tworzenie okna z wykresem histogramu
        ChartFrame frame = new ChartFrame("Histogram", chart);

        // Ustawienie preferowanej wielkości okna
        frame.setPreferredSize(new Dimension(600, 400));

        // Wyśrodkowanie okna na ekranie
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (screenSize.width - frame.getWidth()) / 2;
        int centerY = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(centerX, centerY);

        frame.pack();
        frame.setVisible(true);
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
