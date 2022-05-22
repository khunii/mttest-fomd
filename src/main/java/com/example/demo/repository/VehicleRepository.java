package com.example.demo.repository;

import com.example.demo.domain.Brand;
import com.example.demo.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

//spring data rest 부분은 무시하고 그냥 repository로서 생각하도록...
@RepositoryRestResource(path = "vehicles", collectionResourceRel = "vehicles", itemResourceRel = "vehicle")
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByBrandIn(List<Brand> brandList);
}
