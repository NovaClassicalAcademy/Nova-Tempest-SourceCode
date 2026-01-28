package org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers;

import com.qualcomm.robotcore.hardware.Servo;

public class ScooperController {

    private final SmartServoController _servo;
    private static final double _upPosition = 0.26;
    private static final double _downPosition = 0.0;

    public ScooperController(Servo servo) {
        _servo = new SmartServoController(servo);
    }

    public void ScoopUp(){
        _servo.MoveTo(_upPosition);
    }

    public void ScoopDown(){
        _servo.MoveTo(_downPosition);
    }
}
