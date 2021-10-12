package com.gnudios.libgdx.model;

public class ModelAction {

    private AbstractObject aObject;

    public ModelAction(AbstractObject aObject) {
        this.aObject = aObject;
    }

    /**
     * !WARNING! Feature not implemented! !WARNING! This is an example
     * functionality. Still removes 50 "power". Returns true if .....
     */
    public boolean Jump() {
        System.out.println(
                "!WARNING! Using not implemented function in Action.class. If this function does as intended please remove warning message.");
        if (aObject.getPower() <= 50) {
            return false;
        }

        this.setPower(aObject.getPower() - 50);
        return true;
    }

    /**
     * !WARNING! Feature not implemented! !WARNING! This is an example
     * functionality. Still removes 50 "extra power". Returns true if .....
     */
    public boolean Boost() {
        System.out.println(
                "!WARNING! Using not implemented function in Action.class. If this function does as intended please remove warning message.");
        if (aObject.getExtraPower() <= 50) {
            return false;
        }
        this.setPower(aObject.getExtraPower() - 50);
        return true;
    }

    /**
     * !WARNING! Feature not implemented! !WARNING! This is an example
     * functionality. Still removes 50 "super power", Returns true if .....
     */
    public boolean fly() {
        System.out.println(
                "!WARNING! Using not implemented function in Action.class. If this function does as intended please remove warning message.");
        if (aObject.getExtraPower() <= 50) {
            return false;
        }
        this.setPower(aObject.getExtraPower() - 50);
        return true;
    }

    /**
     * Set life to certain amount, avoid using this for "Taking damage"/"Gaining
     * health". Should mainly be used for setting initial life etc. Returns true
     * if method successfully sets amount of life.
     *
     * @param lifeAmount
     * @return boolean
     */
    public boolean setLife(int lifeAmount) {
        aObject.setLife(lifeAmount);
        return true;
    }

    /**
     * Lose certain amount of life. If life reaches 0 or below, sets variable
     * "alive" to false. Returns true if method successfully reduces amount of
     * life.
     *
     * @param amountLost
     * @return boolean
     */
    public boolean removeLife(int amountLost) {
        aObject.setLife(aObject.getLife() - amountLost);
        if (aObject.getLife() <= 0) {
            aObject.setAlive(false);
        }
        return true;
    }

    /**
     * Add certain amount of life. If life goes above 1, sets variable "alive"
     * to true. Returns true if method successfully reduces amount of life.
     *
     * @param amountGained
     * @return boolean
     */
    public boolean addLife(int amountGained) {
        aObject.setLife(aObject.getLife() + amountGained);
        if (aObject.getLife() <= 0) {
            aObject.setAlive(true);
        }
        return true;
    }

    /**
     * Uses .removeLife method first to remove life from target. Look into docs
     * for .removeLife. Uses .addLife method second to add life to this object.
     * Look into docs for .addLife. Steal life ONLY STEALS THE AMOUNT TARGET HAS
     * LEFT. AmountStolen DO NOT surpass health amount. Returns true if method
     * successfully reduces amount of life and adds to this objects life.
     *
     * @param amountStolen
     * @param target
     * @return boolean
     */
    public boolean stealLife(int amountStolen, AbstractObject target) {
        if (target.getLife() < amountStolen) {
            target.getAction().removeLife(target.getLife());
            return this.addLife(target.getLife());
        }
        target.getAction().removeLife(amountStolen);
        return this.addLife(amountStolen);
    }

    /**
     * Uses .removeLife method first to remove life from target. Look into docs
     * for .removeLife. Uses .addLife method second to add life to this object.
     * Look into docs for .addLife. Steal life STEALS THE ENTIRE AMOUNT, EVEN IF
     * TARGET HAS LESS HEALTH. AmountStolen DO surpass health amount. Returns
     * true if method successfully reduces amount of life and adds to this
     * objects life.
     *
     * @param amountStolen
     * @param target
     * @return boolean
     */
    public boolean stealLifeSurpassHealth(int amountStolen, AbstractObject target) {
        target.getAction().removeLife(amountStolen);
        return this.addLife(amountStolen);
    }

    ////////////// NORMAL POWER

