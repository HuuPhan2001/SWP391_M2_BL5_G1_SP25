/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.common;

import com.cloudinary.utils.ObjectUtils;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import vn.edu.fpt.config.CloudinaryUtil;

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

    public static PreparedStatement prepareStatement(Connection conn, String sql, List<Object> params) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);

        for (int i = 0; i < params.size(); i++) {
            Object param = params.get(i);
            if (param instanceof String) {
                pstmt.setString(i + 1, (String) param);
            } else if (param instanceof Integer) {
                pstmt.setInt(i + 1, (Integer) param);
            } else if (param instanceof Boolean) {
                pstmt.setBoolean(i + 1, (Boolean) param);
            }
        }

        return pstmt;
    }

    public static String uploadImage(InputStream imageStream, String fileName, String publicIdUrl) throws IOException {
        try {
            byte[] imageBytes = imageStream.readAllBytes();

            Map uploadResult = CloudinaryUtil.getCloudinary().uploader().upload(
                    imageBytes,
                    ObjectUtils.asMap(
                            "public_id", publicIdUrl + "/" + fileName,
                            "resource_type", "auto"
                    )
            );
            return (String) uploadResult.get("secure_url");
        } catch (IOException e) {
            throw new IOException("Failed to upload image: " + e.getMessage(), e);
        } finally {
            if (imageStream != null) {
                try {
                    imageStream.close();
                } catch (IOException e) {
                    System.out.println("error");
                    e.printStackTrace();
                }
            }

        }
    }
}
