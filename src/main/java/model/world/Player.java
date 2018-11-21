package main.java.model.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {

    private int id;
    private int lastTurnBonusUnits;
    private List<Country> conqueredCountries;
    private List<Continent> conqueredContinents;

    //TODO : logic and order of calculating bonus armies ( next & current)
    public Player(int id) {
        this.id = id;
        lastTurnBonusUnits = 0;
        conqueredContinents = new ArrayList<>();
        conqueredCountries = new ArrayList<>();
        //2 * conqueredContinents.size() + max(3, floor(conqueredCountries.size() / 3) + lastTurnBonusUnits)
    }

    public int getId() {
        return id;
    }

    public int getlastTurnBonusUnits() {
        return lastTurnBonusUnits;
    }

    public List<Country> getConqueredCountries() {
        return conqueredCountries;
    }

    public List<Continent> getConqueredContinents() {
        return conqueredContinents;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setlastTurnBonusUnits(int bonusUnits) {
        this.lastTurnBonusUnits = bonusUnits;
    }

    public void setConqueredCountries(List<Country> conqueredCountries) {
        this.conqueredCountries = conqueredCountries;
    }

    public void setConqueredContinents(List<Continent> conqueredContinents) {
        this.conqueredContinents = conqueredContinents;
    }


    public Country getMostFortifiedCountry(){
        Country mostFortifiedCountry =  this.getConqueredCountries().get(0);
        int maxUnits = mostFortifiedCountry.getUnits();

        for (Country c:this.getConqueredCountries()){
            if(c.getUnits()>maxUnits){
                mostFortifiedCountry = c;
                maxUnits = c.getUnits();
            }
        }
        return mostFortifiedCountry;
        // loop and get the max number of armies in countries
    }

    public Country getWeakestCountry(){
        Country weakestCountry =  this.getConqueredCountries().get(0);
        int minUnits = weakestCountry.getUnits();

        for (Country c:this.getConqueredCountries()){
            if(c.getUnits()<minUnits){
               weakestCountry = c;
               minUnits = c.getUnits();
            }
        }
        return weakestCountry;
    }

    public Continent getNearestConqueredContinent(){
        // TODO
        return null;
    }

    public int getTurnBonus(){
        Double bonus =  2 * conqueredContinents.size() +
                Math.max(3, Math.floor(conqueredCountries.size() / 3) + lastTurnBonusUnits);
        return bonus.intValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return id == player.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    //TODO implement / decide : canAttack in Player Class or State Class
    public boolean canAttack(Country country){

        return false;
    }

    //TODO implement / discuss logic
    public void attack(Country country){

    }
}
