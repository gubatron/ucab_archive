/*
 * PanelAbout.java
 *
 * Created on January 14, 2001, 4:09 AM
 */
 
package tuplas;

/** 
 *
 * @author  root_vaio
 * @version 
 */
public class PanelAbout extends javax.swing.JPanel {

  /** Creates new form PanelAbout */
  public PanelAbout() {
    initComponents ();
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the FormEditor.
   */
  private void initComponents () {//GEN-BEGIN:initComponents
    jLabel1 = new javax.swing.JLabel ();
    jLabel2 = new javax.swing.JLabel ();
    jLabel3 = new javax.swing.JLabel ();
    jLabel4 = new javax.swing.JLabel ();
    jLabel5 = new javax.swing.JLabel ();
    setLayout (new java.awt.GridBagLayout ());
    java.awt.GridBagConstraints gridBagConstraints1;
    setBorder (new javax.swing.border.LineBorder(java.awt.Color.black, 3));

    jLabel1.setText ("<html><font color=red face=Verdana size=7><b>Tupla Admin</font></html>");


    gridBagConstraints1 = new java.awt.GridBagConstraints ();
    gridBagConstraints1.gridwidth = 2;
    gridBagConstraints1.insets = new java.awt.Insets (0, 0, 22, 0);
    add (jLabel1, gridBagConstraints1);

    jLabel2.setText ("Creado por:");


    gridBagConstraints1 = new java.awt.GridBagConstraints ();
    gridBagConstraints1.gridx = 0;
    gridBagConstraints1.gridy = 1;
    gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints1.insets = new java.awt.Insets (5, 0, 0, 0);
    add (jLabel2, gridBagConstraints1);

    jLabel3.setText ("<html><font face=Verdana  color=black>Angel L�on");


    gridBagConstraints1 = new java.awt.GridBagConstraints ();
    gridBagConstraints1.gridx = 0;
    gridBagConstraints1.gridy = 2;
    gridBagConstraints1.gridwidth = 2;
    gridBagConstraints1.insets = new java.awt.Insets (9, 0, 0, 0);
    add (jLabel3, gridBagConstraints1);

    jLabel4.setText ("<html><font face=Verdana  color=black>Luis Fleitas");


    gridBagConstraints1 = new java.awt.GridBagConstraints ();
    gridBagConstraints1.gridx = 0;
    gridBagConstraints1.gridy = 3;
    gridBagConstraints1.gridwidth = 2;
    gridBagConstraints1.insets = new java.awt.Insets (4, 0, 16, 0);
    add (jLabel4, gridBagConstraints1);

    jLabel5.setText ("<html><font face=Arial color=black size=1><b><br><a href='www.ucab.edu.ve'>UCAB</a> - Sistemas Operativos II - Enero 2001");


    gridBagConstraints1 = new java.awt.GridBagConstraints ();
    gridBagConstraints1.gridx = 0;
    gridBagConstraints1.gridy = 4;
    gridBagConstraints1.gridwidth = 2;
    gridBagConstraints1.insets = new java.awt.Insets (6, 3, 3, 3);
    add (jLabel5, gridBagConstraints1);

  }//GEN-END:initComponents


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  // End of variables declaration//GEN-END:variables

}