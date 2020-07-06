package com.farm.buddy.traits;

public class Soil {
    private int moisture;
    private int percolation = 6;

    public int getPercolation() {
        return percolation;
    }

    public void setPercolation(int percolation) {
        this.percolation = percolation;
    }

    public int getMoisture() {
        return moisture;
    }

    public void setMoisture(int moisture) {
        this.moisture = moisture;
    }
}
