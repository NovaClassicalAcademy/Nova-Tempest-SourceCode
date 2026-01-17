package org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers;

import com.qualcomm.robotcore.hardware.Servo;

public class ScooperController {

    private final SmartServoController _servo;
    private static final double _upPosition = 0.26;
    private static final double _downPosition = 0.0;

    private double _actuationTime;

    public ScooperController(Servo servo) {
        _servo = new SmartServoController(servo);
    }

    public void ScoopUp(double actuationTime){
        if (_actuationTime == 0)
            actuationTime += 500;

        _actuationTime = actuationTime;
        _servo.MoveTo(_upPosition, _actuationTime);
    }

    public void ScoopDown(double actuationTime){
        _actuationTime = actuationTime;
        _servo.MoveTo(_downPosition, _actuationTime);
    }

    public boolean IsActuationComplete(){
        return _servo.IsFinished();
    }
}
