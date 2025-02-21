/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package interfaz;

import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import main.CPU;
import main.GestorConfiguracion;
import main.Kernel;
import main.PCB;
import main.Proceso;
import structures.Node;

/**
 *
 * @author andresimery
 */
public class SimuladorInterfaz extends javax.swing.JFrame  {
    private static GestorConfiguracion gestor;
    private static Kernel kernel;
    
    /**
     * Creates new form SimuladorInterfaz
     */
    public SimuladorInterfaz(GestorConfiguracion gestor) {
        this.gestor = gestor;
        this.kernel = new Kernel(gestor, this);
        initComponents();
        setup();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    /**
     * Método para actualizar la interfaz de usuario
     */
    public void actualizarInterfaz() {
        SwingUtilities.invokeLater(() -> {
            setCicloReloj(kernel.getCicloReloj());

            limpiarTablas();

            // Actualizar colas de procesos
            Node<Proceso> aux = kernel.getMemoria().getColaListos().getHead();
            while (aux != null) {
                agregarProcesoATablaListos(aux.getData());
                aux = aux.getNext();
            }

            aux = kernel.getMemoria().getColaBloqueados().getHead();
            while (aux != null) {
                agregarProcesoATablaBloqueados(aux.getData());
                aux = aux.getNext();
            }

            aux = kernel.getMemoria().getColaTerminados().getHead();
            while (aux != null) {
                agregarProcesoATablaTerminados(aux.getData());
                aux = aux.getNext();
            }

            // Actualizar CPUs en ejecución
            Node<CPU> auxCpu = kernel.getCpus().getHead();
            while (auxCpu != null) {
                agregarProcesoATablaCPUs(auxCpu.getData());
                auxCpu = auxCpu.getNext();
            }
        });
    }
    
    public void setup() {
        comboAlgoritmo.setSelectedItem(gestor.getAlgoritmoActual());
    }
    
    public void setCicloReloj(int cicloReloj) {
        jLabelCicloDeReloj.setText("Ciclo de Reloj: " + cicloReloj);
    }
    
    public void agregarProcesoATablaListos(Proceso proceso) {
        DefaultTableModel tablaModelo = (DefaultTableModel) tableColaListos.getModel();
        tablaModelo.addRow(new Object[]{
                    proceso.getPCB().getProcesoId(),
                    proceso.getPCB().getNombre(),
                    proceso.getPCB().getMAR(),
                    proceso.getPCB().getEstado(),
                    proceso.getPCB().getProgramCounter()
            });
//        tablaModelo.fireTableDataChanged();
    }
    
    public void agregarProcesoATablaBloqueados(Proceso proceso) {
        DefaultTableModel tablaModelo = (DefaultTableModel) tableColaBloqueados.getModel();
        tablaModelo.addRow(new Object[]{
                    proceso.getPCB().getProcesoId(),
                    proceso.getPCB().getNombre(),
                    proceso.getPCB().getMAR(),
                    proceso.getPCB().getEstado(),
                    proceso.getPCB().getProgramCounter()
            });
//        tablaModelo.fireTableDataChanged();
    }
    
    public void agregarProcesoATablaTerminados(Proceso proceso) {
        DefaultTableModel tablaModelo = (DefaultTableModel) tableColaTerminados.getModel();
        tablaModelo.addRow(new Object[]{
                    proceso.getPCB().getProcesoId(),
                    proceso.getPCB().getNombre(),
                    proceso.getPCB().getMAR(),
                    proceso.getPCB().getEstado(),
                    proceso.getPCB().getProgramCounter()
            });
//        tablaModelo.fireTableDataChanged();
    }
    
    public void agregarProcesoATablaCPUs(CPU cpu) {
        if (cpu.getProcesoActual() != null) {
            cpu.getProcesoActual().setEstado(PCB.Estado.EJECUTANDO);
        }
        DefaultTableModel tablaModelo = (DefaultTableModel) tableCPUs.getModel();
        tablaModelo.addRow(new Object[]{
                    cpu.getCpuId(),
                    cpu.getProcesoActual() != null ? cpu.getProcesoActual().getPCB().getNombre() : "NONE",
                    cpu.getProcesoActual() != null ? cpu.getProcesoActual().getPCB().getProgramCounter() : "",
                    cpu.getQuantum(),
                    cpu.getProcesoActual() != null ? cpu.getProcesoActual().getPCB().getEstado() : "IDLE",
                    cpu.getProcesoActual() != null ? cpu.getProcesoActual().getPCB().getMAR() : ""
            });
//        tablaModelo.fireTableDataChanged();
    }
    
    public void limpiarTablas() {
        DefaultTableModel tablaModelo = (DefaultTableModel) tableColaListos.getModel();
        tablaModelo.setRowCount(0);
        
        tablaModelo = (DefaultTableModel) tableColaBloqueados.getModel();
        tablaModelo.setRowCount(0);
        
        tablaModelo = (DefaultTableModel) tableColaTerminados.getModel();
        tablaModelo.setRowCount(0);
        
//        tablaModelo = (DefaultTableModel) tableProcesos.getModel();
//        tablaModelo.setRowCount(0);
        
        tablaModelo = (DefaultTableModel) tableCPUs.getModel();
        tablaModelo.setRowCount(0);
        
    }
    
    public void start() {
        
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
        jPanel2 = new javax.swing.JPanel();
        jLabelCicloDeReloj = new javax.swing.JLabel();
        comboAlgoritmo = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        btnIniciar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableCPUs = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableColaTerminados = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableColaListos = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableColaBloqueados = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 0, 36)); // NOI18N
        jLabel1.setText("Sistema Operativo");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, -1, -1));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabelCicloDeReloj.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabelCicloDeReloj.setText("Ciclo de Reloj: [XX]");

        comboAlgoritmo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FCFS", "SPN", "HRRN", "SRT", "RR" }));
        comboAlgoritmo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboAlgoritmoActionPerformed(evt);
            }
        });

        jLabel3.setText("Algoritmo:");

        btnIniciar.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        btnIniciar.setText("INICIAR");
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(comboAlgoritmo, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 453, Short.MAX_VALUE)
                                .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabelCicloDeReloj)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabelCicloDeReloj)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3))
                    .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboAlgoritmo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 770, 140));

        tableCPUs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Proceso", "Program Counter", "Quantum", "Estado", "MAR"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableCPUs);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 590, 770, 110));

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel4.setText("Colas de Procesos:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, -1));

        jLabel5.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel5.setText("CPUs:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 560, 100, -1));

        tableColaTerminados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "id", "Proceso", "MAR", "Estado", "PC"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
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
        jScrollPane2.setViewportView(tableColaTerminados);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 280, 320, 110));

        tableColaListos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "id", "Proceso", "MAR", "Estado", "PC"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
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
        jScrollPane4.setViewportView(tableColaListos);

        jPanel1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 320, 110));

        tableColaBloqueados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "id", "Proceso", "MAR", "Estado", "PC"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
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
        jScrollPane5.setViewportView(tableColaBloqueados);

        jPanel1.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 430, 320, 110));

        jLabel2.setText("Terminados");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 260, -1, -1));

        jLabel7.setText("Listos");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 260, -1, -1));

        jLabel8.setText("Bloqueados");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 410, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 810));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        btnIniciar.setVisible(false);
//        this.run();
        kernel.start();
//        this.run();
        
    }//GEN-LAST:event_btnIniciarActionPerformed

    private void comboAlgoritmoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboAlgoritmoActionPerformed
        String algoritmo = comboAlgoritmo.getSelectedItem().toString();
        gestor.setAlgoritmoActual(algoritmo);
        kernel.getPlanificador().setAlgoritmoActual(algoritmo);
    }//GEN-LAST:event_comboAlgoritmoActionPerformed

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
            java.util.logging.Logger.getLogger(SimuladorInterfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SimuladorInterfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SimuladorInterfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SimuladorInterfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SimuladorInterfaz(gestor).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIniciar;
    private javax.swing.JComboBox<String> comboAlgoritmo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelCicloDeReloj;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable tableCPUs;
    private javax.swing.JTable tableColaBloqueados;
    private javax.swing.JTable tableColaListos;
    private javax.swing.JTable tableColaTerminados;
    // End of variables declaration//GEN-END:variables
}
