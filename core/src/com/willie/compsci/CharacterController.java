package com.willie.compsci;

/**
 * Wrapper class to represent the keys that a particular character may use. Two characters can use
 * the same instance of this class and will thus move under the same controls.
 * 
 * @author William Reed
 */
public class CharacterController {

    private int jumpKey, leftKey, rightKey, fallKey, fireKey;

    /**
     * 
     * @param jumpKey
     * @param leftKey
     * @param rightKey
     */
    public CharacterController(int jumpKey, int leftKey, int rightKey, int fallKey, int fireKey) {
        this.jumpKey = jumpKey;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.fallKey = fallKey;
        this.fireKey = fireKey;
    }

    /**
     * @return the key associated with jumping
     */
    public int getJumpKey() {
        return jumpKey;
    }

    /**
     * Set the key associated with jumping
     * 
     * @param jumpKey the int for the key
     */
    public void setJumpKey(int jumpKey) {
        this.jumpKey = jumpKey;
    }

    /**
     * @return the key associated with left movement
     */
    public int getLeftKey() {
        return leftKey;
    }

    /**
     * Set the key associated with moving left
     * 
     * @param leftKey the int for the key
     */
    public void setLeftKey(int leftKey) {
        this.leftKey = leftKey;
    }

    /**
     * @return the key associated with right movement
     */
    public int getRightKey() {
        return rightKey;
    }

    /**
     * Set the key associated with moving right
     * 
     * @param rightKey the int for the key
     */
    public void setRightKey(int rightKey) {
        this.rightKey = rightKey;
    }
    public int getFallKey(){
    	return fallKey;
    }
    public void setFallKey(int fallKey){
    	this.fallKey = fallKey;
    }
    public int getFireKey(){
    	return fireKey;
    }
    public void setFireKey(int fireKey){
    	this.fireKey = fireKey;
    }
}
