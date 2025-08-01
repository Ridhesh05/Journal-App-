package com.example.journal.service;

import com.example.journal.model.JournalEntry;
import com.example.journal.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalService {

    @Autowired
    private JournalRepository journalRepository;

    public List<JournalEntry> getAllEntries() {
        return journalRepository.findAll();
    }

    public Optional<JournalEntry> getEntryById(String id) {
        return journalRepository.findById(id);
    }

    public JournalEntry createEntry(JournalEntry entry) {
        return journalRepository.save(entry);
    }

    public JournalEntry updateEntry(String id, JournalEntry updatedEntry) {
        Optional<JournalEntry> existingEntry = journalRepository.findById(id);
        if (existingEntry.isPresent()) {
            updatedEntry.setId(id);
            updatedEntry.setCreatedAt(existingEntry.get().getCreatedAt());
            return journalRepository.save(updatedEntry);
        }
        return null;
    }

    public boolean deleteEntry(String id) {
        if (journalRepository.existsById(id)) {
            journalRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<JournalEntry> searchByTitle(String title) {
        return journalRepository.findByTitleContainingIgnoreCase(title);
    }
}