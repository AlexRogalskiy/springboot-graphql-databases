package com.mycompany.bookreviewapi.config;

import com.mycompany.bookreviewapi.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.index.IndexResolver;
import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@RequiredArgsConstructor
@EnableMongoAuditing
@Configuration
public class MongoConfig {

    private final MongoTemplate mongoTemplate;
    private final MongoMappingContext mongoMappingContext;

    @EventListener(ApplicationReadyEvent.class)
    public void initIndicesAfterStartup() {
        IndexOperations indexOps = mongoTemplate.indexOps(Book.class);
        IndexResolver resolver = new MongoPersistentEntityIndexResolver(mongoMappingContext);
        resolver.resolveIndexFor(Book.class).forEach(indexOps::ensureIndex);
    }
}
