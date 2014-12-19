package app.motaroart.com.motarpart.pojo;

import java.io.Serializable;

/**
 * Created by Sneha on 11/11/2014.
 */
public class CategoryPojo implements Serializable
{
    private String Description;

    private String Category;

    private String CategoryId;

    private String IsActive;

    private String CreatedOn;

    public String getDescription ()
    {
        return Description;
    }

    public void setDescription (String Description)
    {
        this.Description = Description;
    }

    public String getCategory ()
    {
        return Category;
    }

    public void setCategory (String Category)
    {
        this.Category = Category;
    }

    public String getCategoryId ()
    {
        return CategoryId;
    }

    public void setCategoryId (String CategoryId)
    {
        this.CategoryId = CategoryId;
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
}