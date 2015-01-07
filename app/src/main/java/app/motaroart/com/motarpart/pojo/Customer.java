package app.motaroart.com.motarpart.pojo;

/**
 * Created by AnilU on 06-01-2015.
 */
public class Customer

{

    public String AccountType;
    public String LoginId;
    public String LoginPwd;
    public String Name;
    public String Email;
    public String Mobile;
    public String CompanyName;
    public String Address;
    public String PoBox;
    public String TIN;
    public String VATNumber;

    public Customer() {

        AccountType="U";
        LoginId="";
        LoginPwd="";
        Name="";
        Email="";
        Mobile="";
        CompanyName="";
        Address="";
        PoBox="";
        TIN="";
        VATNumber="";
    }

    public String getAccountType() {
        return AccountType;
    }

    public void setAccountType(String accountType) {
        AccountType = accountType;
    }

    public String getLoginId() {
        return LoginId;
    }

    public void setLoginId(String loginId) {
        LoginId = loginId;
    }

    public String getLoginPwd() {
        return LoginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        LoginPwd = loginPwd;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPoBox() {
        return PoBox;
    }

    public void setPoBox(String poBox) {
        PoBox = poBox;
    }

    public String getTIN() {
        return TIN;
    }

    public void setTIN(String TIN) {
        this.TIN = TIN;
    }

    public String getVATNumber() {
        return VATNumber;
    }

    public void setVATNumber(String VATNumber) {
        this.VATNumber = VATNumber;
    }
}
