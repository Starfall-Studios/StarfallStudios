package es.starfallstudios.fallenlegends.models;

import androidx.lifecycle.MutableLiveData;

import org.osmdroid.util.GeoPoint;

import java.util.HashMap;

import es.starfallstudios.fallenlegends.R;
import es.starfallstudios.fallenlegends.viewmodels.GameViewModel;

public class Creature extends MapEntity {

    public static enum CreatureType {
        FIRE,
        ROCK,
        ELECTRIC,
        WATER,
        FAIRY,
        NONE
    }

    public static enum BaseCreatures {
        NIGHTMIRE,
        FROSTBITE,
        GRYPHIX,
        LUMINO,
        THUNDERWING,
        NONE
    }

    private String name;
    private int experience;
    private int health;
    private int attack;
    private int defense;
    private int stamina;
    private int cost;
    private boolean inDeck;
    private CreatureType type;
    private BaseCreatures baseCreature;

    private MutableLiveData<Integer> healthLD;

    private int id;

    private int resourceId;

    // If the creature is in a zone, this is the zone id
    private int zoneId;

    // When creature spawns in the world, it will be in a specific location
    private double latitude;
    private double longitude;

    public static Creature blankCreature() {
        return new Creature(BaseCreatures.NONE, 0, 0, 0, 0, 0, 0, CreatureType.NONE, true);
    }

    public static Creature randomCreature() {
        return new Creature(BaseCreatures.values()[(int) (Math.random() * BaseCreatures.values().length)], 0, 0, Utils.getRandomNumberInRange(50, 200), Utils.getRandomNumberInRange(50, 200), Utils.getRandomNumberInRange(50, 200), Utils.getRandomNumberInRange(10, 50), CreatureType.ELECTRIC, false);
    }

    /**
     * Constructor for creatures that are in a zone
     * @param name Name of the creature
     * @param id Id of the creature
     * @param zoneId Id of the zone where the creature is
     */
    public Creature(String name, int id, int zoneId, double latitude, double longitude) {
        super(name, Type.CREATURE, latitude, longitude);
        this.id = id;
        this.zoneId = zoneId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        resourceId = R.drawable.creature_nightmire;
    }

    public Creature(String name, int id, int experience, int health, int attack, int defense, int stamina, CreatureType type) {
        super(name, Type.CREATURE, 0, 0);
        this.name = name;
        this.id = id;
        this.experience = experience;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.stamina = stamina;
        this.type = type;
        resourceId = R.drawable.creature_nightmire;
    }

    public Creature(BaseCreatures baseCreature, int id, int experience, int health, int attack, int defense, int stamina, CreatureType type, boolean inDeck) {
        super(GameManager.getInstance().creatureNames.get(baseCreature), Type.CREATURE, 0, 0);
        this.baseCreature = baseCreature;
        this.name = GameManager.getInstance().creatureNames.get(baseCreature);
        this.id = id;
        this.experience = experience;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.stamina = stamina;
        this.type = type;
        this.inDeck = inDeck;
        resourceId = GameManager.getInstance().creatureResources.get(baseCreature);
    }

    /**
     * Gets the id of the creature
     * @return id of the creature
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the zone id of the creature
     * @return zone id of the creature
     */
    public int getZoneId() {
        return zoneId;
    }

    /**
     * Gets the experience of the creature
     * @return
     */
    public int getExperience() {
        return experience;
    }

    /**
     * Gets the health of the creature
     * @return
     */
    public int getHealth() {
        return health;
    }

    /**
     * Gets the attack of the creature
     * @return
     */
    public int getAttack() {
        return attack;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String toString() {
        return "Name: " + name + " | Health: " + health + " | Attack: " + attack + " | Defense: " + defense + " | Stamina: " + stamina + " | Type: " + type;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public CreatureType getType() {
        return type;
    }

    public int getCost() {
        return stamina;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public boolean isBlank() {
        return getType() == CreatureType.NONE;
    }

    public boolean isInDeck() {
        return inDeck;
    }

    public void addToDeck() {
        this.inDeck = true;
    }

    public void removeFromDeck() {
        this.inDeck = false;
    }

    public MutableLiveData<Integer> getHealthLD() {
        healthLD.setValue(health);
        return healthLD;
    }

    public BaseCreatures getBaseCreature() {
        return baseCreature;
    }

    public int getDefense() {
        return defense;
    }

    public int getStamina() {
        return stamina;
    }
}
