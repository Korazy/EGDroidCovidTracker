package com.korazy.covidtracker.Model;

public class Country {

    private String newCases;
    private int activeCases;
    private int criticalCases;
    private int recoveredCases;
    private int totalCases;
    private String newDeaths;
    private int totalDeaths;

    public String getNewCases() {
        return newCases;
    }

    public void setNewCases(String newCases) {
        this.newCases = newCases;
    }

    public int getActiveCases() {
        return activeCases;
    }

    public void setActiveCases(int activeCases) {
        this.activeCases = activeCases;
    }

    public int getCriticalCases() {
        return criticalCases;
    }

    public void setCriticalCases(int criticalCases) {
        this.criticalCases = criticalCases;
    }

    public int getRecoveredCases() {
        return recoveredCases;
    }

    public void setRecoveredCases(int recoveredCases) {
        this.recoveredCases = recoveredCases;
    }

    public int getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(int totalCases) {
        this.totalCases = totalCases;
    }

    public String getNewDeaths() {
        return newDeaths;
    }

    public void setNewDeaths(String newDeaths) {
        this.newDeaths = newDeaths;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(int totalDeaths) {
        this.totalDeaths = totalDeaths;
    }


}
