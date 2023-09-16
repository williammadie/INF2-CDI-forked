package dev.miage.inf2.course.cdi.model;

/**
 * An instance of a particular physical candy
 *
 * @param flavor flavor of the candy
 * @param weight weight of the candy
 * @param id identifier of the candy
 */
public class Candy {
    private static final int G_TO_KG_METRIC = 1000;
    private String flavor;
    private int weight;
    private String id;

    public Candy(String flavor, int weight, String id) {
        this.flavor = flavor;
        this.weight = weight;
        this.id = id;
    }

    public double getWeightInKg() {
        return ((double) this.weight / G_TO_KG_METRIC);
    }

    public String id() {
        return this.id;
    }

    public String flavor() {
        return flavor;
    }

    public int weight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
