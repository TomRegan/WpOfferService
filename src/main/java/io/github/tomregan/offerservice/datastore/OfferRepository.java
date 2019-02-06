package io.github.tomregan.offerservice.datastore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository public interface OfferRepository extends JpaRepository<OfferData, Long> {

    default List<OfferData> listAll() {
        return new ArrayList<>(findAll());
    }

}
