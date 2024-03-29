package main.java.model.world;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Continent implements Comparable<Continent> {
    private int id;
    private List<Country> countries;
    private int continentBonus;

    public Continent(int id, int continentBonus) {
        this.id = id;
        this.continentBonus = continentBonus;
        this.countries = new ArrayList<>();
    }

    public List<Country> getCountries() {
        return this.countries;
    }

    public List<Country> getCountries(Comparator<Country> comparator) {
        return comparator == null ? this.countries : this.countries.stream().sorted(comparator).collect(Collectors.toList());
    }

    public Integer getId() {
        return id;
    }

    public void addCountry(Country country) {
        this.countries.add(country);
        country.setContinent(this);
    }

    public int getContinentBonus() {
        return continentBonus;
    }

    public boolean isConquered(Player player) {
        for (Country country : this.countries) {
            if (country.getOccupant().getId() != player.getId()) {
                return false;
            }
        }
        return true;
    }

    public int getSize() {
        return this.countries.size();
    }

    @Override
    public int compareTo(Continent continent) {
        return this.getId().compareTo(continent.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Continent)) return false;
        Continent continent = (Continent) o;
        return id == continent.id &&
                continentBonus == continent.continentBonus &&
                Objects.equals(countries.stream().map(Country::getId).collect(Collectors.toList()),
                        continent.countries.stream().map(Country::getId).collect(Collectors.toList()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, countries.stream().map(Country::getId).collect(Collectors.toList()), continentBonus);
    }
}
