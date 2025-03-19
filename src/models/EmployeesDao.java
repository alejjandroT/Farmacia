package models;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class EmployeesDao {

    public static String rol_user = "";
    public static String id_user = "";
    public static String full_name_user = "";
    public static String address_user = "";
    public static String telephone_user = "";
    public static String email_user = "";

    // Método para obtener la conexión
    private Connection getConnection() throws SQLException {
        ConnectionMySQL cn = new ConnectionMySQL();
        return cn.getConnection();
    }

    // Método para cerrar recursos
    private void closeResources(Connection conn, PreparedStatement pst, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método de login
    public Employees loginQuery(String user, String password) {
        String query = "SELECT * FROM employees WHERE username = ? AND password = ?";
        Employees employee = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, user);
            pst.setString(2, password);
            rs = pst.executeQuery();

            if (rs.next()) {
                employee = new Employees();
                employee.setId(rs.getInt("id"));
                employee.setFull_name(rs.getString("full_name"));
                employee.setUsername(rs.getString("username"));
                employee.setAddress(rs.getString("address"));
                employee.setTelephone(rs.getString("telephone"));
                employee.setEmail(rs.getString("email"));
                employee.setRol(rs.getString("rol"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, pst, rs);
        }
        return employee;
    }

    // Método para registrar empleado
    public boolean registerEmployeeQuery(Employees employee) {
        String query = "INSERT INTO employees(id, full_name, username, address, telephone, email, password, rol, created, updated) VALUES(?,?,?,?,?,?,?,?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, employee.getId());
            pst.setString(2, employee.getFull_name());
            pst.setString(3, employee.getUsername());
            pst.setString(4, employee.getAddress());
            pst.setString(5, employee.getTelephone());
            pst.setString(6, employee.getEmail());
            pst.setString(7, employee.getPassword()); // Considera usar hashing aquí
            pst.setString(8, employee.getRol());
            pst.setTimestamp(9, datetime);
            pst.setTimestamp(10, datetime);
            pst.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, pst, null);
        }
    }

    // Método para listar empleados
    public List<Employees> listEmployeesQuery(String value) {
        List<Employees> list_employees = new ArrayList<>();
        String query = "SELECT * FROM employees ORDER BY rol ASC";
        String query_search_employee = "SELECT * FROM employees WHERE id LIKE ?";
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            if (value.isEmpty()) {
                pst = conn.prepareStatement(query);
            } else {
                pst = conn.prepareStatement(query_search_employee);
                pst.setString(1, "%" + value + "%");
            }
            rs = pst.executeQuery();

            while (rs.next()) {
                Employees employee = new Employees();
                employee.setId(rs.getInt("id"));
                employee.setFull_name(rs.getString("full_name"));
                employee.setUsername(rs.getString("username"));
                employee.setAddress(rs.getString("address"));
                employee.setTelephone(rs.getString("telephone"));
                employee.setEmail(rs.getString("email"));
                employee.setRol(rs.getString("rol"));
                list_employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, pst, rs);
        }
        return list_employees;
    }
    
   // Método para actualizar un empleado
   public boolean updateEmployeeQuery(Employees employee) {
    boolean updated = false;
    Connection conn = null;
    PreparedStatement pst = null;
    String query = "UPDATE employees SET full_name = ?, username = ?, address = ?, telephone = ?, email = ?, rol = ?" + (employee.getPassword() != null && !employee.getPassword().isEmpty() ? ", password = ?" : "") + " WHERE id = ?";
    
    try {
        conn = getConnection();
        pst = conn.prepareStatement(query);
        pst.setString(1, employee.getFull_name());
        pst.setString(2, employee.getUsername());
        pst.setString(3, employee.getAddress());
        pst.setString(4, employee.getTelephone());
        pst.setString(5, employee.getEmail());
        pst.setString(6, employee.getRol());
        
        // Actualizar la contraseña solo si se ha proporcionado una nueva
        if (employee.getPassword() != null && !employee.getPassword().isEmpty()) {
            pst.setString(7, employee.getPassword());
            pst.setInt(8, employee.getId());
        } else {
            pst.setInt(7, employee.getId());
        }
        
        int rowsAffected = pst.executeUpdate();
        if(rowsAffected > 0) {
            updated = true;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        closeResources(conn, pst, null);
    }
    return updated;
}
   
   // Método para eliminar un empleado
public boolean deleteEmployeeQuery(int employeeId) {
    boolean deleted = false;
    Connection conn = null;
    PreparedStatement pst = null;
    String query = "DELETE FROM employees WHERE id = ?";
    
    try {
        conn = getConnection();
        pst = conn.prepareStatement(query);
        pst.setInt(1, employeeId);
        
        int rowsAffected = pst.executeUpdate();
        if(rowsAffected > 0) {
            deleted = true;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        closeResources(conn, pst, null);
    }
    return deleted;
}

public boolean updateEmployeePassword(Employees employee) {
    Connection conn = null;
    PreparedStatement pst = null;
    boolean passwordUpdated = false;
    String query = "UPDATE empleados SET contraseña = ? WHERE id = ?";

    try {
        conn = getConnection(); // Asume que tienes un método para obtener la conexión
        pst = conn.prepareStatement(query);
        pst.setString(1, employee.getPassword());
        pst.setInt(2, employee.getId());

        int rowsAffected = pst.executeUpdate();
        if(rowsAffected > 0) {
            passwordUpdated = true;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        closeResources(conn, pst, null); 
    }
    return passwordUpdated;
}
    
}
