/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.common;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 *
 * @author MTTTT
 */
public class Common {
    public static int getIntParameter(HttpServletRequest request, String paramName, int defaultValue) {
        try {
            String param = request.getParameter(paramName);
            return param != null ? Integer.parseInt(param) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    public static String getValidSortColumn(String sortBy, Set<String> validColumns, String defaultSort) {
        return validColumns.contains(sortBy) ? sortBy : defaultSort;
    }

}
