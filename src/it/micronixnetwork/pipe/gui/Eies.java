/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.pipe.gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
 
public class Eies
{
  private JFrame mainFrame;
  private static final String APP_NAME = "Eies";
   
  public static void main(String[] args)
  {
    new Eies();
  }
   
  public Eies()
  {
    // display the jframe
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        mainFrame = new GUI();
        mainFrame.pack();
        mainFrame.setVisible(true);
      }
    });
  }

   
}