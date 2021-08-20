package com.example.countryinfoapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Pais {
    private String nombreP;
    private String capitalP;
    private String url_P;
    private double [] coordenadasP;

    public Pais() {
    }

    public String getNombreP() {
        return nombreP;
    }

    public void setNombreP(String nombreP) {
        this.nombreP = nombreP;
    }

    public String getCapitalP() {
        return capitalP;
    }

    public void setCapitalP(String capitalP) {
        this.capitalP = capitalP;
    }

    public String getUrl_P() {
        return url_P;
    }

    public void setUrl_P(String url_P) {
        this.url_P = url_P;
    }

    public double[] getCoordenadasP() {
        return coordenadasP;
    }

    public void setCoordenadasP(double[] coordenadasP) {
        this.coordenadasP = coordenadasP;
    }
    public Pais(String npais, String nurl, double [] ncoordenadasPais, String ncapital) throws JSONException {
        nombreP = npais;
        url_P = nurl;
        coordenadasP=ncoordenadasPais;
        capitalP=ncapital;
    }
    public static ArrayList<Pais> JsonObjectsBuild(JSONObject datos) throws JSONException {
        ArrayList<Pais> paises = new ArrayList<>();

        JSONObject results=datos.getJSONObject("Results");
        JSONArray namesCountries=results.names();

        try {
            for (int i = 0; i < namesCountries.length(); i++) {

                String namebd= namesCountries.getString(i);
                JSONObject datosCountries=results.getJSONObject(namebd);
                String nombrePais=datosCountries.getString("Name");
                JSONObject countryCodes=datosCountries.getJSONObject("CountryCodes");
                String iso2=countryCodes.getString("iso2");
                String stringCapital;
                try {
                    JSONObject objcapital = datosCountries.getJSONObject("Capital");
                    stringCapital = objcapital.getString("Name");
                } catch (Exception er) {
                    System.out.println(er.getMessage() + " " + er.getCause());
                    stringCapital = "NoName";
                }

                JSONObject georectangle=datosCountries.getJSONObject("GeoRectangle");
                JSONArray geopt=datosCountries.getJSONArray("GeoPt");

                double [] datosRectangulo = new double[6];
                datosRectangulo[0]=georectangle.getDouble("West");
                datosRectangulo[1]=georectangle.getDouble("East");
                datosRectangulo[2]=georectangle.getDouble("North");
                datosRectangulo[3]=georectangle.getDouble("South");

                datosRectangulo[4]=geopt.getDouble(0);
                datosRectangulo[5]=geopt.getDouble(1);

                paises.add(new Pais(nombrePais,"http://www.geognos.com/api/en/countries/flag/"+iso2+".png",datosRectangulo, stringCapital));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return paises;
    }

}