    /**
     * Set power to certain amount, avoid using this for "Losing power/gaining
     * power". Should mainly be used for setting initial power etc. Returns true
     * if method successfully sets amount of power.
     *
     * @param f
     * @return boolean
     */
    public boolean setPower(float f) {
        aObject.setPower(f);
        return true;
    }

    /**
     * Lose certain amount of power. Returns false if power lost is higher than
     * currently available. (Does not remove anything) Returns true if method
     * successfully reduces amount of power.
     *
     * @param amountLost
     * @return boolean
     */
    public boolean removePower(float amountLost) {
        if (aObject.getPower() > 0 && aObject.getPower() - amountLost >= 0) {
            aObject.setPower(aObject.getPower() - amountLost);
            return true;
        }
        return false;
    }

    /**
     * Add certain amount of power. If power reaces max power, will be set to
     * max power. Returns true if method successfully reduces amount of power.
     *
     * @param amountGained
     * @return boolean
     */
    public boolean addPower(float amountGained) {
        aObject.setPower(aObject.getPower() + amountGained);
        if (aObject.getPower() >= aObject.getMaxPower()) {
            aObject.setPower(aObject.getMaxPower());
            return false;
        }
        return true;
    }

    ////////////// SUPER POWER

    /**
     * Set power to certain amount, avoid using this for "Losing power/gaining
     * power". Should mainly be used for setting initial power etc. Returns true
     * if method successfully sets amount of power.
     *
     * @param powerAmount
     * @return boolean
     */
    public boolean setSuperPower(int powerAmount) {
        aObject.setSuperPower(powerAmount);
        return true;
    }

    /**
     * Lose certain amount of power. Returns false if power lost is higher than
     * currently available. (Does not remove anything) Returns true if method
     * successfully reduces amount of power.
     *
     * @param amountLost
     * @return boolean
     */
    public boolean removeSuperPower(int amountLost) {
        aObject.setSuperPower(aObject.getSuperPower() - amountLost);
        return aObject.getSuperPower() > 0;
    }

    /**
     * Add certain amount of power. If power reaces max power, will be set to
     * max power. Returns true if method successfully reduces amount of power.
     *
     * @param amountGained
     * @return boolean
     */
    public boolean addSuperPower(int amountGained) {
        aObject.setSuperPower(aObject.getSuperPower() + amountGained);
        if (aObject.getSuperPower() >= aObject.getMaxSuperPower()) {
            aObject.setSuperPower(aObject.getMaxSuperPower());
            return false;
        }
        return true;
    }

    ////////////// EXTRA POWER

    /**
     * Set power to certain amount, avoid using this for "Losing power/gaining
     * power". Should mainly be used for setting initial power etc. Returns true
     * if method successfully sets amount of power.
     *
     * @param powerAmount
     * @return boolean
     */
    public boolean setExtraPower(int powerAmount) {
        aObject.setExtraPower(powerAmount);
        return true;
    }

    /**
     * Lose certain amount of power. Returns false if power lost is higher than
     * currently available. (Does not remove anything) Returns true if method
     * successfully reduces amount of power.
     *
     * @param amountLost
     * @return boolean
     */
    public boolean removeExtraPower(int amountLost) {
        aObject.setExtraPower(aObject.getExtraPower() - amountLost);
        return aObject.getExtraPower() > 0;
    }

    /**
     * Add certain amount of power. If power reaces max power, will be set to
     * max power. Returns true if method successfully reduces amount of power.
     *
     * @param amountGained
     * @return boolean
     */
    public boolean addExtraPower(int amountGained) {
        aObject.setExtraPower(aObject.getExtraPower() + amountGained);
        if (aObject.getExtraPower() >= aObject.getMaxExtraPower()) {
            aObject.setExtraPower(aObject.getMaxExtraPower());
            return false;
        }
        return true;
    }

    /**
     * Set defense to certain amount. Returns true if method successfully sets
     * amount of defense.
     *
     * @param defenseAmount
     * @return boolean
     */
    public boolean setDefense(int defenseAmount) {
        aObject.setDefense(defenseAmount);
        return true;
    }

    /**
     * Set armor to certain amount. Returns true if method successfully sets
     * amount of armor
     *
     * @param armorAmount
     * @return boolean
     */
    public boolean setArmor(int armorAmount) {
        aObject.setArmor(armorAmount);
        return true;
    }

