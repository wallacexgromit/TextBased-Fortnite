/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package regret2;

import java.util.Random;
import java.util.Scanner;
import regret2.Cell.POI;
import regret2.Loot.Rarity;
import java.lang.Character;
import java.util.ArrayList;
import regret2.Item.Consume;

/**
 *
 * @author coolg
 */
public class Game {

    private Scanner in;
    private char[] landing;
    private int playerCount;
    private Random random;
    private POI currentCell;
    private static int time;
    //variable for end space
    private int endX;
    private int endY;
    private int top;
    private int bottom;
    private int left;
    private int right;

    public void runGame(Map map) {
        in = new Scanner(System.in);
        random = new Random();
        System.out.println("Select Game Mode");
        System.out.println("1.Solo\n2.Duos\n3.Squads(3)");

        switch (in.nextInt()) {
            case 1:
                soloGame(map);
                break;
            case 2:
                duosGame();
                break;
            case 3:
                squadGame();
                break;
        }
    }

    public void soloGame(Map map) {
        Player player = new Player();
        System.out.println("Where We Dropping Bois? Please refer to the provided map. Enter letter then number, no spaces.");
        landing = (in.next().toCharArray());
        //set player starting coords
        player.setX((int) landing[0] - 'a');
        player.setY(Integer.parseInt(String.valueOf(landing[1])) - 1);
        //now set end space
        this.endX = random.nextInt(8);
        this.endY = random.nextInt(8);
        initStorm(map);

        //set playerCOunt
        playerCount = 100;

        gameLoop(player, map);
    }

    public static void duosGame() {

    }

    public static void squadGame() {

    }

    public void gameLoop(Player player, Map map) { //take in the player so we know which one we are dealing with, as well as the map
        //display description
        //display, ask for action

        while (player.getHealth() != 0 && playerCount > 1) {
            //clearScr(); 
            currentCell = map.getMap()[player.getY()][player.getX()];
            if (currentCell.isEnemy()) {
                if (currentCell.getEnemyPlayer().isAcquired()) {
                    //initiate combat loop
                    combat(currentCell, map, player);
                }
            }
            printDesc(player); //get a better description later

            printActions();
            int input = in.nextInt();
            if (player.isHide() && (input != 8 && input != 6 && input != 7 && input != 4)) {
                player.setHide(false, currentCell);
            }
            switch (input) {
                case 1: //move
                    clearScr();
                    move(map, player);
                    if (player.isCrouch()) {
                        tick(2, map, player);
                    } else {
                        tick(1, map, player);
                    }
                    break;
                case 2:
                    //loot
                    clearScr();
                    loot(map, player);
                    break;
                case 3:
                //attack when undetected
                    if (currentCell.isEnemy()) {
                        currentCell.getEnemyPlayer().setAcquired(true);
                        combat(currentCell, map, player) ;
                    }
                    else {//if no enemy exists
                        clearScr() ;
                        System.out.println("There is no enemy Nearby");
                        pause() ;
                    }
                    
                case 4:
                    //show map
                    clearScr();
                    map.showMap(player);
                    break;
                case 5:
                    //crouch- just increase stealth score
                    player.setCrouch(true, currentCell);
                    tick(1, map, player);
                    break;
                case 6:
                    //hide- again, just increase stealth score
                    player.setHide(true, currentCell);
                    tick(4, map, player);
                    break;
                case 7:
                    //inv
                    openInv(map, player);
                    break;
                case 8:
                    //wait
                    
                    clearScr();
                    
                    System.out.println("Time passes...");
                    tick(1, map, player);
                    pause();
                    break;
                default:
                    break;
            }
        }
        clearScr() ;
        System.out.println("You win!");
    }

