/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package regret2;

import java.util.ArrayList;
import regret2.Cell.POI;

/**
 *
 * @author coolg
 */
public class Player {
    //invnentory array list of items
    //ammo arrayList
    //health
    //shield
    //coords
    private int x ;
    private int y ;
    private int health ;
    private int shield ;
    private ArrayList<Item> inv ;
    private int ammo ;
    private int hideI ; //hide index, 0-100. base level = 25
    private boolean crouch ;
    private boolean hide ;
    
    Player() {
        this.health = 100 ;
        this.shield = 0 ;
        this. inv = new ArrayList() ;
        this.ammo = 0 ;
        this.hideI = 25 ;
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
    public void increaseHealth(int health) {
        this.health = this.health + health;
        if (this.health > 100) { //if you heal too much
            this.health = 100 ;
        }
    }
    public void setHealth (int health) {
        this. health = health ;
    }
    /**
     * @return the shield
     */
    public int getShield() {
        return shield;
    }
    public void setShield(int shield) {
        this.shield = shield ;
    }
    /**
     * @param shield the shield to set
     */
    public void IncreaseShield(int shield) {
        this.shield = this.shield + shield;
        if (this.shield > 100) { //if you shield too much
            this.shield = 100 ;
        }
    }

    /**
     * @return the inv
     */
    public ArrayList<Item> getInv() {
        return inv;
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
    public void increaseAmmo(int ammo) {
        this.ammo = this.ammo + ammo;
    }
    
    public boolean checkFull () {
        if (this.inv.size() == 5) {
            return true;
        }
        else return false ;
    }
    public void printInv () {
        System.out.println("0. Exit");
        for (Item i : this.inv) {
            System.out.print(this.inv.indexOf(i)+1 + ". ");
                i.printName() ;
            }
        System.out.println("Ammo Count: " + this.getAmmo());
    }

    /**
     * @return the hideI
     */
    public int getHideI() {
        return hideI;
    }

    /**
     * @param hideI the hideI to set
     */
    public void updateHideI(POI currentCell) {
        this.hideI = 25 ;
        this.hideI += currentCell.getHideI() ;
        if (this.isCrouch()) {
            this.hideI += 25 ;
        }
        if (this.isHide()) {
            this.hideI += 25 ;
        }
    }

    /**
     * @return the crouch
     */
    public boolean isCrouch() {
        return crouch;
    }

    /**
     * @param crouch the crouch to set
     */
    public void setCrouch(boolean crouch, POI currentCell) {
        this.crouch = crouch;
        this.updateHideI(currentCell);
        if (crouch) {
            
            Game.clearScr() ;
            System.out.println("You are now Crouching");
            Game.pause();
        }
        else { //uncrouching
            Game.clearScr() ;
            System.out.println("You are no longer Crouching");
            Game.pause();
        }
        
    }

    /**
     * @return the hide
     */
    public boolean isHide() {
        return hide;
    }

    /**
     * @param hide the hide to set
     */
    public void setHide(boolean hide, POI currentCell) {
        this.hide = hide;
        this.updateHideI(currentCell);
        if (hide) {
            
            Game.clearScr() ;
            System.out.println("You are now Hidden");
            Game.pause();
        }
        else { //uncrouching
            Game.clearScr() ;
            System.out.println("You are no longer Hidden");
            Game.pause();
        }
    }
        
}
