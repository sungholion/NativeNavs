import { createContext, useCallback, useReducer, useState } from "react";
import Create1 from "./Create1";
import Create2 from "./Create2";
import Create3 from "./Create3";
import Confirm from "./Confirm";

const Tour_Create = () => {
  const [Tourdata, setTourData] = useState({
    title: "", // 투어 제목
    thumbnail_image: "", //투어 썸네일
    description: "", //투어에 대한 설명
    location: "", // 투어에 대한 위치 장소
    price: 0, // 투어 에상 비용
    start_date: 0, // 투어 시작 날짜
    end_date: 0, //투어 종료 날짜
    max_participant: 20, // 최대 투어 참가 수
    plans: [], //투어에 대한 계획 정보들
  });

  const onChangeData = (key, data) => {
    setTourData((cur) => {
      if (key === "plans") {
        return { ...cur, [key]: [...data] };
      }
      return { ...cur, [key]: data };
    });
  };

  const goBeforePage = () => {
    settNowPageLook((cur) => (cur < 1 ? cur : cur - 1));
  };

  const goAfterPage = () => {
    settNowPageLook((cur) => (cur > 4 ? cur : cur + 1));
  };

  const [nowPageLook, settNowPageLook] = useState(1);
  return (
    <div>
      {nowPageLook === 1 ? (
        <Create1 AfterPage={goAfterPage} onTourDataChange={onChangeData} />
      ) : null}
      {nowPageLook === 2 ? (
        <Create2
          BeforePage={goBeforePage}
          AfterPage={goAfterPage}
          onTourDataChange={onChangeData}
        />
      ) : null}
      {nowPageLook === 3 ? (
        <Create3
          BeforePage={goBeforePage}
          AfterPage={goAfterPage}
          onTourDataChange={onChangeData}
        />
      ) : null}
      {nowPageLook === 4 ? (
        <Confirm BeforePage={goBeforePage} {...Tourdata} />
      ) : null}
    </div>
  );
};

export default Tour_Create;