    public void printDesc(Player player) {
        System.out.println(currentCell.getName()); //print location name
        //create another desc based on people,loot nerby, etc
        System.out.println("Loot Quality: " + currentCell.getRarity());
        //list player stats
        System.out.println("Health: " + player.getHealth());
        System.out.println("Shield: " + player.getShield());
        //check if crouched or hidden
        if (player.isCrouch()) {
            System.out.println("You are crouched.");
        }
        if (player.isHide()) {
            System.out.println("You are hidden.");
        }

        System.out.println("Player Count: " + playerCount);
        if (currentCell.isEnemy()) {
            System.out.println("There is an enemy here.");
            if (currentCell.getEnemyPlayer().isAcquired()) {
                System.out.println("He can see you.");

            } else {
                System.out.println("He does not know you are here.");

            }
            System.out.print("The enemy is " );
            switch(currentCell.getEnemyPlayer().getRange()) {
                case 1 ://close
                    System.out.println("Close Range");
                    break ;
                case 2 ://med
                    System.out.println("Medium Range");
                    break ;
                case 3 ://far
                    System.out.println("Far Away");
                    break ;
                case 4 ://long
                    System.out.println("Very Far Away");
                    break ;
            }
            pause();
        }

        //populate the loot if needed
        if (!currentCell.isLootPopulated()) { //if no starting loot, then add some
            addLoot(currentCell.getRarity(), player, false);
            currentCell.setLootPopulated(true); //make sure it doesn't get looted again
        }
    }

    public void loot(Map map, Player player) {

        //list loot in area- set to empty if you take all of it
        while (true) {
            if (!currentCell.getLoot().isEmpty()) { //if loot exists
                clearScr();
                System.out.println("0. Exit"); //press 0 to exit
                for (Item i : currentCell.getLoot()) { //printing out all the loot
                    System.out.print(currentCell.getLoot().indexOf(i) + 1 + ". ");
                    i.printName();
                }
                int temp = in.nextInt() - 1; //index of the item

                if (temp == -1) { //if exit
                    return;
                }
                //if we're not exiting, we can continue, since we want a loot item
                Item lootedItem = currentCell.getLoot().get(temp);
                //first, check if the item is ammo. If so, increase ammo count
                if (lootedItem.getName() == Consume.AMMO) {
                    player.increaseAmmo(3);
                    currentCell.getLoot().remove(lootedItem);
                    tick(1, map, player);
                    continue;
                }
                //otherwise, it's an item to be picked up
                if (!player.checkFull()) { //if inventory has space
                    player.getInv().add(lootedItem);
                    currentCell.getLoot().remove(lootedItem);
                    tick(2, map, player);
                    //check if weapon. If so, add ammo
                    if (lootedItem instanceof Weapon) {
                        player.increaseAmmo(5);
                    }

                    //check if that was the last item. if so, set loot to empty
                    if (currentCell.getLoot().isEmpty()) {
                        currentCell.setRarity(Rarity.NONE);
                    }
                } else { //inv is full
                    System.out.println("Inventory Is Full");
                }
            } else {
                System.out.println("No loot here.");
                pause();
                return;
            }

        }

    }

    public void addLoot(Rarity rarity, Player player, boolean enemy) {// fill in the arrayList will the approprite loot depending on the map tile/enemy gear- take a rarity enum and base loot off that
        if (enemy == false) { //populating a world space for the first time
            if (rarity == Rarity.NONE) {
                //do nothing... I guess this is bad code, but...
            } //add to POI arrayList a random amount of weapons/ammo/consumables based on the rarity
            else if (rarity == Rarity.CONSUMABLE) {
                int temp = random.nextInt(2) + 1; //add a rand amount
                for (int i = 0; i < temp; i++) {
                    currentCell.getLoot().add(new Item());
                }
            } else { //weapon space
                //25% chance to add some consumables
                int temp = random.nextInt(4);
                if (temp == 1) { //if we're doing consumables
                    temp = random.nextInt(2) + 1; //add a rand amount
                    for (int i = 0; i < temp; i++) {
                        currentCell.getLoot().add(new Item());

                    }
                }

                //always add weapons
                temp = random.nextInt(3) + 1;
                for (int i = 0; i < temp; i++) { //add 1-3 weapons to loot ArrayList of active Cell
                    currentCell.getLoot().add(new Weapon(rarity));
                }

            }
        } //if it's an enemy, do a similar thing, but limit it to 5
        else {
            int i;
            for (i = 0; i < 5; i++) {
                int temp = random.nextInt(4); //25% chance of consumable
                if (temp == 1) {
                    currentCell.getEnemyPlayer().getInv().add(new Item());
                } else {
                    currentCell.getEnemyPlayer().getInv().add(new Weapon(rarity));
                }
            }
        }
    }

