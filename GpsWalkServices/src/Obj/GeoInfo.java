/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Obj;

/**
 *
 * @author Revan
 */
public class GeoInfo extends GpsPoint{

    private String Adress,Country,City,AdministrativeLevel1;

    
    public String getAdministrativeLevel1() {
        return AdministrativeLevel1;
    }

    public void setAdministrativeLevel1(String AdministrativeLevel1) {
        this.AdministrativeLevel1 = AdministrativeLevel1;
    }

    public String getCountry() {
        return Country;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getCity() {
        return City;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

   

    public GeoInfo() {
    }

    public String getAdress() {
        return Adress;
    }


    public void setAdress(String Adress) {
        this.Adress = Adress;
    }
    

    public String toString() {
        return super.toString()+" "+Adress+" "+City+" "+Country+" "+AdministrativeLevel1;
    }
    
}
