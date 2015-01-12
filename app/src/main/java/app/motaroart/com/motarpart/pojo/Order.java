package app.motaroart.com.motarpart.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by AnilU on 08-01-2015.
 */
public class Order implements Serializable
{
    private String VATAmount;
    private String OrderAmount;
    private String VoucherCode;
    private String OrderBy;
    private String TransactionMode;
    private String ProductCount;
    private String VATPercent;
    private String AccountId;
    private String OrderSource;
    private String TotalAmount;
    private String TransactionNumber;
    private String Remark;
    private List<Product> ProductList;


    public String ShipmentAddress1;
    public String ShipmentAddress2;

    public String ShipmentCity;
    public String ShipmentState;
    public String ShipmentPoBox;

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

    public String getVATAmount ()
    {
        return VATAmount;
    }

    public void setVATAmount (String VATAmount)
    {
        this.VATAmount = VATAmount;
    }

    public String getOrderAmount ()
    {
        return OrderAmount;
    }

    public void setOrderAmount (String OrderAmount)
    {
        this.OrderAmount = OrderAmount;
    }

    public String getVoucherCode ()
    {
        return VoucherCode;
    }

    public void setVoucherCode (String VoucherCode)
    {
        this.VoucherCode = VoucherCode;
    }

    public String getOrderBy ()
    {
        return OrderBy;
    }

    public void setOrderBy (String OrderBy)
    {
        this.OrderBy = OrderBy;
    }

    public String getTransactionMode ()
    {
        return TransactionMode;
    }

    public void setTransactionMode (String TransactionMode)
    {
        this.TransactionMode = TransactionMode;
    }

    public String getProductCount ()
    {
        return ProductCount;
    }

    public void setProductCount (String ProductCount)
    {
        this.ProductCount = ProductCount;
    }

    public String getVATPercent ()
    {
        return VATPercent;
    }

    public void setVATPercent (String VATPercent)
    {
        this.VATPercent = VATPercent;
    }

    public String getAccountId ()
    {
        return AccountId;
    }

    public void setAccountId (String AccountId)
    {
        this.AccountId = AccountId;
    }

    public String getOrderSource ()
    {
        return OrderSource;
    }

    public void setOrderSource (String OrderSource)
    {
        this.OrderSource = OrderSource;
    }

    public String getTotalAmount ()
    {
        return TotalAmount;
    }

    public void setTotalAmount (String TotalAmount)
    {
        this.TotalAmount = TotalAmount;
    }

    public String getTransactionNumber ()
    {
        return TransactionNumber;
    }

    public void setTransactionNumber (String TransactionNumber)
    {
        this.TransactionNumber = TransactionNumber;
    }

    public String getRemark ()
    {
        return Remark;
    }

    public void setRemark (String Remark)
    {
        this.Remark = Remark;
    }

    public  List<Product> getProductList ()
    {
        return ProductList;
    }

    public void setProductList ( List<Product> ProductList)
    {
        this.ProductList = ProductList;
    }
}