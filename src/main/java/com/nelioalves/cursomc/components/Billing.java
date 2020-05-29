package com.nelioalves.cursomc.components;

public class Billing
{
    private String number;

    private String country;

    private String city;

    private String street;

    private String useShippingAddress;

    private String district;

    private String postalCode;

    private String state;

    private String complement;

    public String getNumber ()
    {
        return number;
    }

    public void setNumber (String number)
    {
        this.number = number;
    }

    public String getCountry ()
    {
        return country;
    }

    public void setCountry (String country)
    {
        this.country = country;
    }

    public String getCity ()
    {
        return city;
    }

    public void setCity (String city)
    {
        this.city = city;
    }

    public String getStreet ()
    {
        return street;
    }

    public void setStreet (String street)
    {
        this.street = street;
    }

    public String getUseShippingAddress ()
    {
        return useShippingAddress;
    }

    public void setUseShippingAddress (String useShippingAddress)
    {
        this.useShippingAddress = useShippingAddress;
    }

    public String getDistrict ()
    {
        return district;
    }

    public void setDistrict (String district)
    {
        this.district = district;
    }

    public String getPostalCode ()
    {
        return postalCode;
    }

    public void setPostalCode (String postalCode)
    {
        this.postalCode = postalCode;
    }

    public String getState ()
    {
        return state;
    }

    public void setState (String state)
    {
        this.state = state;
    }

    public String getComplement ()
    {
        return complement;
    }

    public void setComplement (String complement)
    {
        this.complement = complement;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [number = "+number+", country = "+country+", city = "+city+", street = "+street+", useShippingAddress = "+useShippingAddress+", district = "+district+", postalCode = "+postalCode+", state = "+state+", complement = "+complement+"]";
    }
}
