package com.hillspet.wearables.email.templates;

public class EmailTemplate {
	
	public static String getSMSCodeEmail(String smsCode, boolean isClinicTrial)
    {
        StringBuffer sb = new StringBuffer();

        sb.append("<!doctype html>");
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<title>Management Portal | Wearables Clinical Trials</title>");
        sb.append("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />");
        sb.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0\">");
        sb.append("<style type=\"text/css\">");
        sb.append("html, body, div { margin: 0; padding: 0; }");
        sb.append("h2 { font-size: 28px; color: #2AB27B; }");
        sb.append("p { line-height: 1.4; }");
        sb.append("a { text-decoration: none; color: #489fe0; }");
        sb.append("a:hover { text-decoration: underline; }");
        sb.append("</style>");
        sb.append("</head>");
        sb.append("<body style=\"font-family:arial;\">");
        sb.append("<center>");
        sb.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"font-family:arial;\">");
        sb.append("<tbody>");
        sb.append("<tr height=\"10\"><td></td></tr>");
        sb.append("<tr>");
        sb.append("<td align=\"center\">");
        sb.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"500\" style=\"border:4px solid #efefef;\">");
        sb.append("<tbody>");
        sb.append("<tr>");
        sb.append("<td>");
        sb.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">");
        sb.append("<tbody>");
        sb.append("<tr>");
        sb.append("<td width=\"40\"></td>");
        sb.append("<td>");
        sb.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">");
        sb.append("<tbody>");
        sb.append("<tr height=\"15\"><td></td></tr>");
        sb.append("<tr>");
        sb.append("<td align=\"center\">");
        sb.append("<a href=\"#\">");
        if (isClinicTrial)
        {
            sb.append("<img src=\"cid:#vetrax_logo.png#\" height=\"100\" alt=\"ClinicalTrial\"/>");
        }
        else
        {
            sb.append("<img src=\"cid:#vetrax_logo.png#\" height=\"60\" alt=\"Vetrax\"/>");
        }
        sb.append("</a>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td>");
        sb.append("<p style=\"margin-top:20px;\">");
        if (isClinicTrial)
        {
            sb.append("<span style=\"font-family:arial;\">Your sensor's verification code is:</span>");
        }
        else
        {
            sb.append("<span style=\"font-family:arial;\">Your sensor's verification code is:</span>");
        }
        sb.append("</p>");
        sb.append("<p style=\"line-height:1.4;text-align:center;\">");
        sb.append("<span style=\"font-family:arial;font-size:28px; color:#2AB27B;\">");
        sb.append(smsCode);
        sb.append("</span>");
        sb.append("</p>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td align=\"center\">");
        sb.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"230\">");
        sb.append("<tr>");
        sb.append("<td align=\"center\">");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("</table>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("<tr height=\"20\">");
        sb.append("<td align=\"center\">");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td align=\"center\">");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td>");
        sb.append("<p>");
        sb.append("<span style=\"font-family:arial;\">");
        sb.append("Thanks,");
        sb.append("<br><br/>");
        if (isClinicTrial)
        {
            sb.append("Wearables Clinical Trials Support");
        }
        else
        {
            sb.append("Wearables Clinical Trials Support");
        }
        sb.append("</span>");
        sb.append("</span>");
        sb.append("<span style=\"font-family:arial;\">");
        sb.append("<br><br/>");
        if (isClinicTrial)
        {
            sb.append("If you have any questions, contact support at ");
            sb.append("<a href=\"mailto:support@wearablesclinicaltrials.com\" style=\"text-decoration:none; color:#489fe0;\">");
            sb.append("<span style=\"font-family:arial; font-size:12px;\">support@wearablesclinicaltrials.com</span>");
        }
        else
        {
            sb.append("If you have any questions, you can find more info at ");
            sb.append("<a href=\"http://support.Wearablesclinicaltrials.com\" style=\"text-decoration:none; color:#489fe0;\">");
            sb.append("<span style=\"font-family:arial; font-size:12px;\">support.werablesclinicaltrials.com</span>");
        }
        sb.append("</a>");
        sb.append("</span>");
        sb.append("</p>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("<tr height=\"40\"><td>&nbsp;</td></tr>");
        sb.append("</tbody>");
        sb.append("</table>");
        sb.append("</td>");
        sb.append("<td width=\"40\"></td>");
        sb.append("</tr>");
        sb.append("</tbody>");
        sb.append("</table>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("</tbody>");
        sb.append("</table>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("</tbody>");
        sb.append("</table>");
        sb.append("</center>");
        sb.append("</body>");
        sb.append("</html>");

        return sb.toString();
    }
	
	public static String getForgetPasswordEmail(String changePasswordUrl)
    {
        String baseUrl = "";  // System.Web.HttpContext.Current.Request.Url.GetLeftPart(UriPartial.Authority);
        StringBuffer sb = new StringBuffer();

        sb.append("<!doctype html>");
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<title>Management Portal | Wearables Clinical Trials</title>");
        sb.append("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />");
        sb.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0\">");
        sb.append("<style type=\"text/css\">");
        sb.append("html, body, div { margin: 0; padding: 0; }");
        sb.append("h2 { font-size: 28px; color: #2AB27B; }");
        sb.append("p { line-height: 1.4; }");
        sb.append("a { text-decoration: none; color: #489fe0; }");
        sb.append("a:hover { text-decoration: underline; }");
        sb.append("</style>");
        sb.append("</head>");
        sb.append("<body style=\"font-family:arial;\">");
        sb.append("<center>");
        sb.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"font-family:arial;\">");
        sb.append("<tbody>");
        sb.append("<tr height=\"10\"><td></td></tr>");
        sb.append("<tr>");
        sb.append("<td align=\"center\">");
        sb.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"500\" style=\"border:4px solid #efefef;\">");
        sb.append("<tbody>");
        sb.append("<tr>");
        sb.append("<td>");
        sb.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">");
        sb.append("<tbody>");
        sb.append("<tr>");
        sb.append("<td width=\"40\"></td>");
        sb.append("<td>");
        sb.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">");
        sb.append("<tbody>");
        sb.append("<tr height=\"15\"><td></td></tr>");
        sb.append("<tr>");
        sb.append("<td align=\"center\">");
        sb.append("<a href=\"#\">");
        sb.append("<img src=\"cid:#vetrax_logo.png#\" height=\"60\" alt=\"Wearables\"/>");
        sb.append("</a>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td>");
        sb.append("<h2 style=\"margin-top:20px;\">");
        sb.append("<span style=\"font-family:arial; font-size:28px; color:#2AB27B;\">Hello {ContactName},</span>");
        sb.append("</h2>");
        sb.append("<p style=\"line-height:1.4;\">");
        sb.append("<span style=\"font-family:arial;\">");
        sb.append("We’ve received a request to reset your password. If you didn’t make the request, just ignore this email.");
        sb.append("</span>");
        sb.append("</p>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("<tr height=\"80\">");
        sb.append("<td align=\"center\">");
        sb.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"230\">");
        sb.append("<tr height=\"60\">");
        sb.append("<td align=\"center\">");
        sb.append("<a href=\""+baseUrl+"/"+changePasswordUrl+"\">");
        sb.append("<img src=\"cid:#btn_reset_password.png#\" width=\"230\" height=\"57\" alt=\"Create password\" title=\"Create password\" style=\"border:none; font-family:arial; font-size:20px; color:#2AB27B;\"/>");
        sb.append("</a>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("</table>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("<tr height=\"25\">");
        sb.append("<td align=\"center\">");
        //sb.append("<span style=\"font-family:arial; font-size:12px; color:#999;\">");
        //sb.append("You may copy/paste this link into your browser:");
        //sb.append("</span>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td align=\"center\">");
        sb.append("<a href=\""+baseUrl+"/"+changePasswordUrl+"\" style=\"text-decoration:none; color:#489fe0;\">" );
        sb.append("<span style=\"font-family:arial; font-size:12px;\">");
        sb.append(""+baseUrl+"/"+changePasswordUrl+"");
        sb.append("</span>");
        sb.append("</a>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td>");
        sb.append("<p>");
        sb.append("<span style=\"font-family:arial;\">");
        sb.append("Thanks,");
        sb.append("<br><br/>");
        sb.append("Your Wearables support team");
        sb.append("</span>");
        sb.append("</p>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("<tr height=\"40\"><td>&nbsp;</td></tr>");
        sb.append("</tbody>");
        sb.append("</table>");
        sb.append("</td>");
        sb.append("<td width=\"40\"></td>");
        sb.append("</tr>");
        sb.append("</tbody>");
        sb.append("</table>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("</tbody>");
        sb.append("</table>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("</tbody>");
        sb.append("</table>");
        sb.append("</center>");
        sb.append("</body>");
        sb.append("</html>");

        return sb.toString();
    }
	
	public static String getClientWelcomeStep2Email()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<!doctype html>");
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<title>Management Portal | Wearables Clinical Trials</title>");
        sb.append("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />");
        sb.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0\">");
        sb.append("<style type=\"text/css\">");
        sb.append("html, body, div { margin: 0; padding: 0; }");
        sb.append("h2 { font-size: 28px; color: #2AB27B; }");
        sb.append("p { line-height: 1.4; }");
        sb.append("a { text-decoration: none; color: #489fe0; }");
        sb.append("a:hover { text-decoration: underline; }");
        sb.append("</style>");
        sb.append("</head>");
        sb.append("<body style=\"font-family:arial;\">");
        sb.append("<center>");
        sb.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"font-family:arial;\">");
        sb.append("<tbody>");
        sb.append("<tr height=\"10\"><td></td></tr>");
        sb.append("<tr>");
        sb.append("<td align=\"center\">");
        sb.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"500\" style=\"border:4px solid #efefef;\">");
        sb.append("<tbody>");
        sb.append("<tr>");
        sb.append("<td>");
        sb.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">");
        sb.append("<tbody>");
        sb.append("<tr>");
        sb.append("<td width=\"40\"></td>");
        sb.append("<td>");
        sb.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">");
        sb.append("<tbody>");
        sb.append("<tr height=\"15\"><td></td></tr>");
        sb.append("<tr>");
        sb.append("<td align=\"center\"><a href=\"#\"><img src=\"cid:#vetrax_logo.png#\" height=\"60\" alt=\"Wearables\"/></a></td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td>");
        sb.append("<p style=\"line-height:1.4;\">");
        sb.append("<span style=\"font-family:arial;\">");
        sb.append("From your mobile device, click the link below to verify your Wearables account and complete your setup.");
        sb.append("</span>");
        sb.append("</p>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("<tr height=\"80\">");
        sb.append("<td align=\"center\">");
        sb.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"230\">");
        sb.append("<tr height=\"60\">");
        sb.append("<td align=\"center\">");
        sb.append("<a href=\"{ChangePwdUrl}\">");
        sb.append("<img src=\"cid:#btn_complete_setup.png#\" width=\"230\" height=\"57\" alt=\"Complete Setup\" title=\"Complete Setup\" style=\"border:none; font-family:arial; font-size:20px; color:#2AB27B;\"/>");
        sb.append("</a>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("</table>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("<tr height=\"25\">");
        sb.append("<td align=\"center\">");
        sb.append("<span style=\"font-family:arial; font-size:12px; color:#999;\">");
        sb.append("You may copy/paste this link into your browser:");
        sb.append("</span>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td align=\"center\">");
        sb.append("<a href=\"{ChangePwdUrl}\" style=\"text-decoration:none; color:#489fe0;\">");
        sb.append("<span style=\"font-family:arial; font-size:12px;\">{ChangePwdUrl}</span>");
        sb.append("</a>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td>");
        sb.append("<p style=\"line-height:1.4;\">");
        sb.append("<span style=\"font-family:arial;\">");
        sb.append("<br /><br />");
        sb.append("Thanks!");
        sb.append("<br />");
        sb.append("-The Wearables Team");
        sb.append("</span>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("<tr height=\"40\"><td>&nbsp;</td></tr>");
        sb.append("</tbody>");
        sb.append("</table>");
        sb.append("</td>");
        sb.append("<td width=\"40\"></td>");
        sb.append("</tr>");
        sb.append("</tbody>");
        sb.append("</table>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("</tbody>");
        sb.append("</table>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("</tbody>");
        sb.append("</table>");
        sb.append("</center>");
        sb.append("</body>");
        sb.append("</html>");

        return sb.toString();
    }
	
	public static String getPetOnboardWelcomeEmail(String clientName)
	{
		StringBuilder sb = new StringBuilder(9000);

		sb.append("<!DOCTYPE html><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><title>Wearables Clinical Trials</title><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" /><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0\" /><style type=\"text/css\">html,body,div {margin: 0;padding: 0;}p {line-height: 1.4;}a{text-decoration: none;color: #489fe0;}a:hover {text-decoration: underline;} label{font-weight:bold;color:#000000;}</style></head>");
		sb.append("<body style=\"font-family:arial; background-color:#efefef;\"><center><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100% \" style=\"font-family:Avenir Next; \"><tbody><tr><td align=\"center\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"800\" style=\"box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.5);background-color:#fff;\"> <tbody>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("<img src=\"https://clinic.wearablesclinicaltrials.com/assets/img/homepage-dog.png\" height=\"140\" width=\"350\" alt=\"Wearables\" />");
		sb.append("</td>");
		sb.append("<td>");
		sb.append("<table>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td style=\"text-align:center;font-size:40px;font-weight:bold;color:rgb(127,127,127);\">Wearables</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td style=\"text-align:center;font-size:18px;font-weight:bold;color:rgb(127,127,127);\">Clinical Trials</td>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr height=\"3\">");
		sb.append("</tr>");
		sb.append("<tr height=\"2\">");
		sb.append("<td colspan=\"2\" style=\"background-color: rgb(158,36,89);\"></td>");
		sb.append("</tr>");
		sb.append("<tr height=\"20\">");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td colspan=\"2\">");
		sb.append("<table>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td style=\"padding:0px 10px;font-weight:bold;color:rgb(127,127,127);\">Hi " + clientName + ",");
		sb.append("<br/>");
		sb.append("<br/>Thanks for signing up for Wearables Clinical Trials!<br/><br/>We are grateful for your time in helping improve the lives of pets around the world! We hope the app can make participating in the trial/study a fun and rewarding experience for you and your pet. If you have any questions with the app or sensor you can reach out to support by emailing support@wearablesclinicaltrials.com.<br/><br/>Thank you, Wearables Clinical Trials Support<br/>866-414-5861 - support@wearablesclinicaltrials.com<br/>M-F 7AM – 5PM Central");
		sb.append("<br/>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td style=\"padding:0px 10px;font-weight:bold;color:rgb(127,127,127);\">");
		sb.append("<br/>");
		sb.append("<br/>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");

		sb.append("<tr height=\"30\">");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td colspan=\"2\" align=\"center\" style=\"background-color:rgb(127,127,127);color:white;\">");
		sb.append("<br/>  We’re here for you. Please contact us if we can assist<br/>");
		sb.append("<br/>866-414-5861 - <a href=\"mailto:support@wearablesclinicaltrials.com\">support@wearablesclinicaltrials.com</a>");
		sb.append("<br/>M-F 7AM – 5PM Central<br/>");
		sb.append("<br/>");
		sb.append("<br/>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</tbody></table></td></tr></tbody></table></center></body></html>");
		return sb.toString();
	}
}
