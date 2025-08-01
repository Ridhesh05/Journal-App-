package com.example.journal.controller;

import com.example.journal.model.JournalEntry;
import com.example.journal.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/journal")
@CrossOrigin // Allow frontend from different origin (if needed later)
public class JournalController {

    @Autowired
    private JournalService journalService;

    // GET: All entries
    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllEntries() {
        List<JournalEntry> entries = journalService.getAllEntries();
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    // GET: By ID
    @GetMapping("/{id}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable String id) {
        Optional<JournalEntry> entry = journalService.getEntryById(id);
        return entry.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // POST: Create new entry
    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry) {
        JournalEntry saved = journalService.createEntry(entry);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // PUT: Update entry
    @PutMapping("/{id}")
    public ResponseEntity<JournalEntry> updateEntry(@PathVariable String id, @RequestBody JournalEntry entry) {
        JournalEntry updated = journalService.updateEntry(id, entry);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // DELETE: Delete entry
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable String id) {
        boolean deleted = journalService.deleteEntry(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                        new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // GET: Search by title
    @GetMapping("/search")
    public ResponseEntity<List<JournalEntry>> searchByTitle(@RequestParam String title) {
        List<JournalEntry> entries = journalService.searchByTitle(title);
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }
    // Inside controller, inject Security:
private String getCurrentUsername() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
}

// Then in each method:
@GetMapping
public ResponseEntity<List<JournalEntry>> getAllEntries() {
    String username = getCurrentUsername();
    User user = userService.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
    List<JournalEntry> entries = journalService.getAllEntriesByUser(user.getId());
    return ResponseEntity.ok(entries);
}
}