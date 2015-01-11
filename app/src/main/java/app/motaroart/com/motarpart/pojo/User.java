package app.motaroart.com.motarpart.pojo;

/**
 * Created by AnilU on 07-01-2015.
 */
public class User
{
    private String CreditLimt;
    private String LoginId;
    private String CompanyName;
    private String CreditUsed;
    private String IsApproved;
    private String MobileNo;
    private String Name;
    private String AccountId;
    private String IsActive;
    private String CreatedOn;
    private String EmailId;
    private String LastLoggedIn;
    private String AccountType;

    /// Shipping Address
    public String ShipmentAddress1;
    public String ShipmentAddress2 ;
    public String ShipmentCity;
    public String ShipmentState ;
    public String ShipmentPoBox ;

    public String getShipmentAddress1() {
        return ShipmentAddress1;
    }

    public void setShipmentAddress1(String shipmentAddress1) {
        ShipmentAddress1 = shipmentAddress1;
    }

    public String getShipmentAddress2() {
        return ShipmentAddress2;
    }

    public void setShipmentAddress2(String shipmentAddress2) {
        ShipmentAddress2 = shipmentAddress2;
    }

    public String getShipmentCity() {
        return ShipmentCity;
    }

    public void setShipmentCity(String shipmentCity) {
        ShipmentCity = shipmentCity;
    }

    public String getShipmentState() {
        return ShipmentState;
    }

    public void setShipmentState(String shipmentState) {
        ShipmentState = shipmentState;
    }

    public String getShipmentPoBox() {
        return ShipmentPoBox;
    }

    public void setShipmentPoBox(String shipmentPoBox) {
        ShipmentPoBox = shipmentPoBox;
    }

    public String getCreditLimt ()
    {
        return CreditLimt;
    }
    public void setCreditLimt (String CreditLimt)
    {
        this.CreditLimt = CreditLimt;
    }
    public String getLoginId ()
    {
        return LoginId;
    }
    public void setLoginId (String LoginId)
    {
        this.LoginId = LoginId;
    }
    public String getCompanyName ()
    {
        return CompanyName;
    }
    public void setCompanyName (String CompanyName)
    {
        this.CompanyName = CompanyName;
    }
    public String getCreditUsed ()
    {
        return CreditUsed;
    }
    public void setCreditUsed (String CreditUsed)
    {
        this.CreditUsed = CreditUsed;
    }
    public String getIsApproved ()
    {
        return IsApproved;
    }
    public void setIsApproved (String IsApproved)
    {
        this.IsApproved = IsApproved;
    }
    public String getMobileNo ()
    {
        return MobileNo;
    }
    public void setMobileNo (String MobileNo)
    {
        this.MobileNo = MobileNo;
    }
    public String getName ()
    {
        return Name;
    }
    public void setName (String Name)
    {
        this.Name = Name;
    }

    public String getAccountId ()
    {
        return AccountId;
    }

    public void setAccountId (String AccountId)
    {
        this.AccountId = AccountId;
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

    public String getEmailId ()
    {
        return EmailId;
    }

    public void setEmailId (String EmailId)
    {
        this.EmailId = EmailId;
    }

    public String getLastLoggedIn ()
    {
        return LastLoggedIn;
    }

    public void setLastLoggedIn (String LastLoggedIn)
    {
        this.LastLoggedIn = LastLoggedIn;
    }

    public String getAccountType ()
    {
        return AccountType;
    }

    public void setAccountType (String AccountType)
    {
        this.AccountType = AccountType;
    }
}