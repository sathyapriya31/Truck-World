package com.spritle.truck_apps;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/********* Adapter class extends with BaseAdapter and implements with OnClickListener ************/
public class CustomAdapter extends BaseAdapter  {
          
         /*********** Declare Used Variables *********/
         private FragmentActivity activity;
         private ArrayList data;
         private static LayoutInflater inflater=null;
         EventModel tempValues=null;
         int i=0;
          
         /*************  CustomAdapter Constructor *****************/
         public CustomAdapter(FragmentActivity a, ArrayList d) {
              
                /********** Take passed values **********/
                 activity = a;
                 data=d;
              
                 /***********  Layout inflator to call external xml layout () ***********/
                  inflater = ( LayoutInflater )activity.
                                              getSystemService(Context.LAYOUT_INFLATER_SERVICE);
              
         }
      
         /******** What is the size of Passed Arraylist Size ************/
         public int getCount() {
              
             if(data.size()<=0)
                 return 1;
             return data.size();
         }
      
         public Object getItem(int position) {
             return position;
         }
      
         public long getItemId(int position) {
             return position;
         }
          
         /********* Create a holder Class to contain inflated xml file elements *********/
         public static class ViewHolder{
              
             public TextView text;
             public TextView text1;
             public TextView textWide;
             public ImageView image;
      
         }
      
         /****** Depends upon data size called for each row , Create each ListView row *****/
         public View getView(int position, View convertView, ViewGroup parent) {
              
             View vi = convertView;
             ViewHolder holder;
              
             if(convertView==null){
                  
                 /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
                 vi = inflater.inflate(R.layout.custom_row, null);
                  
                 /****** View Holder Object to contain tabitem.xml file elements ******/
 
                 holder = new ViewHolder();
                 holder.text = (TextView) vi.findViewById(R.id.name);
                 holder.text1=(TextView)vi.findViewById(R.id.description);
                  
                /************  Set holder with LayoutInflater ************/
                 vi.setTag( holder );
             }
             else 
                 holder=(ViewHolder)vi.getTag();
              
             if(data.size()<=0)
             {
                 holder.text.setText("No Data");
                  
             }
             else
             {
                 
                 tempValues=null;
                 tempValues = ( EventModel ) data.get( position );
                  
                 
 
                  holder.text.setText( tempValues.getName() );
                  holder.text1.setText( tempValues.getDescription() );
                   
                   
                  
 
                  
             }
             return vi;
         }
          
        
         
     }
