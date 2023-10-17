package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.quizapp.adapter.CustomRulesPagerAdapter;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

public class RuleActivity extends AppCompatActivity {

    public static ViewPager ruleViewPager;
    CustomRulesPagerAdapter rulesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        SpringDotsIndicator dotsIndicator = findViewById(R.id.spring_dots_indicator);
        ImageView backButton = findViewById(R.id.imageBackToMain);

        // Event handling for the back button
        backButton.setOnClickListener(v -> finish());

        // Initialize dotsIndicator and ViewPager
        ruleViewPager = findViewById(R.id.rulesViewPager);
        rulesAdapter = new CustomRulesPagerAdapter(this);
        ruleViewPager.setAdapter(rulesAdapter);
        dotsIndicator.setViewPager(ruleViewPager);
    }
}
