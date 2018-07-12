package com.example.philipp.tetheract.data;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.List;

public class User {
    public String[][] installedApps;
    public String username;

    public static User user;

    public User(Context context){

        final PackageManager pm = context.getPackageManager();
//get a list of installed apps.
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        int counter=0;
        installedApps = new String [packages.size()][2];
        for (ApplicationInfo packageInfo : packages) {
            String TAG= "packages";
        /*    Log.d(TAG, "package :" + packageInfo.packageName);
            Log.d(TAG, "name :" + packageInfo.loadLabel(context.getPackageManager()).toString());*/


            installedApps[counter][0] = packageInfo.loadLabel(context.getPackageManager()).toString();
            installedApps[counter][1] = packageInfo.packageName;
            counter++;
         //   Log.d("installed packages", "installed :" + installedApps[counter][1]);

        }

        username = "testUser";

        user = this;

    }

    public boolean gameIsInLibrary(String packageName){

        for(int i=0;i<installedApps.length;i++){
            //Log.d("installed packages", "installed :" + installedApps[i][1]);
            if(installedApps[i][1]!=null){

                if(installedApps[i][1].equals(packageName)){
                    return true;
                }
            }
        }
        return false;

    }
}
