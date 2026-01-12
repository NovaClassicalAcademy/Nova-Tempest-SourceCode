package org.firstinspires.ftc.teamcode.Utilities;

public class StopWatch {

    private long _startTime;

    public boolean IsStarted(){
        return  _startTime >= 0;
    }
    public void StartTimer() {
        _startTime = System.currentTimeMillis();
    }

    public long GetElapseTime() {
        return System.currentTimeMillis() - _startTime;
    }

    public void ResetTimer() {
        _startTime = -1;
    }
}
