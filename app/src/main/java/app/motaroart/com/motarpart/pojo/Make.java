package app.motaroart.com.motarpart.pojo;

import java.io.Serializable;

/**
 * Created by Anil Ugale on 20-11-2014.
 */

public class Make implements Serializable
{
    private String MakeName;

    private String Description;

    private String IsActive;

    private String CreatedOn;

    private int MakeId;

    public String getMakeName ()
    {
        return MakeName;
    }

    public void setMakeName (String MakeName)
    {
        this.MakeName = MakeName;
    }

    public String getDescription ()
    {
        return Description;
    }

    public void setDescription (String Description)
    {
        this.Description = Description;
    }

    public String getIsActive ()
    {
        return IsActive;
    }

    public void setIsActive (String IsActive)
    {
        this.IsActive = IsActive;
    }

    public String getCreatedOn ()
    {
        return CreatedOn;
    }

    public void setCreatedOn (String CreatedOn)
    {
        this.CreatedOn = CreatedOn;
    }

    public int getMakeId ()
    {
        return MakeId;
    }

    public void setMakeId (int MakeId)
    {
        this.MakeId = MakeId;
    }
}
