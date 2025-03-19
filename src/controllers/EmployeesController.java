package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import models.Employees;
import models.EmployeesDao;
import static models.EmployeesDao.rol_user;
import Vista.SystemView;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class EmployeesController implements ActionListener, MouseListener, KeyListener {
    private Employees employee;
    private EmployeesDao employeeDao;
    private SystemView views;
    // ROL
    String rol = rol_user;
    DefaultTableModel model;
    
    public EmployeesController(Employees employee, EmployeesDao employeeDao, SystemView views) {
        this.employee = employee;
        this.employeeDao = employeeDao;
        this.views = views;
        this.model = (DefaultTableModel) views.employees_table.getModel();
        // Botón de registrar empleado
        this.views.btn_register_employee.addActionListener(this);
        // boton de modificar empleado
        this.views.btn_update_employee.addActionListener(this);
        //boton de eliminar empleado
        this.views.btn_delete_employee.addActionListener(this);
        //boton de cambiar contraseña
        this.views.btn_modify_data.addActionListener(this);
        //colocar label en escucha
        this.views.jLabelEmployees.addMouseListener(this);
        this.views.employees_table.addMouseListener(this);
        this.views.txt_search_employee.addKeyListener(this);
    }

    @Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == views.btn_register_employee) {
        registerEmployee();
    } else if (e.getSource() == views.btn_update_employee) {
        updateEmployee();
    } else if (e.getSource() == views.btn_delete_employee) {
        deleteEmployee();
    } else if (e.getSource() == views.btn_modify_data){
        //recolectar informacion de las cajas password
        String password = String.valueOf(views.txt_password_modify.getPassword());
        String confirm_password = String.valueOf(views.txt_password_modify_confirm.getPassword());
        //verificar que las contraseñas estan vacias
        if (!password.equals("") && !confirm_password.equals("")){
            //verificar que las contraseñas sean iguales
            if (password.equals(confirm_password)){
                employee.setPassword(String.valueOf(views.txt_password_modify.getPassword()));
                
                if (employeeDao.updateEmployeePassword(employee) != false) {
                    JOptionPane.showMessageDialog(null, "contraseña modificada con exito");
                    
                } else {
                    JOptionPane.showMessageDialog(null, "ha ocurrido un error al modificar la contraseña");
                }
            } else {
                JOptionPane.showMessageDialog(null, "las contraseñas no coinciden");
            }
        } else {
            JOptionPane.showMessageDialog(null, "todos los campos son obligatorios");
        }
    }
}

    
    private void registerEmployee() {
        // verificar si los campos están vacios
        String[] fields = {
            views.txt_employee_idTest.getText(),
            views.txt_employee_fullname.getText(),
            views.txt_employee_username.getText(),
            views.txt_employee_address.getText(),
            views.txt_employee_telephone.getText(),
            views.txt_employee_email.getText(),
            views.cmb_rol.getSelectedItem().toString(),
            String.valueOf(views.txt_employee_password.getPassword())
        };
        
        for (String field : fields) {
            if (field.trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                return;
            }
        }
        
        try {
            // REALIZAR LA INSERCION
            employee.setId(Integer.parseInt(views.txt_employee_idTest.getText().trim()));
            employee.setFull_name(fields[1].trim());
            employee.setUsername(fields[2].trim());
            employee.setAddress(fields[3].trim());
            employee.setTelephone(fields[4].trim());
            employee.setEmail(fields[5].trim());
            employee.setPassword(fields[7].trim());
            employee.setRol(fields[6].trim());
            
            if (employeeDao.registerEmployeeQuery(employee)) {
                cleanTable();
                listAllEmployees("");
                JOptionPane.showMessageDialog(null, "Empleado registrado con éxito!!!");
            } else {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar al empleado");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "El ID del empleado debe ser numérico");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al registrar al empleado: " + ex.getMessage());
        }
    }
    
    
    // LISTAR TODOS LOS EMPLEADOS
    public void listAllEmployees(String search) {
    cleanTable();
    List<Employees> list = employeeDao.listEmployeesQuery(search);
    model = (DefaultTableModel) views.employees_table.getModel();
    Object[] row = new Object[7];
    for (int i = 0; i < list.size(); i++) {
        row[0] = list.get(i).getId();
        row[1] = list.get(i).getFull_name();
        row[2] = list.get(i).getUsername();
        row[3] = list.get(i).getAddress();
        row[4] = list.get(i).getTelephone();
        row[5] = list.get(i).getEmail();
        row[6] = list.get(i).getRol();
        model.addRow(row);
    }
}

    
    // Métodos de MouseListener y KeyListener...

    @Override
