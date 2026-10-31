package com.enterprise.search.service;

import com.enterprise.search.model.ProductDocument;
import com.enterprise.search.repository.ProductSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    private static final Logger log = LoggerFactory.getLogger(SearchService.class);
    private final ProductSearchRepository searchRepository;

    public SearchService(ProductSearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public ProductDocument indexProduct(ProductDocument product) {
        ProductDocument saved = searchRepository.save(product);
        log.info("Indexed product in Elasticsearch with ID: {}", saved.getProductId());
        return saved;
    }

    public List<ProductDocument> searchProductsByName(String query) {
        return searchRepository.findByNameContainingIgnoreCase(query);
    }

    public List<ProductDocument> searchProductsByCategory(String category) {
        return searchRepository.findByCategory(category);
    }
}