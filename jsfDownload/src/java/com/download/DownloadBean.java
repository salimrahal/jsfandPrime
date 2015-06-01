/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.download;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author salim
 */
@ManagedBean
public class DownloadBean implements Serializable {

    private static final long serialVersionUID = 626953318628565053L;

    // A PDF to download
    private static final String PDF_URL = "http://download.itcuties.com/jsf2/jsf2-download-pdf/itcuties-logo-concept.pdf";

    /**
     * This method reads PDF from the URL and writes it back as a response.
     *
     * @throws IOException
     */
    public void downloadPdf(String downloadMode) throws IOException {
        // Get the FacesContext
        FacesContext facesContext = FacesContext.getCurrentInstance();

        // Get HTTP response
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

        // Set response headers
        response.reset();	// Reset the response in the first place
        response.setHeader("Content-Type", "application/pdf");	// Set only the content type
        response.setHeader("Content-disposition", downloadMode+"; filename=test.pdf");//
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setHeader("Pragma", "No-cache");
        // Open response output stream
        OutputStream responseOutputStream = response.getOutputStream();

        // Read PDF contents
        URL url = new URL(PDF_URL);
        InputStream pdfInputStream = url.openStream();

        // Read PDF contents and write them to the output
        byte[] bytesBuffer = new byte[2048];
        int bytesRead;
        while ((bytesRead = pdfInputStream.read(bytesBuffer)) > 0) {
            responseOutputStream.write(bytesBuffer, 0, bytesRead);
        }

        // Make sure that everything is out
        responseOutputStream.flush();

        // Close both streams
        pdfInputStream.close();
        responseOutputStream.close();

        // JSF doc: 
        // Signal the JavaServer Faces implementation that the HTTP response for this request has already been generated 
        // (such as an HTTP redirect), and that the request processing lifecycle should be terminated
        // as soon as the current phase is completed.
        facesContext.responseComplete();
    }

}
