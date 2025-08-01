package com.example.journal.repository;

import com.example.journal.model.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalRepository extends MongoRepository<JournalEntry, String> {
    // Custom query methods can be added here if needed
    List<JournalEntry> findByTitleContainingIgnoreCase(String title);
}