package lapr.project.data;

import lapr.project.model.Continent;
import lapr.project.model.Country;
import lapr.project.model.Location;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CountryStore {

    private List<Country> countryStore;

    public CountryStore(){
        countryStore= new ArrayList<>();
    }

    public void addCountry(Country country){
        countryStore.add(country);
    }

    public List<Country> getCountryStore() {
        return countryStore;
    }

    public String getContinentByCountry(String countryName){
        for (Country country:countryStore){
            if (country.getName().equals(countryName)){
                return country.getContinent().getName();
            }
        }
        return null;
    }
    public List <Country> importCountriesCSV () {
        List<Country> countries = new LinkedList<>();
        String path2 = "csvFiles\\countries.csv";
        File arquivoCSV2 = new File(path2);
        Scanner leitor2 = null;
        try {
            leitor2 = new Scanner(arquivoCSV2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert leitor2 != null;
        String lines2;
        leitor2.nextLine();
        while (leitor2.hasNextLine()) {
            lines2 = leitor2.nextLine();
            String[] text = lines2.split(",");
            Location location = new Location(text[6],text[7]);
            countries.add(new Country(new Continent(text[0]),location, text[3], text[1], text[2], Double.parseDouble(text[4]), text[5]));
        }
        return countries;
    }
    public void getCountriesFromDatabase(){
        CountryStoreDB countryStoreDB= new CountryStoreDB();
        this.countryStore= countryStoreDB.getAllCountries( new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword"));
    }
    public Country getCountryByName(String name){
        for (Country country:this.countryStore){
            if (country.getName().equals(name)){
                return country;
            }
        }
        return null;
    }
}
