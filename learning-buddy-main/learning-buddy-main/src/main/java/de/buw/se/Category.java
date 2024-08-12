package de.buw.se;

public class Category {
    protected Integer category_id;
    protected String name;
    protected String study_information;

    public Category(Integer category_id, String name, String study_information) {
        if (name == null || name.trim().isEmpty() || study_information == null || study_information.trim().isEmpty()) {
            throw new IllegalArgumentException("Name and Study Information cannot be null or empty");
        }
        this.category_id = category_id;
        this.name = name;
        this.study_information = study_information;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public String getStudy_information() {
        return study_information;
    }

    public void setStudy_information(String study_information) {
        if (study_information == null || study_information.trim().isEmpty()) {
            throw new IllegalArgumentException("Study Information cannot be null or empty");
        }
        this.study_information = study_information;
    }

    public Integer getCategory_id() {
        return category_id;
    }
}