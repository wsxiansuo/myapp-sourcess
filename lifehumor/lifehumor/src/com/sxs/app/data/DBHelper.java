package com.sxs.app.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.sxs.app.lifehumor.R;


public class DBHelper{

	 private Context context; 
     private String rootDirectory = "/data/data/com.sxs.app.lifehumor/data/";
     private final String DATABASE_PATH = android.os.Environment.getExternalStorageDirectory().getAbsolutePath()
     + "/everydaypoem";
     private final String DATABASE_FILENAME = "lifehumor";
     
     public DBHelper(Context context)
     {
    	 this.context = context;
     }
     public SQLiteDatabase updateDatabase(){
    	 try  
         {   
             // ���dictionary.db�ļ��ľ���·��   
             String databaseFilename = DATABASE_PATH+ "/" + DATABASE_FILENAME;   
             File dir = new File(DATABASE_PATH);   
             // ���/sdcard/dictionaryĿ¼���д��ڣ��������Ŀ¼   
             if (!dir.exists())   
                 dir.mkdir();   
             if ((new File(databaseFilename)).exists()) 
             {   
                 // ��÷�װdictionary.db�ļ���InputStream����   
                 InputStream is = context.getResources().openRawResource(R.raw.lifehumor);   
                 FileOutputStream fos = new FileOutputStream(databaseFilename);   
                 byte[] buffer = new byte[7168];   
                 int count = 0;   
                 // ��ʼ����dictionary.db�ļ�   
                 while ((count = is.read(buffer)) > 0)   
                 {   
                     fos.write(buffer, 0, count);   
                 }   
                 fos.close();   
                 is.close();   
             }   
             // ��/sdcard/dictionaryĿ¼�е�dictionary.db�ļ�   
             SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);   
             return database;   
         }   
         catch (Exception e)   
         {   
         }  
         return null;
     }
    //����С��1M�����ݿ����        
    public SQLiteDatabase openDatabase()   
    {   
        try  
        {   
            // ���dictionary.db�ļ��ľ���·��   
            String databaseFilename = DATABASE_PATH+ "/" + DATABASE_FILENAME;   
            File dir = new File(DATABASE_PATH);   
            // ���/sdcard/dictionaryĿ¼���д��ڣ��������Ŀ¼   
            if (!dir.exists())   
                dir.mkdir();   
            // �����/sdcard/dictionaryĿ¼�в�����   
            // dictionary.db�ļ������res\rawĿ¼�и�������ļ���   
            // SD����Ŀ¼��/sdcard/dictionary��   
            if (!(new File(databaseFilename)).exists()) 
//            if ((new File(databaseFilename)).exists()) 
            {   
                // ��÷�װdictionary.db�ļ���InputStream����   
                InputStream is = context.getResources().openRawResource(R.raw.lifehumor);   
                FileOutputStream fos = new FileOutputStream(databaseFilename);   
                byte[] buffer = new byte[7168];   
                int count = 0;   
                // ��ʼ����dictionary.db�ļ�   
                while ((count = is.read(buffer)) > 0)   
                {   
                    fos.write(buffer, 0, count);   
                }   
                fos.close();   
                is.close();   
            }   
            // ��/sdcard/dictionaryĿ¼�е�dictionary.db�ļ�   
            SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);   
            return database;   
        }   
        catch (Exception e)   
        {   
        }  
        return null;
    }  
}
