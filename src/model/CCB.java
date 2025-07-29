package model;

import model.persistence.CCBFile;

public class CCB {
    private static CCB instance;
    //Atrib
    private double coveredTrayCost;
    private String startDate, endDate;
    //Builders
    private CCB(double value, String startDate, String endDate) {
        this.coveredTrayCost = value;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public static synchronized void init(double value, String startDate, String endDate) {
        if (instance == null) {
            instance = new CCB(value, startDate, endDate);
        }
    }
    public static synchronized void loadFromFile(){
        if (instance == null) {
            CCBFile CCBfile = new CCBFile();
            CCBfile.readCCB();
        }
    }
    public static synchronized CCB getInstance() {
        if (instance == null) {
            throw new IllegalStateException("CCB has not been initialized yet.");
        }
        return instance;
    }
    public static synchronized void clearInstance() {
        instance = null;
    }
    //Getters
    public double getValueCCB(){return coveredTrayCost;}   
    public String getStartDate(){return startDate;}
    public String getEndDate(){return endDate;}
    public double getRateByType(String userType) {
        switch (userType.toLowerCase()) {
            case "estudiante":
                return coveredTrayCost * 0.25;                              // rango de 20% a 30%
            case "profesor":
                return coveredTrayCost * 0.85;                              // promedio entre 70%-90%
            case "empleado":
                return coveredTrayCost * 1.00;                              // promedio entre 90%-110%
            default:
                return coveredTrayCost;                                     // precio base si no se reconoce
        }
    }
    //Setters
    public void setValueCCB(double value){this.coveredTrayCost =value;}
    public void setStartDate(String startDate){this.startDate =startDate;}
    public void setEndDate(String endDate){this.endDate =endDate;}
}
