/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Obj;

/**
 *
 * @author Revan
 */
public class WeatherInfo extends GeoInfo  {
    private String Date;
    private String ConditionData;
    private String Umidity;
    private String Wind;
    private String Temp;
    private String IconData;

    public String getIconData() {
        return IconData;
    }

    public void setIconData(String iconData) {
        IconData = iconData;
    }

    public WeatherInfo() {
    }

    public String getConditionData() {
        return ConditionData;
    }

    public String getDate() {
        return Date;
    }



    public String getUmidity() {
        return Umidity;
    }

    public String getWind() {
        return Wind;
    }

    public void setConditionData(String ConditionData) {
        this.ConditionData = ConditionData;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }


    public void setUmidity(String Umidity) {
        this.Umidity = Umidity;
    }

    public void setWind(String Wind) {
        this.Wind = Wind;
    }

    public String getTemp() {
        return Temp;
    }

    public void setTemp(String Temp) {
        this.Temp = Temp;
    }

    @Override
    public String toString() {
        return super.toString()+" "+ConditionData+" "+Date+" "+" "+Temp+" "+Umidity+" "+Wind;
    }
    
    
}
