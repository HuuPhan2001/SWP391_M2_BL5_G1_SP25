/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

/**
 *
 * @author MTTTT
 */
public class CloudinaryUtil {
    private static final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dshfsku1t",
            "api_key", "193717569936572",
            "api_secret", "bqz5zy2UlSC47ynlpZwPbPLkAi0"
    ));

    public static Cloudinary getCloudinary() {
        return cloudinary;
    }
}
