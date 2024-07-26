import styles from "./CompletedTravel.module.css";
import Image from "../Image/Image.jsx";

const CompletedTravel = ({ id, name, img_url, title, start_date, regional_information }) => {
  console.log(id);

  const formattedDate = new Date(start_date).toLocaleDateString();
  return (
    <div className={styles.completedContainer}>
    <div className={styles.completedTravelContainer}>
      <div className={styles.thumbnailImageContainer}>
        <img className={styles.thumbnail_image} src={img_url} />
      </div>
      <div className={styles.tourDescriptionContainer}>
        <div className={styles.tourTitle}>{title}</div>
        <div className={styles.tourNavName}>{name}</div>
        <div className={styles.startDate}>{formattedDate}{regional_information}</div>
      </div>
    </div>
    </div>
  );
};

export default CompletedTravel;
