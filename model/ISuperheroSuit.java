package model;

public interface ISuperheroSuit {
    String getSuitId();
    String getSuitType();
    int getDurability();
    void repair();
    boolean isValid();
}
