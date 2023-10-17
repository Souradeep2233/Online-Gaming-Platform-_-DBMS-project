package com.myquizapp.example;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * This is an instrumented test that runs on an Android device.
 *
 * For more information on testing, please visit: http://example.com/testing-docs
 */
@RunWith(AndroidJUnit4.class)
public class CustomInstrumentedTest {
    @Test
    public void testAppContext() {
        // Context of the app under test.
        Context myAppContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.myquizapp.example", myAppContext.getPackageName());
    }
}
