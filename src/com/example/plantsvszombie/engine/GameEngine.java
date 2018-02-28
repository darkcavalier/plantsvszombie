package com.example.plantsvszombie.engine;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import org.cocos2d.actions.CCProgressTimer;
import org.cocos2d.actions.CCScheduler;
import org.cocos2d.actions.base.CCAction;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import android.view.MotionEvent;


import com.example.plantsvszombie.domain.Car;
import com.example.plantsvszombie.domain.DoublePeaPlant;
import com.example.plantsvszombie.domain.GatlingPeaPlant;
import com.example.plantsvszombie.domain.LargeZombie;
import com.example.plantsvszombie.domain.Nut;
import com.example.plantsvszombie.domain.PeaPlant;
import com.example.plantsvszombie.domain.PrimaryZombie;
import com.example.plantsvszombie.domain.ShowPlant;
import com.example.plantsvszombie.domain.SplitPeaPlant;
import com.example.plantsvszombie.domain.Sun;
import com.example.plantsvszombie.domain.SunPlant;
import com.example.plantsvszombie.domain.base.Plant;
import com.example.plantsvszombie.layer.FightLayer;
import com.example.plantsvszombie.layer.MenuLayer;
import com.example.plantsvszombie.utils.CommonUtils;

/**
 * 涓撻棬澶勭悊鎴樻枟閫昏緫鐨勭被 鍗曚緥绫�
 * 
 * @author Kevin
 * 
 */
public class GameEngine {
	private static Sun sun;// 闃冲厜
	private int flag = 0;

	private CCScheduler shceduler = CCScheduler.sharedScheduler();// 璁℃椂鍣�
	private static GameEngine mInstance = new GameEngine();
	private FightLayer fightlayer;
	private CCSprite pause;// 鏆傚仠鎸夐挳
	private CCSprite start;// 寮�濮嬫寜閽�
	private CCSprite newzombie;// 鐪嬫姤鍍靛案
	private CCSprite menu;// 鑿滃崟鏉垮瓙
	private CCSprite returngame;// 杩斿洖娓告垙鎸夐挳
	private CCSprite menubank;// 鑿滃崟鏉垮瓙
	private CCSprite rem;
	private CCSprite rest;
	private CCAction animate;
	private CCTMXTiledMap map;
	private CCScheduler shceduler2;// 鍍靛案璁℃椂鍣�
	private CopyOnWriteArrayList<ShowPlant> mSelectedPlants;
	private CCSprite ret;
	public static boolean isStart;// 鏍囪娓告垙鏄惁姝ｅ紡寮�濮�
	CGSize winSize = CCDirector.sharedDirector().winSize();// 灞忓箷瀹介珮
	private ArrayList<CGPoint> mZombeiPoints;

	private ArrayList<FightLine> mFightLines;

	private int progress = 0;// 鍍靛案鍔犺浇鐨勮繘搴�

	// 鍒濆鍖�5鏉℃垬绾�
	{
		mFightLines = new ArrayList<FightLine>();
		for (int i = 0; i < 5; i++) {
			FightLine line = new FightLine(i);
			mFightLines.add(line);
		}
	}

	private GameEngine() {

	}

	public static GameEngine getInstance() {
		return mInstance;
	}

	/**
	 * 娓告垙寮�濮�
	 */
	public void gameStart(CCTMXTiledMap map,
			CopyOnWriteArrayList<ShowPlant> selectedPlants,
			FightLayer fightlayer) {
		isStart = true;
		this.fightlayer = fightlayer;
		this.map = map;
		this.mSelectedPlants = selectedPlants;

		mZombeiPoints = CommonUtils.loadPoint(map, "road");// 鍔犺浇鍍靛案绉诲姩璺緞
		loadCar();
		// 瀹氭椂鍣�
		
		shceduler2 = CCScheduler.sharedScheduler();
		shceduler.schedule("loadZombie", this, 5, false);// 姣忛殧5绉�,鎵ц涓�娆oadZombie鏂规硶
		shceduler.schedule("loadZombie2", this, 7, false);// 姣忛殧7绉�,鎵ц涓�娆oadZombie2鏂规硶
		// loadZombie();

		loadPlantPoints();

		progress();// 鏄剧ず杩涘害鏉�
		pause();
		men();
		// amZombie();
		// shceduler2.schedule("amZombie", this, 0.2f, false);
	}

