package com.example.spede;

import java.io.Serializable;

public class Pelaaja implements Serializable, Comparable<Pelaaja> {

    private String nimi;
    private String pisteet;

    public Pelaaja(String n, String p) {
        this.nimi = n;
        this.pisteet = p;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getPisteet() {
        return pisteet;
    }

    public void setPisteet(String pisteet) {
        this.pisteet = pisteet;
    }


    @Override
    public int compareTo(Pelaaja pelaaja) {

        int i = Integer.parseInt(this.pisteet);
        int a = Integer.parseInt(pelaaja.getPisteet());

        if(i>a) {
            return -1;
        }
        if(i==a){
            return 0;
        }
        return 1;
    }
}
