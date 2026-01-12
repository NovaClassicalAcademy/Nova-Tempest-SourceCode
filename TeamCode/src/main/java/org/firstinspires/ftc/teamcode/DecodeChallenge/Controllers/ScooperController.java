package org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Utilities.TimedAction;

public class ScooperController {

    private final SmartServoController _servo;
    private final double _actuationTime;

    private static final double _upPosition = 0.25;
    private static final double _downPosition = 0.0;

    public ScooperController(Servo servo, double avgActuationTime) {
        _servo = new SmartServoController(servo);
        _actuationTime = avgActuationTime;
    }

    public void ScoopUp(){
        _servo.MoveTo(_upPosition, _actuationTime);
    }

    public void ScoopDown(){
        _servo.MoveTo(_downPosition, _actuationTime);
    }

    public boolean IsBusy(){
        return !_servo.IsFinished();
    }
}
