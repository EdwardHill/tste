package com.nelioalves.cursomc.components;

import br.com.uol.pagseguro.api.common.domain.enums.DocumentType;

public class Document
{
    private DocumentType type;

    private String value;

    public DocumentType getType ()
    {
        return type;
    }

    public void setType (DocumentType type)
    {
        this.type = type;
    }

    public String getValue ()
    {
        return value;
    }

    public void setValue (String value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [type = "+type+", value = "+value+"]";
    }
}
