package com.helpmycity.repository;

import com.helpmycity.model.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
}
