package RolePlayingGame;

import java.util.Random;

public abstract class Character implements Hero {
    private String name;
    private int health, gold, experience, dexterity, power;

    public Character(String name, int health, int gold, int experience, int dexterity, int power) {
        this.name = name;
        this.health = health;
        this.gold = gold;
        this.experience = experience;
        this.dexterity = dexterity;
        this.power = power;
    }

    @Override
    public int attack() { //  успех атаки зависит от Ловкости персонажа
        int hurt = 0; // ранения
        int healthRemain; // остаток здоровья после урона
        if (dexterity * 3 > getRandomValue()) {
            return power;
        } else if (dexterity * 2 > getRandomValue()) {
            return power * 2;
        } else {
            return 0;
        }
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getGold() {
        return gold;
    }

    public int getExperience() {
        return experience;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getPower() {
        return power;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void setPower(int power) {
        this.power = power;
    }

    private int getRandomValue() {
        return (int) (Math.random() + 6);
    }

    @Override
    public String toString() {
        return String.format("%s здоровье:%d", name, health);
    }
}
