// In createEntry:
public JournalEntry createEntry(JournalEntry entry, String userId) {
    entry.setUserId(userId);
    return journalRepository.save(entry);
}

// Get all entries for a user
public List<JournalEntry> getAllEntriesByUser(String userId) {
    return journalRepository.findByUserId(userId);
}

// Get by ID and user ownership
public Optional<JournalEntry> getEntryByIdAndUser(String id, String userId) {
    return journalRepository.findByIdAndUserId(id, userId);
}

// Update only if user owns it
public JournalEntry updateEntry(String id, JournalEntry updatedEntry, String userId) {
    Optional<JournalEntry> existing = journalRepository.findByIdAndUserId(id, userId);
    if (existing.isPresent()) {
        updatedEntry.setId(id);
        updatedEntry.setUserId(userId);
        updatedEntry.setCreatedAt(existing.get().getCreatedAt());
        return journalRepository.save(updatedEntry);
    }
    return null;
}

// Delete only if owned
public boolean deleteEntry(String id, String userId) {
    if (journalRepository.existsByIdAndUserId(id, userId)) {
        journalRepository.deleteById(id);
        return true;
    }
    return false;
}