package es.starfallstudios.fallenlegends.models;

import java.util.ArrayList;

public class Player extends MapEntity {

    private GameManager gm;

    // Creatures owned by the player
    private CreatureCollection playerCreatureCollection;

    //User related variables
    private String username;
    private int userExperience;
    private String uid;
    private int gems;
    private int stone;
    private int wood;
    private int food;

    public Player(String uid, String username, int userExperience, int gems, int stone, int wood, int food) {
        super(username, Type.PLAYER, 0, 0);
        gm = GameManager.getInstance();
        playerCreatureCollection = new CreatureCollection(uid);
        this.uid = uid;
        this.username = username;
        this.userExperience = userExperience;
        this.gems = gems;
        this.stone = stone;
        this.wood = wood;
        this.food = food;
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

    public void setPlayerCreatureCollection(CreatureCollection playerCreatureCollection) {
        this.playerCreatureCollection = playerCreatureCollection;
    }

    public CreatureCollection getPlayerCreatureCollection() {
        return playerCreatureCollection;
    }

}
