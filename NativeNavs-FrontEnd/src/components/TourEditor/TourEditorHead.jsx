import {
  createContext,
  useCallback,
  useEffect,
  useReducer,
  useState,
} from "react";
import TourEditor1 from "./TourEditor1";
import TourEditor2 from "./TourEditor2";
import TourEditor3 from "./TourEditor3";
import Confirm from "./TourEditor4";
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

const DefaultTourData = {
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
    case "init":
      return action.data;
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

const TourEditorHead = ({ initData, onSubmit }) => {
  const [Tourdata, dispatch] = useReducer(reducer, {
    ...DefaultTourData,
    start_date: new Date().getTime(),
    end_date: new Date().getTime(),
  });

  useEffect(() => {
    if (initData) {
      dispatch({
        type: "init",
        data: { ...initData },
      });
    }
  }, [initData]);
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
            <TourEditor1 goAfterPage={goAfterPage} />
          ) : nowPageLook === 2 ? (
            <TourEditor2
              goBeforePage={goBeforePage}
              goAfterPage={goAfterPage}
            />
          ) : nowPageLook === 3 ? (
            <TourEditor3
              goBeforePage={goBeforePage}
              goAfterPage={goAfterPage}
            />
          ) : (
            <Confirm goBeforePage={goBeforePage} onSubmit={onSubmit} />
          )}
        </TourDispatchContext.Provider>
      </TourDataContext.Provider>
    </div>
  );
};

export default TourEditorHead;
