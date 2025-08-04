const express = require('express');
const nodemailer = require('nodemailer');
const cors = require('cors');
const app = express();
const PORT = 3000;

// Middleware
app.use(cors());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Email configuration
const transporter = nodemailer.createTransporter({
    service: 'gmail',
    auth: {
        user: 'mudumalabhanuprasadreddy@gmail.com', // Replace with your Gmail
        pass: 'your-16-character-app-password'     // Replace with your Gmail app password
    }
});

// Email sending endpoint
app.post('/send-email', async (req, res) => {
    try {
        const { email, otp } = req.body;
        
        if (!email || !otp) {
            return res.status(400).json({ error: 'Email and OTP are required' });
        }
        
        // Email HTML content
        const htmlContent = `
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
                            <div class="otp-code">${otp}</div>
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
        `;
        
        // Email options
        const mailOptions = {
            from: 'mudumalabhanuprasadreddy@gmail.com', // Replace with your email
            to: email,
            subject: 'Password Reset OTP - MindPeace App',
            html: htmlContent
        };
        
        // Send email
        const info = await transporter.sendMail(mailOptions);
        
        console.log('Email sent successfully:', info.messageId);
        res.json({ success: true, messageId: info.messageId });
        
    } catch (error) {
        console.error('Error sending email:', error);
        res.status(500).json({ error: 'Failed to send email' });
    }
});

// Health check endpoint
app.get('/health', (req, res) => {
    res.json({ status: 'OK', message: 'Email server is running' });
});

// Start server
app.listen(PORT, () => {
    console.log(`Email server running on port ${PORT}`);
    console.log(`Health check: http://localhost:${PORT}/health`);
    console.log(`Email endpoint: http://localhost:${PORT}/send-email`);
});

module.exports = app; 