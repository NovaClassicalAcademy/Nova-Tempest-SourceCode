package org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers;

import com.qualcomm.robotcore.hardware.Servo;

public class ScooperController {
    private final Servo _servo;
    private double _upPosition = 0.25;
    private double _downPosition = 0.0;
    private double _tolerance = 0.005;


    public ScooperController(Servo servo) {
        _servo = servo;
    }

    public void ScoopUp() {
        _servo.setPosition(_upPosition);
    }

    public void ScoopDown() {
        _servo.setPosition(_downPosition);
    }

    public void SetPosition(double position) {
        _servo.setPosition(position);
    }

    public double GetPosition() {
        return _servo.getPosition();
    }

    public void SetUpPosition(double position) {
        _upPosition = position;
    }

    public void SetDownPosition(double position) {
        _downPosition = position;
    }

    public boolean IsUp(){
        return _upPosition - GetPosition() <= _tolerance;
    }

    public boolean IsDown(){
        return  _downPosition - GetPosition() <= _tolerance;
    }
}
