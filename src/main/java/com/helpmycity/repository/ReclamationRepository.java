package com.helpmycity.repository;

import com.helpmycity.model.Reclamation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
    long countByCreatedBy(long id);

    Page<Reclamation> findByCreatedBy(Long userId, Pageable pageable);

    Page<Reclamation> findByIsEnabledTrue(Pageable pageable);

    Optional<Reclamation> findByIdAndIsEnabledTrue(Long id);

}
