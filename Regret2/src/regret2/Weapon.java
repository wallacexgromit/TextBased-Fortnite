/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package regret2;

import java.util.Random;
import regret2.Loot.Rarity;

/**
 *
 * @author coolg
 */
public class Weapon extends Item {
    private double dmg ;
    private WeaponType type ;
    private String name ;
    public enum WeaponType{ //include base damage, effective range(1-4; close, medium, far, long), and usabilty (1-4, 4 being the easiest)
        ASSAULT_RIFLE(10,3,2), SHOTGUN(20,2,4), SMG(6,3,3), PISTOL(8,2,2), SNIPER(20,4,1), ROCKET(30,2,1) ;
        
        private int baseDmg ;
        private int range ;
        private int usability ;
        
        WeaponType(int dmg, int range, int usability) {
            this.baseDmg = dmg ;
            this.range = range ;
            this.usability = usability ;
        }

        /**
         * @return the baseDmg
         */
        public int getBaseDmg() {
            return baseDmg;
        }

        /**
         * @return the range
         */
        public int getRange() {
            return range;
        }

        /**
         * @return the usability
         */
        public int getUsability() {
            return usability;
        }
    }
    
    //when creating a weapon, take in the rarity enum, and randomly choose a weapon type
    Weapon(Rarity rarity) {
        type = WeaponType.values()[new Random().nextInt(WeaponType.values().length)]; //random type
        //modulate damage based on rarity
        dmg = type.getBaseDmg() * rarity.getMod() ;
        this.rarity = rarity ;
    }
    public void printName() {
        if (getRarity() != Rarity.CONSUMABLE && getRarity() != Rarity.NONE) {
            System.out.println(this.getRarity() + " " + this.getType());
        }
        
    }
    public void printDesc() {
        System.out.println("Dmg = " + this.getDmg() + ", Effective Range = " + this.getType().getRange() + ", Usability = " + this.getType().getUsability());
        System.out.println("Press 0 to exit, or 1 to destroy");
    }

    /**
     * @return the dmg
     */
    public double getDmg() {
        return dmg;
    }

    /**
     * @return the type
     */
    public WeaponType getType() {
        return type;
    }
    
}
