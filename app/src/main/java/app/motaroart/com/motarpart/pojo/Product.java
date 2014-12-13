package app.motaroart.com.motarpart.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Aanil1989 on 12/11/2014.
 */
public class Product implements Serializable {

    String MakeName;
    String ModelName;
    String Category;
    int ProductId;
    long ProductNumber;
    int ProductCode;
    long OME;
    String ProductName;
    int MakeId;
    int ModelId;
    int CategoryId;
    boolean IsAvailable;
    String ProductImageUrl;
    String ProductDesc;
    double ProductPrice;
    double RetailerPrice;
    double WholesalerPrice;

    public String getMakeName() {
        return MakeName;
    }

    public void setMakeName(String makeName) {
        MakeName = makeName;
    }

    public String getModelName() {
        return ModelName;
    }

    public void setModelName(String modelName) {
        ModelName = modelName;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public long getProductNumber() {
        return ProductNumber;
    }

    public void setProductNumber(long productNumber) {
        ProductNumber = productNumber;
    }

    public int getProductCode() {
        return ProductCode;
    }

    public void setProductCode(int productCode) {
        ProductCode = productCode;
    }

    public long getOME() {
        return OME;
    }

    public void setOME(long OME) {
        this.OME = OME;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getMakeId() {
        return MakeId;
    }

    public void setMakeId(int makeId) {
        MakeId = makeId;
    }

    public int getModelId() {
        return ModelId;
    }

    public void setModelId(int modelId) {
        ModelId = modelId;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public boolean isAvailable() {
        return IsAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        IsAvailable = isAvailable;
    }

    public String getProductImageUrl() {
        return ProductImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        ProductImageUrl = productImageUrl;
    }

    public String getProductDesc() {
        return ProductDesc;
    }

    public void setProductDesc(String productDesc) {
        ProductDesc = productDesc;
    }

    public double getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(double productPrice) {
        ProductPrice = productPrice;
    }

    public double getRetailerPrice() {
        return RetailerPrice;
    }

    public void setRetailerPrice(double retailerPrice) {
        RetailerPrice = retailerPrice;
    }

    public double getWholesalerPrice() {
        return WholesalerPrice;
    }

    public void setWholesalerPrice(double wholesalerPrice) {
        WholesalerPrice = wholesalerPrice;
    }


}