import styles from "./Profile.module.css";
import language_img from "../../assets/language.png";

const Profile = ({ image, nickname, language, created_at }) => {
  const currentYear = new Date().getFullYear()
  const experienceYears = currentYear - created_at.getFullYear()
  
  return (
    <div className={styles.TotalContainer}>
      <div className={styles.TopContainer}>
        <img className={styles.Image} src={image} alt="" />
        <h3 className={styles.Nickname}>{nickname}</h3>
      </div>
      <div className={styles.CenterContainer}>
        <p className={styles.P}>경력 : {experienceYears}년</p>
      </div>

      <div className={styles.BottomContainer}>
        <img src={language_img} alt="" />
        <p className={styles.P}>{language}</p>
      </div>
    </div>
  );
};

export default Profile;
