package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Suppliers;
import models.SuppliersDao;
import Vista.SystemView;

public class SuppliersController implements ActionListener {

    private Suppliers supplier;
    private SuppliersDao supplierDao;
    private SystemView views;
    private DefaultTableModel model;

    public SuppliersController(SystemView views) {
        this.supplierDao = new SuppliersDao();
        this.views = views;
        this.views.btn_register_supplier.addActionListener(this);
        this.views.btn_update_supplier.addActionListener(this);
        initModel();
        fillSuppliersTable();
    }

    private void initModel() {
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"ID", "Nombre", "Descripción", "Dirección", "Teléfono", "Email", "Ciudad"});
        views.suppliers_table.setModel(model);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_supplier) {
            registerSupplier();
        } else if (e.getSource() == views.btn_update_supplier) {
            updateSupplier();
        }
    }

    private void registerSupplier() {
        supplier = new Suppliers();
        supplier.setName(views.txt_supplier_name.getText().trim());
        supplier.setDescription(views.txt_supplier_description.getText().trim());
        supplier.setAddress(views.txt_supplier_addres.getText().trim());
        supplier.setTelephone(views.txt_supplier_telephone.getText().trim());
        supplier.setEmail(views.txt_supplier_email.getText().trim());
        supplier.setCity(views.txt_supplier_addres.getText().trim());

        if (supplierDao.registerSupplierQuery(supplier)) {
            JOptionPane.showMessageDialog(null, "Proveedor registrado con éxito.");
            fillSuppliersTable();
        } else {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar el proveedor.");
        }
    }

    private void fillSuppliersTable() {
        model.setRowCount(0); // Limpiar la tabla antes de agregar nuevos datos
        for (Suppliers s : supplierDao.listSuppliersQuery("")) {
            model.addRow(new Object[]{
                s.getId(),
                s.getName(),
                s.getDescription(),
                s.getAddress(),
                s.getTelephone(),
                s.getEmail(),
                s.getCity()
            });
        }
    }

    private void updateSupplier() {
        supplier = new Suppliers();
        supplier.setId(Integer.parseInt(views.txt_supplier_id.getText().trim()));
        supplier.setName(views.txt_supplier_name.getText().trim());
        supplier.setDescription(views.txt_supplier_description.getText().trim());
        supplier.setAddress(views.txt_supplier_addres.getText().trim());
        supplier.setTelephone(views.txt_supplier_telephone.getText().trim());
        supplier.setEmail(views.txt_supplier_email.getText().trim());
        supplier.setCity(views.txt_supplier_addres.getText().trim());

        if (supplierDao.updatedSupplierQuery(supplier)) {
            JOptionPane.showMessageDialog(null, "Proveedor actualizado con éxito.");
            fillSuppliersTable();
        } else {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al actualizar el proveedor.");
        }
    }
}
