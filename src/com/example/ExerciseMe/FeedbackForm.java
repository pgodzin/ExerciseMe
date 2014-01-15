package com.example.ExerciseMe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class FeedbackForm extends Activity {

    protected Button submitButton;
    protected RatingBar ratingBar;
    protected EditText nameField;
    protected EditText commentsField;

    public static AmazonClientManager clientManager = null;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.feedback);
        submitButton = (Button) findViewById(R.id.submitButton);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        nameField = (EditText) findViewById(R.id.nameField);
        commentsField = (EditText) findViewById(R.id.commentsField);

        clientManager = new AmazonClientManager( getSharedPreferences( "com.amazon.aws.demo.AWSDemo", Context.MODE_PRIVATE ));
        if (FeedbackForm.clientManager.hasCredentials()) {
            submitButton.setVisibility(View.VISIBLE);
            this.wireButtons();
        } else {
            this.displayCredentialsIssueAndExit();
        }
    }

    /**
     * Configure onClick handlers for buttons
     */
    private void wireButtons() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFeedback();
            }
        });
    }

    /**
     * Check data that was entered and send email if valid
     */
    protected void sendFeedback() {

        if (commentsField.getText().length() == 0
                || nameField.getText().length() == 0) {

            AlertDialog.Builder confirm = new AlertDialog.Builder(this);

            confirm.setTitle("Feedback Not Sent!");
            confirm.setMessage("Please fill out the form before submitting.");
            confirm.setNegativeButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            confirm.show();
        } else {
            new SendFeedbackTask().execute();
        }
    }

    /**
     * Display an error and exit if credentials aren't provided
     */
    protected void displayCredentialsIssueAndExit() {
        AlertDialog.Builder confirm = new AlertDialog.Builder(this);
        confirm.setTitle("Credential Problem!");
        confirm.setMessage("AWS Credentials not configured correctly.  Please review the README file.");
        confirm.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                FeedbackForm.this.finish();
            }
        });
        confirm.show().show();
    }

    private class SendFeedbackTask extends AsyncTask<Void, Void, Boolean> {

        protected Boolean doInBackground(Void... voids) {

            return SESManager.sendFeedbackEmail(commentsField
                    .getText().toString(), nameField.getText().toString(),
                    ratingBar.getRating());

        }

        protected void onPostExecute(Boolean didSucceed) {

            AlertDialog.Builder confirm = new AlertDialog.Builder(
                    FeedbackForm.this);

            if (didSucceed) {
                confirm.setTitle("Feedback Success!");
                confirm.setMessage("Thank you for your feedback.");
                confirm.setNegativeButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        });
            } else {
                confirm.setTitle("Feedback Failed!");
                confirm.setMessage("Unable to send feedback at this time.");
                confirm.setNegativeButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                FeedbackForm.this.finish();
                            }
                        });
            }

            confirm.show();
        }
    }


}