/*
 * PanelConectar.java
 *
 * Created on January 11, 2001, 4:25 PM
 */
 
package tuplas;
import javax.swing.*;
/** 
 *
 * @author  Valued Sony Customer
 * @version 
 */
public class PanelConectar extends javax.swing.JPanel {

  /** La ventana TuplaAdmin en donde esta contenido este panel. */
  private TuplaAdmin _contenedor;
  
  
  
  
  /** Creates new form PanelConectar */
  public PanelConectar(TuplaAdmin contenedor) {
    _contenedor = contenedor;
    initComponents ();
  }
  
  public static void main(String[] arg){
    TuplaAdmin contenedor = new TuplaAdmin();
    PanelConectar panel = new PanelConectar(contenedor);
    contenedor.show();
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the FormEditor.
   */
  private void initComponents () {//GEN-BEGIN:initComponents
    jLabel1 = new javax.swing.JLabel ();
    jTextHostName = new javax.swing.JTextField ();
    jLabel2 = new javax.swing.JLabel ();
    jTextPort = new javax.swing.JTextField ();
    jLabel3 = new javax.swing.JLabel ();
    jTextName = new javax.swing.JTextField ();
    jButtonConectar = new javax.swing.JButton ();
    setLayout (new java.awt.GridBagLayout ());
    java.awt.GridBagConstraints gridBagConstraints1;
    setDoubleBuffered (false);

    jLabel1.setText ("Nombre del Host");


    gridBagConstraints1 = new java.awt.GridBagConstraints ();
    gridBagConstraints1.insets = new java.awt.Insets (0, 5, 0, 0);
    gridBagConstraints1.anchor = java.awt.GridBagConstraints.EAST;
    add (jLabel1, gridBagConstraints1);

    jTextHostName.setPreferredSize (new java.awt.Dimension(200, 20));
    jTextHostName.setText ("localhost");


    gridBagConstraints1 = new java.awt.GridBagConstraints ();
    gridBagConstraints1.insets = new java.awt.Insets (5, 5, 5, 4);
    gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
    add (jTextHostName, gridBagConstraints1);

    jLabel2.setText ("Puerto");


    gridBagConstraints1 = new java.awt.GridBagConstraints ();
    gridBagConstraints1.gridx = 0;
    gridBagConstraints1.gridy = 1;
    gridBagConstraints1.insets = new java.awt.Insets (0, 5, 0, 0);
    gridBagConstraints1.anchor = java.awt.GridBagConstraints.EAST;
    add (jLabel2, gridBagConstraints1);

    jTextPort.setPreferredSize (new java.awt.Dimension(80, 20));
    jTextPort.setText ("1099");


    gridBagConstraints1 = new java.awt.GridBagConstraints ();
    gridBagConstraints1.gridx = 1;
    gridBagConstraints1.gridy = 1;
    gridBagConstraints1.insets = new java.awt.Insets (0, 5, 5, 5);
    gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
    add (jTextPort, gridBagConstraints1);

    jLabel3.setText ("Nombre del Servicio");


    gridBagConstraints1 = new java.awt.GridBagConstraints ();
    gridBagConstraints1.gridx = 0;
    gridBagConstraints1.gridy = 2;
    gridBagConstraints1.insets = new java.awt.Insets (0, 5, 0, 0);
    gridBagConstraints1.anchor = java.awt.GridBagConstraints.EAST;
    add (jLabel3, gridBagConstraints1);

    jTextName.setPreferredSize (new java.awt.Dimension(200, 20));
    jTextName.setText ("tuplas/TuplaD");


    gridBagConstraints1 = new java.awt.GridBagConstraints ();
    gridBagConstraints1.gridx = 1;
    gridBagConstraints1.gridy = 2;
    gridBagConstraints1.insets = new java.awt.Insets (0, 5, 5, 5);
    gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
    add (jTextName, gridBagConstraints1);

    jButtonConectar.setToolTipText ("Si deja alg�n campo en blanco, se conectar� a la conexi�n por defecto");
    jButtonConectar.setText ("Conectar");
    jButtonConectar.addActionListener (new java.awt.event.ActionListener () {
      public void actionPerformed (java.awt.event.ActionEvent evt) {
        jButtonConectarActionPerformed (evt);
      }
    }
    );


    gridBagConstraints1 = new java.awt.GridBagConstraints ();
    gridBagConstraints1.gridx = 1;
    gridBagConstraints1.gridy = 3;
    gridBagConstraints1.insets = new java.awt.Insets (0, 5, 5, 5);
    gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
    add (jButtonConectar, gridBagConstraints1);

  }//GEN-END:initComponents

  /** Click en el boton de Conectar */  
  private void jButtonConectarActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConectarActionPerformed
    String host = jTextHostName.getText();
    int port = Integer.parseInt(jTextPort.getText());
    String objUrl = jTextName.getText();

    
    ConexionRMI hilo = new ConexionRMI(host,port,objUrl);
    try
    {
      hilo._conectar();
    }
    catch (Exception e) {};
    
    if (hilo._conectado)
    {
      System.out.println("Panel de Conexion -> Si esta conectado");
      _contenedor._objetoRemoto = hilo._objetoRemoto;
      _contenedor._probar();
      _contenedor.setTitle("TuplaAdmin");
      _contenedor._cambiarPanel(_contenedor._panelCrear);
      JOptionPane.showMessageDialog(null,new String("Conexi�n RMI Exitosa."),"TuplaAdmin :: Informaci�n",JOptionPane.INFORMATION_MESSAGE);
    }
    else
      JOptionPane.showMessageDialog(null,new String("No se pudo efectuar la conexi�n."),"TuplaAdmin :: Error",JOptionPane.ERROR_MESSAGE);
    
  }//GEN-LAST:event_jButtonConectarActionPerformed

  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel jLabel1;
  private javax.swing.JTextField jTextHostName;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JTextField jTextPort;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JTextField jTextName;
  private javax.swing.JButton jButtonConectar;
  // End of variables declaration//GEN-END:variables

}