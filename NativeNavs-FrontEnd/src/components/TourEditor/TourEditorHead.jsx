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

const DefaultTourData = {
  title: "",
  thumbnailImage: "",
  description: "",
  location: "",
  price: 0,
  startDate: getStringedDate(new Date()),
  endDate: getStringedDate(new Date()),
  maxParticipants: 1,
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

// sucessFunc :  성공시 실행할 함수 - 안드로이드 전용 함수 - TourCreate.jsx 혹은 TourEdit.jsx에서 정의할 것
// FailFunc : 실패시 실행할 함수 - 안드로이드 전용 함수 - TourCreate.jsx 혹은 TourEdit.jsx에서 정의할 것
const TourEditorHead = ({ initData, onSubmit, sucessFunc, failFunc }) => {
  const [user, setUser] = useState(null);

  // 컴포넌트가 마운트될 때 localStorage에서 유저 정보를 가져옴
  useEffect(() => {
    setUser(JSON.parse(localStorage.getItem("user")));
  }, []);
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
            <TourEditor1 goAfterPage={goAfterPage} user={user} />
          ) : nowPageLook === 2 ? (
            <TourEditor2
              goBeforePage={goBeforePage}
              goAfterPage={goAfterPage}
              user={user}
            />
          ) : nowPageLook === 3 ? (
            <TourEditor3
              goBeforePage={goBeforePage}
              goAfterPage={goAfterPage}
              user={user}
            />
          ) : (
            <Confirm
              goBeforePage={goBeforePage}
              onSubmit={onSubmit}
              user={user}
            />
          )}
        </TourDispatchContext.Provider>
      </TourDataContext.Provider>
    </div>
  );
};

export default TourEditorHead;
