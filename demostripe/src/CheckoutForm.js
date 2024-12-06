import React from "react";
import { CardElement, useStripe, useElements } from "@stripe/react-stripe-js";

const CheckoutForm = () => {
  const stripe = useStripe();
  const elements = useElements();

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (!stripe || !elements) {
      return;
    }

    const cardElement = elements.getElement(CardElement);

    // Tạo Token
    const { error, token } = await stripe.createToken(cardElement);

    if (error) {
      console.error(error);
      alert("Error: " + error.message);
      return;
    }

    // Gửi token lên backend
    const response = await fetch("http://192.168.55.38:8080/api/payments/charge", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        amount: 500000, // 50.00 USD
        currency: "usd",
        token: token.id,
        description: "Test Payment",
      }),
    });

    const data = await response.json();
    if (data.status === "success") {
      alert("Payment Successful! Charge ID: " + data.chargeId);
    } else {
      alert("Payment Failed: " + data.message);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <CardElement />
      <button type="submit" disabled={!stripe}>
        Pay $50
      </button>
    </form>
  );
};

export default CheckoutForm;
