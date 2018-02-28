package com.example.plantsvszombie.layer;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;


import android.view.MotionEvent;


import com.example.plantsvszombie.R;
import com.example.plantsvszombie.domain.ShowPlant;
import com.example.plantsvszombie.domain.ShowZombie;
import com.example.plantsvszombie.domain.ShowZombie2;
import com.example.plantsvszombie.domain.Sun;
import com.example.plantsvszombie.engine.GameEngine;
import com.example.plantsvszombie.utils.CommonUtils;


/**
 * 鎴樻枟鍥惧眰
 * 
 * @author Kevin
 * 
 */
public class FightLayer extends BaseLayer {

	public static  int TAG_SELECTED_BOX = 1;
	public static  int TAG_TOTAL_MONEY = 2;
	private CCTMXTiledMap map;
	private ArrayList<CGPoint> mZombiePoint;// 鍍靛案鍧愭爣鐐归泦鍚�
	private CCSprite mSelectedBox;// 宸查�夋鐗╂
	private CCSprite mChooseBox;// 閫夋嫨妞嶇墿妗�

	private ArrayList<ShowZombie> mShowZombies;// 灞曠ず鐨勫兊灏搁泦鍚�
	private ArrayList<ShowZombie2> mShowZombies2;

	private CopyOnWriteArrayList<ShowPlant> mShowPlants;// 閫夋嫨妗嗗唴妞嶇墿鐨勯泦鍚�
	private CopyOnWriteArrayList<ShowPlant> mSelectedPlants = new CopyOnWriteArrayList<ShowPlant>();// 宸查�夋鐗╃殑闆嗗悎

	public FightLayer() {
		loadMap();
		loadZombie();
	}
	
	public void resetState(){
		TAG_SELECTED_BOX = 1;
		TAG_TOTAL_MONEY = 2;
		map = null;
		mZombiePoint.clear();
		mSelectedBox=null;
		mChooseBox=null;
		mShowZombies.clear();
		mShowPlants.clear();
		mSelectedPlants.clear();
		label=null;
		
	}

	/**
	 * 鍔犺浇鍦板浘
	 */
	private void loadMap() {
		map = CCTMXTiledMap.tiledMap("image/fight/map_day.tmx");
		this.addChild(map);
		mZombiePoint = CommonUtils.loadPoint(map, "zombies");
		moveMap();
	}

	/**
	 * 鍔犺浇鍍靛案
	 */
	private void loadZombie() {
//		mShowZombies = new ArrayList<ShowZombie>();
//		for (CGPoint point : mZombiePoint) {
//			ShowZombie zombie = new ShowZombie();
//			zombie.setPosition(point);
//			map.addChild(zombie);
//			mShowZombies.add(zombie);
//		}
		
		mShowZombies = new ArrayList<ShowZombie>();
		mShowZombies2=new ArrayList<ShowZombie2>();
		for(int i=0;i<mZombiePoint.size();i++){
			if(i%2==0){
				ShowZombie zombie = new ShowZombie();
				zombie.setPosition(mZombiePoint.get(i));
				map.addChild(zombie);
				mShowZombies.add(zombie);
				}else{
					ShowZombie2 zombie2=new ShowZombie2();
					zombie2.setPosition(mZombiePoint.get(i));
					map.addChild(zombie2);
					mShowZombies2.add(zombie2);
				}
		}
	}

	/**
	 * 绉诲姩鍦板浘
	 */
	private void moveMap() {
		float offset = winSize.width - map.getContentSizeRef().width;// 鍦板浘绉诲姩鐨勫亸绉婚噺

		CCDelayTime delay = CCDelayTime.action(1);// 寤舵椂1绉�
		CCMoveBy move = CCMoveBy.action(2, ccp(offset, 0));

		CCSequence s = CCSequence.actions(delay, move, delay,
				CCCallFunc.action(this, "showPlantBox"));

		map.runAction(s);
	}

	/**
	 * 灞曠ず妞嶇墿妗�
	 */
	public void showPlantBox() {
		setIsTouchEnabled(true);
		showSelectedBox();
		showChooseBox();
	}

