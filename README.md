# Offer Service

## 🧭 Getting Started

### Get the project

`git clone https://github.com/TomRegan/WpOfferService.git`

### Build the project

`./gradlew build`

### Run the service

`./gradlew bootRun`

### Explore the API

You can view the API documentation at [localhost:8080/swagger-ui.html]([http://localhost:8080/swagger-ui.html])

## 🛌 REST

I've tried to follow the "principle of least surprise", and I've assumed the user is a person manually testing out this
service with some kind of simple client like curl. For example, in ordinary circumstances I might not have provided an explicit
`cancel` endpoint to set the validity of the offer to `false` - I often follow the Apigee guidelines for API design,
[but I've broken them here](https://apigee.com/about/blog/technology/restful-api-design-nouns-are-good-verbs-are-bad).

## Offers/Products

> an offer is a proposal to sell a specific product

For simplicity I have assumed that there is no explicit link from an offer to a product. Realistically I think there
is a missing concept like a stock-keeping-unit that would be needed for an offer to be applied to a (presumably
existing) product - I've assumed that a link from a product to an offer would cover this requirement.

## ⏰ A note on time...

### Timestamp format

`expiry` timestamp is parsed by [`Instant#parse`](https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html#parse-java.lang.CharSequence-)
 which expects a valid RFC 3339 format: you can generate an RFC
 3339 timestamp at the command line with `date -u +"%Y-%m-%dT%H:%M:%SZ"`, but be aware that for a timestamp to be
 accepted it needs to be in the future.

### Expiration

Offers are updated on access. Offers queried after the timestamp in `expiry` has passed will be updated so `valid` is
set to `false`.

## 🔓 Security

I've allowed insecure connections for simplicity. A deployment would require HTTPS/TLS.

## 🇺🇳Localisation

I've assumed the retailer doesn't require localisation.

## 🏃‍♀ Examples

### Retrieve offers

> Offers, once created, may be queried

Responses contain an `id` property which can be used to query or cancel an offer.

*all offers*

```
curl http://localhost:8080/offers --header accept:application/json
```

*a single offer*

```
curl http://localhost:8080/offers/1 --header accept:application/json
```

### Create an offer

> allow a merchant to create a new simple offer

*Example data*

```
{
  "currency": {
    "amount": 10,
    "code": "GBP"
  },
  "description": "A fantastic offer",
  "expiry": "2207-02-06T18:50:12.358Z",
  "valid": true
}
```

*Request*

```
curl -X PUT http://localhost:8080/offers \
--header accept:application/json \
--header content-type:application/json \
--data '{ "currency": { "amount": 10, "code": "GBP" }, "description": "A fantastic offer", "expiry": "2207-02-06T18:50:12.358Z", "valid": true}'
```

### Cancel an offer

> Before an offer has expired users may cancel it

```
curl http://localhost:8080/offers/1/cancel --header accept:application/json
```
