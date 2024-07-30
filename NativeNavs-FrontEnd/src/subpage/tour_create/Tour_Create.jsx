import { useReducer, useState } from "react";
import Create1 from "./Create1";
import Create2 from "./Create2";
import Create3 from "./Create3";

const reducer = (state, action) => {
  console.log("Hello World");
};

const Tour_Create = () => {
  const [data, setData] = useState({});
  return (
    <div>
      <Create1 />
      <Create2 />
      <Create3 />
    </div>
  );
};

export default Tour_Create;
