package org.example.DataAccesLayer.DAO;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AbstractDAO<T> {
    protected static final Logger logger = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;
    public AbstractDAO(Class<T> type) {
        this.type = type;
    }
    private String createSelectQuery(String field) {
        StringBuilder s = new StringBuilder();
        s.append("SELECT");
        s.append(" *");
        s.append(" FROM ");
        s.append(type.getSimpleName());
        s.append(" WHERE ");
        s.append(field);
        s.append(" = ?");
        return s.toString();
    }

    public T findById(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            return createObjects(resultSet).get(0);

        } catch (SQLException e) {
            logger.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            assert resultSet != null;
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }

        try {
            while (resultSet.next()) {
                assert ctor != null;
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();

                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException |
                 InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<T> findAll() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<T> list = new ArrayList<>();
        String query = "SELECT * FROM " + type.getSimpleName();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert resultSet != null;
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    private String createInsertQuery(String[] fields) {
        StringBuilder s = new StringBuilder();
        s.append("INSERT INTO ");
        s.append(type.getSimpleName());
        s.append(" (");

        for (int i = 0; i < fields.length; i++) {
            s.append(fields[i]);
            if (i < fields.length - 1) {
                s.append(", ");
            }
        }

        s.append(") VALUES (");

        for (int i = 0; i < fields.length; i++) {
            s.append("?");
            if (i < fields.length - 1) {
                s.append(", ");
            }
        }

        s.append(")");

        return s.toString();
    }

    private String createUpdateQuery(String[] fields) {
        StringBuilder s = new StringBuilder();
        s.append("UPDATE ");
        s.append(type.getSimpleName());
        s.append(" SET ");

        for (int i = 0; i < fields.length; i++) {
            s.append(fields[i]);
            s.append(" = ?");
            if (i < fields.length - 1) {
                s.append(", ");
            }
        }

        s.append(" WHERE id = ? ");
        return s.toString();
    }

    private String createDeleteQuery(int id) {
        StringBuilder s = new StringBuilder();
        s.append("DELETE FROM ");
        s.append(type.getSimpleName());
        s.append(" WHERE id = ?");
        return s.toString();
    }

    public void insert(T t) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        Field[] fields = type.getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }

        String query = createInsertQuery(fieldNames);

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                statement.setObject(i + 1, fields[i].get(t));
            }

            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            assert statement != null;
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    public void update(int id, T t) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        Field[] fields = type.getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }

        String query = createUpdateQuery(fieldNames);

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                statement.setObject(i + 1, fields[i].get(t));
            }
            statement.setInt(fields.length + 1, id);

            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            assert connection != null;
            assert statement != null;
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    public void delete(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery(id);

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();        //update nu returneaza nimic, doar executeQuery returneaza un resultSet
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert connection != null;
            assert statement != null;
            ConnectionFactory.close(connection);
            ConnectionFactory.close(statement);
        }
    }

    public String[] columnNames() {
        ArrayList<String> columns = new ArrayList<>();

        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            columns.add(field.getName());
        }

        return columns.toArray(new String[0]);
    }
}
