/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biodraft;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Administrator
 */
public class MyTableModel extends AbstractTableModel {

    private String[] columnNames;
    private Object[][] data;

    public MyTableModel(String[] names, Object[][] data) {
        this.columnNames = names;
        this.data = data;
    }

    public MyTableModel(String[] names) {
        this.columnNames = names;
        this.data = new Object[5][7];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                data[i][j] = "";
            }
        }
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
}
