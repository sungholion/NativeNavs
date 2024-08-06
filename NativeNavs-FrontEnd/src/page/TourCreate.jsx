import TourEditorHead from "@/components/TourEditor/TourEditorHead";
import axios from "axios";
import { useRef, useEffect, useState } from "react";
import { json } from "react-router-dom";
const NotAllowImgData = false;
const TourCreate = () => {
  const [user, setUser] = useState(null);

  useEffect(() => {
    window.getUserData = (userJson) => {
      console.log("Received user JSON:", userJson);
      try {
        const parsedUser = JSON.parse(userJson);
        console.log(`User ID: ${parsedUser.userId}`);
        console.log(`Token: ${parsedUser.userToken}`);
        console.log(`isNav: ${parsedUser.isNav}`);
        setUser(parsedUser);
      } catch (error) {
        console.log("Failed to parse user JSON");
      }
    };
  }, []);

  const onCreate = async (data) => {
    if (!data) {
      console.log("데이터가 없어요!");
    }
    console.log(data);
    axios
      .post("https://i11d110.p.ssafy.io/api/tours", data, {
        headers: {
          Authorization:
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlb2JsdWUyM0BnbWFpbC5jb20iLCJpYXQiOjE3MjI5Mjk2MjAsImV4cCI6MTcyMjkzMzIyMH0.jkG6y0FFP_kjJXsv_EdHnbpQ2rBbAiiIhl2954zquMQLHIkK-DdTJWyQmD8FBcTJRziX7e_8MTOoGbLHDJQP0A",
        },
      })
      .then((res) => {
        window.alert("성공");
      });
  };
  return (
    <div>
      <TourEditorHead onSubmit={onCreate} />
    </div>
  );
};

export default TourCreate;
