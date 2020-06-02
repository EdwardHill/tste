package com.nelioalves.cursomc.pagseguro.components;

public class Items
{
    private String amount;

    private String quantity;

    private String description;

    private String id;

    public String getAmount ()
    {
        return amount;
    }

    public void setAmount (String amount)
    {
        this.amount = amount;
    }

    public String getQuantity ()
    {
        return quantity;
    }

    public void setQuantity (String quantity)
    {
        this.quantity = quantity;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [amount = "+amount+", quantity = "+quantity+", description = "+description+", id = "+id+"]";
    }
}