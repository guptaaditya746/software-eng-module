package de.buw.se;

public class University {
    private String fieldOfInterest;
    private String program;
    private String city;
    private String name;
    private String GmatorGRERequirement;
    private String cgpaRequirement;

    public University(String fieldOfInterest, String program, String city, String name, String GmatorGRERequirement, String cgpaRequirement ) {
        this.fieldOfInterest = (fieldOfInterest != null) ? fieldOfInterest.toLowerCase() : null;
        this.program = program;
        this.city = city;
        this.name = name;
        this.GmatorGRERequirement = GmatorGRERequirement;
        this.cgpaRequirement = cgpaRequirement;
    }

    public String getFieldOfInterest() {
        return fieldOfInterest;
    }

    public void setFieldOfInterest(String fieldOfInterest)
    {
        this.fieldOfInterest = (fieldOfInterest != null) ? fieldOfInterest.toLowerCase() : null;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getGmatorGRERequirement() {
        return GmatorGRERequirement;
    }

    public void setGmatorGRERequirement(String GmatorGRERequirement) {
        this.GmatorGRERequirement = GmatorGRERequirement;
    }

    public String getcgpaRequirement() {
        return cgpaRequirement;
    }

    public void setCgpaRequirement(String cgpaRequirement) {
        this.cgpaRequirement = cgpaRequirement;
    }

    @Override
    public String toString() {
        return "University{" +
                "fieldOfInterest='" + fieldOfInterest + '\'' +
                ", program='" + program + '\'' +
                ", city='" + city + '\'' +
                ", name='" + name + '\'' +
                ", GmatorGRERequirement ='" + GmatorGRERequirement + '\'' +
                ", cgpaRequirement = '" + cgpaRequirement + '\''+
                '}';
    }
}
