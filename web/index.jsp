<%-- 
    Document   : index
    Created on : May 2, 2015, 12:06:26 AM
    Author     : Calvin He
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <form name="fileUploadForm" action="FileUploadServlet" method="POST" enctype="multipart/form-data">
            Select your image: <input type="file" name="file" value="" width="200" />
            <input type="submit" value="Submit" />
        </form>
    </body>
</html>
