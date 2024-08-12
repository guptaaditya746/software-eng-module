package de.buw.se;

import java.awt.Color;

// Esta clase se usa para pasar información entre ventanas del programa
public class TransactionData {
    private Color colorBackGround;
    private Color colorBackTA;
    private Color colorForeTA;
    private Color colorLetters;

    public TransactionData(String menuItemChoice) {
        colorBackGround = new Color(30, 30, 50);
        colorBackTA = new Color(50, 50, 80);
        colorForeTA = new Color(255, 255, 255);
        colorLetters = new Color(255, 255, 255);
        
        if (menuItemChoice.equals("MiDeepBlue")) {
            colorBackGround = new Color(30, 30, 50);
            colorBackTA = new Color(50, 50, 80);
            colorForeTA = new Color(255, 255, 255);
            colorLetters = new Color(255, 255, 255);

        } else if (menuItemChoice.equals("MiBlack")) {
            colorBackGround = new Color(10, 10, 10);
            colorBackTA = new Color(40, 40, 40);
            colorForeTA = new Color(255, 255, 255);
            colorLetters = new Color(255, 255, 255);

        } else {
            colorBackGround = new Color(100, 100, 100);
            colorBackTA = new Color(150, 150, 150);
            colorForeTA = new Color(0, 0, 0);
            colorLetters = new Color(0, 0, 0);
        }   
    }


    public Color getColorBackGround() {
        return colorBackGround;
    }

    public Color getColorBackTA() {
        return colorBackTA;
    }

    public Color getColorForeTA() {
        return colorForeTA;
    }

    public Color getColorLetters() {
        return colorLetters;
    }

}