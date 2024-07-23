import styles from "./Profile.module.css";
import language_img from "../../assets/language.png";

const Profile = ({ image, nickname, language }) => {
  return (
    <div className={styles.TotalContainer}>
      
      <div className={styles.TopContainer}>
        
        <div className={styles.ImageContainer}>
          <img src={image} alt="" />
        </div>

        <div className={styles.NicknameContainer}>
          <h3>{nickname}</h3>
        </div>
        
      </div>

      <div className={styles.BottomContainer}>
        <img src={language_img} alt="" />
        <h6>{language}</h6>
      </div>

    </div>
  );
};

export default Profile;
