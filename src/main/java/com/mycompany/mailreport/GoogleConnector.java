/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mailreport;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nayan
 */
public class GoogleConnector extends HttpServlet
{

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
//        GmailUtil googleSignIn = new GmailUtil();
//        if (request.getParameter("code") == null
//                || request.getParameter("state") == null)
//        {
//            out.println("<a href='" + googleSignIn.buildLoginUrl() + "'>log in with google</a>");
//            request.getSession().setAttribute("state", googleSignIn.getStateToken());
//        }
//        else if (request.getParameter("code") != null && request.getParameter("state") != null && request.getParameter("state").equals(request.getSession().getAttribute("state")))
//        {
//            request.getSession().removeAttribute("state");
//
//            try
//            {
//                Map<String, Integer> conversation = googleSignIn.getUserInfoJson(request.getParameter("code"));
//                
//                for(String email:conversation.keySet())
//                {
//                   out.println("email: "+email+" #conversation- "+conversation.get(email));
//                }
//            }
//            catch (IOException | ParseException ex)
//            {
//                System.out.println("exception-- "+ex);
//                Logger.getLogger(GoogleConnector.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }

   

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

}
