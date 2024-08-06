import React from 'react';
import styles from './WishList.module.css';
import WishListItem from '../components/WishListItem/WishListItem';

function WishList() {

    
    return (
        <div className={styles.container}>
            <WishListItem />
        </div>
    );
}

export default WishList;
