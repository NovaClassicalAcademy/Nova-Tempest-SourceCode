package org.firstinspires.ftc.teamcode.Utilities;

import com.qualcomm.robotcore.util.ElapsedTime;

/// Class acts as a mini-state machine wrapper. It holds a start time, a duration, and the action to perform.
/// Use case: use with a servo motor to set up/down states to allow time for them to complete.
public class TimedAction {

    private final Runnable _startAction;
    private final double _durationMilliseconds;
    private final ElapsedTime _timer;
    private boolean _hasStarted;

    public TimedAction(Runnable action, double durationMilliseconds){
        _startAction = action;
        _durationMilliseconds = durationMilliseconds;
        _timer = new ElapsedTime();
        _hasStarted = false;
    }

    public boolean RunAndUpdate(){
        if (!_hasStarted){
            _startAction.run();
            _timer.reset();
            _hasStarted = true;
        }

        if (_timer.milliseconds() >= _durationMilliseconds){
            _hasStarted = false;
            return  true;
        }

        return  false;
    }

    public void Reset(){
        _hasStarted = false;
    }
}
