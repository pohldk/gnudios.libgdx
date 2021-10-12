package com.gnudios.libgdx.iService;

public interface IGoogleServices {
    //for ads
    void showBannerAd();

    void hideBannerAd();

    void showInterstitialAd(Runnable then);

    void intializeiOSInterstitial();

    //for Game Services
    void signIn();

    void signOut();

    void rateGame();

    void submitScoreHardmode(long score);

    void showScoresHardmode();

    void submitScoreEasymode(long score);

    void showScoresEasymode();

    boolean isSignedIn();

    //for phoneinfo for database
    String getPhoneId();

    String getPhoneName();

    String getLanguage();
}