    public ArrayList<Item> addLoot(Rarity rarity, Player player, boolean enemy, ArrayList<Item> inv) {// fill in the arrayList will the approprite loot depending on the map tile/enemy gear- take a rarity enum and base loot off that

        int i;
        for (i = 0; i < 5; i++) {
            int temp = random.nextInt(4); //25% chance of consumable
            if (temp == 1) {
                inv.add(new Item());
            } else {
                inv.add(new Weapon(rarity));
            }
        }
        return inv;
    }

    public void move(Map map, Player player) {
        System.out.println("Which direction do you want to move in? (N,S,E,W)");
        char input = Character.toLowerCase(in.next().charAt(0));
        if (input == 'n') {
            if (player.getY() > 0) { //not on the top row
                player.setY(player.getY() - 1);
            } else {
                System.out.println("invalid Movement");
                pause();
            }
        }
        if (input == 's') {
            if (player.getY() < 7) { //not on the top row
                player.setY(player.getY() + 1);
            } else {
                System.out.println("invalid Movement");
                pause();
            }
        }
        if (input == 'w') {
            if (player.getX() > 0) { //not on the top row
                player.setX(player.getX() - 1);
            } else {
                System.out.println("invalid Movement");
                pause();
            }
        }
        if (input == 'e') {
            if (player.getX() < 7) { //not on the top row
                player.setX(player.getX() + 1);
            } else {
                System.out.println("invalid Movement");
                pause();
            }
        }
    }

    public void openInv(Map map, Player player) {

        while (true) {
            //displaying inv and asking which item to inspect
            clearScr();
            player.printInv();
            System.out.println("Press a number to Inspect, or 0 to quit");
            int temp = in.nextInt() - 1;
            if (temp == -1) { // exit
                return;
            }

            //inspecting the item
            clearScr();
            player.getInv().get(temp).printDesc();

            int inspectInput = in.nextInt();
            if (inspectInput == 0) {
                continue;
            } else if (inspectInput == 1) {
                if (player.getInv().get(temp) instanceof Weapon) { //if weapon, destroy
                    player.getInv().remove(temp);
                    clearScr();
                    System.out.println("Item Destroyed.");
                    tick(2, map, player);
                    pause();
                    continue;
                } else {//if item, use
                    player.getInv().get(temp).useItem(this, map, player);

                }
            } else if (inspectInput == 2 && player.getInv().get(temp) instanceof Weapon == false) { //destroy item
                player.getInv().remove(temp); //destroy
                clearScr();
                System.out.println("Item Destroyed.");
                tick(1, map, player);
                pause();
                continue;
            }

        }
    }

    public void printActions() {

        System.out.println("1.Move\n2.Loot\n3.Attack\n4.Nothing\n5.Crouch\n6.Hide\n7.Inventory\n8.Wait");

    }

    public void count(int i) { //recalculates the number of players (subtracts a number based on number taken in)
        playerCount -= random.nextInt(i);
    }

    public static void clearScr() {
        for (int i = 0; i < 10; i++) {
            System.out.println("\n");
        }
    }

