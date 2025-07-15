package org.task.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.task.domain.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    @Transactional(readOnly = true)
    User findByEmail(String email);
}
