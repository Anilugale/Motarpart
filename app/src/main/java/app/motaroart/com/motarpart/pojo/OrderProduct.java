package app.motaroart.com.motarpart.pojo;

import java.io.Serializable;

/**
 * Created by AnilU on 13-01-2015.
 */
public class OrderProduct implements Serializable
{
    private String MakeName;
    private String ModelId;
    private String Quantity;
    private String CategoryName;
    private String CategoryId;
    private String ProductCode;
    private String ModelName;
    private String ProductName;
    private String ProductNumber;
    private String MakeId;
    private String ProductPrice;
    private String ProductId;

    public String getMakeName ()
    {
        return MakeName;
    }

    public void setMakeName (String MakeName)
    {
        this.MakeName = MakeName;
    }

    public String getModelId ()
    {
        return ModelId;
    }

    public void setModelId (String ModelId)
    {
        this.ModelId = ModelId;
    }

    public String getQuantity ()
    {
        return Quantity;
    }

    public void setQuantity (String Quantity)
    {
        this.Quantity = Quantity;
    }

    public String getCategoryName ()
    {
        return CategoryName;
    }

    public void setCategoryName (String CategoryName)
    {
        this.CategoryName = CategoryName;
    }

    public String getCategoryId ()
    {
        return CategoryId;
    }

    public void setCategoryId (String CategoryId)
    {
        this.CategoryId = CategoryId;
    }

    public String getProductCode ()
    {
        return ProductCode;
    }

    public void setProductCode (String ProductCode)
    {
        this.ProductCode = ProductCode;
    }

    public String getModelName ()
    {
        return ModelName;
    }

    public void setModelName (String ModelName)
    {
        this.ModelName = ModelName;
    }

    public String getProductName ()
    {
        return ProductName;
    }

    public void setProductName (String ProductName)
    {
        this.ProductName = ProductName;
    }

    public String getProductNumber ()
    {
        return ProductNumber;
    }

    public void setProductNumber (String ProductNumber)
    {
        this.ProductNumber = ProductNumber;
    }

    public String getMakeId ()
    {
        return MakeId;
    }

    public void setMakeId (String MakeId)
    {
        this.MakeId = MakeId;
    }

    public String getProductPrice ()
    {
        return ProductPrice;
    }

    public void setProductPrice (String ProductPrice)
    {
        this.ProductPrice = ProductPrice;
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
