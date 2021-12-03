package de.othr.sw.mos.artistAgency.entities;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String country;
    private String city;
    private String districtCode;
    private String streetName;
    private int houseNumber;

    public Address() {
        // TODO: implement constructor
    }

    public Address (String country, String city, String districtCode, String streetName, int houseNumber) {
        setCountry(country);
        setCity(city);
        setDistrictCode(districtCode);
        setStreetName(streetName);
        setHouseNumber(houseNumber);
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public String getStreetName() {
        return streetName;
    }

    public int getHouseNumber() {
        return houseNumber;
    }
}
