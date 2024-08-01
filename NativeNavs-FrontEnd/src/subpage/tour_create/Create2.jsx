import {
  act,
  useContext,
  useEffect,
  useReducer,
  useRef,
  useState,
} from "react";
import styles from "./Create2.module.css";
import Plan_Item from "@/components/Plan_Item/Plan_Item";
import { getStaticImage } from "@/utils/get-static-image";
import PlanModal from "@/components/PlanModal/PlanModal";
import { createPortal } from "react-dom";
import Button from "@/components/Button/Button";
import { TourDataContext, TourDispatchContext } from "./Tour_Create";

const reducer = (state, action) => {
  switch (action.type) {
    case "INIT": {
      return action.data;
    }
    case "DELETE": {
      return state.filter((item) => item.plan_id !== action.plan_id);
    }
    case "ADD": {
      return [...state, action.data];
    }
    case "EDIT": {
      return state.map((item) =>
        item.plan_id != action.data.plan_id ? item : action.data
      );
    }
  }
};

const Create2 = ({ goBeforePage, goAfterPage }) => {
  const { plans } = useContext(TourDataContext);
  const { onTourDataChange } = useContext(TourDispatchContext);

  const [planList, dispatch] = useReducer(reducer, []);
  const [addmodalOpen, setAddModalOpen] = useState(false);
  const [editmodalOpen, setEditModalOpen] = useState(false);
  const planCount = useRef(0);
  const [editPlanData, setEditPlanData] = useState();

  useEffect(() => {
    if (plans.length) {
      dispatch({
        type: "INIT",
        data: plans,
      });

      plans.forEach((plan) => {
        planCount.current = Math.max(planCount.current, plan.plan_id);
      });
    }
  }, [plans]);

  const onPlanDeleteEvent = (plan_id) => {
    dispatch({
      type: "DELETE",
      plan_id,
    });
  };
  const onPlanAddEvent = ({
    title,
    description,
    latitude,
    longitude,
    address_full,
    image,
  }) => {
    dispatch({
      type: "ADD",
      data: {
        plan_id: ++planCount.current,
        title,
        description,
        latitude,
        longitude,
        address_full,
        image,
      },
    });
  };

  const onPlanEditEvent = ({
    plan_id,
    title,
    description,
    latitude,
    longitude,
    address_full,
    image,
  }) => {
    dispatch({
      type: "EDIT",
      data: {
        plan_id,
        title,
        description,
        latitude,
        longitude,
        address_full,
        image,
      },
    });
  };

  return (
    <>
      <div className={styles.Create2}>
        <section className={styles.Header}>
          <p>일정 목록</p>
          <img
            src={getStaticImage("add")}
            alt=""
            onClick={() => {
              if (planList.length > 20) {
                window.alert("최대 20개 계획만 가능해요!");
                return;
              }
              setAddModalOpen(true);
            }}
          />
          {addmodalOpen &&
            createPortal(
              <PlanModal
                onClose={() => {
                  setAddModalOpen(false);
                }}
                onSubmit={onPlanAddEvent}
              />,
              document.body
            )}
        </section>
        <section className={styles.PlanList}>
          {planList.length ? (
            planList.map((planItem) => {
              return (
                <Plan_Item
                  key={planItem.plan_id}
                  {...planItem}
                  onDeleteEvent={onPlanDeleteEvent}
                  onClickEvent={() => {
                    console.log(planItem);
                    setEditModalOpen(true);
                    setEditPlanData({ ...planItem });
                  }}
                />
              );
            })
          ) : (
            <div className={styles.PlanListEmpty}>
              <p style={{ fontSize: "24px", fontWeight: "bold" }}>
                <span
                  style={{
                    textAlign: "center",
                    padding: 0,
                    margin: 0,
                  }}
                >
                  <img
                    src={getStaticImage("add")}
                    alt=""
                    style={{
                      width: "24px",
                      height: "24px",
                      margin: "auto",
                      padding: 0,
                      textAlign: "center",
                    }}
                  />
                  를 눌러
                </span>
                <br />
                <span>일정을 추가해 주세요</span>
              </p>
            </div>
          )}

          <div>
            {editmodalOpen &&
              createPortal(
                <PlanModal
                  onClose={() => {
                    setEditModalOpen(false);
                  }}
                  onSubmit={onPlanEditEvent}
                  initData={{ ...editPlanData }}
                />,
                document.body
              )}
          </div>
        </section>
        <section className={styles.ButtonSection}>
          <button
            onClick={() => {
              onTourDataChange("plans", [...planList]);
              goBeforePage();
            }}
          >
            뒤로
          </button>
          <button
            onClick={() => {
              onTourDataChange("plans", [...planList]);
              goAfterPage();
            }}
          >
            다음
          </button>
        </section>
      </div>
    </>
  );
};

export default Create2;
