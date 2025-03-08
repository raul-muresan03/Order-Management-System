package org.example.Model;

public class Products {
    private int id;
    private String name;
    private int stoc;

    public Products() {

    }

    public Products(String name, int stoc) {
        this.stoc = stoc;
        this.name = name;
    }

    public Products(int id, String name, int stoc) {
        this.id = id;
        this.stoc = stoc;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStoc() {
        return stoc;
    }

    public void setStoc(int stoc) {
        this.stoc = stoc;
    }
}