	private CGPoint[][] mPlantPoints = new CGPoint[5][9];// 鍒濆鍖栦簩缁存暟缁�,淇濆瓨妞嶇墿鍧愭爣鐐�

	/**
	 * 鍔犺浇妞嶇墿鍧愭爣鐐�
	 */
	private void loadPlantPoints() {
		String format = "tower%02d";

		for (int i = 1; i <= 5; i++) {
			ArrayList<CGPoint> loadPoint = CommonUtils.loadPoint(map,
					String.format(format, i));
			for (int j = 0; j < loadPoint.size(); j++) {
				mPlantPoints[i - 1][j] = loadPoint.get(j);
			}
		}
	}

	/**
	 * 鍔犺浇鍍靛案 float鍙傛暟蹇呴』鏈�,鍚﹀垯CCScheduler鏃犳硶閫氳繃鍙嶅皠璋冪敤
	 */
	public void loadZombie(float f) {
		// System.out.println("float:" + f);
		Random random = new Random();
		int line = random.nextInt(5);// 0,1,2,3,4

		CGPoint startPoint = mZombeiPoints.get(line * 2);// 璧风偣鍧愭爣
		CGPoint endPoint = mZombeiPoints.get(line * 2 + 1);// 缁堢偣鍧愭爣

		PrimaryZombie zombie = new PrimaryZombie(startPoint, endPoint);
		map.addChild(zombie, 1);

		mFightLines.get(line).addZombie(zombie);// 鎶婂兊灏告坊鍔犲埌鎴樼嚎涓�
		JudgeProgress();
		// progress += 5;// 姣忓姞杞戒竴涓兊灏�,杩涘害澧炲姞5
		// progressTimer.setPercentage(progress);// 鏇存柊杩涘害鏉¤繘搴�
		System.out.println(progress);
	}
	
	public void loadZombie2(float f){
		Random random2 = new Random();
		int line2 = random2.nextInt(5);// 0,1,2,3,4
		
		CGPoint startPoint2 = mZombeiPoints.get(line2 * 2);// 璧风偣鍧愭爣
		CGPoint endPoint2 = mZombeiPoints.get(line2 * 2 + 1);// 缁堢偣鍧愭爣
		LargeZombie larzombie = new LargeZombie(startPoint2, endPoint2);
		map.addChild(larzombie, 1);
		mFightLines.get(line2).addZombie(larzombie);// 灏嗕笂绛夊兊灏告坊鍔犲埌鎴樼嚎涓婂幓
		progress += 5;// 姣忓姞杞戒竴涓兊灏�,杩涘害澧炲姞5
		progressTimer.setPercentage(progress);// 鏇存柊杩涘害鏉¤繘搴�
	}
	
	private void loadCar(){
		
		for(int carline=0;carline<5;carline++){
		
		CGPoint carstartPoint=mZombeiPoints.get(carline * 2 + 1);
		CGPoint mcarstartPoint=CCNode.ccp(carstartPoint.x+20,carstartPoint.y+10);
		CGPoint mcarendPoint=CCNode.ccp(carstartPoint.x,carstartPoint.y);
		
		Car car=new Car(mcarstartPoint,mcarendPoint);
		map.addChild(car,1);
		mFightLines.get(carline).addCar(car);// 鎶婅溅瀛愭坊鍔犲埌鎴樼嚎涓�
		
		
		}
		
	}
	
	

	// 妫�楠岃繘搴︽潯
	public void JudgeProgress() {
		if (progress < 100) {
			progress += 2;// 姣忓姞杞戒竴涓兊灏�,杩涘害澧炲姞2
			progressTimer.setPercentage(progress);// 鏇存柊杩涘害鏉¤繘搴�
		} else {
			shceduler.pause(this);

			// shceduler2.schedule("amZombie", this, 0.2f, false);//
		}
	}

	// 妫�楠屾墍鏈夋垬绾挎槸鍚﹁繕鏈夊兊灏�
	public int JudgeZombie() {
		int noZombei = 0;
		if (progress == 100) {
			for (FightLine fightline : mFightLines) {
				if (fightline.NoZombie()) {
					noZombei++;
				}
			}
		}
		return noZombei;
	}

