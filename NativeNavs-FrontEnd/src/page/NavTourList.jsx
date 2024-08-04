import styles from "./NavTourList.module.css";
import { upcomingTours } from "@/dummy";
import Tour_Item4 from "@components/Tour_Item/Tour_Item4";

const NavTourList = () => {
  const tours = upcomingTours;

  return (
    <div className={styles.NavTourList}>
      <h2 className={styles.Header}>My TourList</h2>
      <div className={styles.TourList}>
        {tours.map((tour) => (
          <Tour_Item4 key={tour.tour_id} tour={tour} />
        ))}
      </div>
    </div>
  );
};

export default NavTourList;
