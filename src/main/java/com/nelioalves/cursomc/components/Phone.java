package com.nelioalves.cursomc.components;

public class Phone
{
    private String number;

    private String areaCode;

    public String getNumber ()
    {
        return number;
    }

    public void setNumber (String number)
    {
        this.number = number;
    }

    public String getAreaCode ()
    {
        return areaCode;
    }

    public void setAreaCode (String areaCode)
    {
        this.areaCode = areaCode;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [number = "+number+", areaCode = "+areaCode+"]";
    }
}
