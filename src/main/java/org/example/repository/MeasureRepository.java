package org.example.repository;

import org.example.model.Measures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MeasureRepository extends JpaRepository<Measures, Long> {
}
