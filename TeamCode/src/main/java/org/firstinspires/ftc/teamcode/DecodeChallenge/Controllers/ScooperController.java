package org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers;

import com.qualcomm.robotcore.hardware.Servo;

public class ScooperController {
    private final Servo _servo;
    private double _upPosition = 0.25;
    private double _downPosition = 0.0;


    public ScooperController(Servo servo) {
        _servo = servo;
    }

    public void up() {
        _servo.setPosition(_upPosition);
    }

    public void down() {
        _servo.setPosition(_downPosition);
    }

    public void setPosition(double position) {
        _servo.setPosition(position);
    }

    public double getPosition() {
        return _servo.getPosition();
    }

    public void setUpPosition(double position) {
        _upPosition = position;
    }

    public void setDownPosition(double position) {
        _downPosition = position;
    }
}
