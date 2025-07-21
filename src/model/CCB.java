package model;

public class CCB {
    //Atrib
    private double value;
    private String startDate , endDate;
    //Builders
    public CCB(double value, String startDate, String endDate){
        this.value = value;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    //Getters
    public double getValueCCB(){return value;}   
    public String getStartDate(){return startDate;}
    public String getEndDate(){return endDate;}
    //Setters
    public void setValueCCB(double value){this.value =value;}
    public void setStartDate(String startDate){this.startDate =startDate;}
    public void setEndDate(String endDate){this.endDate =endDate;}
}
