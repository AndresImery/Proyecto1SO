/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package interfaz;

//import java.util.ArrayList;   
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import main.GestorConfiguracion;
import main.Proceso;
import structures.LinkedList;
import structures.Node;

/**
 *
 * @author andresimery
 */
public class GestionProcesos extends javax.swing.JFrame {

    private static GestorConfiguracion gestor;
    private static VentanaConfiguracion vc;
    /**
     * Creates new form GestionProcesos
     * @param gestor 
     */
    public GestionProcesos(VentanaConfiguracion vc) {
        initComponents();
        setLocationRelativeTo(null);
        this.gestor = vc.getGestor();
        this.vc = vc;
        setup();
        jLabelExcepciones.setVisible(false);
        txtExcepciones.setVisible(false);
        txtDesbloqueo.setVisible(false);
        jLabelDesbloqueo.setVisible(false);
        setVisible(true);
    }
    
    private void setup() {
        gestor.cargarDesdeJSON();
        LinkedList<Proceso> procesos = gestor.getProcesosCargados();
        DefaultTableModel model = (DefaultTableModel) tableProcesos.getModel();
        model.setRowCount(0);
        Node<Proceso> aux = procesos.getHead();
        while (aux != null) {
            Proceso proceso = aux.getData();
            model.addRow(new Object[]{
                    proceso.getPCB().getNombre(),
                    proceso.getPCB().getInstruccionesRestantes(),
                    proceso.getPCB().esCPUBound() ? "CPU Bound" : "I/O Bound",
                    proceso.getPCB().getCiclosPorExcepcion(),
                    proceso.getPCB().getCiclosParaDesbloqueo()
            });
            aux = aux.getNext();
        }
//        tableProcesos.
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableProcesos = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtDesbloqueo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        comboTipo = new javax.swing.JComboBox<>();
        jLabelDesbloqueo = new javax.swing.JLabel();
        txtInstrucciones = new javax.swing.JTextField();
        jLabelExcepciones = new javax.swing.JLabel();
        txtExcepciones = new javax.swing.JTextField();
        btnCrearProceso = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        jLabel1.setText("Gestión de Procesos");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, -1, -1));

        tableProcesos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Instrucciones", "Tipo", "Excepciones", "Desbloqueo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableProcesos.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(tableProcesos);
        tableProcesos.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 750, 190));

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel2.setText("Proceso");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, -1, -1));

        jLabel3.setText("Nombre:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, -1, -1));
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 310, 30, 20));
        jPanel1.add(txtDesbloqueo, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 310, 30, 20));

        jLabel4.setText("Instrucciones:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 310, -1, -1));

        jLabel5.setText("Tipo:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 310, -1, -1));

        comboTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CPU Bound", "I/O Bound" }));
        comboTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTipoActionPerformed(evt);
            }
        });
        jPanel1.add(comboTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 310, -1, 20));

        jLabelDesbloqueo.setText("Desbloqueo:");
        jPanel1.add(jLabelDesbloqueo, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 310, -1, -1));
        jPanel1.add(txtInstrucciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 310, 30, 20));

        jLabelExcepciones.setText("Excepciones:");
        jPanel1.add(jLabelExcepciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 310, -1, -1));
        jPanel1.add(txtExcepciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 310, 30, 20));

        btnCrearProceso.setText("Crear Nuevo Proceso");
        btnCrearProceso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearProcesoActionPerformed(evt);
            }
        });
        jPanel1.add(btnCrearProceso, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, -1, -1));

        btnEliminar.setText("Eliminar Proceso");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 340, -1, -1));

        btnVolver.setText("Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });
        jPanel1.add(btnVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 450));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCrearProcesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearProcesoActionPerformed
        String nombre = txtNombre.getText();
        int instruccionesTotales;
        boolean esCPUBound = comboTipo.getSelectedItem().equals("CPU Bound");
        int ciclosPorExcepcion;
        int ciclosParaDesbloqueo;
        
        try {
            instruccionesTotales = Integer.parseInt(txtInstrucciones.getText());
            if (!esCPUBound) {
                ciclosPorExcepcion = Integer.parseInt(txtExcepciones.getText());
                ciclosParaDesbloqueo = Integer.parseInt(txtDesbloqueo.getText());
            } else {
                ciclosPorExcepcion = 0;
                ciclosParaDesbloqueo = 0;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Crear un nuevo proceso
        int count = gestor.getProcesosCargados().getSize();
        Proceso nuevoProceso = new Proceso(count, nombre, instruccionesTotales, esCPUBound, ciclosPorExcepcion, ciclosParaDesbloqueo, 0);
        
        // Agregar a la lista de procesos del gestor
        gestor.getProcesosCargados().add(nuevoProceso);
        
        gestor.guardarEnJSON();
        
        // Actualizar la tabla
        DefaultTableModel model = (DefaultTableModel) tableProcesos.getModel();
        model.addRow(new Object[]{
                nuevoProceso.getPCB().getNombre(),
                nuevoProceso.getPCB().getInstruccionesRestantes(),
                nuevoProceso.getPCB().esCPUBound() ? "CPU Bound" : "I/O Bound",
                nuevoProceso.getPCB().getCiclosPorExcepcion(),
                nuevoProceso.getPCB().getCiclosParaDesbloqueo()
        });
        
        // Limpiar los campos de entrada
        txtNombre.setText("");
        txtInstrucciones.setText("");
        txtExcepciones.setText("");
        txtDesbloqueo.setText("");
    }//GEN-LAST:event_btnCrearProcesoActionPerformed

    private void comboTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTipoActionPerformed
        boolean esCPUBound = comboTipo.getSelectedItem().equals("CPU Bound");
        if (esCPUBound) {
            jLabelExcepciones.setVisible(false);
            txtExcepciones.setVisible(false);
            txtDesbloqueo.setVisible(false);
            jLabelDesbloqueo.setVisible(false);
        } else {
            jLabelExcepciones.setVisible(true);
            txtExcepciones.setVisible(true);
            txtDesbloqueo.setVisible(true);
            jLabelDesbloqueo.setVisible(true);
        }
    }//GEN-LAST:event_comboTipoActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int filaSeleccionada = tableProcesos.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un proceso para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String nombreProceso = tableProcesos.getValueAt(filaSeleccionada, 0).toString();
        
        // Confirmación del usuario
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar el proceso '" + nombreProceso + "'?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion != JOptionPane.YES_OPTION) {
            return; // Si el usuario cancela, no se elimina el proceso
        }
        
        // Buscar y eliminar el proceso en la lista
        LinkedList<Proceso> procesos = gestor.getProcesosCargados();
        Node<Proceso> aux = procesos.getHead();
        Proceso procesoAEliminar = null;
        
        while (aux != null) {
            Proceso proceso = aux.getData();
            if (proceso.getPCB().getNombre().equals(nombreProceso)) {
                procesoAEliminar = proceso;
                break;
            }
            aux = aux.getNext();
        }
        
        if (procesoAEliminar != null) {
            procesos.remove(procesoAEliminar);
        }
        
        // Guardar cambios en JSON
        gestor.guardarEnJSON();
        
        // Actualizar la tabla
        DefaultTableModel model = (DefaultTableModel) tableProcesos.getModel();
        model.setRowCount(0);
        setup();
    
        JOptionPane.showMessageDialog(this, "Proceso eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        this.setVisible(false);
        vc.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

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
            java.util.logging.Logger.getLogger(GestionProcesos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestionProcesos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestionProcesos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestionProcesos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GestionProcesos(vc).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCrearProceso;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> comboTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelDesbloqueo;
    private javax.swing.JLabel jLabelExcepciones;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableProcesos;
    private javax.swing.JTextField txtDesbloqueo;
    private javax.swing.JTextField txtExcepciones;
    private javax.swing.JTextField txtInstrucciones;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
