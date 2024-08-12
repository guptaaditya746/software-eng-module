package de.buw.se.frontend.utils;

public class CheckInPuts {

    protected boolean isLargeInput(String input){
        if(input.length()<5){
            return false;
        }
        return true;
    }


}
