package org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LaunchController {

    private final DcMotorEx _motor;
    private final Telemetry _telemetry;
    private final double _rpmTolerance;
    private final double _targetVelocity;

    public LaunchController(Telemetry telemetry, DcMotorEx motor, double targetVelocity) {
        _telemetry = telemetry;
        _motor = motor;
        _motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        _targetVelocity = targetVelocity;
        _rpmTolerance = Range.clip(_targetVelocity * 0.1, 0, 100);
    }
    public void Start(){
        _motor.setVelocity(_targetVelocity);
    }

    public void Stop(){
        _motor.setVelocity(0.0);
    }

    public boolean IsAtFullSpeed(){
        double currentVelocity = _motor.getVelocity();
        _telemetry.addData("Goat Speed", currentVelocity);

        return Math.abs(_targetVelocity - currentVelocity) < _rpmTolerance;
    }
}
