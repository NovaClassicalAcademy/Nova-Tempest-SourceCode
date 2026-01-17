package org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers;

import com.qualcomm.robotcore.hardware.CRServo;

public class IntakeController {
    private final CRServo _upperLeft;
    private final CRServo _upperRight;
    private final CRServo _lowerLeft;
    private final CRServo _lowerRight;
    private double _maxPower = 1.0;
    private double _minPower = 0.0;

    public IntakeController(CRServo upperLeft, CRServo upperRight, CRServo lowerLeft, CRServo lowerRight) {
        _upperLeft = upperLeft;
        _upperRight = upperRight;
        _lowerLeft = lowerLeft;
        _lowerRight = lowerRight;
    }

    public void Activate() {
        _upperLeft.setPower(_maxPower);
        _upperRight.setPower(_maxPower);
        _lowerLeft.setPower(_maxPower);
        _lowerRight.setPower(_maxPower);
    }

    public void Deactivate() {
        _upperLeft.setPower(_minPower);
        _upperRight.setPower(_minPower);
        _lowerLeft.setPower(_minPower);
        _lowerRight.setPower(_minPower);
    }

    public void SetMaxPower(double newMax){
        _maxPower = newMax;
    }

    public void SetMinPower(double newMin){
        _minPower = newMin;
    }
}
