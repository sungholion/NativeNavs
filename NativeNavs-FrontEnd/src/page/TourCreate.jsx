import TourEditorHead from "@/components/TourEditor/TourEditorHead";
import axios from "axios";
const NotAllowImgData = true;
const TourCreate = () => {
  const onCreate = async (data) => {
    if (!data) {
      console.log("데이터가 없어요!");
    }
    if (NotAllowImgData) {
      data.thumbnailImage =
        "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a7/React-icon.svg/1024px-React-icon.svg.png";
      data.plans = data.plans.map((plan) => {
        return {
          ...plan,
          image:
            "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a7/React-icon.svg/1024px-React-icon.svg.png",
        };
      });
    }
    data.userId = 10;
    console.log(data);
    try {
      const response = await axios.post(
        "http://i11d110.p.ssafy.io:8081/api/tours",
        data,
        {
          headers: {
            AccessToken: "strx ucbb pelf hynv",
          },
        }
      );
      console.log(response);
      window.alert("성공했어요!");
    } catch (error) {
      console.error(error);
      window.alert("실패했어요 ㅠㅠ");
    }
  };
  return (
    <div>
      <TourEditorHead onSubmit={onCreate} />
    </div>
  );
};

export default TourCreate;
