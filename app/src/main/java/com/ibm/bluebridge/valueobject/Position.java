package com.ibm.bluebridge.valueobject;

/**
 * Created by manirm on 3/13/2016.
 */
public class Position {

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public int getVacancies() {
        return vacancies;
    }

    public void setVacancies(int vacancies) {
        this.vacancies = vacancies;
    }

    private String posName;
    private String skills;
    private int vacancies;

}
