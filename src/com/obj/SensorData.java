package com.obj;

import android.location.Location;

/**
 * Created by IntelliJ IDEA.
 * User: Revan
 * Date: 4/4/12
 * Time: 9:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class SensorData {
    double longitudine,latitudine,presiune,altitudine,viteza;
    float distantaParcursa;

    public float getDistantaParcursa() {
        return distantaParcursa;
    }

    public void setDistantaParcursa(float distantaParcursa) {
        this.distantaParcursa = distantaParcursa;
    }

    float orientare;

    public SensorData(double longitudine, double latitudine, double presiune, double altitudine, double viteza, float orientare,float distanta) {
        this.longitudine = longitudine;
        this.latitudine = latitudine;
        this.presiune = presiune;
        this.altitudine = altitudine;
        this.viteza = viteza;
        this.orientare = orientare;
        this.distantaParcursa=distanta;
    }


    public SensorData() {

    }

    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }

    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }

    public void setPresiune(double presiune) {
        this.presiune = presiune;
    }

    public void setAltitudine(double altitudine) {
        this.altitudine = altitudine;
    }

    public void setViteza(double viteza) {
        this.viteza = viteza;
    }

    public void setOrientare(float orientare) {
        this.orientare = orientare;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public double getPresiune() {
        return presiune;
    }

    public double getAltitudine() {
        return altitudine;
    }

    public double getViteza() {
        return viteza;
    }

    public float getOrientare() {
        return orientare;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "longitudine=" + longitudine +
                ", latitudine=" + latitudine +
                ", presiune=" + presiune +
                ", altitudine=" + altitudine +
                ", viteza=" + viteza +
                ", orientare=" + orientare +
                '}';
    }


    public float getDistanceInKm()
    {
        return distantaParcursa/1000;
    }

    public double getSpeedInKmh()
    {
        return viteza*3.6;
    }

    public boolean isEmpty()
    {
        return longitudine==0 && latitudine==0;
    }


}
