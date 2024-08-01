import { createContext, useCallback, useReducer, useState } from "react";
import Create1 from "./Create1";
import Create2 from "./Create2";
import Create3 from "./Create3";
import Confirm from "./Confirm";

const themeList = [
  { key: "시장", state: false, idx: 0 },
  { key: "액티비티", state: false, idx: 1 },
  { key: "자연", state: false, idx: 2 },
  { key: "역사", state: false, idx: 3 },
  { key: "문화", state: false, idx: 4 },
  { key: "축제", state: false, idx: 5 },
  { key: "음식", state: false, idx: 6 },
  { key: "트렌디", state: false, idx: 7 },
  { key: "랜드마크", state: false, idx: 8 },
  { key: "쇼핑", state: false, idx: 9 },
  { key: "미용", state: false, idx: 10 },
  { key: "사진", state: false, idx: 11 },
];

const initTourData = {
  title: "", // 투어 제목
  thumbnail_image: "", //투어 썸네일
  description: "", //투어에 대한 설명
  location: "", // 투어에 대한 위치 장소
  price: 0, // 투어 에상 비용
  start_date: 0, // 투어 시작 날짜
  end_date: 0, //투어 종료 날짜
  max_participant: 1, // 최대 투어 참가 수
  plans: [], //투어에 대한 계획 정보들
  themes: [
    { name: "시장", state: false, idx: 0 },
    { name: "액티비티", state: false, idx: 1 },
    { name: "자연", state: false, idx: 2 },
    { name: "역사", state: false, idx: 3 },
    { name: "문화", state: false, idx: 4 },
    { name: "축제", state: false, idx: 5 },
    { name: "음식", state: false, idx: 6 },
    { name: "트렌디", state: false, idx: 7 },
    { name: "랜드마크", state: false, idx: 8 },
    { name: "쇼핑", state: false, idx: 9 },
    { name: "미용", state: false, idx: 10 },
    { name: "사진", state: false, idx: 11 },
  ], //투어에 대한 테마 정보들
};

const reducer = (state, action) => {
  switch (action.type) {
    case "title":
      return { ...state, title: action.value };
    case "thumbnail_image":
      return { ...state, thumbnail_image: action.value };
    case "description":
      return { ...state, description: action.value };
    case "location":
      return { ...state, location: action.value };
    case "price":
      return { ...state, price: action.value };
    case "start_date":
      return { ...state, start_date: action.value };
    case "end_date":
      return { ...state, end_date: action.value };
    case "max_participant":
      return { ...state, max_participant: action.value };
    case "plans":
      return { ...state, plans: action.value };
    case "themes":
      return { ...state, themes: action.value };
    default:
      return state;
  }
};

export const TourDataContext = createContext(null);
export const TourDispatchContext = createContext(null);

const Tour_Create = () => {
  const [Tourdata, dispatch] = useReducer(reducer, {
    ...initTourData,
    start_date: new Date().getTime(),
    end_date: new Date().getTime(),
  });

  const goBeforePage = useCallback(() => {
    settNowPageLook((cur) => (cur < 1 ? cur : cur - 1));
  }, []);

  const goAfterPage = useCallback(() => {
    settNowPageLook((cur) => (cur > 4 ? cur : cur + 1));
  }, []);

  const onTourDataChange = (type, value) => {
    dispatch({ type, value });
  };

  const [nowPageLook, settNowPageLook] = useState(1);
  return (
    <div>
      <TourDataContext.Provider value={Tourdata}>
        <TourDispatchContext.Provider value={{ onTourDataChange }}>
          {nowPageLook === 1 ? (
            <Create1 goAfterPage={goAfterPage} />
          ) : nowPageLook === 2 ? (
            <Create2 goBeforePage={goBeforePage} goAfterPage={goAfterPage} />
          ) : nowPageLook === 3 ? (
            <Create3 goBeforePage={goBeforePage} goAfterPage={goAfterPage} />
          ) : (
            <Confirm goBeforePage={goBeforePage} />
          )}
        </TourDispatchContext.Provider>
      </TourDataContext.Provider>
    </div>
  );
};

export default Tour_Create;