	// 灏唍oZombei鐨勫�间紶缁�
	public void amZombie(float f) {
		int x = JudgeZombie();
		if (x == 5) {
			//
			// start=CCSprite.sprite("image/fight/pausebank.png");
			// start.setPosition(winSize.width/2, winSize.height/2);
			// map.getParent().addChild(start);// 缁欑埗鎺т欢娣诲姞鏆傚仠鍥惧眰;
			// start.setScale(0.6f);
			shceduler.pause(this);
			System.out.println("鏃犲兊灏�");
		}
		System.out.println("鏈�" + x + "涓垬绾挎病鏈夊兊灏�");
	}

	private ShowPlant mShowPlant;// 褰撳墠琚偣鍑荤殑宸查�夋鐗�
	private Plant mPlant;// 褰撳墠瑕佸畨鏀剧殑妞嶇墿

	/**
	 * 涓撻棬澶勭悊鐐瑰嚮浜嬩欢
	 * 
	 * @param event
	 */
	public void handleTouch(MotionEvent event) {
		CGPoint convertTouchToNodeSpace = map.convertTouchToNodeSpace(event);
		CCSprite selectedBox = (CCSprite) map.getParent().getChildByTag(
				FightLayer.TAG_SELECTED_BOX);

		if (CGRect.containsPoint(selectedBox.getBoundingBox(),
				convertTouchToNodeSpace)) {// 宸查�夋琚偣鍑�

			sun = new Sun(selectedBox, CCNode.ccp(0, CCDirector
					.sharedDirector().winSize().height), CCNode.ccp(0,
					CCDirector.sharedDirector().winSize().height));// 闃冲厜

			for (ShowPlant showPlant : mSelectedPlants) {
				if (CGRect.containsPoint(showPlant.getShowPlant()
						.getBoundingBox(), convertTouchToNodeSpace)
						&& sun.check()) {
					if (mShowPlant != null) {
						mShowPlant.getShowPlant().setOpacity(255);// 灏嗕笂涓�涓鐗╄缃负涓嶉�忔槑
					}

					sun.setVisible(false);// 闅愯棌闃冲厜
					sun.setcollect();

					mShowPlant = showPlant;
					showPlant.getShowPlant().setOpacity(100);

					switch (mShowPlant.getId()) {
					case 1:
						mPlant = new PeaPlant();// 瀹夋斁璞岃眴灏勬墜
						if (sun.test(100)) {
							sun.add(100);
							flag = 0;
						} else {
							sun.add(100);
							flag = 1;

						}
						break;
					case 2:
						mPlant = new SunPlant();// 瀹夋斁鍚戞棩钁�
						if (sun.test(50)) {
							sun.add(50);
							flag = 0;
						} else {
							sun.add(50);
							flag = 1;

						}
						break;
					case 3:
						mPlant = new SplitPeaPlant();// 瀹夋斁鍚戞棩钁�
						if (sun.test(125)) {
							sun.add(125);
							flag = 0;
						} else {
							sun.add(125);
							flag = 1;
						}
						break;

					case 4:
						mPlant = new Nut();// 瀹夋斁鍦熻眴
						if (sun.test(50)) {
							sun.add(50);
							flag = 0;
						} else {
							sun.add(50);
							flag = 1;

						}
						break;
					case 8:
						mPlant = new DoublePeaPlant();// 瀹夋斁shuangchong
						if (sun.test(200)) {
							sun.add(200);
							flag = 0;
						} else {
							sun.add(200);
							flag = 1;
						}
						break;

					case 9:
						mPlant = new GatlingPeaPlant();// 瀹夋斁璞岃眴鏈烘灙鎵�
						if (sun.test(250)) {
							sun.add(250);
							flag = 0;
						} else {
							sun.add(250);
							flag = 1;
						}
						break;
					default:
						break;
					}

					break;
				} else {
					sun.setVisible(false);// 闅愯棌闃冲厜
					sun.setcollect();

				}
			}
		} else {// 榧犳爣钀藉湪鑽夊潽涓�
			if (isInGrass(convertTouchToNodeSpace)) {// 鍒ゆ柇鏄惁鍦ㄨ崏鍧殑鏍煎瓙閲�
				System.out.println("鍦ㄦ牸瀛愰噷闈�");

				if (mPlant != null && mShowPlant != null && sun.check()
						&& flag == 0) {
					map.addChild(mPlant);// 瀹夋斁妞嶇墿
					switch (mShowPlant.getId()) {
					case 1:

						sun.reduce(1);

						break;
					case 2:

						sun.reduce(2);

						break;
					case 3:
						sun.reduce(3);

						break;

					case 4:

						sun.reduce(4);

						break;
					case 8:
						sun.reduce(8);
						break;
					case 9:
						sun.reduce(9);
						break;
					default:
						break;
					}
					mShowPlant.getShowPlant().setOpacity(255);// 璁剧疆涓嶉�忔槑

					// 缁欐垬绾挎坊鍔犳鐗�
					mFightLines.get(mPlant.getLine()).addPlant(mPlant);

					mPlant = null;
					mShowPlant = null;
				} else {

					flag = flag + 1;
				}
			}
		}

		// 鍒ゆ柇闃冲厜鏄惁琚偣鍑�
		CopyOnWriteArrayList<Sun> suns = Sun.suns;
		for (Sun sun : suns) {
			if (CGRect.containsPoint(sun.getBoundingBox(),
					convertTouchToNodeSpace)) {// 闃冲厜琚偣鍑�
				sun.collectAction();// 鏀堕泦闃冲厜
				break;
			}
		}
		//杩斿洖鑿滃崟
		try {
			if(CGRect.containsPoint(rem.getBoundingBox(), convertTouchToNodeSpace)){
				isStart = false;
				progress=0;
				sun.setTotalMoney(1000);
				mFightLines = new ArrayList<FightLine>();
				for (int i = 0; i < 5; i++) {
					FightLine line = new FightLine(i);
					mFightLines.add(line);
				}
				mPlant=null;
				GameEngine mInstance = new GameEngine();
				mShowPlant=null;
				map.removeAllChildren(true);
				
				 this.shceduler.resume(this);
				 
				CommonUtils.changeLayer(new MenuLayer());
			}
			  
		} catch (Exception e) {
			// TODO: handle exception
		}
		//閲嶆柊寮�濮嬫父鎴�
		try {
			if(CGRect.containsPoint(rest.getBoundingBox(), convertTouchToNodeSpace)){
				isStart = false;
				progress=0;
				sun=null;
				sun.setTotalMoney(1000);
				mFightLines = new ArrayList<FightLine>();
				for (int i = 0; i < 5; i++) {
					FightLine line = new FightLine(i);
					mFightLines.add(line);
				}
				mPlant=null;
				GameEngine mInstance = new GameEngine();
				mShowPlant=null;
				
				map.removeAllChildren(true);
//				shceduler.purgeSharedScheduler();
//				shceduler = CCScheduler.sharedScheduler();
				 this.shceduler.resume(this);
				 
				CommonUtils.changeLayer(new FightLayer());
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		

		// 鑿滃崟杩斿洖娓告垙
		try {
			if (CGRect.containsPoint(returngame.getBoundingBox(),
					convertTouchToNodeSpace)) {
				map.onEnter();

				this.shceduler.resume(this);
				menubank.removeSelf();
				returngame.removeSelf();
				rem.removeSelf();
				rest.removeSelf();
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		// 鑿滃崟
		try {
			if (CGRect.containsPoint(menu.getBoundingBox(),
					convertTouchToNodeSpace)) {
				map.onExit();
				this.shceduler.pause(this);
				menubank = CCSprite.sprite("image/menu/menu.png");
				menubank.setPosition(winSize.width / 2, winSize.height / 2);
				map.getParent().addChild(menubank);
				menubank.setScale(0.6f);
				returngame = CCSprite.sprite("image/menu/reg.png");
				returngame.setAnchorPoint(0, 0);
				returngame.setPosition(148, 25);
				map.getParent().addChild(returngame);
				returngame.setScale(0.6f);
				rem = CCSprite.sprite("image/menu/rem.png");
				rem.setPosition(winSize.width / 2, winSize.height / 2);
				map.getParent().addChild(rem);
				rem.setScale(0.6f);
				rest = CCSprite.sprite("image/menu/rest.png");
				rest.setPosition(winSize.width / 2, winSize.height / 2 - 40);
				map.getParent().addChild(rest);
				rest.setScale(0.6f);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		try {
			// 鏆傚仠鎸夐挳鍒ゆ柇
			if (CGRect.containsPoint(pause.getBoundingBox(),
					convertTouchToNodeSpace)) {
				System.out.println("鏆傚仠");
				map.onExit();
				this.shceduler.pause(this);
				start = CCSprite.sprite("image/fight/pausebank.png");
				start.setPosition(winSize.width / 2, winSize.height / 2);
				map.getParent().addChild(start);// 缁欑埗鎺т欢娣诲姞鏆傚仠鍥惧眰;
				start.setScale(0.6f);
				newzombie = CCSprite.sprite("image/zombies/pause/pu_01.png");
				newzombie.setAnchorPoint(0, 0);
				newzombie.setPosition(winSize.width / 2 - 55,
						winSize.height / 2-20);
				map.getParent().addChild(newzombie);
				newzombie.setScale(0.6f);
				animate = CommonUtils.animate(
						"image/zombies/pause/pu_%02d.png", 19, true);
				newzombie.runAction(animate);
				ret = CCSprite.sprite("image/fight/return.PNG");
				ret.setPosition(winSize.width / 2, winSize.height / 2 - 116);
				this.map.getParent().addChild(ret);
				ret.setScale(0.5f);

			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		// 鍥炲埌娓告垙
		try {
			if (CGRect.containsPoint(ret.getBoundingBox(),
					convertTouchToNodeSpace)) {
				map.onEnter();
				this.shceduler.resume(this);
				start.removeSelf();
				newzombie.removeSelf();
				ret.removeSelf();
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/*
	 * 鍒ゆ柇鏄惁鍦ㄨ崏鍧殑鏍煎瓙閲�
	 */
	private boolean isInGrass(CGPoint point) {
		int column = (int) (point.x / 46);// 璁＄畻绗嚑鍒�
		int line = (int) ((CCDirector.sharedDirector().winSize().height - point.y) / 54);// 绗嚑琛�

		if (column >= 1 && column <= 9 && line >= 1 && line <= 5) {
			if (mPlant != null) {
				mPlant.setLine(line - 1);// 璁剧疆琛屽彿鍒楀彿
				mPlant.setColumn(column - 1);
				mPlant.setPosition(mPlantPoints[line - 1][column - 1]);// 璁剧疆妞嶇墿鐨勪綅缃�

				if (mFightLines.get(line - 1).contaionsPlant(mPlant)) {// 鍒ゆ柇鎴樼嚎鏄惁鍖呭惈妞嶇墿
					return false;
				}

				return true;
			}
		}

		return false;
	}

	CCProgressTimer progressTimer;

	/**
	 * 鏇存柊杩涘害
	 */
	private void progress() {
		progressTimer = CCProgressTimer
				.progressWithFile("image/fight/progress.png");// 鍒濆鍖栦竴涓繘搴︽潯

		progressTimer.setPosition(
				CCDirector.sharedDirector().getWinSize().width - 70, 13);
		map.getParent().addChild(progressTimer);// 鏄剧ず鍦ㄥ睆骞曚笂

		progressTimer.setScale(0.6f);// 缂╂斁
		// 0-100
		progressTimer.setPercentage(0);// 姣忓鍔犱竴涓兊灏搁渶瑕佽皟鏁磋繘搴︼紝澧炲姞5 0-100
		// 璁剧疆鏍峰紡
		progressTimer
				.setType(CCProgressTimer.kCCProgressTimerTypeHorizontalBarLR);// 姘村钩杩涘害鏉�,浠庡乏鍚戝彸鏇存柊

		// 杩涘害鏉＄殑澶栧３
		CCSprite sprite = CCSprite.sprite("image/fight/flagmeter.png");
		sprite.setPosition(CCDirector.sharedDirector().getWinSize().width - 70,
				13);
		map.getParent().addChild(sprite);
		sprite.setScale(0.6f);

		// 鍏冲崱杩涚▼
		CCSprite name = CCSprite
				.sprite("image/fight/FlagMeterLevelProgress.png");
		name.setPosition(CCDirector.sharedDirector().getWinSize().width - 70, 5);
		map.getParent().addChild(name);
		name.setScale(0.6f);
	}

	public void pause() {

		pause = CCSprite.sprite("image/fight/pause.PNG");
		pause.setPosition(CCDirector.sharedDirector().getWinSize().width - 70,
				CCDirector.sharedDirector().getWinSize().height - 10);
		map.getParent().addChild(pause);
		pause.setScale(0.5f);
	}

	public void men() {
		menu = CCSprite.sprite("image/menu/mainmenu.png");
		menu.setPosition(CCDirector.sharedDirector().getWinSize().width - 130,
				CCDirector.sharedDirector().getWinSize().height - 10);
		map.getParent().addChild(menu);
		menu.setScale(0.5f);

	}
}
