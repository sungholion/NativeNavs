import React from "react";
import { Router, Route, Outlet } from "react-router-dom";

const Tour = () => {
  return (
    <div>
      <p> Tour </p>
      <Outlet />
    </div>
  );
};

export default Tour;
