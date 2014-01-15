package com.example.ExerciseMe;

import android.util.Log;
import com.amazonaws.services.simpleemail.model.*;

/*
 * Uses Amazon SES http://aws.amazon.com/ses/
 * API: SendEmail http://docs.amazonwebservices.com/ses/latest/APIReference/API_SendEmail.html
 */
public class SESManager {
	private static final String LOG_TAG = "SESManager";
	
	/** Send the feedback message based on data entered */
    public static boolean sendFeedbackEmail(String comments, String name, float rating) {
    	String subjectText = "Feedback from " + name;
    	Content subjectContent = new Content(subjectText);
    	
    	String bodyText = "Rating: " + rating + "\nComments\n" + comments;
    	Body messageBody = new Body(new Content(bodyText));	
    	
    	Message feedbackMessage = new Message(subjectContent,messageBody);
    	
    	String email = PropertyLoader.getInstance().getVerifiedEmail();
    	Destination destination = new Destination().withToAddresses(email);
    	
    	SendEmailRequest request = new SendEmailRequest(email,destination,feedbackMessage);
    	try {
    		FeedbackForm.clientManager.ses().sendEmail(request);
    	}
    	catch (Throwable e) {
    		Log.e( LOG_TAG, "Error sending mail" + e );
    		return false;
    	}
    	
    	return true;
    }
}
