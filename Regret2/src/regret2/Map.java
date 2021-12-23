/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package regret2;

import regret2.Cell.POI;



/**
 *
 * @author coolg
 */
public class Map {
    private POI[][] map;
    Map() {
        int i=0,j=0 ;
        map = new POI[8][8] ;
        for (POI p : POI.values()) {
            map[i][j] = p ; //sets each element of array to a different POI
            i++ ;
            if (i==8) {
                j++ ;
                i=0 ;
            }
            if (j==8)
                break ;
        }
        
        
    }

    /**
     * @return the map
     */
    public POI[][] getMap() {
        return map;
    }
    public void showMap(Player player) {
        int i, j ;
        for (i=0;i<8;i++) {
            for (j=0;j<8;j++) {
                
                if (this.map[i][j].isStorm()) { //covered by storm
                    System.out.print(" S ");
                }
                else if (player.getX() == j && player.getY() == i) { //if the player is there
                    System.out.print(" X ");
                }
                else {//normal space
                    System.out.print(" " + this.map[i][j]+ " ");
                }
                
            }
            System.out.print("\n");
        }
    }
    
}
