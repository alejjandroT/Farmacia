package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Customers;
import models.CustomersDao;
import Vista.SystemView;

public class CustomersController implements ActionListener {

    private Customers customer;
    private CustomersDao customerDao;
    private SystemView views;
    private DefaultTableModel model;

    public CustomersController(Customers customer, CustomersDao customerDao, SystemView views) {
        this.customer = customer;
        this.customerDao = customerDao;
        this.views = views;
        this.views.btn_register_customer.addActionListener(this);
        initModel();
        fillCustomersTable();
    }

    private void initModel() {
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"ID", "Nombre Completo", "Dirección", "Teléfono", "Email"});
        views.customers_table.setModel(model);
    }

     @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_customer) {
            registerCustomer();
        } else if (e.getSource() == views.btn_update_customer) {
            updateCustomer();
        }
        // Aquí puedes agregar más condiciones para otros botones si es necesario
    }

    private void registerCustomer() {
        if (views.txt_customer_id.getText().equals("")
                || views.txt_customer_fullname.getText().equals("")
                || views.txt_customer_addres.getText().equals("")
                || views.txt_customer_telephone.getText().equals("")
                || views.txt_customer_email.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
        } else {
            customer.setId(Integer.parseInt(views.txt_customer_id.getText().trim()));
            customer.setFull_name(views.txt_customer_fullname.getText().trim());
            customer.setAddress(views.txt_customer_addres.getText().trim());
            customer.setTelephone(views.txt_customer_telephone.getText().trim());
            customer.setEmail(views.txt_customer_email.getText().trim());

            if (customerDao.registerCustomerQuery(customer)) {
                JOptionPane.showMessageDialog(null, "Cliente registrado con éxito.");
                fillCustomersTable();
            } else {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar el cliente.");
            }
        }
    }

    private void fillCustomersTable() {
        model.setRowCount(0); // Limpiar la tabla antes de agregar nuevos datos
        for (Customers customer : customerDao.listAllCustomers()) {
            model.addRow(new Object[]{
                customer.getId(),
                customer.getFull_name(),
                customer.getAddress(),
                customer.getTelephone(),
                customer.getEmail()
            });
        }
    }

    // Método para buscar y actualizar la JTable con los resultados
    private void searchCustomers(String searchValue) {
        model.setRowCount(0); // Limpiar la tabla antes de agregar nuevos datos
        for (Customers customer : customerDao.searchCustomers(searchValue)) {
            model.addRow(new Object[]{
                customer.getId(),
                customer.getFull_name(),
                customer.getAddress(),
                customer.getTelephone(),
                customer.getEmail()
            });
        }
    }
    
     private void updateCustomer() {
        if (views.txt_customer_id.getText().equals("")
                || views.txt_customer_fullname.getText().equals("")
                || views.txt_customer_addres.getText().equals("")
                || views.txt_customer_telephone.getText().equals("")
                || views.txt_customer_email.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
        } else {
            customer.setId(Integer.parseInt(views.txt_customer_id.getText().trim()));
            customer.setFull_name(views.txt_customer_fullname.getText().trim());
            customer.setAddress(views.txt_customer_addres.getText().trim());
            customer.setTelephone(views.txt_customer_telephone.getText().trim());
            customer.setEmail(views.txt_customer_email.getText().trim());

            if (customerDao.updateCustomerQuery(customer)) {
                JOptionPane.showMessageDialog(null, "Cliente actualizado con éxito.");
                fillCustomersTable();
            } else {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error al actualizar el cliente.");
            }
        }
    }

    // Otros métodos y lógica del controlador...
}