import TourEditorHead from "@/components/TourEditor/TourEditorHead";
import axios from "axios";
import { useEffect, useReducer, useRef, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

// 투어 수정을 위한 페이지
const TourEdit = () => {
  const [initData, setInitData] = useState();
  const param = useParams();
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
    console.log(subData1);
    console.log("-------");
    if (data.thumbnailImage instanceof File) {
      formData.append("thumbnailImage", data.thumbnailImage);
    }

    data.plans.forEach((plan, index) => {
      if (plan.image instanceof File) {
        formData.append(`planImages`, plan.image);
      }
    });

    try {
      await axios.put(
        `http://192.168.100.140:8080/api/tours/${param.tour_id}`,
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
            Authorization:
              "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlb2JsdWUyM0BnbWFpbC5jb20iLCJpYXQiOjE3MjMwMDYwOTcsImV4cCI6MTcyMzAwOTY5N30.f_45CiZJkW2mEst8aT3DUDaliIbjvmpNYXD4oNGrruK1h98MNjzYWmwZpo4BS6GG4Z9vB6RkVIkkk7JsPryk8g",
          },
        }
      );
      window.alert("성공했어요!");
    } catch (error) {
      console.error(error);
      window.alert("실패했어요 ㅠㅠ");
    }
  };
  return (
    <div>
      <TourEditorHead onSubmit={onEdit} initData={initData} />
    </div>
  );
};

export default TourEdit;
