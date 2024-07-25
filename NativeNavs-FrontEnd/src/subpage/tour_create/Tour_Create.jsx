import { useReducer, useState } from "react";

const reducer = (state, action) => {
  console.log("Hello World");
};

const Tour_Create = () => {
  const [value, setValue] = useReducer(reducer);
  return <div>투어 작성</div>;
};

export default Tour_Create;
