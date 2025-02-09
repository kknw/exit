package model;

public class SuperheroSuit implements ISuperheroSuit {
    private String suitId;
    private String suitType;
    private int durability;

    public SuperheroSuit(String suitId, String suitType, int durability) {
        this.suitId = suitId;
        this.suitType = suitType;
        this.durability = durability;
    }

    @Override
    public String getSuitId() { return suitId; }

    @Override
    public String getSuitType() { return suitType; }

    @Override
    public int getDurability() { return durability; }

    @Override
    public void repair() {
        durability = Math.min(100, durability + 25);
    }

    @Override
    public boolean isValid() {
        if (suitType.equals("Power Suit") && durability < 70) return false;
        if (suitType.equals("Stealth Suit") && durability < 50) return false;
        if (suitType.equals("Disguise Suit") && (durability % 10 == 3 || durability % 10 == 7)) return false;
        return true;
    }
}
