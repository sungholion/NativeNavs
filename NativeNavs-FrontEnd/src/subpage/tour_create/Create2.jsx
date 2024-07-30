import { useState } from "react";
import styles from "./Create2.module.css";
import Plan_Item from "@/components/Plan_Item/Plan_Item";
import { getStaticImage } from "@/utils/get-static-image";
import PlanAdd from "./PlanAdd";

const plan_info = {
  plan_id: 2,
  tour_id: 1,
  title: "Plan제목",
  description:
    "Plan에 대한 내용임 Plan에 대한 내용임 Plan에 대한 내용임Plan에 대한 내용임  Plan에 대한 내용임Plan에 대한 내용임Plan에 대한 내용임 Plan에 대한 내용임 Plan에 대한 내용임 Plan에 대한 내용임 Plan에 대한 내용임 ",
  latitude: 37.5642135,
  longitude: 127.0016958,
  address_full: "서울특별시 중심지",
  image:
    "https://d3kxs6kpbh59hp.cloudfront.net/community/COMMUNITY/ba663a6c2e8446b6ae79f076a33a8b88/d4107e3bf8cd46c5b0ca9b7a11ce9087_1652305717.png",
};

const plan_info2 = {
  plan_id: 3,
  tour_id: 2,
  title: "Plan제목222",
  description: "Plan에 대한 다른것 내용 ",
  latitude: 37.5642135,
  longitude: 127.0016958,
  address_full: "서울특별시 중심지",
  image:
    "https://d3kxs6kpbh59hp.cloudfront.net/community/COMMUNITY/ba663a6c2e8446b6ae79f076a33a8b88/d4107e3bf8cd46c5b0ca9b7a11ce9087_1652305717.png",
};

const plan_info3 = {
  plan_id: 5,
  tour_id: 4,
  title: "제목3",
  description: "ABC초콜릿 ",
  latitude: 37.5642135,
  longitude: 127.0016958,
  address_full: "서울특별시 중심지",
  image:
    "https://d3kxs6kpbh59hp.cloudfront.net/community/COMMUNITY/ba663a6c2e8446b6ae79f076a33a8b88/d4107e3bf8cd46c5b0ca9b7a11ce9087_1652305717.png",
};

const onClickEvent = () => {
  console.log("와 클릭이벤트!");
};
// const onDeleteEvent = () => {
//   console.log("와 삭제이벤트!");
// };

const Create2 = () => {
  const [planList, setPlanList] = useState([]); //[plan_info, plan_info2, plan_info3]
  const [modalOpen, setModalOpen] = useState(false);

  const onDeleteEvent = (plan_id) => {
    setPlanList((cur) =>
      cur.filter((plan) => {
        return plan.plan_id != plan_id;
      })
    );
  };
  return (
    <>
      {modalOpen ? (
        <div>
          <PlanAdd
            toggleEvent={() => {
              setModalOpen(false);
            }}
          />
          <div
            style={{
              backgroundColor: modalOpen ? "rgb(240,240,240)" : "",
              opacity: 0.2,
              zIndex: 2,
              width: "110vw",
              height: "110vh",
            }}
          />
        </div>
      ) : undefined}
      <div className={styles.Create2}>
        <section className={styles.Header}>
          <p>일정 목록</p>
          <img
            src={getStaticImage("add")}
            alt=""
            onClick={() => {
              setModalOpen((cur) => !cur);
              console.log("Modal State :" + modalOpen);
            }}
          />
        </section>
        <section className={styles.PlanList}>
          {planList.length ? (
            planList.planList.map((planItem) => {
              return <Plan_Item key={planItem.plan_id} {...planItem} />;
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
        </section>
      </div>
    </>
  );
};

export default Create2;
