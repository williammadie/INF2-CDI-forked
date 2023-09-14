package dev.miage.inf2.course.cdi.model;

/**
 * An instance of a particular physical candy
 *
 * @param flavor flavor of the candy
 * @param weight weight of the candy
 * @param id identifier of the candy
 */
public record Candy(String flavor, int weight, String id) {
}
