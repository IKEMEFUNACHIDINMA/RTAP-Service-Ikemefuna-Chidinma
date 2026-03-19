package com.example.rtapservice.repository;

import com.example.rtapservice.model.SessionEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionEventRepository extends JpaRepository<SessionEvent, Long> {
    // This exact method is called by our AiSummarizationService to get logs in chronological order
    List<SessionEvent> findBySessionIdOrderByTimestampAsc(Long sessionId);
}
