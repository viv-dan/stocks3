package stocksview;

import javax.swing.*;

public final class Loading extends javax.swing.JDialog {

  public Loading(java.awt.Frame parent,boolean modal) {
    //super(new Frame(), modal);
    this.setSize(300,100);
    this.setLocation(500,300);
    this.setTitle("Loading...Please wait");
    this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
  }
  public void formWindowDeActivated(){
    this.dispose();
  }

  public void formWindowActivated() {
    this.setVisible(true);
  }
}