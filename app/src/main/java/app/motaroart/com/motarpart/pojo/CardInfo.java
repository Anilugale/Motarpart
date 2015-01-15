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



    public void setCreditCardHolderName (String CreditCardHolderName)
    {
        this.CreditCardHolderName = CreditCardHolderName;
    }



    public void setOrderAmount (String OrderAmount)
    {
        this.OrderAmount = OrderAmount;
    }



    public void setCreditCardNumber (String CreditCardNumber)
    {
        this.CreditCardNumber = CreditCardNumber;
    }



    public void setOrderBy (String OrderBy)
    {
        this.OrderBy = OrderBy;
    }


    public void setCreditCardExpiry (String CreditCardExpiry)
    {
        this.CreditCardExpiry = CreditCardExpiry;
    }


    public void setCreditCardCVV (String CreditCardCVV)
    {
        this.CreditCardCVV = CreditCardCVV;
    }
}