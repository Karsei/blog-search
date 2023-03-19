package kr.pe.karsei.blogsearch.service;

import kr.pe.karsei.blogsearch.port.out.EventSavePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlogKeywordCollectEventHandler {
    private final EventSavePort eventSavePort;

    @Async
    @EventListener
    public void handler(final BlogKeywordCollectEvent event) {
        eventSavePort.create(event.getKeyword());
    }
}
