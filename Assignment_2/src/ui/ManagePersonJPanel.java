/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import java.awt.CardLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import model.Address;
import model.Person;
import model.PersonDirectory;

/**
 *
 * @author tawde
 */
public class ManagePersonJPanel extends javax.swing.JPanel {
    
    private JPanel userProcessContainer;
    private PersonDirectory personDirectory;

    /**
     * Creates new form ManagePersonJPanel
     */
    public ManagePersonJPanel(JPanel userProcessContainer, PersonDirectory personDirectory) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        this.personDirectory = personDirectory;
        populateTable();
    }
    
    void populateTable() {
        DefaultTableModel model = (DefaultTableModel) tblPerson.getModel();
        model.setRowCount(0);
        for(Person person: personDirectory.getPersonList()) {
            Object[] row = new Object[6];
            row[0] = person;
            row[1] = person.getLastName();
            row[2] = person.getWorkAddress();
            row[3] = person.getHomeAddress();
            
            model.addRow(row);
        }
    } 

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblPerson = new javax.swing.JTable();
        btnView = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        setBackground(new java.awt.Color(0, 51, 102));

        tblPerson.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "First Name", "Last Name", "Work Location", "Home Location"
            }
        ));
        jScrollPane1.setViewportView(tblPerson);

        btnView.setBackground(new java.awt.Color(204, 102, 0));
        btnView.setForeground(new java.awt.Color(255, 255, 255));
        btnView.setText("View Details");
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(204, 102, 0));
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Delete Person");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnBack.setBackground(new java.awt.Color(204, 102, 0));
        btnBack.setForeground(new java.awt.Color(255, 255, 255));
        btnBack.setText("<<<Back");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(196, Short.MAX_VALUE)
                .addComponent(btnBack)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(100, 100, 100))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBack))
                .addGap(70, 70, 70)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnView)
                    .addComponent(btnDelete))
                .addContainerGap(104, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblPerson.getSelectedRow();
        if(selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Please select a row to view details!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        else {
            Person person = (Person) tblPerson.getValueAt(selectedRow, 0);
            Address workAddress = (Address) tblPerson.getValueAt(selectedRow, 2);
            Address homeAddress = (Address) tblPerson.getValueAt(selectedRow, 3);
            ViewPersonJPanel panel = new ViewPersonJPanel(userProcessContainer, person, workAddress, homeAddress);
            userProcessContainer.add("ViewPersonJPanel", panel);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.next(userProcessContainer);
        }
    }//GEN-LAST:event_btnViewActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblPerson.getSelectedRow();
        if(selectedRow >= 0) {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Would you like to delete this person?", "Warning", dialogButton);
            if (dialogResult == JOptionPane.YES_OPTION) {
                Person person = (Person) tblPerson.getValueAt(selectedRow, 0);
                personDirectory.deletePerson(person);
                populateTable();
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Please select a row to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnView;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPerson;
    // End of variables declaration//GEN-END:variables
}
