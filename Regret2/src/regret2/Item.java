/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package regret2;

import java.util.Random;
import static regret2.Game.pause;
import regret2.Loot.Rarity;

/**
 *
 * @author coolg
 */
public class Item {
    protected Rarity rarity ; //rarity of item
   
    private Consume name ;
    Random random = new Random() ;
    
    public enum Consume {
        AMMO(0,0), BANDAGE(15, 0), MEDKIT(100,0), BIG_SHIELD(0,50), SMALL_SHIELD(0,25) ;
        
        private int health ;
        private int shield ;
        
        Consume(int health, int shield) {
            this.health = health ;
            this.shield = shield ;
        }
        public int getHealth() {
            return this.health ;
        }
        public int getShield() {
            return this.shield ;
        }
    }
    
    
    Item () {
        rarity = Rarity.CONSUMABLE ;
        this.name = Consume.values()[new Random().nextInt(Consume.values().length)] ; //set to random consumable item
        
              
    }

    
    public void printName() {
        
            System.out.println(this.getName().toString());
        
    }
    public void printDesc () {
        int tempShield = this.getName().getShield() ;
        int tempHealth = this.getName().getHealth() ;
        if (tempShield > 0) { //check if item is a shield
            System.out.println("Restores " + tempShield + " Shield.");
        }
        else {//if medkit/bandages
            System.out.println("Restores " + tempHealth + " Health.");
        }
        System.out.println("Press 0 to exit, 1 to use, or 2 to destroy");
        
    }

    /**
     * @return the rarity
     */
    public Rarity getRarity() {
        return rarity;
    }

    /**
     * @return the name
     */
    public Consume getName() {
        return name;
    }
    public void useItem (Game game, Map map, Player player) {
        int tempShield = this.getName().getShield() ;
        int tempHealth = this.getName().getHealth() ;
        if (tempShield > 0) { //check if shield
            //check if at max
            if (player.getShield() == 100) {
                System.out.println("Shields Full");
                Game.pause();
                return ;
            }
            else { // not at max
                //then check what size
                if (tempShield == 50) { //big shield
                    player.IncreaseShield(50);
                    Game.clearScr(); 
                    game.tick(4, map, player) ;
                    System.out.println("Shield Increased by 50");
                    Game.pause() ;
                }
                else { //small shield
                    if (player.getShield() < 50) {
                        player.IncreaseShield(25);
                        Game.clearScr() ;
                        game.tick(2, map, player) ;
                        System.out.println("Health set to Max.Shield increased by 15");
                        Game.pause() ;
                   
                        if (player.getShield() > 50) { //if above 50, set back to 50
                            player.setShield(50) ;
                        }
                    }
                    else { //cant use small
                        System.out.println("Cannot use Shield");
                        Game.pause();
                        return ;
                    }
                }
                player.getInv().remove(this) ; //remove item from inventory if used
            }
        }
        else{//it's health
            //check what kind
            if (player.getHealth() != 100) { //usable
                if (tempHealth > 15) { //medkit
                    player.setHealth(100);
                    Game.clearScr() ;
                    System.out.println("Health set to Max.");
                    game.tick(5, map, player) ;
                    Game.pause() ;
                    return ;
                }
                else {//bandaid
                    //check if usable
                    if (player.getHealth() < 75) {
                        player.increaseHealth(15);
                        game.tick(3, map, player) ;
                        Game.clearScr() ;
                        System.out.println("Health increased by 15.");
                        Game.pause() ;
                        
                        if (player.getHealth() > 75) {
                            player.setHealth(75); //reset to 75
                            
                        }
                    }
                    else {
                        System.out.println("Not usable.");
                        Game.pause();
                        return ;
                    }
                }
                player.getInv().remove(this) ; //remove if used
            }
            else {//at full
                System.out.println("Health is Full, Stupid.");
                Game.pause() ;
                return ;
            }
            
        }
    }
}
