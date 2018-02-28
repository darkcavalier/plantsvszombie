package com.example.plantsvszombie.domain;

import org.cocos2d.actions.base.CCAction;

import com.example.plantsvszombie.domain.base.DefancePlant;
import com.example.plantsvszombie.layer.utils.CommonUtils;


public class Nut extends DefancePlant {

	public Nut() {
		super("image/plant/nut/p_3_01.png");
		// TODO Auto-generated constructor stub
		baseAction();
	}
	
	 public void attacked(int attack) {
	        life -= attack;
	        if (life <= 150) {
	        	CCAction animate = CommonUtils.animate("image/plant/nut/Wallnut_%02d.png",
	    				11, true);
	    		this.runAction(animate);
	        }
	        if(life <= 100){
	        	CCAction animate1 = CommonUtils.animate("image/plant/nut/Wallnuta_%02d.png",
	    				15, true);
	    		this.runAction(animate1);
	        }
	        if(life<=0){
	        	 destroy();
	        }
	    }

	@Override
	public void baseAction() {
		// TODO Auto-generated method stub
        CCAction  animate  =   CommonUtils.animate("image/plant/nut/p_3_%02d.png", 11, true);
        this.runAction(animate);
	}

}
