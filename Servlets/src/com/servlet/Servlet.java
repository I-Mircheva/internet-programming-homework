package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/") //Points to root url
public class Servlet extends HttpServlet {

    private static final long serialVersionUID = -1641096228274971485L;

    private Map<String,String> keyValMap = new HashMap<>(); // Global variable used in doGet and doPost

    public Servlet() {
        super(); // Does initial setup
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Set response headers
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        // Create HTML form
        response.getWriter().append("<!DOCTYPE html>\r\n")
                .append("<html>\r\n")
                .append("		<head>\r\n")
                .append("			<title> Form input </title>\r\n")
                .append("		</head>\r\n")
                .append("		<body>\r\n")
                .append("			<form action=\"welcome\" method=\"POST\">\r\n")
                .append("				Enter your key: \r\n")
                .append("				<input type=\"text\" name=\"key\" />\r\n")
                .append("				And value: \r\n")
                .append("				<input type=\"text\" name=\"value\" />\r\n")
                .append("				<input type=\"submit\" value=\"Submit\" />\r\n")
                .append("			</form>\r\n")
                .append("		</body>\r\n")
                .append("</html>\r\n");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // Set response headers
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        PrintWriter writer = response.getWriter(); // Set it in a var to use it later

        // Getting latest key and value
        String key = request.getParameter("key");
        String value = request.getParameter("value");
        // Put them in the map if they are legit
        if (key != null && !key.trim().isEmpty() && value != null && !value.trim().isEmpty()) {
            keyValMap.put(key, value);
        } else {
            writer.append("	You have not entered key or value!\r\n");
        }

        // Create HTML response
        writer.append("<!DOCTYPE html>\r\n")
                .append("<html>\r\n")
                .append("		<head>\r\n")
                .append("			<title> All rise! </title>\r\n")
                .append("		</head>\r\n")
                .append("		<body>\r\n")
                .append("<h1> All list: </h1>\r\n");


        // Print them all if the map is not empty :)
        if (!keyValMap.isEmpty()) {
            int i = keyValMap.size();
            for (Map.Entry <String, String> entry : keyValMap.entrySet()) {
                writer.append("<h3> " + i + ". " + entry.getKey() + "-" + entry.getValue() + "</h3>\r\n");
                i--;
            }
        } else {
            writer.append("	You have not entered any keys or values yet!\r\n");
        }
        writer.append("		</body>\r\n")
                .append("</html>\r\n");
    }

}