    public static void pause() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.out.println("It broker");
        }
    }

    public void initStorm(Map map) {
        if (endX == 0) {
            left = -1;
        } else {
            left = 0;
        }
        if (endX == 7) {
            right = -1;
        } else {
            right = 7;
        }
        if (endY == 0) {
            top = -1;
        } else {
            top = 0;
        }
        if (endY == 7) {
            bottom = -1;
        } else {
            bottom = 7;
        }
    }

    public void progressStorm(Map map) {
        int i, j;
        if (top != -1) {
            if (top != endY) {

                for (j = 0; j < 8; j++) {
                    map.getMap()[top][j].setStorm(true);
                }
                top++;
            }
        }
        if (bottom != -1) {
            if (bottom != endY) {

                for (j = 0; j < 8; j++) {
                    map.getMap()[bottom][j].setStorm(true);
                }
                bottom--;
            }
        }
        if (left != -1) {
            if (left != endX) {

                for (i = 0; i < 8; i++) {
                    map.getMap()[i][left].setStorm(true);
                }
                left++;
            }
        }
        if (right != -1) {
            if (right != endX) {

                for (i = 0; i < 8; i++) {
                    map.getMap()[i][right].setStorm(true);
                }
                right--;
            }
        }
    }

    public void tick(int count, Map map, Player player) {
        POI currentSpace = map.getMap()[player.getY()][player.getX()];
        Game.time += count; //count is based on the complexity of the task- for ex, destroying a weapon takes less time than drinking a big shield
        //update player hide Index
        player.updateHideI(currentSpace);
        //lose health if in storm on tick
        if (currentSpace.isStorm()) {
            player.setHealth(player.getHealth() - 10);
            System.out.println("You lost 10 Health because of the Storm. Get out of there!");
        }
        //decrease playerCount
        playerCount -= random.nextInt(count + 1) + 1;

        //every x ticks, progress the storm- test value will be 5
        if ((Game.time % 10) == 0) {
            progressStorm(map);
        }

        //every tick, check if an enemy will spawn in the current space (you don't need to check enemy count: the while loop the gameloop has ends when enemycount is 0)
        if (!currentSpace.isEnemy() && random.nextInt(50) + time / 3 > 75) { //assuming no enemy present; out of 100, if greater than 75, spawn an enemy
            currentSpace.setEnemyPlayer(new Enemy(player, this));
            currentSpace.setEnemy(true);
        }

        //also every tick, check to see if the enemy spots you. if so, initiate combat
        if (currentSpace.isEnemy()) {
            if (currentSpace.getEnemyPlayer().getSearchI() > player.getHideI()) {
                currentSpace.getEnemyPlayer().setAcquired(true);

            } 
            else {
                currentSpace.getEnemyPlayer().setAcquired(false);
            }
            if (!currentSpace.getEnemyPlayer().isAcquired()) { //chance for enemy to go away
                if(random.nextInt(10) > 6) { //30% for enemy to leave when hidden
                    currentSpace.setEnemy(false);
                    System.out.println("The enemy left");
                }
            }
        }
        
    }

    public static int getTime() {
        return Game.time;
    }

    public void combat(POI currentSpace, Map map, Player player) {
        Enemy enemy = currentSpace.getEnemyPlayer();
        //set random starting range 1-4 (close medium far long)
        enemy.setRange(random.nextInt(4) + 1);
        while (enemy.isAcquired() && enemy.getHealth() > 0 && player.getHealth() > 0) { //while enemy is alive and can see you. on death, add inv to current cell, and change rarity to enemy rarity
            //display a combat menu, with move and hide actions alongside attack and build actions (build decreases enemy hit chance)
            //combat menu stays active until enemy loses sight of you
            printDesc(player);
            displayCombatActions();
            int defense = 0;
            int input = in.nextInt();
            switch (input) { //after every action, enemy damage to you will be calculated
                case 1: //emote
                    clearScr();
                    //add a 5% chance for the enemy to disappear
                    tick(1, map, player);
                    break;
                case 2:
                    //attack
                    //ask which weapon you want to attack with, and do calculations. check if ammo first
                    if (player.getAmmo() == 0) {
                        clearScr();
                        System.out.println("No ammo!");
                        pause();
                        break;
                    }
                    clearScr() ;
                    playerAttack(player, enemy);
                    clearScr();
                    break;
                case 3:
                    //add a flat number to current defense
                    break;
                case 4:
                    //crouch
                    player.setCrouch(true, currentCell);
                    tick(1, map, player);
                    break;
                case 5:
                    //hide
                    //hide- again, just increase stealth score
                    player.setHide(true, currentCell);
                    tick(4, map, player);
                    break;

            }
            //after the player action, the enemy does their dmg, but check if dead
            if (enemy.getHealth() <= 0) {
                break;
            }
            if (!enemy.isAcquired()) {
                //System.out.println("testingtesting");
                break ;
                
            }
            Weapon enemyWeapon;
            while (true) {
                Item temp = enemy.getInv().get(random.nextInt(enemy.getInv().size())); //get random item from enemy inv
                if (temp instanceof Weapon) { //if item was a weapon
                    enemyWeapon = (Weapon) temp;
                    break;
                } else {//if it was an item, re roll
                    continue;
                }
            }
            int dmg = (int) dmgCalc(player, enemy, enemyWeapon); //enemy dmg
            //calc dmg to player
            if (player.getShield() - dmg >= 0) { //if shield can take the hit
                player.setShield(player.getShield() - dmg);
            } else if (player.getShield() - dmg < 0) { //if it can't
                player.setHealth((player.getHealth() + player.getShield()) - dmg);
            }
            clearScr();
            System.out.println(dmg + " damage dealt to you");
            pause();
        }
        //once out of while loop, either drop loot if dead, or do nothing. return in both cases.
         //remove enemy in both cases
        if (enemy.getHealth() <= 0) {
            System.out.println("The enemy has Fallen!");
            playerCount-- ;
            for (Item i : enemy.getInv()) {
                currentCell.getLoot().add(i);
                currentCell.setRarity(enemy.getRarity());
            }
            currentSpace.setEnemy(false);
            return;
        }
        System.out.println("You escaped the enemy.");
        return;
    }

    public void displayCombatActions() {
        //clearScr();
        System.out.println("1.Emote\n2.Use/Attack\n3.Build\n4.Crouch\n5.Hide");
        pause();
    }

    public double dmgCalc(Player player, Enemy enemy, Weapon weapon) { //returns damage done
        //if the range of the weapon used is equal to the current enemy range, do full damage. Otherwise, deduct dmg
        //add some sort of bonus/penalty for usablity
        if (weapon.getType().getRange() == enemy.getRange()) {
            return weapon.getDmg() + (weapon.getRarity().getMod() * weapon.getType().getUsability()); //if at proper range, give bonus
        } else { //not at optimal range
            return weapon.getDmg() - (weapon.getRarity().getMod() * weapon.getType().getUsability()); //deduct bonus
        }
    }

    public void playerAttack(Player player, Enemy enemy) {
        while (true) {
            System.out.println("0. Exit");
            for (Item i : player.getInv()) {
                if (i instanceof Weapon) { //print all the weapons
                    System.out.print(player.getInv().indexOf(i) + 1 + ". ");
                    i.printName();
                }
            }
            //print current ammo
            System.out.println("Ammo: " + player.getAmmo());
            int input = in.nextInt() - 1; //take in input
            if (input == -1) {
                return;
            }
            //depending on input, print that weapon's desc
            if (player.getInv().size() > 0) {
                Weapon playerWeapon = (Weapon) player.getInv().get(input);
                System.out.println("Dmg = " + playerWeapon.getDmg() + ", Effective Range = " + playerWeapon.getType().getRange() + ", Usability = " + playerWeapon.getType().getUsability());
                System.out.println("Press 0 to exit, or 1 to use");
                input = in.nextInt();
                switch (input) {
                    case 0:
                        break;
                    case 1:
                        int dmg = (int) dmgCalc(player, enemy, playerWeapon);
                        if (enemy.getShield() - dmg >= 0) { //if shield can take the hit
                            enemy.setShield(enemy.getShield() - dmg);
                        } else if (enemy.getShield() - dmg < 0) { //if it can't
                            enemy.setHealth(enemy.getHealth() + enemy.getShield() - dmg);
                        }
                        clearScr();
                        System.out.println(dmg + " damage dealt to enemy");
                        pause();
                        return;
                    default:
                        break;
                }
            } else {
                System.out.println("Inventory Empty");
                pause();
                clearScr();
                continue;
            }

        }

    }

}
