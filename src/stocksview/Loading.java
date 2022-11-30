package stocksview;

import javax.swing.*;

public final class Loading extends javax.swing.JDialog {

  /*
   * Creates new form Loading
   * @param parent
   * @param modal
   */
  public Loading(java.awt.Frame parent,boolean modal) {
    //super(new Frame(), modal);
    this.setSize(300,100);
    this.setLocation(500,300);
    this.setTitle("Loading...Please wait");
    this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
  }

  public void formWindowActivated() {
    if(this.isVisible()){
      this.dispose();
    }else{
      this.setVisible(true);
    }
  }
}