package com.example.b2capi.repository;

import com.example.b2capi.domain.model.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ShipperRepository extends JpaRepository<Shipper, Long> {
    @Query(value = "SELECT s.id, s.name \n" +
            "FROM b2c.shipper s left join b2c.order o on s.id = o.shipper_id \n" +
            "group by s.id \n" +
            "order by count(o.shipper_id) \n" +
            "limit 1;", nativeQuery = true)
    Optional<Shipper> findByLeastOrder();
}
