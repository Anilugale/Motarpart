package app.motaroart.com.motarpart.pojo;

import java.io.Serializable;

/**
 * Created by AnilU on 19-12-2014.
 */
public class Wish implements Serializable
{
    private String AccountId;

    private String RecordId;

    private String CreatedOn;

    private String ProductId;

    public String getAccountId ()
    {
        return AccountId;
    }

    public void setAccountId (String AccountId)
    {
        this.AccountId = AccountId;
    }

    public String getRecordId ()
    {
        return RecordId;
    }

    public void setRecordId (String RecordId)
    {
        this.RecordId = RecordId;
    }

    public String getCreatedOn ()
    {
        return CreatedOn;
    }

    public void setCreatedOn (String CreatedOn)
    {
        this.CreatedOn = CreatedOn;
    }

    public String getProductId ()
    {
        return ProductId;
    }

    public void setProductId (String ProductId)
    {
        this.ProductId = ProductId;
    }
}
