package com.example.plantsvszombie.engine;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.cocos2d.actions.CCScheduler;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;

import com.example.plantsvszombie.domain.Car;
import com.example.plantsvszombie.domain.base.BaseElement.DieListener;
import com.example.plantsvszombie.domain.base.AttackPlant;
import com.example.plantsvszombie.domain.base.Bullet;
import com.example.plantsvszombie.domain.base.CarElement;
import com.example.plantsvszombie.domain.base.Plant;
import com.example.plantsvszombie.domain.base.Zombie;

/**
 * 鎴樼嚎
 * 
 * @author Kevin
 * 
 */
public class FightLine {
    private CCSprite s;
	private HashMap<Integer, Plant> mPlants = new HashMap<Integer, Plant>();// key琛ㄧず妞嶇墿鍦ㄧ鍑犲垪

	private CopyOnWriteArrayList<AttackPlant> mAttackPlants = new CopyOnWriteArrayList<AttackPlant>();// 鍙敾鍑绘�ф鐗╃殑闆嗗悎

	private CopyOnWriteArrayList<Zombie> mZombies = new CopyOnWriteArrayList<Zombie>();// 鍍靛案闆嗗悎
	private CopyOnWriteArrayList<Car> mcar = new CopyOnWriteArrayList<Car>();//杞﹀瓙闆嗗悎

	public FightLine(int i) {

		CCScheduler scheduler = CCScheduler.sharedScheduler();
		scheduler.schedule("attackPlant", this, 0.2f, false);// 姣忛殧0.2绉掓娴嬪兊灏告槸鍚﹀彲浠ユ敾鍑绘鐗�
		scheduler.schedule("createBullet", this, 0.2f, false);// 姣忛殧0.2绉掓娴嬫槸鍚﹁浜х敓瀛愬脊
		scheduler.schedule("attackZombie", this, 0.2f, false);// 姣忛殧0.2绉掓娴嬪瓙寮规槸鍚﹀彲浠ユ敾鍑诲兊灏�
		scheduler.schedule("carattackZombie", this, 0.2f, false);// 姣忛殧0.2绉掓娴嬭溅瀛愭槸鍚﹀彲浠ユ敾鍑诲兊灏�
		scheduler.schedule("fail", this, 0.2f, false);
		
	}
	//娓呯┖hashmap
	public void clearmap(){
		
	}

	/**
	 * 浜х敓瀛愬脊
	 * 
	 * @param f
	 */
	public void createBullet(float f) {
		if (!mZombies.isEmpty() && !mAttackPlants.isEmpty()) {// 鏈夊兊灏稿拰鏀诲嚮鎬ф鐗�
			for (AttackPlant plant : mAttackPlants) {
				plant.createBullet();// 浜х敓瀛愬脊
			}
		}
	}

	/**
	 * 鍍靛案鏀诲嚮妞嶇墿
	 * 
	 * @param f
	 */
	public void attackPlant(float f) {
		if (!mPlants.isEmpty() && !mZombies.isEmpty()) {
			for (Zombie zombie : mZombies) {
				int column = (int) (zombie.getPosition().x / 46 - 1);
				if (mPlants.keySet().contains(column)) {// 鍍靛案褰撳墠鎵�鍦ㄧ殑鍒椾笂,鏈夋鐗╁瓨鍦�
					if (!zombie.isAttacking()) {
						zombie.attack(mPlants.get(column));// 鍍靛案寮�濮嬫敾鍑昏鍒楃殑妞嶇墿
						zombie.setAttacking(true);// 鏍囪姝ｅ湪鏀诲嚮
					}
				}
			}
		}
	}
	
	/**
	 * 杞﹀瓙鏀诲嚮鍍靛案
	 */
	
