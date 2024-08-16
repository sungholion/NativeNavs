import TourEditorHead from "@/components/TourEditor/TourEditorHead";
import axios from "axios";
import { useRef, useEffect, useState } from "react";
import { moveFromTourRegisterToTourDetailFragment } from "@/utils/get-android-function";

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
    console.log(formData.getAll("tour"));
    console.log(formData.getAll("thumbnailImage"));
    console.log("--------");
    axios
      .post("https://i11d110.p.ssafy.io/api/tours", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: user.userToken,
        },
      })
      .then((res) => {
        console.log(res.data.tourId);
        moveFromTourRegisterToTourDetailFragment(
          Number(res.data.tourId),
          user.userId
        );
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
