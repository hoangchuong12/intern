import React from "react";
import { Elements } from "@stripe/react-stripe-js";
import { loadStripe } from "@stripe/stripe-js";
import CheckoutForm from "./CheckoutForm";

const stripePromise = loadStripe("pk_test_51QSUvxAT4KMr5KbLwv1rr4GXmYNlmjzhRUbeQDMnEkR2DuAla7Rl46nysiRQdUizp24eF0i3kMaqOBWb5xLLDOyS00BQlc7v1m");

const App = () => {
  return (
    <Elements stripe={stripePromise}>
      <CheckoutForm />
    </Elements>
  );
};

export default App;
