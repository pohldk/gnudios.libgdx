package com.gnudios.libgdx.iService;

public class IDesktopGoogleService implements IGoogleServices {

    @Override
    public void showBannerAd() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hideBannerAd() {
        // TODO Auto-generated method stub

    }

    @Override
    public void showInterstitialAd(Runnable then) {
        // TODO Auto-generated method stub

    }

    @Override
    public void signIn() {
        // TODO Auto-generated method stub

    }

    @Override
    public void signOut() {
        // TODO Auto-generated method stub

    }

    @Override
    public void rateGame() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isSignedIn() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void submitScoreHardmode(long score) {
        // TODO Auto-generated method stub

    }

    @Override
    public void showScoresHardmode() {
        // TODO Auto-generated method stub

    }

    @Override
    public void submitScoreEasymode(long score) {
        // TODO Auto-generated method stub

    }

    @Override
    public void showScoresEasymode() {
        // TODO Auto-generated method stub

    }

    @Override
    public void intializeiOSInterstitial() {
        // TODO Auto-generated method stub

    }

    @Override
    public String getPhoneId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPhoneName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getLanguage() {
        return System.getProperty("user.language");
    }

}
