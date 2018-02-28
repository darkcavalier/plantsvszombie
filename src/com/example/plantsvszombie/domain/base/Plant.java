
package com.example.plantsvszombie.domain.base;

/**
 * 妞嶇墿
 * 
 * @author Administrator
 */
public abstract class Plant extends BaseElement {

    protected int life = 100;// 鐢熷懡鍊�

    protected int line;// 琛屽彿
    protected int column;// 鍒楀彿

    public Plant(String filepath) {
        super(filepath);
        setScale(0.65);
        setAnchorPoint(0.5f, 0);// 灏嗚В鏋愮殑鐐逛綅鏀惧湪涓よ吙涔嬮棿
    }

    /**
     * 琚敾鍑�
     * 
     * @param attack 鏀诲嚮鍔�
     */
    public void attacked(int attack) {
        life -= attack;
        if (life <= 0) {
            destroy();
        }
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getLife() {
        return life;
    }

}
