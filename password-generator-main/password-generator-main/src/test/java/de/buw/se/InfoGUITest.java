package de.buw.se;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.event.ActionEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InfoGUITest {

    private InfoGUI infoGUI;
    private TransactionData data;

    @BeforeEach
    public void setUp() {
        data = new TransactionData("MiDeepBlue");
        infoGUI = new InfoGUI("en", data);
    }

    @Test
    public void testActionPerformedReturnButton() {
        ActionEvent returnEvent = new ActionEvent(infoGUI.getReturnButton(), ActionEvent.ACTION_PERFORMED, "Return");
        infoGUI.actionPerformed(returnEvent);
        assertFalse(infoGUI.isVisible());
    }
    
    @Test
    public void testInitialColors() {
        assertEquals(new Color(30, 30, 50), infoGUI.getContentPane().getBackground());
        assertEquals(new Color(50, 50, 80), infoGUI.getTextAreaBackgroundColor());
        assertEquals(new Color(255, 255, 255), infoGUI.getTextAreaForegroundColor());
        assertEquals(new Color(255, 255, 255), infoGUI.getLabelTitleTipsColor());
    }

    @Test
    public void testChangeLanguageToGerman() {
        infoGUI.loadLanguage("de");
        infoGUI.updateTexts();
        assertEquals("Tipps zur Benutzersicherheit", infoGUI.getTitle());
    }
    
    @Test
    public void testActionPerformedMiDeepBlue() {
        ActionEvent deepBlueEvent = new ActionEvent(infoGUI.getMiDeepBlue(), ActionEvent.ACTION_PERFORMED, "MiDeepBlue");
        infoGUI.actionPerformed(deepBlueEvent);
        assertEquals(new Color(30, 30, 50), infoGUI.getContentPane().getBackground());
        assertEquals(new Color(50, 50, 80), infoGUI.getTextAreaBackgroundColor());
        assertEquals(new Color(255, 255, 255), infoGUI.getTextAreaForegroundColor());
        assertEquals(new Color(255, 255, 255), infoGUI.getLabelTitleTipsColor());
    }

    @Test
    public void testActionPerformedMiBlack() {
        ActionEvent blackEvent = new ActionEvent(infoGUI.getMiBlack(), ActionEvent.ACTION_PERFORMED, "MiBlack");
        infoGUI.actionPerformed(blackEvent);
        assertEquals(new Color(10, 10, 10), infoGUI.getContentPane().getBackground());
        assertEquals(new Color(40, 40, 40), infoGUI.getTextAreaBackgroundColor());
        assertEquals(new Color(255, 255, 255), infoGUI.getTextAreaForegroundColor());
        assertEquals(new Color(255, 255, 255), infoGUI.getLabelTitleTipsColor());
    }

    @Test
    public void testActionPerformedMiGray() {
        ActionEvent grayEvent = new ActionEvent(infoGUI.getMiGray(), ActionEvent.ACTION_PERFORMED, "MiGray");
        infoGUI.actionPerformed(grayEvent);
        assertEquals(new Color(100, 100, 100), infoGUI.getContentPane().getBackground());
        assertEquals(new Color(150, 150, 150), infoGUI.getTextAreaBackgroundColor());
        assertEquals(new Color(0, 0, 0), infoGUI.getTextAreaForegroundColor());
        assertEquals(new Color(0, 0, 0), infoGUI.getLabelTitleTipsColor());
    }

}

