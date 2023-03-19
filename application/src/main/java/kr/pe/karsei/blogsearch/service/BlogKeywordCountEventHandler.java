package kr.pe.karsei.blogsearch.service;

import kr.pe.karsei.blogsearch.port.out.BlogKeywordEventSavePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlogKeywordCountEventHandler {
    private final BlogKeywordEventSavePort eventSavePort;

    @Async
    @EventListener
    public void handler(final BlogKeywordCountEvent event) {
        eventSavePort.create(event.getKeyword());
    }
}
