package de.buw.se;

public class Question {
    Integer question_id;
    Integer category_id;
    String difficulty;
    String question_text;
    String[] answers;
    String right_answer;

    public Question(Integer question_id, Integer category_id, String difficulty, String question_text, String[] answers, String right_answer) {
        this.question_id = question_id;
        this.category_id = category_id;
        this.difficulty = difficulty;
        this.question_text = question_text;
        this.answers = answers;
        this.right_answer = right_answer;
    }
    public Integer getQuestionId() {
        return question_id;
    }

    public Integer getCategoryId() {
        return category_id;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getQuestion() {
        return question_text;
    }

    public String[] getAnswers() {
        return answers;
    }


    public String getRightAnswer() {
        return right_answer;
    }
}
