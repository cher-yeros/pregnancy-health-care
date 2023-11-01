package com.gonder.pregnancyhealthcare.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.gonder.pregnancyhealthcare.R;
import com.gonder.pregnancyhealthcare.models.Admin;
import com.gonder.pregnancyhealthcare.models.LabTechnician;
import com.gonder.pregnancyhealthcare.models.Mother;
import com.gonder.pregnancyhealthcare.models.Physician;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Set;

public class SharedPrefConfig {
    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPrefConfig(Context context) {
        this.context = context;
        sharedPreferences = this.context.getSharedPreferences(
                context.getString(R.string.login_pref_name),
                Context.MODE_PRIVATE
        );
    }

    public void writeLoginInfo(String userType, String userJson) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.user_type), userType);
        editor.putString(context.getString(R.string.logged_in_user),userJson);
        editor.putBoolean(context.getString(R.string.login_status),true);

        editor.apply();
    }

    public boolean getStatus() {
        return sharedPreferences.getBoolean(context.getString(R.string.login_status),false);
    }

    public String userType() {
        return sharedPreferences.getString(context.getString(R.string.user_type), null);
    }

    public Object user() {

        String type = userType();
        String userJson = sharedPreferences.getString(context.getString(R.string.logged_in_user), null);
        Object object;
        switch (type) {
            case UserType.admin:
                object =  new Gson().fromJson(userJson, Admin.class);
                break;
            case UserType.mother:
                object =  new Gson().fromJson(userJson, Mother.class);
                break;
            case UserType.physician:
                object =  new Gson().fromJson(userJson, Physician.class);
                break;
            case UserType.labTechnician:
                object =  new Gson().fromJson(userJson, LabTechnician.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        return object;
    }

    public void deleteLoginInfo() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.user_type), null);
        editor.putString(context.getString(R.string.logged_in_user),null);
        editor.putBoolean(context.getString(R.string.login_status),false);

        editor.apply();
    }

    public void registerClientCeckupDates(String[] dates) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

//        editor.putStringSet("checkup_date",dates);

        editor.apply();
    }
}
