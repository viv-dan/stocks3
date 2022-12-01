package stocksview;

import javax.swing.JFrame;

final class Loading extends javax.swing.JDialog {

  Loading() {
    this.setSize(300, 100);
    this.setLocation(500, 300);
    this.setTitle("Loading...Please wait");
    this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
  }

  void formWindowDeActivated() {
    this.dispose();
  }

  void formWindowActivated() {
    this.setVisible(true);
  }
}