	/**
	 * 灞曠ず閫夋嫨妞嶇墿妗�
	 */
	private void showChooseBox() {
		mChooseBox = CCSprite.sprite("image/fight/chose/fight_choose.png");
		mChooseBox.setAnchorPoint(0, 0);// 閿氱偣涓哄乏涓婅
		this.addChild(mChooseBox);

		mShowPlants = new CopyOnWriteArrayList<ShowPlant>();
		for (int i = 1; i <= 9; i++) {
			ShowPlant showPlant = new ShowPlant(i);

			// 娣诲姞鑳屾櫙妞嶇墿鍜屽睍绀烘鐗�, 浣嶇疆涓�鏍�
			mChooseBox.addChild(showPlant.getBgPlant());
			mChooseBox.addChild(showPlant.getShowPlant());

			mShowPlants.add(showPlant);
		}

		// 寮�濮嬫垬鏂�
		btnStart = CCSprite.sprite("image/fight/chose/fight_start.png");
		btnStart.setPosition(mChooseBox.getContentSize().width / 2, 30);
		mChooseBox.addChild(btnStart);
	}

	/**
	 * 灞曠ず宸查�夋鐗╂
	 */
	private void showSelectedBox() {
		mSelectedBox = CCSprite.sprite("image/fight/chose/fight_chose.png");
		mSelectedBox.setAnchorPoint(0, 1);// 閿氱偣涓哄乏涓婅
		mSelectedBox.setPosition(0, winSize.height);
		this.addChild(mSelectedBox, 0, TAG_SELECTED_BOX);

		// 鏄剧ず闃冲厜鏁扮殑鏂囧瓧
		label = CCLabel.labelWithString(String.valueOf(Sun.totalMoney),
				"hkbd.ttf", 15);
		label.setColor(ccc3(0, 0, 0));
		label.setPosition(33, CCDirector.sharedDirector().winSize().height - 62);
		this.addChild(label, 1, TAG_TOTAL_MONEY);
	}

	private boolean isMoving = false;// 鏍囪閫夋嫨鐨勬鐗╂槸鍚︽鍦ㄧЩ鍔�
	private CCSprite btnStart;
	private CCSprite startLabel;
	private CCLabel label;// 鏄剧ず闃冲厜鏁扮殑鏂囧瓧

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {

		if (GameEngine.isStart) {// 鍒ゆ柇娓告垙鏄惁宸茬粡寮�濮�
			GameEngine.getInstance().handleTouch(event);
			return true;
		}

		CGPoint convertTouchToNodeSpace = convertTouchToNodeSpace(event);

		if (CGRect.containsPoint(mChooseBox.getBoundingBox(),
				convertTouchToNodeSpace)) {// 钀藉湪妞嶇墿閫夋嫨妗嗗唴

			if (CGRect.containsPoint(btnStart.getBoundingBox(),
					convertTouchToNodeSpace)) {// 寮�濮嬫垬鏂�
				if (!mSelectedPlants.isEmpty()) {
					gamePrepare();
				}
				return true;
			}

			for (ShowPlant showPlant : mShowPlants) {
				if (CGRect.containsPoint(showPlant.getShowPlant()
						.getBoundingBox(), convertTouchToNodeSpace)) {// 妞嶇墿琚�夋嫨浜�

					if (mSelectedPlants.size() < 5 && !isMoving) {
						isMoving = true;
						mSelectedPlants.add(showPlant);

						// 妞嶇墿绉诲姩鍒板凡閫夋鍐�
						CCMoveTo move = CCMoveTo.action(
								0.5f,
								ccp(75 + (mSelectedPlants.size() - 1) * 53,
										winSize.height - 65));
						CCSequence s = CCSequence.actions(move,
								CCCallFunc.action(this, "unlock"));
						showPlant.getShowPlant().runAction(s);
					}

					break;
				}
			}
		} else if (CGRect.containsPoint(mSelectedBox.getBoundingBox(),
				convertTouchToNodeSpace)) {// 宸查�夋琚偣鍑�
			boolean isSelect = false;
			for (ShowPlant showPlant : mSelectedPlants) {
				if (CGRect.containsPoint(showPlant.getShowPlant()
						.getBoundingBox(), convertTouchToNodeSpace)) {// 妞嶇墿琚�夋嫨浜�

					CCMoveTo move = CCMoveTo.action(0.5f, showPlant
							.getBgPlant().getPosition());// 绉诲姩鍒拌儗鏅鐗╃殑浣嶇疆
					showPlant.getShowPlant().runAction(move);
					mSelectedPlants.remove(showPlant);// 绉婚櫎鍙栨秷鐨勬鐗�
					isSelect = true;
					continue;
				}

				if (isSelect) {// 鏈夋鐗╄鐐瑰嚮
					CCMoveBy move = CCMoveBy.action(0.5f, ccp(-53, 0));// 鍚戝乏鍋忕Щ
					showPlant.getShowPlant().runAction(move);
				}
			}
		}

		return super.ccTouchesBegan(event);
	}

