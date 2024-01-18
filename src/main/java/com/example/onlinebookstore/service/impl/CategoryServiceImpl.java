package com.example.onlinebookstore.service.impl;

import com.example.onlinebookstore.dto.category.CategoryResponseDto;
import com.example.onlinebookstore.dto.category.CreateCategoryRequestDto;
import com.example.onlinebookstore.exception.EntityNotFoundException;
import com.example.onlinebookstore.mapper.CategoryMapper;
import com.example.onlinebookstore.model.Category;
import com.example.onlinebookstore.repository.category.CategoryRepository;
import com.example.onlinebookstore.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponseDto> findAll(Pageable pageable) {
        return categoryRepository
                .findAll(pageable)
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryResponseDto findById(Long id) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find category with id " + id));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryResponseDto save(CreateCategoryRequestDto requestDto) {
        Category categoryModel = categoryMapper.toModel(requestDto);
        return categoryMapper.toDto(categoryRepository.save(categoryModel));
    }

    @Override
    public CategoryResponseDto update(Long id, CreateCategoryRequestDto requestDto) {
        Category categoryFromDb = categoryRepository
                .findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't update category: "
                                + "no category with id " + id));
        categoryFromDb.setName(requestDto.getName());
        categoryFromDb.setDescription(requestDto.getDescription());
        return categoryMapper.toDto(categoryRepository.save(categoryFromDb));
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
