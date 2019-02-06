package io.github.tomregan.offerservice.datastore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import static io.github.tomregan.offerservice.datastore.OfferInvalidator.checkExpiry;

@Component public class OfferService {

    @SuppressWarnings("unused") @Autowired private OfferRepository offerRepository;

    public Stream<OfferData> allOffers() {
        return offerRepository.listAll().stream().map(OfferInvalidator::checkExpiry);
    }

    public Optional<OfferData> offerWithId(Number id) {
        return offerRepository.findById(id.longValue()).map(OfferInvalidator::checkExpiry);
    }

    public OfferData createOffer(OfferData offer) {
        return offerRepository.save(checkExpiry(offer));
    }

    public OfferData cancel(Number id) {
        // I have not attempted to synchronize this method because I cannot see a race between this update and
        // any other, but because it is implemented with a createOffer call, some form of exclusion would be needed if
        // we added operations - for example we could easily introduce a delete/create race.
        return offerWithId(id)
                .map(OfferData::copyBuilder)
                .map(b -> b.valid(false))
                .map(OfferData.Builder::build)
                .map(this::createOffer)
                .orElseThrow(NoSuchElementException::new);
    }
}