	public void carattackZombie(float f){
		if(!mZombies.isEmpty() && !mcar.isEmpty()){
			for(Zombie zombie : mZombies){
				int x = (int) zombie.getPosition().x;
				for(Car m:mcar){
					int cx=(int)m.getPosition().x;
					if(x<=cx){
					 
//						Car car=new Car("image/car/LawnCleaner.png");
						
						m.move();
						zombie.attacked(m.getAttack());
						
						
					}
				}
			}
		}
		
		
	}
	//  鍒ゆ柇鏄惁杩樻湁鍍靛案
	public boolean NoZombie(){
		if(mZombies.isEmpty()){
			 return true;
		}else{
			return false;
		}
		
	}

	/**
	 * 瀛愬脊鏀诲嚮鍍靛案
	 * 
	 * @param f
	 */
	public void attackZombie(float f) {
		if (!mZombies.isEmpty() && !mAttackPlants.isEmpty()) {// 鏈夊兊灏稿拰鏀诲嚮鎬ф鐗�
			for (Zombie zombie : mZombies) {
				int x = (int) zombie.getPosition().x;
				int left = x - 20;
				int right = x + 20;

				for (AttackPlant plant : mAttackPlants) {
					List<Bullet> bullets = plant.getBullets();// 鑾峰彇妞嶇墿鐨勫瓙寮�

					for (Bullet bullet : bullets) {
						int bx = (int) bullet.getPosition().x;

						if (bx >= left && bx <= right) {// 瀛愬脊澶勪簬鍙敾鍑荤殑鑼冨洿鍐�
							zombie.attacked(bullet.getAttack());// 鍍靛案鎺夎浜�
							bullet.setVisible(false);// 闅愯棌瀛愬脊
							bullet.setAttack(0);// 璁╁瓙寮规敾鍑诲姏涓�0
						}
					}
				}
			}
		}
	}

	/**
	 * 娣诲姞妞嶇墿
	 * 
	 * @param plant
	 */
	public void addPlant(final Plant plant) {
		plant.setDieListener(new DieListener() {

			@Override
			public void die() {
				mPlants.remove(plant.getColumn());// 绉婚櫎妞嶇墿
				mAttackPlants.remove(plant);
			}
		});
		mPlants.put(plant.getColumn(), plant);

		if (plant instanceof AttackPlant) {// 鍒ゆ柇鏄惁鏄彲鏀诲嚮鐨勬鐗�
			mAttackPlants.add((AttackPlant) plant);
		}
	}

	/**
	 * 娣诲姞鍍靛案
	 * 
	 * @param zombie
	 */
	public void addZombie(final Zombie zombie) {
		// 鍍靛案鐨勬浜″洖璋�
		zombie.setDieListener(new DieListener() {

			@Override
			public void die() {
				mZombies.remove(zombie);// 鍍靛案姝讳骸鍚�,浠庨泦鍚堜腑绉婚櫎
			}
		});
		mZombies.add(zombie);
	}
	
	public void addCar(final CarElement car){
		
		car.setDieListener(new DieListener() {

			@Override
			public void die() {
				mcar.remove(car);// 杞﹀瓙鎺ㄦ帀鍚庯紝浠庨泦鍚堜腑绉婚櫎鎺�
			}
		});
		mcar.add((Car) car);
	}

	//寮瑰嚭澶辫触
	public void fail(float f){
		if(!mZombies.isEmpty()){
			for (Zombie zombie : mZombies) {
				int x = (int) zombie.getPosition().x;
				int left = x - 20;
				
				if(left<=10){
					s = CCSprite.sprite("image/fight/ZombiesWon.jpg");
					s.setPosition(CCDirector.sharedDirector().getWinSize().width/2,
							CCDirector.sharedDirector().getWinSize().height/2);
					zombie.getParent().addChild(s);
					s.setScale(0.5f);
					
					
					
				}
				
			}
			}

	}
	/**
	 * 鍒ゆ柇鎴樼嚎涓婃槸鍚﹀凡缁忔湁妞嶇墿,鏈夌殑璇濆氨涓嶈兘鍐嶅畨鏀�
	 * 
	 * @return
	 */
	public boolean contaionsPlant(Plant plant) {
		// 1, 5, 8 , 5
		return mPlants.keySet().contains(plant.getColumn());
	}
}
