/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cohesion.imgrecog.controller;

import com.cohesion.imgrecog.utils.FileUploader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Calvin He
 */
public class FileUploadServlet extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
        if(isMultiPart){
            ServletFileUpload upload = new ServletFileUpload();
            try{
                FileItemIterator it = upload.getItemIterator(request);
                while(it.hasNext()){
                    FileItemStream item = it.next();
                    if(item.isFormField()){
                        String fieldName = item.getFieldName();
                        InputStream is = item.openStream();
                        byte[] data = new byte[is.available()];
                        is.read(data, 0, data.length);
                        String value = new String(data);
                        response.getWriter().println(fieldName + ":" + value + "</br>");
                    }else{
                        String path = getServletContext().getRealPath("/");
                        if(FileUploader.process(path, item)){
                            request.setAttribute("imgPath", String.format("images\\%s", item.getName()));
                            request.getRequestDispatcher("/processor.jsp").forward(request, response);  
                        }
                        else
                            response.getWriter().println("file upload failed.");
                                
                    }
                }
            }catch(FileUploadException e){
                
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
