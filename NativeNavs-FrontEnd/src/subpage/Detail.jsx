import { Outlet } from "react-router-dom";

const Detail = () => {
  return (
    <div>
      <p>Details</p>
      <Outlet />
    </div>
  );
};

export default Detail;
