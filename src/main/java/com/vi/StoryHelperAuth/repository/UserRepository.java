package com.vi.StoryHelperAuth.repository;

import com.vi.StoryHelperAuth.model.User;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CassandraRepository<User, String> {
    User findByUsername(String username);
    User save(User user);
}
