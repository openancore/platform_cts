/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.hardware.cts.helpers.sensoroperations;

import android.content.Context;
import android.hardware.cts.helpers.TestSensorEventListener;

import java.util.concurrent.TimeUnit;

/**
 * A {@link ISensorOperation} used to verify that sensor events and sensor values are correct.
 * <p>
 * Provides methods to set test expectations as well as providing a set of default expectations
 * depending on sensor type.  When {{@link #execute()} is called, the sensor will collect the
 * events and then run all the tests.
 * </p>
 */
public class TestSensorOperation extends VerifiableSensorOperation {
    private final Integer mEventCount;
    private final Long mDuration;
    private final TimeUnit mTimeUnit;

    /**
     * Create a {@link TestSensorOperation}.
     *
     * @param context the {@link Context}.
     * @param sensorType the sensor type
     * @param rateUs the rate that
     * @param maxBatchReportLatencyUs the max batch report latency
     * @param eventCount the number of events to gather
     */
    public TestSensorOperation(Context context, int sensorType, int rateUs,
            int maxBatchReportLatencyUs, int eventCount) {
        this(context, sensorType, rateUs, maxBatchReportLatencyUs, eventCount, null, null);
    }

    /**
     * Create a {@link TestSensorOperation}.
     *
     * @param context the {@link Context}.
     * @param sensorType the sensor type
     * @param rateUs the rate that
     * @param maxBatchReportLatencyUs the max batch report latency
     * @param duration the duration to gather events for
     * @param timeUnit the time unit of the duration
     */
    public TestSensorOperation(Context context, int sensorType, int rateUs,
            int maxBatchReportLatencyUs, long duration, TimeUnit timeUnit) {
        this(context, sensorType, rateUs, maxBatchReportLatencyUs, null, duration, timeUnit);
    }

    /**
     * Private helper constructor.
     */
    private TestSensorOperation(Context context, int sensorType, int rateUs,
            int maxBatchReportLatencyUs, Integer eventCount, Long duration, TimeUnit timeUnit) {
        super(context, sensorType, rateUs, maxBatchReportLatencyUs);
        mEventCount = eventCount;
        mDuration = duration;
        mTimeUnit = timeUnit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doExecute(TestSensorEventListener listener) {
        if (mEventCount != null) {
            mSensorManager.runSensor(listener, mEventCount);
        } else {
            mSensorManager.runSensor(listener, mDuration, mTimeUnit);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected VerifiableSensorOperation doClone() {
        if (mEventCount != null) {
            return new TestSensorOperation(mContext, mSensorType, mRateUs,
                    mMaxBatchReportLatencyUs, mEventCount);
        } else {
            return new TestSensorOperation(mContext, mSensorType, mRateUs,
                    mMaxBatchReportLatencyUs, mDuration, mTimeUnit);
        }
    }
}
