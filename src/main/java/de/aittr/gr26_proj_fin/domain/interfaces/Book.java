package de.aittr.gr26_proj_fin.domain.interfaces;

public interface Book {
    int getId();
    String getName();
    double getPrice();
    String getYear();
    String getPathimg();
    String getAuthor();
    String getIsbn();
    String getGenre();
    boolean isIs_active();
    void setIs_active(boolean active);
    void setId(int id);
}
