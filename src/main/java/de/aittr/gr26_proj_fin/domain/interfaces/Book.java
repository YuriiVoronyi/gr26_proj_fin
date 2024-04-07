package de.aittr.gr26_proj_fin.domain.interfaces;

public interface Book {
    int getId();
    String getName();
    void setName(String name);
    double getPrice();
    String getPathimg();
    String getAuthor();
    boolean isIs_active();
    void setIs_active(boolean active);
    void setId(int id);
}
