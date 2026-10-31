package com.enterprise.search.controller;

import com.enterprise.search.model.ProductDocument;
import com.enterprise.search.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/index")
    public ResponseEntity<ProductDocument> indexProduct(@RequestBody ProductDocument product) {
        return ResponseEntity.ok(searchService.indexProduct(product));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDocument>> searchProducts(@RequestParam String query) {
        return ResponseEntity.ok(searchService.searchProductsByName(query));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDocument>> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(searchService.searchProductsByCategory(category));
    }
}