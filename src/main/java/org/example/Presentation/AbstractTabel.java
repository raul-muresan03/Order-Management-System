package org.example.Presentation;

import org.example.DataAccesLayer.DAO.AbstractDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

public class AbstractTabel<T> {
    private final Class<T> type;
    private JTable table = new JTable();
    private AbstractDAO<T> abstractDAO;

    public AbstractTabel(Class<T> type) {
        this.type = type;
        abstractDAO = new AbstractDAO<>(type);
    }

    public JTable createTable() throws SQLException {
        List<T> list = abstractDAO.findAll();
        String[] colNames = abstractDAO.columnNames();

        DefaultTableModel model = new DefaultTableModel(colNames, 0);
        for (T obj : list) {
            Object[] rows = getObjectFieldValues(obj);
            model.addRow(rows);
        }

        table = new JTable(model);

        return table;
    }

    private Object[] getObjectFieldValues(T obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        Object[] values = new Object[fields.length];

        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            try {
                values[i] = fields[i].get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return values;
    }
}
