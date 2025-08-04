# 📧 Email OTP Setup Guide for MindPeace App

This guide will help you set up real email functionality for the OTP feature in the MindPeace app.

## 🚀 Quick Setup Options

### Option 1: Use the Local Email Server (Recommended for Testing)

#### Step 1: Install Node.js
1. Download and install Node.js from [https://nodejs.org/](https://nodejs.org/)
2. Verify installation: `node --version` and `npm --version`

#### Step 2: Set up the Email Server
1. Open terminal/command prompt in the project directory
2. Install dependencies:
   ```bash
   npm install
   ```
3. Configure Gmail settings in `email-server.js`:
   ```javascript
   const transporter = nodemailer.createTransporter({
       service: 'gmail',
       auth: {
           user: 'your-email@gmail.com', // Replace with your Gmail
           pass: 'your-app-password'     // Replace with your Gmail app password
       }
   });
   ```

#### Step 3: Get Gmail App Password
1. Go to your Google Account settings
2. Enable 2-Factor Authentication
3. Generate an App Password:
   - Go to Security → App passwords
   - Select "Mail" and "Other (Custom name)"
   - Name it "MindPeace App"
   - Copy the generated 16-character password
4. Replace `'your-app-password'` in the email-server.js file

#### Step 4: Start the Email Server
```bash
npm start
```
The server will run on `http://localhost:3000`

#### Step 5: Test the Setup
1. Open your browser and go to `http://localhost:3000/health`
2. You should see: `{"status":"OK","message":"Email server is running"}`

### Option 2: Use SendGrid (Production Ready)

#### Step 1: Create SendGrid Account
1. Sign up at [https://sendgrid.com/](https://sendgrid.com/)
2. Get your API key from the dashboard

#### Step 2: Update EmailService.kt
Replace the API key in `EmailService.kt`:
```kotlin
private const val API_KEY = "YOUR_ACTUAL_SENDGRID_API_KEY"
```

## 📱 Android App Configuration

### For Android Emulator:
The app is already configured to use `http://10.0.2.2:3000/send-email`

### For Physical Device:
1. Update `EmailService.kt`:
   ```kotlin
   private const val SIMPLE_EMAIL_URL = "http://YOUR_COMPUTER_IP:3000/send-email"
   ```
2. Replace `YOUR_COMPUTER_IP` with your computer's IP address
3. Make sure your device and computer are on the same network

## 🧪 Testing the Email Functionality

### Step 1: Start the Email Server
```bash
npm start
```

### Step 2: Test in Android App
1. Open the MindPeace app
2. Go to "Forgot Password"
3. Enter a valid email address
4. Click "Send Reset Link"
5. Check the email server console for logs
6. Check your email inbox for the OTP

### Step 3: Verify OTP
1. Copy the OTP from your email
2. Enter it in the OTP verification screen
3. Click "Verify"
4. You should be redirected to the password reset page

## 🔧 Troubleshooting

### Email Server Issues:
- **Port already in use**: Change the port in `email-server.js` (line 5)
- **Gmail authentication failed**: Check your app password
- **CORS errors**: Make sure the server is running and accessible

### Android App Issues:
- **Network error**: Check if the server URL is correct
- **Timeout**: Increase the timeout in the EmailService
- **Permission denied**: Make sure INTERNET permission is added

### Common Gmail Issues:
- **"Less secure app access"**: Use App Passwords instead
- **"Username and Password not accepted"**: Double-check your credentials
- **"Authentication failed"**: Regenerate your app password

## 📧 Email Templates

The app includes beautiful HTML email templates with:
- MindPeace branding
- Professional styling
- Clear OTP display
- Security warnings
- Mobile-responsive design

## 🔒 Security Considerations

1. **Never commit API keys** to version control
2. **Use environment variables** for sensitive data
3. **Implement rate limiting** for OTP requests
4. **Add email validation** on the server side
5. **Use HTTPS** in production

## 🚀 Production Deployment

### For Production:
1. Deploy the email server to a cloud service (Heroku, AWS, etc.)
2. Use a production email service (SendGrid, Mailgun, etc.)
3. Set up proper SSL certificates
4. Implement monitoring and logging
5. Add rate limiting and security measures

### Environment Variables:
```bash
# For the email server
GMAIL_USER=your-email@gmail.com
GMAIL_PASS=your-app-password
PORT=3000

# For SendGrid
SENDGRID_API_KEY=your-sendgrid-api-key
```

## 📞 Support

If you encounter any issues:
1. Check the server logs for error messages
2. Verify your Gmail app password
3. Test the email server endpoint manually
4. Check network connectivity between app and server

## ✅ Success Checklist

- [ ] Node.js installed
- [ ] Email server dependencies installed
- [ ] Gmail app password configured
- [ ] Email server running on port 3000
- [ ] Health check endpoint responding
- [ ] Android app configured with correct server URL
- [ ] Test email received successfully
- [ ] OTP verification working

---

**Note**: This setup provides a complete email OTP system for testing and development. For production use, consider using established email services like SendGrid, Mailgun, or AWS SES for better reliability and features. 