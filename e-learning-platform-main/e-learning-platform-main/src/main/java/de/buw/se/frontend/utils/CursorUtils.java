package de.buw.se.frontend.utils;

import javafx.scene.Cursor;
import javafx.scene.Node;

public class CursorUtils {

    public static void setPointerCursor(Node node) {
        node.setOnMouseEntered(event -> node.setCursor(Cursor.HAND));
        node.setOnMouseExited(event -> node.setCursor(Cursor.DEFAULT));
    }
}
