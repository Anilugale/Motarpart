package app.motaroart.com.motarpart.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import app.motaroart.com.motarpart.pojo.Part;

/**
 * Created by AnilU on 25-11-2014.
 */
public class DataHandler extends SQLiteOpenHelper {
    static String DATABASE_NAME="SHOPPING_CART";
    static int VERSION=1;

    String P_NAME="P_NAME";
    String P_MRP="P_MRP";
    String P_MAKE="P_MAKE";
    String P_MODE="P_MODE";
    String P_NUMBER="P_NUMBER";
    String P_ITEM_NO="P_ITEM_NO";
    String P_OEM_NO="P_OEM_NO";
    String P_DESC="P_DESC";
    String PART_TABLE="PART_TABLE";


    public DataHandler(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String Create_part_table="CREATE TABLE "+PART_TABLE+"("+
                P_NAME+" TEXT,"+
                P_MRP+" NUMBER,"+
                P_MAKE+" TEXT,"+
                P_MODE+" TEXT,"+
                P_NUMBER+" TEXT,"+
                P_ITEM_NO+" TEXT,"+
                P_OEM_NO+" TEXT,"+
                P_DESC+" TEXT,"+
                ")";
        sqLiteDatabase.execSQL(Create_part_table);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

        sqLiteDatabase.execSQL("drop table "+PART_TABLE);


        this.onCreate(sqLiteDatabase);

    }



    boolean insertPart(Part p)
    {
        try
        {
            ContentValues values=new ContentValues();
            values.put(P_NAME,p.getName());
            values.put(P_MRP,p.getMrp());
            values.put(P_MAKE,p.getMake());
            values.put(P_MODE,p.getModel());
            values.put(P_NUMBER,p.getNumber());
            values.put(P_ITEM_NO,p.getItem_no());
            values.put(P_OEM_NO,p.getOem_no());
            values.put(P_DESC,p.getDscrp());

            SQLiteDatabase db=this.getWritableDatabase();
            db.insert(PART_TABLE,null,values);
            db.close();
            return  true;
        }
        catch(Exception ex)
        {

            ex.printStackTrace();
            return  false;
        }

    }
    List<Part> getAllPart()
    {
        List<Part> list=new ArrayList<Part>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("",null);
        cursor.moveToFirst();
        while(cursor.moveToNext())
        {
            Part part=new Part();

            part.setName(cursor.getString(cursor.getColumnIndex(P_NAME)));
            part.setMrp(cursor.getInt(cursor.getColumnIndex(P_MRP)));
            part.setMake(cursor.getString(cursor.getColumnIndex(P_MAKE)));
            part.setModel(cursor.getString(cursor.getColumnIndex(P_MODE)));
            part.setNumber(cursor.getString(cursor.getColumnIndex(P_NUMBER)));
            part.setItem_no(cursor.getString(cursor.getColumnIndex(P_ITEM_NO)));
            part.setOem_no(cursor.getString(cursor.getColumnIndex(P_OEM_NO)));
            part.setDscrp(cursor.getString(cursor.getColumnIndex(P_DESC)));

            list.add(part);

        }
        db.close();
        return list;
    }
}
