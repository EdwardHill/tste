package com.nelioalves.cursomc.domain;

public class Sender
{
    private Phone phone;

    private Document document;

    private String name;

    //private String error;

    private String email;

    private String hash;

    public Phone getPhone ()
    {
        return phone;
    }

    public void setPhone (Phone phone)
    {
        this.phone = phone;
    }

    public Document getDocument ()
    {
        return document;
    }

    public void setDocument (Document document)
    {
        this.document = document;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

	/*
	 * public String getError () { return error; }
	 * 
	 * public void setError (String error) { this.error = error; }
	 */
    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getHash ()
    {
        return hash;
    }

    public void setHash (String hash)
    {
        this.hash = hash;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [phone = "+phone+", document = "+document+", name = "+name+", email = "+email+", hash = "+hash+"]";
    }
}
