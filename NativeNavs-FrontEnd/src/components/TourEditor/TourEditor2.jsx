import { useContext, useEffect, useReducer, useRef, useState } from "react";
import styles from "./TourEditor2.module.css";
import Plan_Item from "@/components/Plan_Item/Plan_Item";
import { getStaticImage } from "@/utils/get-static-image";
import PlanModal from "@/components/PlanModal/PlanModal";
import { createPortal } from "react-dom";
import Button from "@/components/Button/Button";
import { TourDataContext, TourDispatchContext } from "./TourEditorHead";

const reducer = (state, action) => {
  switch (action.type) {
    case "INIT": {
      return action.data;
    }
    case "DELETE": {
      return state.filter((item) => item.id !== action.id);
    }
    case "ADD": {
      return [...state, action.data];
    }
    case "EDIT": {
      return state.map((item) =>
        item.id != action.data.id ? item : action.data
      );
    }
  }
};

const TourEditor2 = ({ goBeforePage, goAfterPage }) => {
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
        planCount.current = Math.max(planCount.current, plan.id);
      });
    }
  }, [plans]);

  const onPlanDeleteEvent = (id) => {
    dispatch({
      type: "DELETE",
      id,
    });
  };
  const onPlanAddEvent = ({
    field,
    description,
    latitude,
    longitude,
    addressFull,
    image,
  }) => {
    dispatch({
      type: "ADD",
      data: {
        id: ++planCount.current,
        field,
        description,
        latitude,
        longitude,
        addressFull,
        image,
      },
    });
  };

  const onPlanEditEvent = ({
    id,
    field,
    description,
    latitude,
    longitude,
    addressFull,
    image,
  }) => {
    dispatch({
      type: "EDIT",
      data: {
        id,
        field,
        description,
        latitude,
        longitude,
        addressFull,
        image,
      },
    });
  };

  return (
    <>
      <div className={styles.TourEditor2}>
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
                  key={planItem.id}
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

export default TourEditor2;
