package org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class SmartServoController {
    private final Servo _servo;
    private final ElapsedTime _timer = new ElapsedTime();

    private double _lastTargetPosition = -1;
    private double _moveDurationMs = 0;

    public SmartServoController(Servo servo){
        _servo = servo;
    }

    public void MoveTo(double targetPosition, double avgActionTimeMilliseconds){
        if (Math.abs(_lastTargetPosition - targetPosition) > 0.001){
            _servo.setPosition(targetPosition);
            _lastTargetPosition = targetPosition;
            _moveDurationMs = avgActionTimeMilliseconds;
            _timer.reset();
        }
    }

    public boolean IsFinished(){
        return  _timer.milliseconds() > _moveDurationMs;
    }
}
