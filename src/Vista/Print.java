/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;

/**
 *
 * @author luist
 */
public class Print extends javax.swing.JFrame {

    /**
     * Creates new form Print
     */
    public Print() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        form_print = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_invoice = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        purchase_details_table = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txt_total = new javax.swing.JTextField();
        btn_print_purchase = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        form_print.setBackground(new java.awt.Color(152, 197, 241));
        form_print.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/farmacia.png"))); // NOI18N
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-40, 0, 150, 70));

        form_print.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 70));

        jPanel1.setBackground(new java.awt.Color(18, 45, 61));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("VIDA NATURAL");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, -1, -1));

        txt_invoice.setEditable(false);
        jPanel1.add(txt_invoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, 110, -1));

        form_print.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 620, 70));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("DETALLES DE LA COMPRA");
        form_print.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        purchase_details_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Cantidad", "precio", "subtotal", "proveedor", "comprado por", "fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(purchase_details_table);
        if (purchase_details_table.getColumnModel().getColumnCount() > 0) {
            purchase_details_table.getColumnModel().getColumn(0).setMinWidth(100);
            purchase_details_table.getColumnModel().getColumn(5).setMinWidth(110);
            purchase_details_table.getColumnModel().getColumn(6).setMinWidth(80);
        }

        form_print.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 620, 240));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Total:");
        form_print.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 430, -1, 20));
        form_print.add(txt_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 430, 120, -1));

        getContentPane().add(form_print, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 620, 520));

        btn_print_purchase.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_print_purchase.setText("IMPRIMIR");
        getContentPane().add(btn_print_purchase, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 530, 130, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Print.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Print.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Print.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Print.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Print().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_print_purchase;
    private javax.swing.JPanel form_print;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable purchase_details_table;
    private javax.swing.JTextField txt_invoice;
    private javax.swing.JTextField txt_total;
    // End of variables declaration//GEN-END:variables
}