public void mouseClicked(MouseEvent e) {
    if(e.getSource() == views.employees_table) {
        int row = views.employees_table.rowAtPoint(e.getPoint());
           
           views.txt_employee_idTest.setText(views.employees_table.getValueAt(row, 0).toString());
           views.txt_employee_fullname.setText(views.employees_table.getValueAt(row,1).toString());
           views.txt_employee_username.setText(views.employees_table.getValueAt(row,2).toString());
           views.txt_employee_address.setText(views.employees_table.getValueAt(row,3).toString());
           views.txt_employee_telephone.setText(views.employees_table.getValueAt(row,4).toString());
           views.txt_employee_email.setText(views.employees_table.getValueAt(row,5).toString());
           views.cmb_rol.setSelectedItem(views.employees_table.getValueAt(row, 6).toString());
           
           //desabilitar
           views.txt_employee_idTest.setEditable(false);
           views.txt_employee_password.setEnabled(false);
           views.btn_register_employee.setEnabled(false);
           
           
       }else if (e.getSource() == views.jLabelEmployees){
           if (rol.equals("Administrador")){
               views.jTabbedPane1.setSelectedIndex(3);
               //limpiar tabla
               cleanTable();
               //limpiar campos
               cleanFields();
               //listar empleados
               listAllEmployees("");
               
           } else {
               views.jTabbedPane1.setEnabledAt(3, false);
               views.jLabelEmployees.setEnabled(true);
               JOptionPane.showMessageDialog(null, "no tienes privilegios de administrador para acceder a esta vista ");
           }
                    
       }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
public void keyReleased(KeyEvent e) {
    if(e.getSource() == views.txt_search_employee){
        String search = views.txt_search_employee.getText();
        listAllEmployees(search);
    }
}
    public void cleanTable(){
        for(int i = 0; i<model.getRowCount(); i++){
            model.removeRow(i);
        i = i - 1;
        }
    }

private void updateEmployee() {
    // verificar si los campos están vacios excepto la contraseña
    String[] fields = {
        views.txt_employee_idTest.getText(),
        views.txt_employee_fullname.getText(),
        views.txt_employee_username.getText(),
        views.txt_employee_address.getText(),
        views.txt_employee_telephone.getText(),
        views.txt_employee_email.getText(),
        views.cmb_rol.getSelectedItem().toString()
    };
    
    for (String field : fields) {
        if (field.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios, excepto la contraseña");
            return;
        }
    }
    
    try {
        // REALIZAR LA ACTUALIZACIÓN
        employee.setId(Integer.parseInt(views.txt_employee_idTest.getText().trim()));
        employee.setFull_name(fields[1].trim());
        employee.setUsername(fields[2].trim());
        employee.setAddress(fields[3].trim());
        employee.setTelephone(fields[4].trim());
        employee.setEmail(fields[5].trim());
        employee.setRol(fields[6].trim());
        
        // Actualizar la contraseña solo si se ha ingresado una nueva
        char[] password = views.txt_employee_password.getPassword();
        if (password.length > 0) {
            employee.setPassword(new String(password).trim());
        }
        
        if (employeeDao.updateEmployeeQuery(employee)) {
            cleanTable();
            listAllEmployees("");
            JOptionPane.showMessageDialog(null, "Empleado actualizado con éxito!!!");
        } else {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al actualizar al empleado");
        }
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "El ID del empleado debe ser numérico");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Error al actualizar al empleado: " + ex.getMessage());
    }
}

private void deleteEmployee() {
    int dialogResult = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar este empleado?", "Advertencia", JOptionPane.YES_NO_OPTION);
    if (dialogResult == JOptionPane.YES_OPTION) {
        try {
            int employeeId = Integer.parseInt(views.txt_employee_idTest.getText().trim());
            
            if (employeeDao.deleteEmployeeQuery(employeeId)) {
                cleanTable();
                listAllEmployees("");
                JOptionPane.showMessageDialog(null, "Empleado eliminado con éxito!!!");
            } else {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error al eliminar al empleado");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "El ID del empleado debe ser numérico");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar al empleado: " + ex.getMessage());
        }
    }
}

    private void cleanFields() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}







/*package controllers;

import models.EmployeesDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import models.Employees;
import static models.EmployeesDao.rol_user;
import views.SystemView;

public class EmployeesController implements ActionListener {

    private Employees employee;
    private EmployeesDao employeesDao;
    private SystemView views;
    //rol
    String rol = rol_user;

    public EmployeesController(Employees employee, EmployeesDao employeesDao, SystemView views) {
        this.employee = employee;
        this.employeesDao = employeesDao;
        this.views = views;
        //boton de registrar empleado
        this.views.btn_register_employee.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_employee) {
            //verificar si los campos estan vacios 
            if (views.txt_employee_id.getText().equals("")
                    || views.txt_employee_fullname.getText().equals("")
                    || views.txt_employee_username.getText().equals("")
                    || views.txt_employee_addres.getText().equals("")
                    || views.txt_employee_telephone.getText().equals("")
                    || views.txt_employee_email.getText().equals("")
                    || views.cmb_rol.getSelectedItem().toString().equals("")
                    || String.valueOf(views.txt_employee_password.getPassword()).equals("")) {

                JOptionPane.showMessageDialog(null, "todos los campos son obligatorios");

            } else {
                //realizar la insercion
                employee.setId(Integer.parseInt(views.txt_employee_id.getText().trim()));
                employee.setFull_name(views.txt_employee_fullname.getText().trim());
                employee.setUsername(views.txt_employee_username.getText().trim());
                employee.setAddress(views.txt_employee_addres.getText().trim());
                employee.setTelephone(views.txt_employee_telephone.getText().trim());
                employee.setEmail(views.txt_employee_email.getText().trim());
                employee.setPassword(String.valueOf(views.txt_employee_password.getPassword()));
                employee.setRol(views.cmb_rol.getSelectedItem().toString());

                if (employeesDao.registerEmployeeQuery(employee)) {

                    JOptionPane.showMessageDialog(null, "empleado registrado con exito");

                }else {

                    JOptionPane.showMessageDialog(null, "ha ocurrido un error al registrar al empleado");
                }

            }
        }

    }
    
    //listar todos los empleados

}
*/