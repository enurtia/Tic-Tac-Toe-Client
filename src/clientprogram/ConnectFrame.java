/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientprogram;

import javax.swing.JOptionPane;

/**
 *
 * @author Alex
 */
public class ConnectFrame extends javax.swing.JFrame
{
    private IOHandler ioHandler;
    private ClientFrame clientFrame;
    /**
     * Creates new form ConnectFrame
     */
    public ConnectFrame()
    {
        initComponents();
    }
    
    public void passHandler(IOHandler ioHandler)
    {
        this.ioHandler = ioHandler;
        clientFrame = ioHandler.getClientFrame();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        lblIPAddress = new javax.swing.JLabel();
        lblPort = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtIPAddress = new javax.swing.JTextField();
        txtPort = new javax.swing.JFormattedTextField();
        btnConnect = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                formWindowClosing(evt);
            }
        });

        lblIPAddress.setText("IP:");

        lblPort.setText("Port:");

        lblUsername.setText("Username:");

        txtPort.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtPort.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                txtPortActionPerformed(evt);
            }
        });

        btnConnect.setText("Connect");
        btnConnect.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnConnectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnConnect, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIPAddress)
                            .addComponent(lblPort)
                            .addComponent(lblUsername))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUsername)
                            .addComponent(txtPort, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtIPAddress, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIPAddress)
                    .addComponent(txtIPAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPort)
                    .addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsername)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnConnect)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPortActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_txtPortActionPerformed
    {//GEN-HEADEREND:event_txtPortActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPortActionPerformed

    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnConnectActionPerformed
    {//GEN-HEADEREND:event_btnConnectActionPerformed
        boolean properFormat = false;       //Variable stating if username doesn't contain blacklisted chars
        if(txtUsername.getText().matches("\\[|\\]|#|,"))
        {
            JOptionPane.showMessageDialog(null, "Username cannot contain characters: [, ], #, or ,.");
        }
        else
        {
            properFormat = true;
        }
        
        
        if(!clientFrame.isConnected() && properFormat)
        {
            int port = 0;
            int check = 2;
            if(!txtIPAddress.getText().isEmpty() && !txtPort.getText().isEmpty() && !txtUsername.getText().isEmpty())
            {
                port = Integer.parseInt(txtPort.getText());
                check = ioHandler.connect(txtIPAddress.getText(), port);
            }
            switch(check)
            {
                case 0: //Connection Succesful; Check if username is unique
                {
                    clientFrame.setConnected(true);
                    ioHandler.out("[USERNAMECHECK]" + txtUsername.getText());
                    System.out.println("USERNAME CHECK");
                    
                    break;
                }
                case 1:
                {
                    JOptionPane.showMessageDialog(null, "IP doesn't exist.");
                    
                    break;
                }
                case 2:
                {
                    JOptionPane.showMessageDialog(null, "Connection error. Check that the IP and port are correct.");
                    
                    break;
                }
            }//End switch
        }
        else if(properFormat)
        {   //If already connected, then the connect button is referring to rechecking username availability.
            ioHandler.out("[USERNAMECHECK]" + txtUsername.getText());
        }
    }//GEN-LAST:event_btnConnectActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
    {//GEN-HEADEREND:event_formWindowClosing
        clientFrame.conFrameClosed();
    }//GEN-LAST:event_formWindowClosing
    
    public void usernameValidity(boolean bool)      //If username isn't taken, frame will close and label will be set.
    {                                               //If username is taken, a message will pop up.
        if(bool == true && clientFrame.isConnected())
        {
            this.setVisible(false);
            clientFrame.setLabel("Connected to: " + txtIPAddress.getText() + ":" + txtPort.getText());
            clientFrame.setUsername(txtUsername.getText());
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Please choose a different username.");
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(ConnectFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(ConnectFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(ConnectFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(ConnectFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new ConnectFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConnect;
    private javax.swing.JLabel lblIPAddress;
    private javax.swing.JLabel lblPort;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JTextField txtIPAddress;
    private javax.swing.JFormattedTextField txtPort;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