    /**
     * Set resistance to certain amount. Returns true if method successfully
     * sets amount of resistance
     *
     * @param resAmount
     * @return boolean
     */
    public boolean setResistance(int resAmount) {
        aObject.setResistance(resAmount);
        return true;
    }

    /**
     * Set damage to certain amount. Returns true if method successfully sets
     * amount of damage.
     *
     * @param damageAmount
     * @return boolean
     */
    public boolean setDamage(int damageAmount) {
        aObject.setDamage(damageAmount);
        return true;
    }

    /**
     * Set level to certain amount, avoid using this for "Level up / Level down"
     * Should mainly be used for setting initial level etc. Returns true if
     * method successfully sets amount of level.
     *
     * @param level
     * @return boolean
     */
    public boolean setLevel(int level) {
        aObject.setLevel(level);
        return true;
    }

    /**
     * Increase current level by 1. Cannot surpass max level. Returns true if
     * method successfully increases level by 1.
     *
     * @return boolean
     */
    public boolean levelUp() {
        if ((aObject.getLevel() + 1) > aObject.getMaxLevel()) {
            return false;
        }
        aObject.setLevel(aObject.getLevel() + 1);
        return true;
    }

    /**
     * Increase current level by certain amount. Cannot surpass max level. Sets
     * current level to max, if level increase tries to surpass max level.
     * Returns true if method successfully increases the total amount of levels.
     *
     * @param levelAmount
     * @return boolean
     */
    public boolean levelUp(int levelAmount) {
        if ((aObject.getLevel() + levelAmount) > aObject.getMaxLevel()) {
            aObject.setLevel(aObject.getMaxLevel());
            return false;
        }
        aObject.setLevel(aObject.getLevel() + levelAmount);
        return true;
    }

    /**
     * Decreases current level by 1. Cannot go below level 1. Returns true if
     * method successfully decreases level by 1.
     *
     * @return boolean
     */
    public boolean levelDown() {
        if ((aObject.getLevel() - 1) < 1) {
            return false;
        }
        aObject.setLevel(aObject.getLevel() - 1);
        return true;
    }

    /**
     * Decreases current level by certain amount. Cannot go below level 1. Sets
     * current level to 1, if level increase tries to surpass max level. Returns
     * true if method successfully increases the total amount of levels.
     *
     * @param levelAmount
     * @return boolean
     */
    public boolean levelDown(int levelAmount) {
        if ((aObject.getLevel() - levelAmount) < 1) {
            aObject.setLevel(1);
            return false;
        }
        aObject.setLevel(aObject.getLevel() - levelAmount);
        return true;
    }

    /**
     * Set range to certain amount. Returns true if method successfully sets
     * amount of range
     *
     * @param f
     * @return boolean
     */
    public boolean setRange(float f) {
        aObject.setRange(f);
        return true;
    }

    /**
     * Set type to certain name (string) Returns true if method successfully
     * sets type.
     *
     * @param type
     * @return boolean
     */
    public boolean setType(String type) {
        aObject.setType(type);
        return true;
    }

    /**
     * Sets active to boolean parameter Returns this.isActive method.
     *
     * @param active
     * @return boolean
     */
    public boolean setActive(boolean active) {
        aObject.setActive(active);
        return aObject.isActive();
    }

    /**
     * Sets active to true Returns object isActive() method, showing whether
     * activating failed or not.
     *
     * @return boolean
     */
    public boolean activate() {
        aObject.setActive(true);
        return aObject.isActive();
    }

    /**
     * Sets active to false Returns object isActive() method, showing whether
     * deactivating failed or not.
     *
     * @return boolean
     */
    public boolean deactivate() {
        aObject.setActive(false);
        return aObject.isActive();
    }

    /**
     * Sets alive to boolean parameter Returns this.isalive method.
     *
     * @param alive
     * @return boolean
     */
    public boolean setAlive(boolean alive) {
        aObject.setAlive(alive);
        return aObject.isAlive();
    }

    /**
     * Sets alive to true Returns object isAlive() method, showing whether
     * activating failed or not.
     *
     * @return boolean
     */
    public boolean resurrect() {
        aObject.setAlive(true);
        return aObject.isAlive();
    }

    /**
     * Sets alive to false Returns object isAlive() method, showing whether
     * deactivating failed or not.
     *
     * @return boolean
     */
    public boolean kill() {
        aObject.setAlive(false);
        return aObject.isAlive();
    }

