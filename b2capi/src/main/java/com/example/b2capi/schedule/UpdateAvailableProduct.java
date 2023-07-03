package com.example.b2capi.schedule;

import com.example.b2capi.domain.dto.AvailableItemDTO;
import com.example.b2capi.domain.model.Product;
import com.example.b2capi.domain.model.ProductStore;
import com.example.b2capi.repository.ProductRepository;
import com.example.b2capi.repository.ProductStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class UpdateAvailableProduct {

    private final ProductRepository productRepository;
    private final ProductStoreRepository psRepository;

    @Scheduled(cron = "0 * * * * *")
    public void updateAvailableItem() {
        List<AvailableItemDTO> productStores = psRepository.countAllQuantity();

        for (var ps : productStores) {
            Optional<Product> product = productRepository.findById(ps.getId());

            if (product.isEmpty()) throw new IllegalArgumentException("No product found");

            if (ps.getTotal() > 0) {
                product.get().setAvailable(true);
            } else {
                product.get().setAvailable(false);
            }
            productRepository.save(product.get());
            System.out.println("UpdateAvailableItem run");
        }

    }

}
