package ittalents.dominos.model.repositories;

import ittalents.dominos.model.entities.Address;
import ittalents.dominos.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);

    Optional<User> getByEmail(String email);

    Optional<User> findByAddressNames(Optional<Address> existedAddress);
}