    /**
     * Sets visible to boolean parameter Returns this.isVisible method.
     *
     * @param visible
     * @return boolean
     */
    public boolean setVisible(boolean visible) {
        aObject.setVisible(visible);
        return aObject.isVisible();
    }

    /**
     * Sets visible to true Returns object isVisible() method, showing whether
     * activating failed or not.
     *
     * @return boolean
     */
    public boolean show() {
        aObject.setVisible(true);
        return aObject.isVisible();
    }

    /**
     * Sets visible to true Returns object isVisible() method, showing whether
     * activating failed or not.
     *
     * @return boolean
     */
    public boolean makeVisible() {
        aObject.setVisible(true);
        return aObject.isVisible();
    }

    /**
     * Sets visible to false Returns object isVisible() method, showing whether
     * deactivating failed or not.
     *
     * @return boolean
     */
    public boolean hide() {
        aObject.setVisible(false);
        return aObject.isVisible();
    }

    /**
     * Sets visible to false Returns object isVisible() method, showing whether
     * deactivating failed or not.
     *
     * @return boolean
     */
    public boolean makeInvisible() {
        aObject.setVisible(false);
        return aObject.isVisible();
    }

    /**
     * Sets price to a certain value Returns true if price is successfully set.
     *
     * @param price
     * @return boolean
     */
    public boolean setPrice(int price) {
        aObject.setPrice(price);
        return true;
    }

    /**
     * Increases price by a certain value. Returns true if price is successfully
     * increased.
     *
     * @param priceIncrease
     * @return boolean
     */
    public boolean increasePrice(int priceIncrease) {
        aObject.setPrice(aObject.getPrice() + priceIncrease);
        return true;
    }

    /**
     * Decreases price by a certain value. Returns true if price is sucessfully
     * decreased.
     *
     * @param priceDecrease
     * @return boolean
     */
    public boolean decreasePrice(int priceDecrease) {
        aObject.setPrice(aObject.getPrice() - priceDecrease);
        return true;
    }

    /**
     * Sets value to a certain value Returns true if price is successfully set.
     *
     * @param value
     * @return boolean
     */
    public boolean setValue(int value) {
        aObject.setValue(value);
        return true;
    }

    /**
     * Increases value by a certain amount. Returns true if value is
     * successfully increased.
     *
     * @param valueIncrease
     * @return boolean
     */
    public boolean increaseValue(int valueIncrease) {
        aObject.setValue(aObject.getValue() + valueIncrease);
        return true;
    }

    /**
     * Decreases value by a certain amount. Returns true if value is
     * successfully decreased.
     *
     * @param valueDecrease
     * @return boolean
     */
    public boolean decreaseValue(int valueDecrease) {
        aObject.setValue(aObject.getValue() - valueDecrease);
        return true;
    }

    /**
     * Max amount of power to store
     *
     * @param maxPower
     * @return boolean
     */
    public boolean setMaxPower(int maxPower) {
        aObject.setMaxPower(maxPower);
        return true;
    }

    /**
     * Max amount of coins to store
     *
     * @param maxCoins
     * @return boolean
     */
    public boolean setMaxCoins(int maxCoins) {
        aObject.setMaxCoins(maxCoins);
        return true;
    }

    /**
     * Max amount of coins to store
     *
     * @param coins
     * @return boolean
     */
    public boolean setCoinAmount(int coins) {
        if (coins <= aObject.getMaxCoins()) {
            aObject.setCoinsCollected(coins);
            return true;
        } else {
            aObject.setCoinsCollected(aObject.getMaxCoins());
            return false;
        }

    }

    /**
     * Max amount of power to store
     *
     * @param coins
     * @return boolean
     */
    public boolean addCoinAmount(int coins) {
        if (aObject.getCoinsCollected() + coins <= aObject.getMaxCoins()) {
            aObject.setCoinsCollected(aObject.getCoinsCollected() + coins);
            return true;
        } else if (aObject.getCoinsCollected() + coins > aObject.getMaxCoins()) {
            aObject.setCoinsCollected(aObject.getMaxCoins());
            return false;
        }

        return false;
    }

    public boolean resetCoinAmount() {
        aObject.setCoinsCollected(0);
        return true;
    }

}