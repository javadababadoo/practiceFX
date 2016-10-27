/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.nebulae.course.world;

import javafx.scene.control.TextArea;

/**
 *
 * @author Sebastian Molano - nebulae.com.co
 */
public class Log {

    private static TextArea textArea;

    public static void setTextArea(TextArea textArea) {
        Log.textArea = textArea;
    }

    public static void print(String text) {
        if (textArea != null) {
            textArea.setText(text + textArea.getText());
        }else{
            System.out.print(text);
        }
    }

    public static void println(String text) {
        print(text + "\n");
    }

}
