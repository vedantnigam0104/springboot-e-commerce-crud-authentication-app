// src/index.js or src/App.js
import React from "react";
import ReactDOM from "react-dom";
import App from "./App";
import { UserProvider } from "./Context/UserContext";

ReactDOM.render(
  <UserProvider>
    <App />
  </UserProvider>,
  document.getElementById("root")
);
