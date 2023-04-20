package es.starfallstudios.fallenlegends.models;

import java.util.ArrayList;

public class Player {

    private GameManager gm;

    // Creatures that are in the player's inventory
    private ArrayList<Creature> playerCreatures;

    // Creatures that are in the player's deck (max 4)
    private Deck playerDeck;

    //User related variables
    private String username;
    private int userExperience;
    private String uid;
    private int gems;
    private int stone;
    private int wood;
    private int food;

    public static Player createPlayer(String uid, String username, int userExperience, int gems, int stone, int wood, int food) {
        return new Player(uid, username, userExperience, gems, stone, wood, food);
    }

    public Player(String uid, String username, int userExperience, int gems, int stone, int wood, int food) {
        gm = GameManager.getInstance();
        playerCreatures = new ArrayList<Creature>();
        playerDeck = new Deck();
        this.uid = uid;
        this.username = username;
        this.userExperience = userExperience;
        this.gems = gems;
        this.stone = stone;
        this.wood = wood;
        this.food = food;
    }

    public Player(String uid) {
        gm = GameManager.getInstance();
        playerCreatures = new ArrayList<Creature>();
        this.uid = uid;
        this.username = "TempUsername";
        this.userExperience = 0;
        this.gems = 0;
        this.stone = 0;
        this.wood = 0;
        this.food = 0;
        playerDeck = new Deck();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserExperience() {
        return userExperience;
    }

    public void setUserExperience(int userExperience) {
        this.userExperience = userExperience;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getGems() {
        return gems;
    }

    public void setGems(int gems) {
        this.gems = gems;
    }

    public int getStone() {
        return stone;
    }

    public void setStone(int stone) {
        this.stone = stone;
    }

    public int getWood() {
        return wood;
    }

    public void setWood(int wood) {
        this.wood = wood;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public void setDeck(Deck deck) {
        this.playerDeck = deck;
    }

    public Deck getDeck() {
        return playerDeck;
    }
}
