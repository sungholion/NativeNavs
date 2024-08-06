import TourEditorHead from "@/components/TourEditor/TourEditorHead";
import axios from "axios";
import { useRef, useEffect, useState } from "react";
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
    const formDt = new FormData();
    formDt.append("title", data.title);
    formDt.append("description", data.description);
    formDt.append("location", data.location);
    formDt.append("price", data.price);
    formDt.append("startDate", data.startDate);
    formDt.append("endDate", data.endDate);
    formDt.append("maxParticipants", data.maxParticipants);
    formDt.append("categoryIds", data.categoryIds);
    formDt.append("thumbnailImage", data.thumbnailImage);
    formDt.append(
      "plans",
      data.plans.map((plan) => {
        return JSON.stringify(plan);
      })
    );

    axios
      .post("https://i11d110.p.ssafy.io/api/tours", formDt, {
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization:
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlb2JsdWUyM0BnbWFpbC5jb20iLCJpYXQiOjE3MjI5Mjk2MjAsImV4cCI6MTcyMjkzMzIyMH0.jkG6y0FFP_kjJXsv_EdHnbpQ2rBbAiiIhl2954zquMQLHIkK-DdTJWyQmD8FBcTJRziX7e_8MTOoGbLHDJQP0A",
        },
      })
      .then((res) => {
        window.alert("성공");
      })
      .catch((err) => {
        window.alert("실패");
        console.log(err);
      });
  };
  return (
    <div>
      <TourEditorHead onSubmit={onCreate} />
    </div>
  );
};

export default TourCreate;
