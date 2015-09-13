<%-- 
    Document   : index
    Created on : 11 Sep, 2015, 1:11:07 AM
    Author     : nayan
--%>


<%@page import="java.text.SimpleDateFormat"%>
<%@page import="util.ConversationComparator"%>
<%@page import="java.util.Collections"%>
<%@page import="util.ConversationCount"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.Map"%>
<%@page import="util.GmailUtil"%>
<%@page contentType="text/html; pageEncoding=UTF-8 ; charset=UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <%
        GmailUtil googleSignIn = new GmailUtil();
        if (request.getParameter("code") == null
                || request.getParameter("state") == null)
        {
            out.println("<a href='" + googleSignIn.buildLoginUrl() + "' style>Get your mail report</a>");
            request.getSession().setAttribute("state", googleSignIn.getStateToken());
        }
        else if (request.getParameter("code") != null && request.getParameter("state") != null && request.getParameter("state").equals(request.getSession().getAttribute("state")))
        {
            out.println("<a href= '" + googleSignIn.revoke() + "'> Thanks and logout</a>");
            request.getSession().removeAttribute("state");
            try
            {
                SimpleDateFormat sdf=new SimpleDateFormat("mm-dd-yyyy");
                List<ConversationCount> conversationCounts = googleSignIn.getUserInfoJson(request.getParameter("code"));
                out.print("<table><tr><td>EmailId</td><td>"+googleSignIn.getUserEmailId()+"</td></tr>");
                out.print("<table><tr><td>Start Date</td><td>"+sdf.format(googleSignIn.getStartDate())+"</td></tr>");
                out.print("<table><tr><td>End Date</td><td>"+sdf.format(googleSignIn.getEndDate())+"</td></tr>");
                out.print("<tr><td>email</td><td> #conversation</td></tr>");
                for (ConversationCount c : conversationCounts)
                {
                    out.println("<tr><td>" + c.getEmail() + "</td><td>" + c.getNumberOfMessages() + "</td></tr>");
                }
                out.print("</table>");
            }
            catch (Exception ex)
            {
                System.out.println("exception-- " + ex);
            }
        }
    %>
    <body>
    </body>
</html>
