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
public class Cell {
    
    //enemy density- 0-2, with the numbers being how many other people/squads, player count permitting
    public enum POI { //enum for all the locations (name, lootQuality, enemyDensity, vehicle y/n, water)
        A1("Ocean",0,0,0,1,5),A2("Coral Cove", 0, 0, 0, 1,8),A3("Discovery Dish",0,0,1,0,6),A4("Fancy View",0,0,0,1,10),A5("Viking Vessel",0,0,1,1,8),A6("Ocean",0,0,0,1,5),A7("Ocean",0,0,0,1,5),A8("Ocean",0,0,0,1,5),
        B1("The Shark",0,0,1,1,15),B2("Coral Castle",0,0,0,1,10),B3("Beleiver Beach",0,0,1,1,12),B4("Junction",0,0,0,0,0),B5("Holly Hedges",0,0,1,0,9),B6("Base Domino",0,0,1,0,6),B7("Dampy Dish",0,0,1,1,8),B8("Flushed Building",0,0,1,1,10),
        C1("Fort Crumpet",0,0,1,1,8),C2("Crash Site",0,0,0,0,7),C3("River",0,0,0,1,3),C4("Field",0,0,0,0,0),C5("The Durrr Burger",0,0,1,0,6),C6("Weeping Woods",0,0,1,1,20),C7("Sludgy Swamp",0,0,1,1,16),C8("Ocean",0,0,0,1,5),
        D1("Stealthy Stronghold",0,0,0,0,25),D2("Pleasant Park",0,0,1,0,12),D3("Base Rio",0,0,1,0,8),D4("Boney Burbs",0,0,1,0,18),D5("River",0,0,0,1,3),D6("Hydro 16",0,0,0,1,7),D7("Bend",0,0,0,0,1),D8("Lonely Isle",0,0,0,1,10),
        E1("Craggy Cliffs",0,0,1,0,12),E2("Blue Steel Bridge",0,0,1,1,1),E3("Risky Reels",0,0,1,0,8),E4("The Pyramid",0,0,0,0,21),E5("Defiant Dish",0,0,1,0,15),E6("Lazy Lake Island",0,0,0,1,14),E7("Misty Meadows",0,0,1,1,15),E8("Apres Ski",0,0,0,0,20),
        F1("Ocean",0,0,0,1,5),F2("Dinky Dish",0,0,1,0,8),F3("Corny Crops",0,0,1,0,22),F4("Green Steel Bridge",0,0,1,1,1),F5("Gas N' Grub",0,0,1,0,4),F6("Lazy Lake",0,0,1,1,10),F7("Yellow Steel Bridge",0,0,1,1,2),F8("Destined Dish",0,0,1,0,8),
        G1("Ocean",0,0,0,1,5),G2("Steamy Stacks",0,0,1,0,15),G3("The Orchard",0,0,1,1,19),G4("Destroyed Dish",0,0,1,0,13),G5("Retail Row",0,0,1,0,8),G6("Catty Corner",0,0,1,0,14),G7("Base Camp Golf",0,0,0,0,4),G8("Camp Cod",0,0,0,1,25),
        H1("Bob's Bluff",0,0,0,1,8),H2("Big Rig",0,0,0,1,2),H3("Base Roger",0,0,1,0,8),H4("Dirty Docks",0,0,1,1,16),H5("Brutus Basin",0,0,0,1,6),H6("Base camp Hotel",0,0,0,0,7),H7("Camp I can't find the name",0,0,0,0,7),H8("Ocean",0,0,0,1,5) ;
      
        
        
        private String name ;
        private int lootQuality ;
        private int enemyDensity ;
        private int vehicle ;
        private int water ;
        private Rarity rarity ;
        private ArrayList<Item> loot ; //dont fill this until necessary
        private boolean lootPopulated ;
        private int hideI ; // score 0-25. higher means it's easier to hide
        private boolean storm ;
        private boolean enemy ;
        private Enemy enemyPlayer ;
        
        POI(String name, int lootQuality, int enemyDensity, int vehicle, int water, int hideI) {
            Random random = new Random() ;
            this.name = name ;
            this.lootPopulated = false ;
            this.loot = new ArrayList() ;
            this.storm = false ;
            this.enemy = false ;
            if (this.name.equalsIgnoreCase("Ocean")){ //no loot here
                this.rarity = Rarity.NONE ;
            }
            else { //give random loot quality
                this.rarity = rarity.values()[new Random().nextInt(rarity.values().length)];
            }
            
            this.enemyDensity = enemyDensity + random.nextInt(3);
            this.vehicle = vehicle ;
            this.water = water ;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @return the lootQuality
         */
        public int getLootQuality() {
            return lootQuality;
        }

        /**
         * @return the enemyDensity
         */
        public int getEnemyDensity() {
            return enemyDensity;
        }

        /**
         * @return the vehicle
         */
        public int getVehicle() {
            return vehicle;
        }

        /**
         * @return the water
         */
        public int getWater() {
            return water;
        }

        /**
         * @return the rarity
         */
        public Rarity getRarity() {
            return rarity;
        }

        /**
         * @return the loot
         */
        public ArrayList<Item> getLoot() {
            return loot;
        }

        /**
         * @return the lootPopulated
         */
        public boolean isLootPopulated() {
            return lootPopulated;
        }

        /**
         * @param lootPopulated the lootPopulated to set
         */
        public void setLootPopulated(boolean lootPopulated) {
            this.lootPopulated = lootPopulated;
        }

        /**
         * @param rarity the rarity to set
         */
        public void setRarity(Rarity rarity) {
            this.rarity = rarity;
        }

        /**
         * @return the hideI
         */
        public int getHideI() {
            return hideI;
        }

        /**
         * @return the storm
         */
        public boolean isStorm() {
            return storm;
        }

        /**
         * @param storm the storm to set
         */
        public void setStorm(boolean storm) {
            this.storm = storm;
        }

        /**
         * @return the enemy
         */
        public boolean isEnemy() {
            return enemy;
        }

        /**
         * @param enemy the enemy to set
         */
        public void setEnemy(boolean enemy) {
            this.enemy = enemy;
        }

        /**
         * @return the enemyPlayer
         */
        public Enemy getEnemyPlayer() {
            return enemyPlayer;
        }

        /**
         * @param enemyPlayer the enemyPlayer to set
         */
        public void setEnemyPlayer(Enemy enemyPlayer) {
            this.enemyPlayer = enemyPlayer;
        }
        
    }
}