	/**
	 * 鍑嗗鎴樻枟
	 */
	private void gamePrepare() {
		setIsTouchEnabled(false);

		System.out.println("鍑嗗鎴樻枟");
		// 闅愯棌妞嶇墿妗�
		mChooseBox.removeSelf();
		// 鍦板浘绉诲姩鍥炲幓
		moveMapBack();
		// 缂╂斁宸查�夋
		mSelectedBox.setScale(0.65);

		// 閲嶆柊娣诲姞宸查�夌殑妞嶇墿
		for (ShowPlant plant : mSelectedPlants) {
			plant.getShowPlant().setScale(0.65f);// 鍥犱负鐖跺鍣ㄧ缉灏忎簡 瀛╁瓙涓�璧风缉灏�
			plant.getShowPlant().setPosition(
					plant.getShowPlant().getPosition().x * 0.65f,
					plant.getShowPlant().getPosition().y
							+ (winSize.height - plant.getShowPlant()
									.getPosition().y) * 0.35f);// 璁剧疆鍧愭爣

			this.addChild(plant.getShowPlant());
		}

		// 缂╂斁闃冲厜鏁扮殑鏂囧瓧
		label.setPosition(22, CCDirector.sharedDirector().winSize().height - 42);
		label.setScale(0.65f);
	}

	// 鍦板浘绉诲姩鍥炲幓
	private void moveMapBack() {
		float offset = map.getContentSizeRef().width - winSize.width;// 鍦板浘绉诲姩鐨勫亸绉婚噺

		CCDelayTime delay = CCDelayTime.action(1);// 寤舵椂1绉�
		CCMoveBy move = CCMoveBy.action(2, ccp(offset, 0));

		CCSequence s = CCSequence.actions(delay, move, delay,
				CCCallFunc.action(this, "showLabel"));

		map.runAction(s);
	}

	/**
	 * 灞曠ず鏂囧瓧
	 */
	public void showLabel() {
		// 鍥炴敹鍍靛案, 鑺傜渷鍐呭瓨
		for (ShowZombie zombie : mShowZombies) {
			zombie.removeSelf();
		}

		mShowZombies.clear();

		// 鏄剧ず鍑嗗寮�濮嬫垬鏂楃殑鏂囧瓧
		startLabel = CCSprite.sprite("image/fight/startready_01.png");
		startLabel.setPosition(winSize.width / 2, winSize.height / 2);
		this.addChild(startLabel);

		CCAnimate animate = (CCAnimate) CommonUtils.animate(
				"image/fight/startready_%02d.png", 3, false, 0.5f);

		CCSequence s = CCSequence.actions(animate,
				CCCallFunc.action(this, "gameBegin"));

		startLabel.runAction(s);
	}

	/**
	 * 娓告垙寮�濮�
	 */
	public void gameBegin() {
		startLabel.removeSelf();
		System.out.println("娓告垙姝ｅ紡寮�濮�!!!");
		setIsTouchEnabled(true);
		GameEngine.getInstance().gameStart(map, mSelectedPlants,this);
		
		SoundEngine.sharedEngine().playSound(CCDirector.theApp, R.raw.day, true);//鎾斁闊充箰
	}

	// 閫夋嫨妗嗙殑妞嶇墿绉诲姩缁撴潫鍚庤皟姝ゆ柟娉曡В閿�
	public void unlock() {
		isMoving = false;
	}

	public void onDestroy() {
		// TODO Auto-generated method stub
		
		
		//GameEngine.isStart = false;
		System.exit(0);//鏉�姝昏繘绋�,娓呯┖鎵�鏈夐潤鎬佸彉閲�
	}
}
