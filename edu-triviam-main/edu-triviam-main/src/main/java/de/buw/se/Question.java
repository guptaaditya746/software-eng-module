package de.buw.se;

public class Question {
    private String text;
    private String[] options;
    private String correctOption;
    private String hint;
    

    public Question(String text, String[] options, String correctOption) {
        this.text = text;
        this.options = options;
        this.correctOption = correctOption;
        
    }
    
    public Question(String text, String[] options, String correctOption, String hint) {
        this.text = text;
        this.options = options;
        this.correctOption = correctOption;
        this.hint = hint; // Hint for hard questions
    }
   

    public String getText() {
        return text;
    }

    public String[] getOptions() {
        return options;
    }

    public String getCorrectOption() {
        return correctOption;
    }

    public String getQuestion() {
        // Return the question text
        return this.text;
    }
    
    public String getHint() {
        return hint;
    }
}