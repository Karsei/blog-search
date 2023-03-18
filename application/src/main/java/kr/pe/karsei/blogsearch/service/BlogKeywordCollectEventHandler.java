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
        // TODO: 메시지 큐로 넘기면 좋겠지만... 우선은 DB 에 저장한다.
        eventSavePort.create(event.getKeyword());
    }
}
