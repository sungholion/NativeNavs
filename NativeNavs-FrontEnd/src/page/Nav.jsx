import React from "react";
import { Outlet } from "react-router-dom";

const Nav = () => {
  return (
    <div>
      Nav
      <Outlet />
    </div>
  );
};

export default Nav;
