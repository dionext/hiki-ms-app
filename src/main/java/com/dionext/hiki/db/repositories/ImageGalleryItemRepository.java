package com.dionext.hiki.db.repositories;

import com.dionext.hiki.db.entity.ImageGalleryItem;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ImageGalleryItemRepository extends PagingAndSortingRepository<ImageGalleryItem, Integer> {
}