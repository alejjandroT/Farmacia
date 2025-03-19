package models;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class CustomersDao {

    // Instanciar la conexión
    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    // Registrar cliente
    public boolean registerCustomerQuery(Customers customer) {
        String query = "INSERT INTO customers(id, full_name, address, telephone, email, created, updated) "
                + "VALUES (?,?,?,?,?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, customer.getId());
            pst.setString(2, customer.getFull_name());
            pst.setString(3, customer.getAddress());
            pst.setString(4, customer.getTelephone());
            pst.setString(5, customer.getEmail());
            pst.setTimestamp(6, datetime);
            pst.setTimestamp(7, datetime);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al registrar cliente: " + e);
            return false;
        }
    }

    // Método para listar todos los clientes
    public List<Customers> listAllCustomers() {
        return listCustomerQuery("");
    }

    // Método para buscar clientes
    public List<Customers> searchCustomers(String searchValue) {
        return listCustomerQuery(searchValue);
    }

    // Método para listar clientes con opción de búsqueda
    public List<Customers> listCustomerQuery(String value) {
        List<Customers> list_customers = new ArrayList<>();
        String query = "SELECT * FROM customers";
        String query_search_customer = "SELECT * FROM customers WHERE id LIKE '%" + value + "%' OR full_name LIKE '%" + value + "%'";

        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
            } else {
                pst = conn.prepareStatement(query_search_customer);
            }
            rs = pst.executeQuery();

            while (rs.next()) {
                Customers customer = new Customers();
                customer.setId(rs.getInt("id"));
                customer.setFull_name(rs.getString("full_name"));
                customer.setAddress(rs.getString("address"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setEmail(rs.getString("email"));
                list_customers.add(customer);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_customers;
    }

    // Modificar cliente
    public boolean updateCustomerQuery(Customers customer) {
        String query = "UPDATE customers SET full_name = ?, address = ?, telephone = ?, email = ?, updated = ? "
                + "WHERE id = ?";
        Timestamp datetime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, customer.getFull_name());
            pst.setString(2, customer.getAddress());
            pst.setString(3, customer.getTelephone());
            pst.setString(4, customer.getEmail());
            pst.setTimestamp(5, datetime);
            pst.setInt(6, customer.getId());
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al modificar los datos del cliente: " + e);
            return false;
        }
    }

    // Eliminar cliente
    public boolean deleteCustomerQuery(int id) {
        String query = "DELETE FROM customers WHERE id = ?";
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se puede eliminar cliente que tenga relación con otra tabla: " + e);
            return false;
        }
    }
}