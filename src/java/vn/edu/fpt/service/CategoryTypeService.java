/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.service;

import java.util.List;
import vn.edu.fpt.dao.CategoryTypeDao;
import vn.edu.fpt.model.CategoryType;

/**
 *
 * @author MTTTT
 */
public class CategoryTypeService {
    private CategoryTypeDao categoryTypeDao = new CategoryTypeDao();

    public List<CategoryType> getAllCategoryTypes() {
        return categoryTypeDao.getAllCategoryTypes();
    }
}
