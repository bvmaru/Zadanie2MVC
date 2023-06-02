package app;

import java.util.Random;

import javax.swing.table.AbstractTableModel;

public class IntegerTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    private static int countRowTable = 5;
    private static int countColumnTable = 5;
    private static Float[][] data = new Float[5][5];
    private static String[] colName = {"1","2","3","4","5"};

    public IntegerTableModel() {
        super();
        setZeroTable();
    }
    public int getColumnCount() {
        return countColumnTable;
    }
    public int getRowCount() {
        return countColumnTable;
    }
    public Object getValueAt(int row, int col) {
        Object object = (Object) data[row][col];
        return object;
    }
    public Float[][] getIntegerValuesTable() {
        return data;
    }
    public String getStringValuesTable() {
        String str = "";
        for(int i=0; i<5; i++)
            for(int j=0; j<5; j++) {
                str = str + data[i][j] +" ";
            }
        return str;
    }
    public String getColumnName(int col) {
        return colName[col];
    }
    public String[] getColumnNames() {
        return colName;
    }
    public void setValue(Float value, int row, int col) {
        data[row][col] = value;
        fireTableDataChanged();
    }
    public void setRandomTable() {
        Random random = new Random();
        for(int i=0; i<5; i++)
            for(int j=0; j<5; j++) {
                // ograniczenie znaku liczby i zakresu do 10000
                data[i][j] = Math.abs(random.nextFloat()) % 10000;
            }
        fireTableDataChanged();
    }
    public void setZeroTable() {
        for(int i=0; i<5; i++)
            for(int j=0; j<5; j++) {
                data[i][j] = new Float(0);
            }
        fireTableDataChanged();
    }
    public static float calculateSum() {
        float sum = 0;
        for(int i=0; i<5; i++)
            for(int j=0; j<5; j++) {
                sum = sum + data[i][j];
            }
        return sum;
    }

}
