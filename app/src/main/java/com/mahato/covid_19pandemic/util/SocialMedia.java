package com.mahato.covid_19pandemic.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class SocialMedia {


    public static Intent openFacebook(Context context) {
        try {
            context.getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse(Constants.FACEBOOK_PROFILE_URL));
        } catch (Exception e){

            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse(Constants.FACEBOOK_PROFILE_CATCH_URL));
        }
    }
}
