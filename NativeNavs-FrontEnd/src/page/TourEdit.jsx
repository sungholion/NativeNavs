import TourEditorHead from "@/components/TourEditor/TourEditorHead";
import axios from "axios";
import { useEffect, useReducer, useRef, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
const NotAllowImgData = true;

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
    // if (NotAllowImgData) {
    //   data.thumbnailImage =
    //     "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a7/React-icon.svg/1024px-React-icon.svg.png";
    //   data.plans = data.plans.map((plan) => {
    //     return {
    //       ...plan,
    //       image:
    //         "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a7/React-icon.svg/1024px-React-icon.svg.png",
    //     };
    //   });
    // }

    const { createdAt, updatedAt, ...editData } = data;
    console.log(editData);
    try {
      const response = await axios.put(
        `https://i11d110.p.ssafy.io/api/tours/${param.tour_id}`,
        editData,
        {
          headers: {
            AccessToken: "strx ucbb pelf hynv",
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
