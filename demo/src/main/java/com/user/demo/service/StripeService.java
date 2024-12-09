package com.user.demo.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.ChargeCollection;
import com.stripe.model.ChargeSearchResult;
import com.stripe.model.Refund;
import com.stripe.param.ChargeSearchParams;
import com.stripe.param.RefundCreateParams;
import com.user.demo.payload.request.PaymentRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StripeService {

    @Value("${stripe.secretKey}")
    private String secretKey;

    public String createCharge(PaymentRequest paymentRequest) throws StripeException {
        Stripe.apiKey = secretKey;

        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", paymentRequest.getAmount());
        chargeParams.put("currency", paymentRequest.getCurrency());
        chargeParams.put("source", paymentRequest.getToken());
        chargeParams.put("description", paymentRequest.getDescription());

        Charge charge = Charge.create(chargeParams);
        return charge.getId();
    }

    public Charge getChargeById(String id) throws StripeException {
        Stripe.apiKey = secretKey; 
        return Charge.retrieve(id);
    }
    public Map<String, Object> getChargeByIdAsMap(String id) throws StripeException {
        com.stripe.Stripe.apiKey = secretKey;

        Charge charge = Charge.retrieve(id);

        Map<String, Object> response = new HashMap<>();
        response.put("id", charge.getId());
        response.put("status", charge.getStatus());
        response.put("amount", charge.getAmount());
        response.put("currency", charge.getCurrency());
        response.put("description", charge.getDescription());
        response.put("created", charge.getCreated());

        return response;
    }
    

    public List<Charge> getAllCharges(Integer limit) throws StripeException {
        Stripe.apiKey = secretKey;

        Map<String, Object> params = new HashMap<>();
        if (limit != null) {
            params.put("limit", limit);
        }

        ChargeCollection charges = Charge.list(params);
        return charges.getData();
    }

    public Charge captureCharge(String id) throws StripeException {
        Stripe.apiKey = secretKey;

        Charge charge = Charge.retrieve(id);
        return charge.capture();
    }

    public List<Charge> searchCharges(String query) throws StripeException {
        Stripe.apiKey = secretKey;

        ChargeSearchParams params = ChargeSearchParams.builder()
                .setQuery(query)
                .build();

        ChargeSearchResult searchResult = Charge.search(params);
        return searchResult.getData();
    }
     public Refund createRefund(String chargeId, Long amount) throws StripeException {
        Stripe.apiKey = secretKey;
        RefundCreateParams params = RefundCreateParams.builder()
                .setCharge(chargeId) 
                .setAmount(amount)   
                .build();

        return Refund.create(params);
    }
}
