package com.xcesso;

import android.content.Intent;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;


/**
 * Created by Shanza on 6/12/2018.
 */

public class MyIntro extends AppIntro {
    // Please DO NOT override onCreate. Use init
    @Override
    public void init(Bundle savedInstanceState) {

        //adding the three slides for introduction app you can ad as many you needed

        //unselectedIndicatorColor = R.color.White;
        //selectedIndicatorColor = R.color.colorPrimaryDark;
        addSlide(AppIntroSampleSlider.newInstance(R.layout.intro1));
        addSlide(AppIntroSampleSlider.newInstance(R.layout.commands_list));
        addSlide(AppIntroSampleSlider.newInstance(R.layout.intro5));
        addSlide(AppIntroSampleSlider.newInstance(R.layout.intro6));
        //addSlide(AppIntroSampleSlider.newInstance(R.layout.guide_to_access));
        //addSlide(AppIntroSampleSlider.newInstance(R.layout.intro8));
        addSlide(AppIntroSampleSlider.newInstance(R.layout.intro9));
        addSlide(AppIntroSampleSlider.newInstance(R.layout.intro10));
        addSlide(AppIntroSampleSlider.newInstance(R.layout.intro11));
        addSlide(AppIntroSampleSlider.newInstance(R.layout.intro12));
        //addSlide(AppIntroSampleSlider.newInstance(R.layout.intro13));
        addSlide(AppIntroSampleSlider.newInstance(R.layout.intro14));
        addSlide(AppIntroSampleSlider.newInstance(R.layout.intro15));



        setBarColor(getResources().getColor(R.color.colorPrimary));
        //setIndicatorColor(selectedIndicatorColor,unselectedIndicatorColor);
        // Show and Hide Skip and Done buttons
        showStatusBar(false);
        showSkipButton(true);

        //showStatusBar(true);

        //setColorSkipButton(getResources().getColor(R.color.colorPrimaryX));
        //setColorDoneText(getResources().getColor(R.color.colorPrimaryX));
       //setSeparatorColor(getResources().getColor(R.color.Aqua));
        // Turn vibration on and set intensity
        // You will need to add VIBRATE permission in Manifest file
        //Add animation to the intro slider
        setDepthAnimation();
    }

    @Override
    public void onSkipPressed() {
        // Do something here when users click or tap on Skip button.
        Intent i = new Intent(getApplicationContext(), nav_drawer.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //i.putExtra("status", "Success");
        startActivity(i);
        finish();
    }

    @Override
    public void onNextPressed() {
    }

    @Override
    public void onDonePressed() {
        Intent i = new Intent(getApplicationContext(), nav_drawer.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //i.putExtra("status", "Success");
        startActivity(i);
        finish();
    }

    @Override
    public void onSlideChanged() {

    }

    @Override
    public void finish() {
        super.finish();
    }


}