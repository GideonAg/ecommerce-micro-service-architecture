package com.redeemerlives.ecommerce.product;

import com.redeemerlives.ecommerce.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Integer createProduct(ProductRequest request) {
        Product product = productMapper.toProduct(request);
        return productRepository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
        var productIds = request.stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        var storedProducts = productRepository.findAllByIdIn(productIds);

        if (productIds.size() != storedProducts.size())
            throw new ProductPurchaseException("One or more product does not exist");

        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
        request.sort(Comparator.comparingInt(ProductPurchaseRequest::productId));
        for (int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = request.get(i);
            if (product.getAvailableQuantity() < productRequest.quantity())
                throw new ProductPurchaseException(
                        "Insufficient stock quantity for product with ID::" + productRequest.productId()
                );

            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            productRepository.save(product);

            purchasedProducts.add(
                    productMapper.toProductPurchaseResponse(product, productRequest.quantity())
            );
        }
        return purchasedProducts;
    }

    public ProductResponse findProductById(Integer productId) {
        return productRepository.findById(productId)
                .map(productMapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("No product found with ID:: " + productId));
    }

    public List<ProductResponse> findAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .toList();
    }
}
