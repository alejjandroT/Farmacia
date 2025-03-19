package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class CategoriesDao {
    //instanciar la conexion
    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    //registrar categoria
     public boolean registerCategoryQuery(Categories category) {
        String query = "INCERT INTO categories(name, created, updated) VALUES (?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, category.getName());
            pst.setTimestamp(2, datetime);
            pst.setTimestamp(3, datetime);
            pst.execute();
            return true; 

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al registrar la categoria");
            return false;

        }

    }
    
     //listar categorias
     public List listCategoriesQuery(String value) {
        List<Categories> list_categories = new ArrayList();
        String query = "SELECT * FROM categories";
        String query_search_category = "SELECT * FROM categories WHERE name LIKE ´% " + value + "%´";

        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_search_category);
                rs = pst.executeQuery();
            }
            while (rs.next()) {
                Categories category = new Categories();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                list_categories.add(category);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());

        }
        return list_categories;

    }
     
     //modificar categoria
     public boolean updateCategoryQuery(Categories category) {
        String query = "UPDATE categories SET name = ?, updated = ?, VALUES";
        Timestamp datetime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, category.getName());
            pst.setTimestamp(2, datetime);
            pst.setInt(3, category.getId()); 
            pst.execute();
            return true; 

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al modficar los datos de la categoria");
            return false;

        }

    }
     
     //eliminar categoria
     public boolean deleteCategoryQuery(int id) {
        String query = "DELTE FROM categories WHERE id = " + id;
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();
            return (true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "no puede elminar una categoria que tenga relacion con otra tabla");
            return false;

        }
    }

}
