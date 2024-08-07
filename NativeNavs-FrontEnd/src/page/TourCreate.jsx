import TourEditorHead from "@/components/TourEditor/TourEditorHead";
import axios from "axios";
import { useRef, useEffect, useState } from "react";

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

  window.getUserData = (userJson) => {
    console.log("Received user JSON:", userJson);
    try {
      const puser = JSON.parse(userJson);
      setUser(puser);
      console.log(user);
    } catch (error) {
      console.error("Failed to parse user JSON", error);
    }
  };

  const onCreate = async (data) => {
    if (!data) {
      console.log("데이터가 없어요!");
    }
    const formData = new FormData();

    const subData1 = {
      title: data.title,
      description: data.description,
      thumbnailImage: "",
      location: data.location || "서울",
      price: data.price || 0,
      startDate: data.startDate || "2021-06-01",
      endDate: data.endDate || "2021-06-01",
      maxParticipants: data.maxParticipants || 1,
      categoryIds: data.categoryIds,
      plans: data.plans.map((plan) => {
        const { image, ...rest } = plan;
        rest.image = "";
        return rest;
      }),
    };

    console.log(subData1);
    // formData.append("tour", JSON.stringify(subData1));
    formData.append(
      "tour",
      new Blob([JSON.stringify(subData1)], { type: "application/json" })
    );

    formData.append("thumbnailImage", data.thumbnailImage);
    data.plans.forEach((plan, index) => {
      formData.append(`planImages`, plan.image);
    });
    console.log(formData.getAll("planImages"));
    console.log("--------");
    axios
      .post("https://i11d110.p.ssafy.io/api/tours", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization:
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlb2JsdWUyM0BnbWFpbC5jb20iLCJpYXQiOjE3MjMwMDIzNDUsImV4cCI6MTcyMzAwNTk0NX0.x5ECJw7goelL6tVz2Tb26bQprmdv6-PwhV6yWQnvwHvP0B3l7hragzHoscecn7EA5SlMwsdn_wBVtG7HdPuCUQ",
        },
      })
      .then((res) => {
        window.alert("성공");
      })
      .catch((err) => {
        console.error(err);
      });
  };
  return (
    <div>
      <TourEditorHead onSubmit={onCreate} />
    </div>
  );
};

export default TourCreate;
