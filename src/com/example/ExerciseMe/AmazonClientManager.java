package com.example.ExerciseMe;

import android.content.SharedPreferences;
import android.util.Log;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.example.ExerciseMe.tvmclient.AmazonSharedPreferencesWrapper;
import com.example.ExerciseMe.tvmclient.AmazonTVMClient;
import com.example.ExerciseMe.tvmclient.Response;

/**
 * This class is used to get clients to the various AWS services.  Before accessing a client
 * the credentials should be checked to ensure validity.
 */
public class AmazonClientManager {
    private static final String LOG_TAG = "AmazonClientManager";

    private AmazonSimpleEmailServiceClient sesClient = null;
    private SharedPreferences sharedPreferences = null;

//    public AmazonClientManager() {
//    }

    public AmazonClientManager(SharedPreferences settings) {
        this.sharedPreferences = settings;
    }

    public AmazonSimpleEmailServiceClient ses() {
        validateCredentials();
        return sesClient;
    }

    public boolean hasCredentials() {
        return PropertyLoader.getInstance().hasCredentials();
    }

    public Response validateCredentials() {

        Response ableToGetToken = Response.SUCCESSFUL;

        if (AmazonSharedPreferencesWrapper
                .areCredentialsExpired(this.sharedPreferences)) {

            synchronized (this) {

                if (AmazonSharedPreferencesWrapper
                        .areCredentialsExpired(this.sharedPreferences)) {

                    Log.i(LOG_TAG, "Credentials were expired.");

                    AmazonTVMClient tvm = new AmazonTVMClient(this.sharedPreferences,
                            PropertyLoader.getInstance().getTokenVendingMachineURL(),
                            PropertyLoader.getInstance().useSSL());

                    ableToGetToken = tvm.anonymousRegister();

                    if (ableToGetToken.requestWasSuccessful()) {

                        ableToGetToken = tvm.getToken();

                        if (ableToGetToken.requestWasSuccessful()) {
                            Log.i(LOG_TAG, "Creating New Credentials.");
                            initClients();
                        }
                    }
                }
            }

        } else if (sesClient == null) {

            synchronized (this) {

                if (sesClient == null) {

                    Log.i(LOG_TAG, "Creating New Credentials.");
                    initClients();
                }
            }
        }

        return ableToGetToken;
    }

    public void clearCredentials() {
        AmazonSharedPreferencesWrapper.wipe(this.sharedPreferences);
        sesClient = null;
    }

    private void initClients() {
        AWSCredentials credentials = AmazonSharedPreferencesWrapper
                .getCredentialsFromSharedPreferences(this.sharedPreferences);

        Region region = Region.getRegion(Regions.US_EAST_1);

        sesClient = new AmazonSimpleEmailServiceClient(credentials);
        sesClient.setRegion(region);
    }

}
