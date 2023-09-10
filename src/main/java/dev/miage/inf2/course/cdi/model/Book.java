package dev.miage.inf2.course.cdi.model;

/**
 * An instance of a particular physical copy of a book
 *
 * @param author name of the book's author
 * @param title  title of the book
 * @param isbn   International Standard Book Number
 */
public record Book(String author, String title, String isbn) {
}
