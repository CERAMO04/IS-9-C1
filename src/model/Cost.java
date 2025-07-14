package model;

public class Cost {
    private String type, category,name;
    private Double cost;

    public Cost(String type, String category,String name, Double cost){
        this.type = type;
        this.cost = cost;
        this.category = category;
        this.name = name;
    }
    /*Setters */
    public void setType(String type) {this.type = type;}
    public void setCategory(String category) {this.category = category;}
    public void setValue(Double cost) {this.cost = cost;}
    public void setName(String name) {this.name = name;}
    /*Getters */
    public Double getCost() {return this.cost;}
    public String getType() {return this.type;}
    public String getCategory() {return this.category;}
    public String getName() {return this.name;}
}
