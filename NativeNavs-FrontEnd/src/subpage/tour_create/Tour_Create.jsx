import { createContext, useCallback, useReducer, useState } from "react";
import Create1 from "./Create1";
import Create2 from "./Create2";
import Create3 from "./Create3";
import Confirm from "./Confirm";
import { getStringedDate } from "@/utils/get-stringed-date";

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

const testJSON = {
  userId: 10,
  title: "Summer Vacation",
  thumbnailImage: "http://example.com/image.jpg",
  description: "A relaxing summer vacation tour",
  location: "서울특별시 종로구",
  price: 500000,
  startDate: "2024-08-01",
  endDate: "2024-08-15",
  reviewAverage: 0,
  reviewCount: 0,
  maxParticipants: 6,
  removed: false,
  categoryIds: [1, 2],
  plans: [
    {
      id: 1,
      field: "Field 1",
      description: "Description of plan 1",
      image: "http://example.com/plan1.jpg",
      latitude: 37.5665,
      longitude: 126.978,
      addressFull: "123 Example Street",
    },
    {
      id: 2,
      field: "Field 2",
      description: "Description of plan 2",
      image: "http://example.com/plan2.jpg",
      latitude: 37.567,
      longitude: 126.979,
      addressFull: "456 Example Avenue",
    },
  ],
};

const initTourData = {
  userId: 10,
  title: "",
  thumbnailImage: "",
  description: "",
  location: "",
  price: 0,
  startDate: getStringedDate(new Date()),
  endDate: getStringedDate(new Date()),
  reviewAverage: 0,
  reviewCount: 0,
  maxParticipants: 1,
  removed: false,
  categoryIds: [],
  plans: [],
};

const reducer = (state, action) => {
  switch (action.type) {
    case "title":
      return { ...state, title: action.value };
    case "thumbnailImage":
      return { ...state, thumbnailImage: action.value };
    case "description":
      return { ...state, description: action.value };
    case "location":
      return { ...state, location: action.value };
    case "price":
      return { ...state, price: action.value };
    case "startDate":
      return { ...state, startDate: action.value };
    case "endDate":
      return { ...state, endDate: action.value };
    case "reviewAverage":
      return { ...state, reviewAverage: action.value };
    case "reviewCount":
      return { ...state, reviewCount: action.value };
    case "maxParticipants":
      return { ...state, maxParticipants: action.value };
    case "categoryIds":
      return { ...state, categoryIds: action.value };
    case "plans":
      return { ...state, plans: action.value };
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
