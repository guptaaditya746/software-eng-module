package de.buw.se;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.awt.Color;

class TransactionDataTest {
	
	//this is just a class withoutmain method
	@Test
    public void testTransactionDataConstructor() {
    	
        // Testing MiDeepBlue menu item choice
        TransactionData dataDeepBlue = new TransactionData("MiDeepBlue");
        assertEquals(new Color(30, 30, 50), dataDeepBlue.getColorBackGround());
        assertEquals(new Color(50, 50, 80), dataDeepBlue.getColorBackTA());
        assertEquals(new Color(255, 255, 255), dataDeepBlue.getColorForeTA());
        assertEquals(new Color(255, 255, 255), dataDeepBlue.getColorLetters());

        // Testing MiBlack menu item choice
        TransactionData dataBlack = new TransactionData("MiBlack");
        assertEquals(new Color(10, 10, 10), dataBlack.getColorBackGround());
        assertEquals(new Color(40, 40, 40), dataBlack.getColorBackTA());
        assertEquals(new Color(255, 255, 255), dataBlack.getColorForeTA());
        assertEquals(new Color(255, 255, 255), dataBlack.getColorLetters());

        // Testing other menu item choices (default)
        TransactionData dataDefault = new TransactionData("Other");
        assertEquals(new Color(100, 100, 100), dataDefault.getColorBackGround());
        assertEquals(new Color(150, 150, 150), dataDefault.getColorBackTA());
        assertEquals(new Color(0, 0, 0), dataDefault.getColorForeTA());
        assertEquals(new Color(0, 0, 0), dataDefault.getColorLetters());
    }

}
