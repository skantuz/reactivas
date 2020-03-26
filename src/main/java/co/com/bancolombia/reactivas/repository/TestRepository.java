package co.com.bancolombia.reactivas.repository;

import co.com.bancolombia.reactivas.model.Test;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface TestRepository extends ReactiveCrudRepository<Test, UUID> {
}
