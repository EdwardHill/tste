package com.nelioalves.cursomc.domain;

public class Shipping
{
    private String addressRequired;

    public String getAddressRequired ()
    {
        return addressRequired;
    }

    public void setAddressRequired (String addressRequired)
    {
        this.addressRequired = addressRequired;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [addressRequired = "+addressRequired+"]";
    }
}
