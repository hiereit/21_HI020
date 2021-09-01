package com.hanium.greenduks;

import android.content.Context;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;

public interface AmplifyInterface {
    default void amplifyInit(Context context){
        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(context);

            Log.d("AmplifyInterface", "Initialized Amplify");
        } catch (AmplifyException e) {
            Log.d("AmplifyInterface", "Could not initialize Amplify", e);
        }
    }

}
