/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package regret2;

/**
 *
 * @author coolg
 */
public class Loot {
    /*loot quality
    0- common and uncommon
    1- unc and rare
    2- rare and epic
    3- epic and legend
    4- legend
*/

    
    public enum Rarity { //attach a damage mod for each rarity
        NONE(0.0), CONSUMABLE(0.0), COMMON(1.0), UNCOMMON(1.5), RARE(2.0), EPIC(2.5), LEGENDARY(3.0) ;
        
        private double mod ;
        
        Rarity(Double mod) {
            this.mod = mod ;
        }

        /**
         * @return the mod
         */
        public double getMod() {
            return mod;
        }
    }
}
