package app.motaroart.com.motarpart.pojo;

/**
 * Created by Aanil1989 on 1/14/2015.
 */
public class CardInfo
{
    private String CreditCardHolderName;

    private String OrderAmount;

    private String CreditCardNumber;

    private String OrderBy;

    private String CreditCardExpiry;

    private String CreditCardCVV;

    public String getCreditCardHolderName ()
    {
        return CreditCardHolderName;
    }

    public void setCreditCardHolderName (String CreditCardHolderName)
    {
        this.CreditCardHolderName = CreditCardHolderName;
    }

    public String getOrderAmount ()
    {
        return OrderAmount;
    }

    public void setOrderAmount (String OrderAmount)
    {
        this.OrderAmount = OrderAmount;
    }

    public String getCreditCardNumber ()
    {
        return CreditCardNumber;
    }

    public void setCreditCardNumber (String CreditCardNumber)
    {
        this.CreditCardNumber = CreditCardNumber;
    }

    public String getOrderBy ()
    {
        return OrderBy;
    }

    public void setOrderBy (String OrderBy)
    {
        this.OrderBy = OrderBy;
    }

    public String getCreditCardExpiry ()
    {
        return CreditCardExpiry;
    }

    public void setCreditCardExpiry (String CreditCardExpiry)
    {
        this.CreditCardExpiry = CreditCardExpiry;
    }

    public String getCreditCardCVV ()
    {
        return CreditCardCVV;
    }

    public void setCreditCardCVV (String CreditCardCVV)
    {
        this.CreditCardCVV = CreditCardCVV;
    }
}