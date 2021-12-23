/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package regret2;

import java.util.ArrayList;
import java.util.Random;
import regret2.Loot.Rarity;

/**
 *
 * @author coolg
 */
public class Enemy {
    private int x ;
    private int y ;
    private int health ;
    private int shield ;
    private ArrayList<Item> inv ;
    private int searchI ; //search index: must be higher than player's hide index to find them. 0-100, base 25
    private int ammo ; //might not use
    private boolean acquired ; //if he can see you or not
    private Rarity rarity ;
    private int range ;
    
    Enemy(Player player, Game game) { //modify each variable depending on the tick number (more ticks = more shield)
        x = player.getX() ;
        y = player.getY() ;
        int time = Game.getTime() ;
        health = 100 - (time / 2) ;
        shield = 0 + (2 * time ) ;
        inv = new ArrayList() ;
        Random random = new Random() ;
        range = random.nextInt(4) + 1 ;
        if (time < 10) {
            inv = game.addLoot(Loot.Rarity.COMMON, player, true, inv);
            rarity = Loot.Rarity.COMMON ;
            searchI = 30 ;
        }
        else if (time >= 10 && time < 20) {
            inv = game.addLoot(Loot.Rarity.UNCOMMON, player, true, inv);
            rarity = Loot.Rarity.UNCOMMON ;
            searchI = 51 ;
        }
        else if (time >= 20 && time < 30) {
            inv = game.addLoot(Loot.Rarity.RARE, player, true, inv);
            rarity = Loot.Rarity.RARE ;
            searchI = 65 ;
        }
        else if (time >= 30 && time < 40) {
            inv = game.addLoot(Loot.Rarity.EPIC, player, true, inv);
            rarity = Loot.Rarity.EPIC ;
            searchI = 80 ;
        }
        else if (time >= 40) {
            inv = game.addLoot(Loot.Rarity.LEGENDARY, player, true, inv);
            rarity = Loot.Rarity.LEGENDARY ;
            searchI = 90 ;
        }
                
        
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * @param health the health to set
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * @return the shield
     */
    public int getShield() {
        return shield;
    }

    /**
     * @param shield the shield to set
     */
    public void setShield(int shield) {
        this.shield = shield;
    }

    /**
     * @return the inv
     */
    public ArrayList<Item> getInv() {
        return inv;
    }

    /**
     * @param inv the inv to set
     */
    public void setInv(ArrayList<Item> inv) {
        this.inv = inv;
    }

    /**
     * @return the searchI
     */
    public int getSearchI() {
        return searchI;
    }

    /**
     * @param searchI the searchI to set
     */
    public void setSearchI(int searchI) {
        this.searchI = searchI;
    }

    /**
     * @return the ammo
     */
    public int getAmmo() {
        return ammo;
    }

    /**
     * @param ammo the ammo to set
     */
    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    /**
     * @return the acquired
     */
    public boolean isAcquired() {
        return acquired;
    }

    /**
     * @param acquired the acquired to set
     */
    public void setAcquired(boolean acquired) {
        this.acquired = acquired;
    }

    /**
     * @return the range
     */
    public int getRange() {
        return range;
    }

    /**
     * @param range the range to set
     */
    public void setRange(int range) {
        this.range = range;
    }

    /**
     * @return the rarity
     */
    public Rarity getRarity() {
        return rarity;
    }
}
