package com.example.anger_management

import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import org.json.JSONObject
import java.util.Properties
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class EmailService {
    
    companion object {
        private const val TAG = "EmailService"
        
        // Email service configuration
        private const val EMAIL_API_URL = "https://api.emailjs.com/api/v1.0/email/send"
        private const val SERVICE_ID = "your_service_id" // Replace with your EmailJS service ID
        private const val TEMPLATE_ID = "your_template_id" // Replace with your EmailJS template ID
        private const val USER_ID = "your_user_id" // Replace with your EmailJS user ID
        
        // Fallback: Gmail SMTP Configuration
        private const val SMTP_HOST = "smtp.gmail.com"
        private const val SMTP_PORT = "587"
        private const val SENDER_EMAIL = "mudumalabhanuprasadreddy@gmail.com"
        private const val SENDER_PASSWORD = "YOUR_ACTUAL_16_CHAR_APP_PASSWORD" // Replace with your actual 16-character app password
    }
    
    interface EmailCallback {
        fun onSuccess()
        fun onFailure(error: String)
    }
    
    fun sendOTPEmail(recipientEmail: String, otp: String, callback: EmailCallback) {
        Log.d(TAG, "Attempting to send email to: $recipientEmail with OTP: $otp")
        
        // Try to send real email first, fallback to simulation if it fails
        SendEmailTask(recipientEmail, otp, callback).execute()
    }
    
    private inner class SendEmailTask(
        private val recipientEmail: String,
        private val otp: String,
        private val callback: EmailCallback
    ) : AsyncTask<Void, Void, Boolean>() {
        
        private var errorMessage: String = ""
        
        override fun doInBackground(vararg params: Void?): Boolean {
            return try {
                // Try EmailJS first (if configured)
                if (SERVICE_ID != "your_service_id") {
                    sendViaEmailJS()
                } else {
                    // Fallback to SMTP
                    sendViaSMTP()
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Unknown error occurred"
                Log.e(TAG, "Failed to send email: $errorMessage", e)
                false
            }
        }
        
        private fun sendViaEmailJS(): Boolean {
            val url = URL(EMAIL_API_URL)
            val connection = url.openConnection() as HttpURLConnection
            
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true
            
            val emailData = JSONObject().apply {
                put("service_id", SERVICE_ID)
                put("template_id", TEMPLATE_ID)
                put("user_id", USER_ID)
                put("template_params", JSONObject().apply {
                    put("to_email", recipientEmail)
                    put("otp", otp)
                    put("message", getEmailContent(otp))
                })
            }
            
            val outputStream: OutputStream = connection.outputStream
            outputStream.write(emailData.toString().toByteArray())
            outputStream.flush()
            outputStream.close()
            
            val responseCode = connection.responseCode
            return responseCode == 200
        }
        
        private fun sendViaSMTP(): Boolean {
            // Set up mail server properties
            val properties = Properties()
            properties.put("mail.smtp.auth", "true")
            properties.put("mail.smtp.starttls.enable", "true")
            properties.put("mail.smtp.host", SMTP_HOST)
            properties.put("mail.smtp.port", SMTP_PORT)
            
            // Create session with authentication
            val session = Session.getInstance(properties, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD)
                }
            })
            
            try {
                // Create message
                val message = MimeMessage(session)
                message.setFrom(InternetAddress(SENDER_EMAIL))
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail))
                message.subject = "Password Reset OTP - MindPeace App"
                message.setContent(getEmailHTMLContent(otp), "text/html; charset=UTF-8")
                
                // Send message
                Transport.send(message)
                
                Log.d(TAG, "Email sent successfully via SMTP to $recipientEmail")
                return true
                
            } catch (e: Exception) {
                Log.e(TAG, "SMTP email sending failed: ${e.message}", e)
                errorMessage = "SMTP Error: ${e.message}"
                return false
            }
        }
        
        override fun onPostExecute(success: Boolean) {
            if (success) {
                Log.d(TAG, "Email sent successfully to $recipientEmail")
                callback.onSuccess()
            } else {
                Log.e(TAG, "Email sending failed: $errorMessage")
                callback.onFailure(errorMessage)
            }
        }
    }
    
    private fun getEmailHTMLContent(otp: String): String {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Password Reset OTP</title>
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; margin: 0; padding: 0; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); padding: 30px; border-radius: 10px; text-align: center; margin-bottom: 30px; }
                    .header h1 { color: white; margin: 0; font-size: 28px; }
                    .header p { color: white; margin: 10px 0 0 0; font-size: 16px; }
                    .content { background: #f8f9fa; padding: 30px; border-radius: 10px; margin-bottom: 30px; }
                    .otp-box { background: #fff; border: 2px solid #667eea; border-radius: 8px; padding: 20px; text-align: center; margin: 20px 0; }
                    .otp-code { color: #667eea; font-size: 32px; margin: 0; letter-spacing: 5px; font-family: 'Courier New', monospace; font-weight: bold; }
                    .footer { text-align: center; color: #999; font-size: 12px; }
                    .warning { background: #fff3cd; border: 1px solid #ffeaa7; border-radius: 5px; padding: 15px; margin: 20px 0; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>MindPeace</h1>
                        <p>Password Reset Request</p>
                    </div>
                    
                    <div class="content">
                        <h2 style="color: #333; margin-top: 0;">Your Password Reset OTP</h2>
                        <p style="color: #666; margin-bottom: 25px;">
                            We received a request to reset your password. Use the following OTP to complete the process:
                        </p>
                        
                        <div class="otp-box">
                            <div class="otp-code">$otp</div>
                        </div>
                        
                        <div class="warning">
                            <p style="color: #856404; margin: 0; font-weight: bold;">Important:</p>
                            <ul style="color: #856404; margin: 10px 0 0 0; padding-left: 20px;">
                                <li>This OTP is valid for 3 minutes only</li>
                                <li>Do not share this OTP with anyone</li>
                                <li>If you didn't request this, please ignore this email</li>
                            </ul>
                        </div>
                    </div>
                    
                    <div class="footer">
                        <p>This is an automated message, please do not reply to this email.</p>
                        <p>&copy; 2024 MindPeace. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
        """.trimIndent()
    }
    
    // Helper method to get email content (for logging purposes)
    fun getEmailContent(otp: String): String {
        return """
            Subject: Password Reset OTP - MindPeace App
            
            Dear User,
            
            We received a request to reset your password. Use the following OTP to complete the process:
            
            OTP: $otp
            
            This OTP is valid for 3 minutes only.
            Do not share this OTP with anyone.
            
            If you didn't request this, please ignore this email.
            
            Best regards,
            MindPeace Team
        """.trimIndent()
    }
} 