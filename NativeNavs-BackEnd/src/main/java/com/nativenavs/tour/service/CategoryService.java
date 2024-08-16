package com.nativenavs.tour.service;

import com.nativenavs.tour.dto.CategoryDTO;
import com.nativenavs.tour.entity.CategoryEntity;
import com.nativenavs.tour.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryDTO> getAllCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CategoryDTO convertToDTO(CategoryEntity categoryEntity) {
        return new CategoryDTO(
                categoryEntity.getId(),
                categoryEntity.getName(),
                categoryEntity.getCategoryImage(),
                categoryEntity.getEnglishName()
        );
    }

}
