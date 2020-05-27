package com.nelioalves.cursomc.dto;

import com.nelioalves.cursomc.domain.Billing;
import com.nelioalves.cursomc.domain.Items;
import com.nelioalves.cursomc.domain.Sender;
import com.nelioalves.cursomc.domain.Shipping;



public class BoletoDTO
{
    private String reference;

    private String method;

    private Shipping shipping;

    private Sender sender;

    private Items[] items;

    private Billing billing;

    public String getReference ()
    {
        return reference;
    }

    public void setReference (String reference)
    {
        this.reference = reference;
    }

    public String getMethod ()
    {
        return method;
    }

    public void setMethod (String method)
    {
        this.method = method;
    }

    public Shipping getShipping ()
    {
        return shipping;
    }

    public void setShipping (Shipping shipping)
    {
        this.shipping = shipping;
    }

    public Sender getSender ()
    {
        return sender;
    }

    public void setSender (Sender sender)
    {
        this.sender = sender;
    }

    public Items[] getItems ()
    {
        return items;
    }

    public void setItems (Items[] items)
    {
        this.items = items;
    }

    public Billing getBilling ()
    {
        return billing;
    }

    public void setBilling (Billing billing)
    {
        this.billing = billing;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [reference = "+reference+", method = "+method+", shipping = "+shipping+", sender = "+sender+", items = "+items+", billing = "+billing+"]";
    }
}

