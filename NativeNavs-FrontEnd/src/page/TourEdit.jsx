import TourEditorHead from "@/components/TourEditor/TourEditorHead";
import axios from "axios";
import { useEffect, useReducer, useRef, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import {
  showModifyFailDialog,
  navigateFromTourModifyToTourDetailFragment,
} from "@/utils/get-android-function";

const TourEdit = () => {
  const [initData, setInitData] = useState();
  const param = useParams();
  const [navUser, setNavUser] = useState(null);
  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      const parsedUser = JSON.parse(storedUser);
      setNavUser(parsedUser);
      console.log(storedUser);
    }
  }, []);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(
          `https://i11d110.p.ssafy.io/api/tours/${param.tour_id || -1}`
        );
        const data = response.data;
        console.log(data);

        if (data) setInitData(data);
        else {
          window.alert("데이터가 없어요!");
        }
      } catch (error) {
        console.error(error);
      }
    };
    fetchData();
  }, []);

  const onEdit = async (data) => {
    if (!data) {
      throw new Error("데이터가 없어요!");
    }
    const formData = new FormData();

    const subData1 = {
      title: data.title,
      description: data.description,
      thumbnailImage:
        data.thumbnailImage && typeof data.thumbnailImage === "string"
          ? data.thumbnailImage
          : "",
      location: data.location || "서울",
      price: data.price || 0,
      startDate: data.startDate || "2021-06-01",
      endDate: data.endDate || "2021-06-01",
      maxParticipants: data.maxParticipants || 1,
      categoryIds: data.categoryIds,
      plans: data.plans.map((plan) => {
        const { image, ...rest } = plan;
        rest.image = typeof image === "string" ? image : "";
        return rest;
      }),
    };

    console.log(subData1);
    formData.append(
      "tour",
      new Blob([JSON.stringify(subData1)], { type: "application/json" })
    );
    console.log(subData1);
    console.log("-------");
    if (data.thumbnailImage instanceof File) {
      formData.append("thumbnailImage", data.thumbnailImage);
    }

    console.log(formData.getAll("thumbnailImage"));

    data.plans.forEach((plan, index) => {
      if (plan.image instanceof File) {
        formData.append(`planImages`, plan.image);
      }
    });
    console.log(formData.getAll("planImages"));

    try {
      await axios
        .put(
          `https://i11d110.p.ssafy.io/api/tours/${param.tour_id}`,
          formData,
          {
            headers: {
              "Content-Type": "multipart/form-data",
              Authorization: navUser.userToken,
            },
          }
        )
        .then((response) => {
          navigateFromTourModifyToTourDetailFragment(
            param.tour_id,
            navUser.userId
          );
        });
    } catch (error) {
      console.error(error);
      showModifyFailDialog();
    }
  };
  return (
    <div>
      <TourEditorHead onSubmit={onEdit} initData={initData} />
    </div>
  );
};

export default TourEdit;
